package fhv.teamd.hotel.rest;

import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.DomainFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private CategoryService categoryService;

    private StringBuilder localAddress;

    @SuppressWarnings("HttpUrlsUsage")
    @BeforeEach
    void init() {
        this.localAddress = new StringBuilder();
        this.localAddress.append("http://");
        this.localAddress.append("localhost:");
        this.localAddress.append(this.port);
        this.localAddress.append("/rest/category");
    }

    @Test
    void given_none_when_get_all_Categories_then_return_Categories() {
        final List<CategoryDTO> categoryDTOs = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            categoryDTOs.add(CategoryDTO.fromCategory(DomainFactory.createCategory()));
        }
        this.localAddress.append("/all");

        Mockito.when(this.categoryService.getAll()).thenReturn(categoryDTOs);


        final ResponseEntity<CategoryDTO[]> res = this.restTemplate.getForEntity(this.localAddress.toString(), CategoryDTO[].class);
        final List<CategoryDTO> actual = Arrays.asList(Objects.requireNonNull(res.getBody()));


        Assertions.assertTrue(categoryDTOs.containsAll(actual) && actual.containsAll(categoryDTOs));
    }

    @Test
    void given_from_until_get_available_Categories_then_return_Categories() {
        final List<AvailableCategoryDTO> availableCategory = new LinkedList<>();
        final LocalDate from = LocalDate.now();
        final LocalDate until = LocalDate.now().plusDays(1);
        final Map<String, LocalDate> param = Map.of("from", from, "until", until);

        for (int i = 0; i < 10; i++) {
            Category cat = DomainFactory.createCategory();
            availableCategory.add(new AvailableCategoryDTO(cat.categoryId().toString(), cat.title(), (i * 4) % 10 ));
        }
        this.localAddress.append("/available");
        this.localAddress.append("?from=");
        this.localAddress.append(from);
        this.localAddress.append("&until=");
        this.localAddress.append(until);

        Mockito.when(this.categoryService.getAvailableCategories(from.atStartOfDay(), until.atStartOfDay())).thenReturn(availableCategory);


        final ResponseEntity<AvailableCategoryDTO[]> res = this.restTemplate.getForEntity(this.localAddress.toString(), AvailableCategoryDTO[].class, param);
        final List<AvailableCategoryDTO> actual = Arrays.asList(Objects.requireNonNull(res.getBody()));

        Assertions.assertTrue(availableCategory.containsAll(actual) && actual.containsAll(availableCategory));

    }
}
