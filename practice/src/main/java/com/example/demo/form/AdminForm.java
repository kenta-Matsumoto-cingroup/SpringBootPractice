package com.example.demo.form;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminForm implements Serializable {

	@NotBlank(message="姓を入力してください。")
	private String lastName;

	@NotBlank(message="名を入力してください。")
	private String firstName;

	@NotBlank(message="e-mailアドレスを入力してください。")
	@Email
	private String email;

	@NotBlank(message="パスワードを入力してください。")
	private String password;

}
