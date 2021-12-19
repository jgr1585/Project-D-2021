package fhv.teamd.hotel.domain;

import java.time.Month;

public enum Season {
    Summer(Month.MAY, Month.OCTOBER),
    Winter(Month.NOVEMBER, Month.APRIL);

    private final Month from;
    private final Month to;

    Season(Month from, Month to) {
        this.from = from;
        this.to = to;
    }

    public static Season getSeasonFromMonth(Month month) {
        if (month.getValue() >= 5 && month.getValue() <= 10) {
            return Season.Summer;
        } else {
            return Season.Winter;
        }
    }

    public Month from() {
        return this.from;
    }

    public Month to() {
        return this.to;
    }
}
