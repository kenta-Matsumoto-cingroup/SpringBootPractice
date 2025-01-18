package com.example.demo.form;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm implements Serializable {
	@NotBlank(message="e-mailアドレスを入力してください。")
	@Email
	private String email;

	@NotBlank(message="パスワードを入力してください。")
	private String password;

}
