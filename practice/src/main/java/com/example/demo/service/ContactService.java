package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;

public interface ContactService {

	void saveContact(ContactForm contactForm);
	
	Contact save(Contact contact);

	List<Contact> getAllContacts();
	
	Optional<Contact> findById(Long id);
	
	public void deleteById(Long id);

}