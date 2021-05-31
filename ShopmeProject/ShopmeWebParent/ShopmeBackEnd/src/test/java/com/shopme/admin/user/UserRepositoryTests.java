package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userJohnnTR = new User("johnnyTheRasta@gmail.com", "brasil1102", "Johnny", "The Rasta");
		userJohnnTR.addRole(roleAdmin);
		
		User savedUser = repo.save(userJohnnTR);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userJojo = new User("anchiao0417@gmail.com", "hinet123", "Jojo", "The Hippie");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userJojo.addRole(roleEditor);
		userJojo.addRole(roleAssistant);
		
		User savedUser = repo.save(userJojo);
		
		assertThat(savedUser.getId()).isGreaterThan(0);	
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	
	}
	
	@Test
	public void testGetUserById() {
		User userJohn = repo.findById(1).get();
		System.out.println(userJohn);
		assertThat(userJohn).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userJohn = repo.findById(1).get();
		userJohn.setEnabled(true);
		userJohn.setEmail("lianglianganan112@gmail.com");
		
		repo.save(userJohn);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userJojo = repo.findById(2).get(); 
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		userJojo.getRoles().remove(roleEditor);
		userJojo.addRole(roleSalesperson);
		
		repo.save(userJojo);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 19;
		repo.deleteById(userId);
		
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "lianglianganan112@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
}
