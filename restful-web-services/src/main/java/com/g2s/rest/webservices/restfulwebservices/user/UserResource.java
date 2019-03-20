package com.g2s.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class UserResource {

	@Autowired
	public UserDaoService service;

	// retrieve All Users
	// retrieve user passing id

	@GetMapping(path = "/users")
	public MappingJacksonValue retrieveAllUsers() {
		
		List<User> userList = service.findAll();
		
		Set<String> hashSet = new HashSet<>(Arrays.asList("id", "name"));
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(hashSet);
		FilterProvider filters = new SimpleFilterProvider().addFilter("User", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(userList);
		
		mapping.setFilters(filters);
		
		
		return mapping;
	}

	@GetMapping(path = "/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {	

		User user = service.findOne(id);

		if (user == null)
			throw new UserNotFoundException("id -" + id);
		
		// " all -user", server_path + "/users"
		
		org.springframework.hateoas.Resource<User> resource = new Resource<User>(user);
		
		ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}

	@PostMapping(path = "/users")
	public ResponseEntity<Object> users(@Valid @RequestBody User user) {

		User saveUser = service.save(user);

		// Created
		// /user/4

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saveUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

		// return service.save(user);
	}

	@DeleteMapping(path = "/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {

		User user = service.deleteById(id);

		if (user == null)
			throw new UserNotFoundException("id - " + id);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( user.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
}
