package com.pluralsight.conferencedemo.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
	@Autowired
	private SessionRepository sessionRepositry;
	
	@GetMapping
	public List<Session> list() {
		return sessionRepositry.findAll();
	}
	
	@GetMapping
	@RequestMapping("{id}")
	public Session get(@PathVariable Long id) {
		return sessionRepositry.getById(id);
	}
	
	@PostMapping
	public Session create(@RequestBody final Session session) {
		return sessionRepositry.saveAndFlush(session); // Flush means commit transaction to database
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete (@PathVariable Long id) {
		//TODO -- Also need to check for children records before deleting.
		//Left as an exercise for the user.
		sessionRepositry.deleteById(id);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Session update (@PathVariable Long id, @RequestBody Session session) {
		//Because this is a PUT, we would expect all attributes to be passed in.
		// A PATCH would update only the subset of attributes passed in. 
		//TODO -- Add validation that all attributes are passed in, otherwise return a 400 bad payload
		Session existingSession = sessionRepositry.getById(id);
		BeanUtils.copyProperties(session, existingSession, "session_id");
		return sessionRepositry.saveAndFlush(existingSession); // Flush means commit transaction to database
	}
}
