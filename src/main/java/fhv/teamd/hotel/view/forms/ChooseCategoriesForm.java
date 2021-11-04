package fhv.teamd.hotel.view.forms;

import java.time.LocalDate;
import java.util.Map;

public class ChooseCategoriesForm {

    private LocalDate from;
    private LocalDate until;

    private Map<String, Integer> categorySelection;

    public ChooseCategoriesForm() {
        // required by spring/thymeleaf
    }

    public ChooseCategoriesForm(LocalDate from, LocalDate until, Map<String, Integer> categorySelection) {
        this.from = from;
        this.until = until;
        this.categorySelection = categorySelection;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getUntil() {
        return until;
    }

    public void setUntil(LocalDate until) {
        this.until = until;
    }

    public int getCountForCategory(String category) {
        return this.categorySelection.get(category);
    }

    public void setCountForCategory(String category, int count) {
        this.categorySelection.put(category, count);
    }
}
