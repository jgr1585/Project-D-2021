package fhv.teamd.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@GetMapping("/booking/chooseCategories")
	public ModelAndView createBooking() {
		return new ModelAndView("booking/chooseCategories");
	}

	@GetMapping("/booking/personalDetails")
	public ModelAndView personalDetails() {
		return new ModelAndView("booking/personalDetails");
	}

}
