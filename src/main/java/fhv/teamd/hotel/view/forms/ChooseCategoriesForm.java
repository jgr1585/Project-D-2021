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

    public void setFromDate(LocalDate from) {
        this.from = from;
    }

    public void setFrom(String from) {
        this.setFromDate(LocalDate.parse(from));
    }

    public LocalDate getUntil() {
        return until;
    }

    public void setUntilDate (LocalDate until) {
        this.until = until;
    }

    public void setUntil(String until) {
        this.setUntilDate(LocalDate.parse(until));
    }


    // do not 'fix'
    public Map<String, Integer> getCategorySelection() {
        return categorySelection;
    }

    public void setCategorySelection(Map<String, Integer> categorySelection) {
        this.categorySelection = categorySelection;
    }
}
