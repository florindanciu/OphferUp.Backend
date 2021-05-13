package com.florindanciu.opherUpbackend;

import com.florindanciu.opherUpbackend.auth.model.AppUser;
import com.florindanciu.opherUpbackend.auth.model.EnumRole;
import com.florindanciu.opherUpbackend.auth.model.Role;
import com.florindanciu.opherUpbackend.auth.repository.AppUserRepository;
import com.florindanciu.opherUpbackend.auth.repository.RoleRepository;
import com.florindanciu.opherUpbackend.item.model.Category;
import com.florindanciu.opherUpbackend.item.model.EnumCategory;
import com.florindanciu.opherUpbackend.item.model.Item;
import com.florindanciu.opherUpbackend.item.repository.CategoryRepository;
import com.florindanciu.opherUpbackend.item.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
@EnableSwagger2
public class Application {

//	private final AppUserRepository appUserRepository;
//	private final RoleRepository roleRepository;
//	private final CategoryRepository categoryRepository;
//	private final ItemRepository itemRepository;
//	private final PasswordEncoder encoder;
//
//	@Autowired
//	public Application(AppUserRepository appUserRepository, RoleRepository roleRepository, CategoryRepository categoryRepository, ItemRepository itemRepository, PasswordEncoder encoder) {
//		this.appUserRepository = appUserRepository;
//		this.roleRepository = roleRepository;
//		this.categoryRepository = categoryRepository;
//		this.itemRepository = itemRepository;
//		this.encoder = encoder;
//	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

//	@Bean
//	public CommandLineRunner init() {
//		return args -> {
//			Role userRole = new Role();
//			userRole.setName(EnumRole.ROLE_USER);
//			Role seller = new Role();
//			seller.setName(EnumRole.ROLE_SELLER);
//			Role adminRole = new Role();
//			adminRole.setName(EnumRole.ROLE_ADMIN);
//			roleRepository.saveAll(List.of(userRole, seller, adminRole));
//
//			Category auto = new Category(EnumCategory.Vehicles);
//			Category electronics = new Category(EnumCategory.Electronics);
//			Category fashion = new Category(EnumCategory.Fashion);
//			Category home = new Category(EnumCategory.Home);
//			Category jobs = new Category(EnumCategory.Jobs);
//			Category pets = new Category(EnumCategory.Pets);
//			Category realEstate = new Category(EnumCategory.Estates);
//			Category services = new Category(EnumCategory.Services);
//			categoryRepository.saveAll(List.of(auto, electronics, fashion, home, jobs, pets, realEstate, services));
//
//			AppUser admin = AppUser.builder()
//					.username("admin")
//					.email("admin@gmail.com")
//					.password(encoder.encode("password"))
//					.roles(Set.of(adminRole))
//					.build();
//			appUserRepository.save(admin);
//

//			Category categoryAuto = categoryRepository.findByEnumCategory(EnumCategory.Vehicles);
//			Item item1 = Item.builder()
//					.itemName("2020 Dodge Challenger R/T")
//					.categories(Set.of(categoryAuto))
//					.postingDate(new Date())
//					.description("For sale in very good condition")
//					.location("Chicago, IL 60659")
//					.contactPerson("Tom Sawyer")
//					.contactEmail("tom@gmail.com")
//					.phoneNumber("0788566674")
//					.price("34,450")
//					.image("https://www.cstatic-images.com/supersized/in/v1/432197/2C3CDZBT0LH100374/1c43347ca716191a34cca15e74b07ae4.jpg")
//					.build();
//
//			Item item2 = Item.builder()
//					.itemName("2020 Tesla Model 3")
//					.categories(Set.of(categoryAuto))
//					.postingDate(new Date())
//					.description("In excellent condition")
//					.location("Desplaines, IL 60018")
//					.contactPerson("Bill Gates")
//					.contactEmail("bill@gmail.com")
//					.phoneNumber("0788566674")
//					.price("37,800")
//					.image("https://www.cstatic-images.com/supersized/in/v1/45580652/5YJ3E1EA8LF598026/f98e3e0e6fef99aaa088ebbcf0356635.jpg")
//					.build();
//
//			itemRepository.saveAll(List.of(item1, item2));
//		};
//	}
}
