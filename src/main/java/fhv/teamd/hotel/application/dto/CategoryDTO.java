package fhv.teamd.hotel.application.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.Season;

import java.util.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CategoryDTO {

    private String id;
    private String title;
    private String description;
    private List<SeasonPriceDTO> priceList;

    @JsonIgnore
    private Map<SeasonDTO, Double> price;

    public String id() {
        return this.id;
    }

    public String title() {
        return this.title;
    }

    public String description() {
        return this.description;
    }

    public List<SeasonPriceDTO> priceList() {
        return this.priceList;
    }

    private CategoryDTO() {
    }

    public CategoryDTO(String id, String title, String description, Map<Season, Double> price) {
        this.id = id;
        this.title = title;
        this.description = description;

        this.price = new HashMap<>();
        this.priceList = new LinkedList<>();
        price.forEach((season, aDouble) -> {
            this.price.put(SeasonDTO.fromSeason(season), aDouble);
            this.priceList.add(SeasonPriceDTO.fromSeasonAndPrice(season, aDouble));
        });
    }

    public static CategoryDTO fromCategory(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.id = category.categoryId() == null ? null : category.categoryId().toString();
        categoryDTO.description = category.description();
        categoryDTO.title = category.title();


        Map<SeasonDTO, Double> price = new HashMap<>();
        List<SeasonPriceDTO> seasonPriceDTOs = new LinkedList<>();

        category.pricePerSeason().forEach((season, aDouble) -> {
            price.put(SeasonDTO.fromSeason(season), aDouble);
            seasonPriceDTOs.add(SeasonPriceDTO.fromSeasonAndPrice(season, aDouble));
        });

        categoryDTO.price = price;
        categoryDTO.priceList = seasonPriceDTOs;


        return categoryDTO;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.title, that.title) && Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.description, this.price);
    }
}
