package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.SeasonId;

import java.time.Month;
import java.util.Objects;

public class Season {
    private Long id;
    private SeasonId domainId;

    private String name;
    private Month from;
    private Month to;

    protected Season() {
        //hibernate
    }

    //Only for Testing
    @Deprecated
    public Season(Long id, SeasonId domainId, String name, Month from, Month to) {
        this.id = id;
        this.domainId = domainId;
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public Long id() {
        return this.id;
    }

    public SeasonId seasonId() {
        return this.domainId;
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
        final Season season = (Season) o;
        return Objects.equals(this.id, season.id) && Objects.equals(this.domainId, season.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.domainId);
    }
}
