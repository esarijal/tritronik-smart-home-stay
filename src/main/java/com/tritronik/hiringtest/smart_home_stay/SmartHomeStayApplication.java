package com.tritronik.hiringtest.smart_home_stay;

import com.tritronik.hiringtest.smart_home_stay.model.*;
import com.tritronik.hiringtest.smart_home_stay.model.enums.Role;
import com.tritronik.hiringtest.smart_home_stay.model.enums.RoomStatus;
import com.tritronik.hiringtest.smart_home_stay.repository.AdditionalFacilityRepository;
import com.tritronik.hiringtest.smart_home_stay.repository.RoomRepository;
import com.tritronik.hiringtest.smart_home_stay.repository.RoomTypeRepository;
import com.tritronik.hiringtest.smart_home_stay.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SmartHomeStayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartHomeStayApplication.class, args);
	}

	@Bean
	@Profile("!test")
	CommandLineRunner initDatabase(UserRepository userRepository,
										AdditionalFacilityRepository additionalFacilityRepository,
										RoomTypeRepository roomTypeRepository,
										RoomRepository roomRepository,
									   PasswordEncoder passwordEncoder){
		return args -> {
			RoomType singleRoomType = new RoomType(1L, "Single Room");
			RoomType twinRoomType = new RoomType(2L, "Twin Room");
			roomTypeRepository.saveAll(Arrays.asList(singleRoomType, twinRoomType));

			// Create sample rooms
			Room room1 = new Room(1L, "101", singleRoomType, RoomStatus.AVAILABLE, 250000);
			Room room2 = new Room(2L, "201", twinRoomType, RoomStatus.AVAILABLE, 400000);
			roomRepository.saveAll(Arrays.asList(room1, room2));

			// Create sample additional facilities
			AdditionalFacility breakfastFacility = new AdditionalFacility(1l, "Breakfast", 50000);
			AdditionalFacility extraBedFacility = new AdditionalFacility(2l, "Extra Bed", 25000);
			additionalFacilityRepository.saveAll(Arrays.asList(breakfastFacility, extraBedFacility));

			// Create a sample employee user
			Set<Role> employeeRoles = new HashSet<>(Arrays.asList(Role.EMPLOYEE));
			Set<Role> userRoles = new HashSet<>(Arrays.asList(Role.USER));
			User employeeUser = new User(1l, "employee", passwordEncoder.encode("password"), employeeRoles);
			User user = new User(2l, "salsabilla", passwordEncoder.encode("password"), userRoles);
			userRepository.save(employeeUser);
			userRepository.save(user);

		};
	}

}
