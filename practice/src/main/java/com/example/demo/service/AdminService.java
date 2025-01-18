package com.example.demo.service;

import com.example.demo.form.AdminForm;

public interface AdminService {

	//管理者情報を登録するメソッド
	void saveAdmin(AdminForm adminForm);

	//ログインするメソッド
	boolean adminLogin(String email, String plainPassword);

}
