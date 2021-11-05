package fhv.teamd.hotel;

import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import fhv.teamd.hotel.infrastructure.HibernateBookingRepository;
import fhv.teamd.hotel.infrastructure.HibernateCategoryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class HotelApplication {
	private final EntityManagerFactory entityManagerFactory;

	public HotelApplication() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("hotel");
	}

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

	@Bean
	public EntityManager entityManager() {
		return this.entityManagerFactory.createEntityManager();
	}

	@Bean
	public CategoryRepository categoryRepository() {
		return new HibernateCategoryRepository();
	}

	@Bean
	public BookingRepository bookingRepository() {
		return new HibernateBookingRepository();
	}
}
