package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Season;
import fhv.teamd.hotel.domain.repositories.SeasonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SeasonRepositoryTest {

    @Autowired
    private SeasonRepository seasonRepository;

    @Test
    void given_season_when_getDate_return_Month() {
        List<Season> seasons = BaseRepositoryData.seasons();

        seasons.forEach(season -> {
            Month month = season.from();

            while (month != season.to()) {
                Assertions.assertEquals(season, this.seasonRepository.getSeasonFromMonth(month));
                month = month.plus(1);
            }
        });

        Arrays.stream(Month.values()).forEach(month -> {
            Assertions.assertNotNull(this.seasonRepository.getSeasonFromMonth(month));
        });
    }
}
