package fhv.teamd.hotel.view.forms;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ChooseCategoriesForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate from;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate until;

    private Map<String, Integer> categorySelection;

    public ChooseCategoriesForm() {
        // required by spring/thymeleaf
        this.categorySelection = new HashMap<>();
    }

    public ChooseCategoriesForm(LocalDate from, LocalDate until, Map<String, Integer> categorySelection) {
        this.from = from;
        this.until = until;
        this.categorySelection = categorySelection;
    }

    public LocalDate getFrom() {
        return this.from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getUntil() {
        return this.until;
    }

    public void setUntil (LocalDate until) {
        this.until = until;
    }


    public Map<String, Integer> getCategorySelection() {
        return this.categorySelection;
    }

    public void setCategorySelection(Map<String, Integer> categorySelection) {
        this.categorySelection = categorySelection;
    }
}
