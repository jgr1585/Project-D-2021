package fhv.teamd.hotel.application.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import fhv.teamd.hotel.domain.Season;

import java.time.Month;
import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SeasonDTO {
    private String name;
    private Month from;
    private Month to;

    public static SeasonDTO fromSeason(Season season) {
        SeasonDTO seasonDTO = new SeasonDTO();

        seasonDTO.name = season.name();
        seasonDTO.from = season.from();
        seasonDTO.to = season.to();

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


    @Override
    public String toString() {
        return "SeasonDTO{" +
                "name='" + this.name + '\'' +
                ", from=" + this.from +
                ", to=" + this.to +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SeasonDTO seasonDTO = (SeasonDTO) o;
        return Objects.equals(this.name, seasonDTO.name) && this.from == seasonDTO.from && this.to == seasonDTO.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.from, this.to);
    }
}
