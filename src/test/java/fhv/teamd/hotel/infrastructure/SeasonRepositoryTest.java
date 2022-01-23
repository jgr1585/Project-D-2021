package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Season;
import fhv.teamd.hotel.domain.repositories.SeasonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SeasonRepositoryTest {

    @Autowired
    private SeasonRepository seasonRepository;

    @Test
    void given_season_when_getDate_return_Month() {
        List<Season> seasons = BaseRepositoryData.seasons();

        seasons.forEach(season -> {
            Month month;
            int i = season.to().getValue() > season.from().getValue() ? season.from().getValue() : season.from().getValue() - 12;

            while (i <= season.to().getValue()) {
                month = i > 0 ? Month.of(i) : Month.of(i + 12);
                Season actual = this.seasonRepository.getSeasonFromMonth(month);
                Assertions.assertEquals(season, actual);
                i++;
            }
        });

        Arrays.stream(Month.values()).forEach(
                month -> Assertions.assertNotNull(this.seasonRepository.getSeasonFromMonth(month))
        );
    }
}
