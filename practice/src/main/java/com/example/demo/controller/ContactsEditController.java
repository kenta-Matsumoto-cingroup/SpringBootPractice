package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Contact;
import com.example.demo.service.ContactService;

@Controller
public class ContactsEditController {

	@Autowired
	private ContactService contactService;

	//お問い合わせの詳細画面の表示
	@GetMapping("/admin/contacts/{id}")
	public String showContactDetail(@PathVariable("id") Long id, Model model) {

		//サービスを使ってIDに基づいたお問い合わせの詳細情報を取得
		Optional<Contact> contactOpt = contactService.findById(id);

		if (contactOpt.isPresent()) {
			// Optional内のContactを取り出してモデルに追加
			model.addAttribute("contact", contactOpt.get());
			return "contacts-detail"; //詳細ページに遷移
		}
		return "contacts-list";
	}

	// 編集画面の表示
	@GetMapping("/admin/contacts/{id}/edit")
	public String editContact(@PathVariable("id") Long id, Model model) {
		
		//編集対象のお問い合わせデータを取得
		Optional<Contact> contact = contactService.findById(id);

		if (contact.isPresent()) {
			//モデルにデータを追加して編集フォームに渡す
			model.addAttribute("contact", contact.get());
			return "contacts-edit"; //編集画面に遷移
		}
		return "redirect:/admin/contacts"; //見つからない場合は一覧画面にリダイレクト
	}
	
	// 編集内容の保存
    @PostMapping("/admin/contacts/{id}/edit")
    public String updateContact(@PathVariable("id") Long id, @ModelAttribute Contact contact) {
        // 受け取った contact オブジェクトに id をセット
        contact.setId(id);
        
        // そのまま保存
        contactService.save(contact);

        return "redirect:/admin/contacts";  // 保存後は一覧画面に戻る
    }

    // お問い合わせの削除
    @PostMapping("/admin/contacts/{id}/delete")
    public String deleteContact(@PathVariable("id") Long id) {
        contactService.deleteById(id); // IDで削除
        return "redirect:/admin/contacts";  // 削除後は一覧画面に戻る
    }

}
