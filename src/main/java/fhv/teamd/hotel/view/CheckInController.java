package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.RoomAssignmentService;
import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.application.dto.RoomDTO;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
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
            @ModelAttribute CheckInForm checkInForm,
            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            @RequestParam(required = false) String action,
            Model model) {

        ChooseCategoriesForm chooseCategoriesForm = checkInForm.getChooseCategoriesForm();

        if(!"prev".equals(action)) {
            LocalDate defaultCheckIn = LocalDate.now();
            LocalDate defaultCheckOut = defaultCheckIn.plus(defaultStayDuration);

            chooseCategoriesForm.setFrom(defaultCheckIn);
            chooseCategoriesForm.setUntil(defaultCheckOut);
        }

        List<AvailableCategoryDTO> categories
                = this.categoryService.getAvailableCategories(
                chooseCategoriesForm.getFrom().atStartOfDay(),
                chooseCategoriesForm.getUntil().atStartOfDay());

        checkInForm.setChooseCategoriesForm(chooseCategoriesForm);

        model.addAttribute("categories", categories);
        model.addAttribute("checkInForm", checkInForm);

        return new ModelAndView("/checkIn/chooseCategories");
    }

    @PostMapping("chooseCategories")
    public RedirectView submitCategories(
            @ModelAttribute CheckInForm checkInForm,

//            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
//            @ModelAttribute PersonalDetailsForm personalDetailsForm,
//            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("checkInForm", checkInForm);
//        redirectAttributes.addFlashAttribute("chooseCategoriesForm", chooseCategoriesForm);
//        redirectAttributes.addFlashAttribute("personalDetailsForm", personalDetailsForm);
//        redirectAttributes.addFlashAttribute("roomAssignmentForm", roomAssignmentForm);

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
//            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
//            @ModelAttribute PersonalDetailsForm personalDetailsForm,
//            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("checkInForm", checkInForm);
//        redirectAttributes.addFlashAttribute("chooseCategoriesForm", chooseCategoriesForm);
//        redirectAttributes.addFlashAttribute("personalDetailsForm", personalDetailsForm);
//        redirectAttributes.addFlashAttribute("roomAssignmentForm", roomAssignmentForm);

        if(action.equals("prev")) {
            return new RedirectView("chooseCategories");
        }

        return new RedirectView("roomAssignment");
    }

    @GetMapping("roomAssignment")
    public ModelAndView roomAssignment(
            @ModelAttribute CheckInForm checkInForm,
//            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
//            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            Model model) {

        List<CategoryDTO> categories = new ArrayList<>();

        checkInForm.getChooseCategoriesForm().getCategorySelection().forEach((categoryId, amount) -> {

            if (amount != null && amount > 0) {

                List<RoomDTO> rooms = this.roomAssignmentService.findSuitableRooms(categoryId, amount);
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
//            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
//            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            @ModelAttribute RoomAssignmentForm roomAssignmentForm,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("checkInForm", checkInForm);
//        redirectAttributes.addFlashAttribute("chooseCategoriesForm", chooseCategoriesForm);
//        redirectAttributes.addFlashAttribute("personalDetailsForm", personalDetailsForm);
//        redirectAttributes.addFlashAttribute("roomAssignmentForm", roomAssignmentForm);

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
                personalDetailsForm.getRepresentativePhone()
        );

        try {
            this.frontDeskService.checkIn(roomIds, duration, guest, representative);
        } catch (Exception x) {
            x.printStackTrace();
        }

        return new RedirectView("/");
    }

}
