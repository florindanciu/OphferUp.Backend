package com.florindanciu.opherUpbackend;

import com.florindanciu.opherUpbackend.auth.model.EnumRole;
import com.florindanciu.opherUpbackend.auth.model.Role;
import com.florindanciu.opherUpbackend.auth.repository.RoleRepository;
import com.florindanciu.opherUpbackend.item.model.Category;
import com.florindanciu.opherUpbackend.item.model.EnumCategory;
import com.florindanciu.opherUpbackend.item.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public CommandLineRunner init() {
		return args -> {
			Role user = new Role();
			user.setName(EnumRole.ROLE_USER);
			Role seller = new Role();
			seller.setName(EnumRole.ROLE_SELLER);
			Role admin = new Role();
			admin.setName(EnumRole.ROLE_ADMIN);
			roleRepository.saveAll(List.of(user, seller, admin));

			Category auto = new Category(EnumCategory.AUTO_AND_MOTO);
			Category electronics = new Category(EnumCategory.ELECTRONICS_AND_APPLIANCES);
			Category fashion = new Category(EnumCategory.FASHION_AND_CARE);
			Category home = new Category(EnumCategory.HOME_AND_GARDEN);
			Category jobs = new Category(EnumCategory.JOBS);
			Category pets = new Category(EnumCategory.PETS);
			Category realEstate = new Category(EnumCategory.REAL_ESTATE);
			Category services = new Category(EnumCategory.SERVICES);
			categoryRepository.saveAll(List.of(auto, electronics, fashion, home, jobs, pets, realEstate, services));
		};
	}
}
