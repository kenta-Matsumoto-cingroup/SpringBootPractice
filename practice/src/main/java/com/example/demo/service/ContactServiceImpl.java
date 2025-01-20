package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public void saveContact(ContactForm contactForm) {
		Contact contact = new Contact();

		contact.setLastName(contactForm.getLastName());
		contact.setFirstName(contactForm.getFirstName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhone(contactForm.getPhone());
		contact.setZipCode(contactForm.getZipCode());
		contact.setAddress(contactForm.getAddress());
		contact.setBuildingName(contactForm.getBuildingName());
		contact.setContactType(contactForm.getContactType());
		contact.setBody(contactForm.getBody());

		contactRepository.save(contact);
	}

	@Override
	public Contact save(Contact contact) {
		return contactRepository.save(contact);
	}

	//データベースからすべてのお問い合わせ情報を取得
	@Override
	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}

	//IDでお問い合わせを検索
	@Override
	public Optional<Contact> findById(Long id) {
		return contactRepository.findById(id);
	}

	//お問い合わせの削除
	@Override
	public void deleteById(Long id) {
		contactRepository.deleteById(id);
	}

}