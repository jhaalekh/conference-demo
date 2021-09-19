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

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {
	@Autowired
	SpeakerRepository speakerRepository;
	
	@GetMapping
	public List<Speaker> list() {
		return speakerRepository.findAll();
	}

	@GetMapping
	@RequestMapping("{id}")
	public Speaker get(@PathVariable Long id) {
		return speakerRepository.getById(id);
	}
	
	@PostMapping
	public Speaker create(@RequestBody final Speaker speaker ) {
		return speakerRepository.saveAndFlush(speaker); // Flush means commit transaction to database
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		//TODO -- Also need to check for children records before deleting.
		//Left as an exercise for the user.
		speakerRepository.deleteById(id);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
		//Because this is a PUT, we would expect all attributes to be passed in.
		// A PATCH would update only the subset of attributes passed in. 
		//TODO -- Add validation that all attributes are passed in, otherwise return a 400 bad payload
		Speaker existingSpeaker = speakerRepository.getById(id);
		BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
		return speakerRepository.saveAndFlush(existingSpeaker); // Flush means commit transaction to database
	}
	
}
