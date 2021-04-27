package com.develop.machinecomm.Machinecomm;

import com.develop.machinecomm.Machinecomm.model.Users;
import com.develop.machinecomm.Machinecomm.model.enums.Perfil;
import com.develop.machinecomm.Machinecomm.dto.FileStorageDTO;
import com.develop.machinecomm.Machinecomm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
		FileStorageDTO.class
})
public class MachinecommApplication implements CommandLineRunner {
	@Autowired
	private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(MachinecommApplication.class, args);


	}

	@Override
	public void run(String... args) {
		List<Users> users = usersBuilder();

		for(Users user : users) {
			if (userService.findByUserByRecord(user.getReg()) == null){
				userService.createUser(user);
			}
		}
	}

	private List<Users> usersBuilder(){
		Users admin= new Users();
		Users admin2 = new Users();
		Users matchbox = new Users();

		//matchbox user data
		matchbox.setName("matchbox");
		matchbox.setEmail("matchbox@matchbox.com");
		matchbox.setReg("matchbox");
		matchbox.setRfid("N.A");
		matchbox.setPassword("YWRt");
		matchbox.setType(3);
		matchbox.setPerfis(Collections.singleton(Perfil.ADMIN.getCod()));

		//admin user data
		admin.setName("admin");
		admin.setEmail("admin@matchbox.com");
		admin.setReg("admin");
		admin.setRfid("NA");
		admin.setPassword("YWRt");
		admin.setType(3);
		admin.setPerfis(Collections.singleton(Perfil.ADMIN.getCod()));

		//Users list build
		List<Users> users = new ArrayList<>();
		users.add(admin);
		users.add(matchbox);
		return users;
	}
}
