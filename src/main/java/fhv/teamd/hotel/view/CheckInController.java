package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.RoomSuggestionService;
import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.application.dto.RoomDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.application.exceptions.OccupiedRoomException;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.exceptions.CannotCheckinException;
import fhv.teamd.hotel.view.forms.CheckInForm;
import fhv.teamd.hotel.view.forms.subForms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.subForms.PersonalDetailsForm;
import fhv.teamd.hotel.view.forms.subForms.RoomAssignmentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("checkIn")
public class CheckInController {

    private static final Period defaultBookingLeadTime = Period.ofWeeks(2);
    private static final Period defaultStayDuration = Period.ofWeeks(1);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RoomSuggestionService roomSuggestionService;

    @Autowired
    private FrontDeskService frontDeskService;


    @GetMapping("chooseCategories")
    public ModelAndView chooseCategories(
            @ModelAttribute CheckInForm checkInForm,
            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            @RequestParam(required = false) String action,
            Model model) {

        ChooseCategoriesForm chooseCategoriesForm = checkInForm.getChooseCategoriesForm();

        if(chooseCategoriesForm.getFrom() == null) {
            chooseCategoriesForm.setFrom(LocalDate.now());
        }

        if(chooseCategoriesForm.getUntil() == null) {
            LocalDate defaultCheckOut
                    = chooseCategoriesForm.getFrom().plus(defaultStayDuration);
            chooseCategoriesForm.setUntil(defaultCheckOut);
        }

        List<AvailableCategoryDTO> categories
                = this.categoryService.getAvailableCategories(
                chooseCategoriesForm.getFrom().atStartOfDay(),
                chooseCategoriesForm.getUntil().atStartOfDay());

        model.addAttribute("categories", categories);
        model.addAttribute("checkInForm", checkInForm);

        return new ModelAndView("/checkIn/chooseCategories");
    }

    @PostMapping("chooseCategories")
    public RedirectView submitCategories(
            @ModelAttribute CheckInForm checkInForm,
            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("checkInForm", checkInForm);

        return new RedirectView("personalDetails");
    }

    @GetMapping("personalDetails")
    public ModelAndView personalDetails(
            @ModelAttribute CheckInForm checkInForm,
            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            Model model) {

        model.addAttribute("checkInForm", checkInForm);

        return new ModelAndView("/checkIn/personalDetails");
    }

    @PostMapping("personalDetails")
    public RedirectView submitPersonalDetails(
            @ModelAttribute CheckInForm checkInForm,
            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("checkInForm", checkInForm);

        if(action.equals("prev")) {
            // getMapping uses this too
            redirectAttributes.addAttribute("action", "prev");
            return new RedirectView("chooseCategories");
        }

        return new RedirectView("roomAssignment");
    }

    @GetMapping("roomAssignment")
    public ModelAndView roomAssignment(
            @ModelAttribute CheckInForm checkInForm,
            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            Model model) {

        List<CategoryDTO> categories = new ArrayList<>();

        ChooseCategoriesForm chooseCategoriesForm = checkInForm.getChooseCategoriesForm();

        LocalDateTime from = chooseCategoriesForm.getFrom().atStartOfDay();
        // at end of check out day
        LocalDateTime until = chooseCategoriesForm.getUntil().atStartOfDay().plus(Period.ofDays(1));


        chooseCategoriesForm.getCategorySelection().forEach((categoryId, amount) -> {

            if (amount != null && amount > 0) {

                List<RoomDTO> rooms = this.roomSuggestionService.findSuitableRooms(categoryId, from, until, amount);
                List<String> roomIds = rooms.stream().map(RoomDTO::id).collect(Collectors.toList());

                roomAssignmentForm.getCategoriesAndRooms().put(categoryId, roomIds);
                categories.add(this.categoryService.findCategoryById(categoryId).get());

            }

        });

        checkInForm.setRoomAssignmentForm(roomAssignmentForm);

        model.addAttribute("categories", categories);
        model.addAttribute("checkInForm", checkInForm);

        return new ModelAndView("/checkIn/roomAssignment");
    }

    @PostMapping("roomAssignment")
    public RedirectView submitRoomAssignment(
            @ModelAttribute CheckInForm checkInForm,
            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("checkInForm", checkInForm);

        if(action.equals("prev")) {
            return new RedirectView("personalDetails");
        }

        ChooseCategoriesForm chooseCategoriesForm = checkInForm.getChooseCategoriesForm();
        PersonalDetailsForm personalDetailsForm = checkInForm.getPersonalDetailsForm();

        List<String> roomIds = new ArrayList<>();

        Collection<List<String>> roomGroups = roomAssignmentForm.getCategoriesAndRooms().values();
        for(List<String> roomGroup: roomGroups) {
            roomIds.addAll(roomGroup);
        }

        Duration duration = Duration.between(
                chooseCategoriesForm.getFrom().atStartOfDay(),
                chooseCategoriesForm.getUntil().atStartOfDay());

        GuestDetails guest = new GuestDetails(
                personalDetailsForm.getGuestFirstName(),
                personalDetailsForm.getGuestLastName(),
                new Address(
                        personalDetailsForm.getGuestStreet(),
                        personalDetailsForm.getGuestZip(),
                        personalDetailsForm.getGuestCity(),
                        personalDetailsForm.getGuestCountry()
                )
        );

        RepresentativeDetails representative = new RepresentativeDetails(
                personalDetailsForm.getRepresentativeFirstName(),
                personalDetailsForm.getRepresentativeLastName(),
                personalDetailsForm.getRepresentativeMail(),
                new Address(
                        personalDetailsForm.getRepresentativeStreet(),
                        personalDetailsForm.getRepresentativeZip(),
                        personalDetailsForm.getRepresentativeCity(),
                        personalDetailsForm.getRepresentativeCountry()),
                personalDetailsForm.getRepresentativePhone(),
                personalDetailsForm.getRepresentativeCreditCardNumber(),
                personalDetailsForm.getRepresentativePaymentMethod());

        String bookingId = checkInForm.getBookingId();

        try {

            if(bookingId == null || bookingId.length() == 0) {
                this.frontDeskService.checkInWalkInGuest(
                        roomIds,
                        duration,
                        guest,
                        representative);
            }
            else {
                this.frontDeskService.checkInWithBooking(
                        roomIds, duration,
                        guest, representative,
                        bookingId);
            }

        } catch (InvalidIdException | CannotCheckinException x) {
            x.printStackTrace();
        } catch (OccupiedRoomException x) {
            redirectAttributes.addFlashAttribute("error", x.getMessage());
            return new RedirectView("roomAssignment");
        }

        return new RedirectView("/");
    }

}
