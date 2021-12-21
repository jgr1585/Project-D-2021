package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Season;

import java.time.Month;
import java.util.Objects;

public class SeasonDTO {
    private String id;
    private String name;
    private Month from;
    private Month to;

    public static SeasonDTO fromSeason(Season season) {
        SeasonDTO seasonDTO = new SeasonDTO();

        seasonDTO.id = season.seasonId() == null ? null : season.seasonId().toString();
        seasonDTO.name = season.name();
        seasonDTO.from = season.from();
        seasonDTO.to = season.to();

        return seasonDTO;
    }

    public String id() {
        return this.id;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SeasonDTO seasonDTO = (SeasonDTO) o;
        return Objects.equals(this.id, seasonDTO.id) && Objects.equals(this.name, seasonDTO.name) && this.from == seasonDTO.from && this.to == seasonDTO.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.from, this.to);
    }
}
