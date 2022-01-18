package fhv.teamd.hotel.application.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import fhv.teamd.hotel.domain.Season;

import java.time.Month;
import java.util.Map;
import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SeasonPriceDTO {

    private String name;
    private Month from;
    private Month to;
    private Double price;

    public static SeasonPriceDTO fromSeasonAndPrice(Season season, Double price) {
        SeasonPriceDTO seasonDTO = new SeasonPriceDTO();

        seasonDTO.name = season.name();
        seasonDTO.from = season.from();
        seasonDTO.to = season.to();
        seasonDTO.price = price;

        return seasonDTO;
    }

    public String name() {
        return this.name;
    }

    public Month from() {
        return this.from;
    }

    public Month to() {
        return this.to;
    }

    public Double price() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SeasonPriceDTO that = (SeasonPriceDTO) o;
        return Objects.equals(this.name, that.name) && this.from == that.from && this.to == that.to && Objects.equals(this.price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.from, this.to, this.price);
    }
}
