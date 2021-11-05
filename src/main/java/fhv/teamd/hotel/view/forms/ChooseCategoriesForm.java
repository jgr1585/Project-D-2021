package fhv.teamd.hotel.view.forms;

import fhv.teamd.hotel.application.dto.BookableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.domain.Category;

import java.time.LocalDate;
import java.util.Map;

public class ChooseCategoriesForm {

    // todo entspaghettifizierung

    public static class CategoryField {
        private BookableCategoryDTO category;
        private int selectedAmount;

        public CategoryField(BookableCategoryDTO category, int selectedAmount) {
            this.category = category;
            this.selectedAmount = selectedAmount;
        }

        public CategoryField() {
            // thymeleaf
        }

        public BookableCategoryDTO getCategory() {
            return this.category;
        }

        public void setCategory(BookableCategoryDTO category) {
            this.category = category;
        }

        public int getSelectedAmount() {
            return this.selectedAmount;
        }

        public void setSelectedAmount(int selectedAmount) {
            this.selectedAmount = selectedAmount;
        }
    }

    private LocalDate from;
    private LocalDate until;

    // need a string index (-> id) for thymeleaf.
    private Map<String, CategoryField> categorySelection;

    public ChooseCategoriesForm() {
        // required by spring/thymeleaf
    }

    public ChooseCategoriesForm(LocalDate from, LocalDate until, Map<String, CategoryField> categorySelection) {
        this.from = from;
        this.until = until;
        this.categorySelection = categorySelection;
    }

    public LocalDate getFrom() {
        return this.from;
    }

    public void setFromDate(LocalDate from) {
        this.from = from;
    }

    public void setFrom(String from) {
        this.setFromDate(LocalDate.parse(from));
    }

    public LocalDate getUntil() {
        return this.until;
    }

    public void setUntilDate (LocalDate until) {
        this.until = until;
    }

    public void setUntil(String until) {
        this.setUntilDate(LocalDate.parse(until));
    }


    public Map<String, CategoryField> getCategorySelection() {
        return this.categorySelection;
    }

    public void setCategorySelection(Map<String, CategoryField> categorySelection) {
        this.categorySelection = categorySelection;
    }
}
