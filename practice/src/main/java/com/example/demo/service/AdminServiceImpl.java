package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.form.AdminForm;
import com.example.demo.repository.AdminRepository;
import com.example.demo.util.PasswordUtils;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	//管理者の新規登録情報をデータベースへ保存する
	@Override
	public void saveAdmin(AdminForm adminForm) {
		Admin admin = new Admin();

		admin.setLastName(adminForm.getLastName());
		admin.setFirstName(adminForm.getFirstName());
		admin.setEmail(adminForm.getEmail());
		admin.setPassword(adminForm.getPassword());

		// パスワードを暗号化
		String encodedPassword = passwordEncoder.encode(adminForm.getPassword());
		admin.setPassword(encodedPassword);

		//管理者の登録情報をデータベースへ保存
		adminRepository.save(admin);
	}

	//管理者画面へログインする
	@Override
	public boolean adminLogin(String email, String plainPassword) {
		Admin admin = adminRepository.findByEmail(email);
		if (admin != null && PasswordUtils.matches(plainPassword, admin.getPassword())) {
			return true;
		}
		return false;
	}

}
