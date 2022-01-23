package fhv.teamd.hotel.rest;

import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    @ResponseBody
    public List<CategoryDTO> allCategories() {
        return this.categoryService.getAll();
    }

    @GetMapping("/available")
    @ResponseBody
    private List<AvailableCategoryDTO> availableCategory(@RequestParam LocalDate from, @RequestParam LocalDate until) {
        return this.categoryService.getAvailableCategories(from.atStartOfDay(), until.atStartOfDay());
    }
}
