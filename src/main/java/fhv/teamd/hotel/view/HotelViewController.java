package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.RoomAssignmentService;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.view.forms.BookingListForm;
import fhv.teamd.hotel.view.forms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.PersonalDetailsForm;
import fhv.teamd.hotel.view.forms.RoomAssignmentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HotelViewController {

    private static final Period defaultBookingLeadTime = Period.ofWeeks(2);
    private static final Period defaultStayDuration = Period.ofWeeks(1);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomAssignmentService roomAssignmentService;

    @GetMapping("/")
    public ModelAndView index(Model model) {

        return new ModelAndView("index");
    }

    @GetMapping("/booking/bookingOverview")
    public ModelAndView bookingOverview(
            @ModelAttribute BookingListForm form,
            Model model) {

        List<BookingDTO> bookings = this.bookingService.getAll();

        model.addAttribute("form", form);
        model.addAttribute("bookings", bookings);

        return new ModelAndView("/booking/bookingOverview");
    }

    @GetMapping("/booking/chooseCategories")
    public ModelAndView bookingChooseCategories(Model model) {

        LocalDate defaultStartDate = LocalDate.now().plus(defaultBookingLeadTime);
        LocalDate defaultEndDate = defaultStartDate.plus(defaultStayDuration);

        List<AvailableCategoryDTO> categories = this.categoryService.getAvailableCategories(
                defaultStartDate.atStartOfDay(),
                defaultEndDate.atStartOfDay());

        Map<String, Integer> defaultValues
                = categories.stream().collect(Collectors.toMap(AvailableCategoryDTO::categoryId, cat -> 0));

        ChooseCategoriesForm chooseCategoriesForm
                = new ChooseCategoriesForm(defaultStartDate, defaultEndDate, defaultValues);

        model.addAttribute("categories", categories);
        model.addAttribute("chooseCategoriesForm", chooseCategoriesForm);

        return new ModelAndView("/booking/chooseCategories");

    }

    @PostMapping("/booking/chooseCategories")
    public ModelAndView bookingSubmitCategories(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            Model model,
            HttpServletResponse response) throws IOException {

        // todo: check availability and other validations
        // todo: validation should be elsewhere

        LocalDate from = chooseCategoriesForm.getFrom();
        LocalDate until = chooseCategoriesForm.getUntil();

        boolean valid = from.isAfter(LocalDate.now()) && from.isBefore(until);


        if (!valid) {
            // todo add an error message obj to the model
            response.sendRedirect("/booking/chooseCategories");
        }



        return this.bookingPersonalDetails(chooseCategoriesForm, model);
    }

    @GetMapping("/booking/personalDetails")
    public ModelAndView bookingPersonalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm, // keep first step of the 2-part form
            Model model) {


        model.addAttribute("personalDetailsForm", new PersonalDetailsForm());


        return new ModelAndView("/booking/personalDetails");
    }

    @GetMapping("/booking/bookingSummary")
    public ModelAndView bookingSummary(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model,
            HttpServletResponse response) {

        List<CategoryDTO> categories = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : chooseCategoriesForm.getCategorySelection().entrySet()) {
            String categoryId = entry.getKey();
            Integer amount = entry.getValue();

            Optional<CategoryDTO> result = this.categoryService.findCategoryById(categoryId);

            if(amount > 0 && result.isPresent()) {
                categories.add(result.get());
            }
        }

        model.addAttribute("categories", categories);

        return new ModelAndView("/booking/bookingSummary");
    }

    @PostMapping("/booking/submitPersonalDetails")
    public ModelAndView submitPersonalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model,
            HttpServletResponse response) {

        return this.bookingSummary(chooseCategoriesForm, personalDetailsForm, model, response);
    }

    @PostMapping("/booking/submit")
    public void submitBooking(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model,
            HttpServletResponse response) throws IOException {

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

        RepresentativeDetails rep = new RepresentativeDetails(
                personalDetailsForm.getRepresentativeFirstName(),
                personalDetailsForm.getRepresentativeLastName(),
                personalDetailsForm.getRepresentativeMail(),
                new Address(
                    personalDetailsForm.getRepresentativeStreet(),
                    personalDetailsForm.getRepresentativeZip(),
                    personalDetailsForm.getRepresentativeCity(),
                    personalDetailsForm.getRepresentativeCountry()),
                personalDetailsForm.getRepresentativePhone());

        this.bookingService.book(
                chooseCategoriesForm.getCategorySelection(),
                chooseCategoriesForm.getFrom().atStartOfDay(),
                chooseCategoriesForm.getUntil().atStartOfDay(),
                guest, rep
        );

        response.sendRedirect("/booking/bookingOverview");
    }




    @GetMapping("/checkIn/chooseCategories")
    public ModelAndView checkInChooseCategories(
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

    @PostMapping("/checkIn/chooseCategories")
    public RedirectView checkInSubmitCategories(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            RedirectAttributes redirAttributes) {

        redirAttributes.addFlashAttribute("chooseCategoriesForm", chooseCategoriesForm);

        return new RedirectView("/checkIn/personalDetails");
    }

    @GetMapping("/checkIn/personalDetails")
    public ModelAndView checkInPersonalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            Model model) {

        model.addAttribute("personalDetailsForm", new PersonalDetailsForm());

        return new ModelAndView("/checkIn/personalDetails");
    }

    @PostMapping("/checkIn/submitPersonalDetails")
    public RedirectView checkInSubmitPersonalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            RedirectAttributes redirAttributes) {

        redirAttributes.addFlashAttribute("chooseCategoriesForm", chooseCategoriesForm);
        redirAttributes.addFlashAttribute("personalDetailsForm", personalDetailsForm);

        return new RedirectView("/checkIn/roomAssignment");
    }

    @GetMapping("/checkIn/roomAssignment")
    public ModelAndView checkInRoomAssignment(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model) {

        RoomAssignmentForm assignmentForm = new RoomAssignmentForm();
        List<CategoryDTO> categories = new ArrayList<>();

        for(Map.Entry<String, Integer> entry: chooseCategoriesForm.getCategorySelection().entrySet()) {

            String categoryId = entry.getKey();
            Integer amount = entry.getValue();

            if(amount > 0) {

                List<RoomDTO> rooms = this.roomAssignmentService.findSuitableRooms(categoryId, amount);
                List<String> roomIds = rooms.stream().map(RoomDTO::id).collect(Collectors.toList());

                assignmentForm.getCategoriesAndRooms().put(categoryId, roomIds);

                categories.add(this.categoryService.findCategoryById(categoryId).get());
            }

        }


        model.addAttribute("roomAssignmentForm", assignmentForm);
        model.addAttribute("categories", categories);

        return new ModelAndView("/checkIn/roomAssignment");
    }
}
