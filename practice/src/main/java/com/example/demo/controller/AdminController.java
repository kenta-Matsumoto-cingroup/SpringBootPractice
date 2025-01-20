package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.AdminForm;
import com.example.demo.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//管理者の新規登録処理を行うコントローラーの記述
@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;//adminServiceを注入

	
	//最初のリクエストからレスポンスを返し、管理者の新規登録画面へ
	@GetMapping("/admin/signup")
	public String signup(@ModelAttribute AdminForm adminForm) {
		//Formは空のAdminFormオブジェクトとして初期化
		return "signup-regist";//新規登録入力画面へ
	}

	//新規登録画面から受け取ったデータを登録確認画面へ渡してレスポンスを返す
	@PostMapping("/admin/signup-regist-confirm")
	public String signupRegist(@Validated @ModelAttribute("adminForm") AdminForm adminForm, BindingResult result,
			HttpServletRequest request) {
		
		//@Validatedで検証、BindingResult型の引数で検証結果を格納
		//入力エラーがあれば、新規登録画面へ戻す
		if (result.hasErrors()) {
			return "signup-regist";
		}

		HttpSession session = request.getSession();
		session.setAttribute("adminForm", adminForm);

		return "redirect:/signup/confirm";
	}

	@GetMapping("/signup/confirm")
	public String adminConfirm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();

		AdminForm adminForm = (AdminForm) session.getAttribute("adminForm");
		model.addAttribute("adminForm", adminForm);
		return "signup-regist-confirm";//登録内容の確認画面へ
	}

	//登録内容確認画面から登録完了画面へ、データベースへ管理者情報の登録
	@PostMapping("/admin/signup-register")
	public String signupRegister(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminForm adminForm = (AdminForm) session.getAttribute("adminForm");

		System.out.println(adminForm);

		adminService.saveAdmin(adminForm);//データベースへの登録処理を呼び出し

		return "redirect:/admin/signup-complete";//登録完了画面へ
	}

	@GetMapping("admin/signup-complete")
	public String signupComplete(Model model, HttpServletRequest request) {

		if (request.getSession(false) == null) {
			return "redirect:/signup-regist";
		}

		HttpSession session = request.getSession();
		AdminForm adminForm = (AdminForm) session.getAttribute("adminform");
		model.addAttribute("adminForm", adminForm);

		session.invalidate();//セッションの終了（セッション内のすべてのデータをクリア）

		return "signup-complete";//管理者登録完了画面へ
	}

	

}
