package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.RoomAssignmentService;
import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.application.dto.RoomDTO;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.view.forms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.PersonalDetailsForm;
import fhv.teamd.hotel.view.forms.RoomAssignmentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Duration;
import java.time.LocalDate;
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
    private RoomAssignmentService roomAssignmentService;

    @Autowired
    private FrontDeskService frontDeskService;


    @GetMapping("chooseCategories")
    public ModelAndView chooseCategories(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            Model model) {

        LocalDate defaultCheckIn = LocalDate.now();
        LocalDate defaultCheckOut = defaultCheckIn.plus(defaultStayDuration);

        List<AvailableCategoryDTO> categories
                = this.categoryService.getAvailableCategories(
                defaultCheckIn.atStartOfDay(),
                defaultCheckOut.atStartOfDay());

        Map<String, Integer> defaultValues
                = categories.stream().collect(Collectors.toMap(AvailableCategoryDTO::categoryId, cat -> 0));

        chooseCategoriesForm.setFrom(defaultCheckIn);
        chooseCategoriesForm.setUntil(defaultCheckOut);
        chooseCategoriesForm.setCategorySelection(defaultValues);

        model.addAttribute("categories", categories);

        return new ModelAndView("/checkIn/chooseCategories");
    }

    @PostMapping("chooseCategories")
    public RedirectView submitCategories(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            RedirectAttributes redirAttributes) {

        redirAttributes.addFlashAttribute("chooseCategoriesForm", chooseCategoriesForm);

        return new RedirectView("/checkIn/personalDetails");
    }

    @GetMapping("personalDetails")
    public ModelAndView personalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            Model model) {

        model.addAttribute("personalDetailsForm", new PersonalDetailsForm());

        return new ModelAndView("/checkIn/personalDetails");
    }

    @PostMapping("personalDetails")
    public RedirectView submitPersonalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            RedirectAttributes redirAttributes) {

        redirAttributes.addFlashAttribute("chooseCategoriesForm", chooseCategoriesForm);
        redirAttributes.addFlashAttribute("personalDetailsForm", personalDetailsForm);

        return new RedirectView("/checkIn/roomAssignment");
    }

    @GetMapping("roomAssignment")
    public ModelAndView roomAssignment(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model) {

        RoomAssignmentForm roomAssignmentForm = new RoomAssignmentForm();
        List<CategoryDTO> categories = new ArrayList<>();

        chooseCategoriesForm.getCategorySelection().forEach((categoryId, amount) -> {

            if (amount > 0) {

                List<RoomDTO> rooms = this.roomAssignmentService.findSuitableRooms(categoryId, amount);
                List<String> roomIds = rooms.stream().map(RoomDTO::id).collect(Collectors.toList());

                roomAssignmentForm.getCategoriesAndRooms().put(categoryId, roomIds);
                categories.add(this.categoryService.findCategoryById(categoryId).get());

            }

        });


        model.addAttribute("roomAssignmentForm", roomAssignmentForm);
        model.addAttribute("categories", categories);

        return new ModelAndView("/checkIn/roomAssignment");
    }

    @PostMapping("roomAssignment")
    public ModelAndView submitRoomAssignment(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            Model model) {

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
                personalDetailsForm.getRepresentativePhone()
        );

        try {
            this.frontDeskService.checkIn(roomIds, duration, guest, representative);
        } catch (Exception x) {
            x.printStackTrace();
        }

        return new ModelAndView("redirect:/");
    }

}
