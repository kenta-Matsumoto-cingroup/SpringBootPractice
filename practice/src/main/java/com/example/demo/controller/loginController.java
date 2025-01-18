package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Contact;
import com.example.demo.form.LoginForm;
import com.example.demo.service.AdminService;
import com.example.demo.service.ContactService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class loginController {
	@Autowired
	private AdminService adminService;//adminServiceを注入

	@Autowired
	private ContactService contactService;//contactServiceを注入

	//「ログイン画面へ」ボタンが押されたときにレスポンスを返す処理
	@GetMapping("/admin/sign-in")
	public String showSigninForm(@ModelAttribute LoginForm loginForm) {
		return "admin-signin"; // HTMLのテンプレート名
	}

	@PostMapping("/admin/sign-in")
	public String loginAdmin(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult result,
			HttpServletRequest request,Model model) {

		//@Validatedで検証、BindingResult型の引数で検証結果を格納
		//入力エラーがあれば、ログイン画面へ戻す
		if (result.hasErrors()) {
			return "admin-signin";
		}

		HttpSession session = request.getSession();
		session.setAttribute("loginForm", loginForm);

		LoginForm login = (LoginForm) session.getAttribute("loginForm");

		// ログイン処理を呼び出す
		boolean loginSuccess = adminService.adminLogin(login.getEmail(), login.getPassword());

		if (loginSuccess) {
			HttpSession loginSession = request.getSession();
		    loginSession.setAttribute("isLoggedIn", true);
			return "redirect:/admin/contacts"; // ログイン成功後、問い合わせ一覧ページへ
		} else {
			model.addAttribute("errorMSG","ログインIDとパスワードの組み合わせが間違っています。");
			return "admin-signin"; // ログイン画面へ戻す
		}
	}

	//管理者のログイン後にお問い合わせ一覧を表示
	@GetMapping("admin/contacts")
	public String showContactsList(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginForm") == null) {
			return "redirect:/admin/sign-in"; // ログインしていなければ、再度ログイン画面にリダイレクト
		}
		//ログイン状態が確認できれば、お問い合わせ一覧を表示
		//サービスを使ってお問い合わせ情報を取得
		List<Contact> contacts = contactService.getAllContacts();
		//取得したデータをビューに渡す
		model.addAttribute("contacts", contacts);
		return "contacts-list";//お問い合わせ一覧ページのビュー
	}

}
