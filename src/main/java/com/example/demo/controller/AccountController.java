package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

@Controller
public class AccountController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	Account account;
	
	// ログイン画面表示
	@GetMapping("/login")
	public String index() {
		// 画面遷移
		return "login";
	}
	
	// ログイン処理
	@PostMapping("/login")
	public String login(
			@RequestParam("email") String email, 
			@RequestParam("password") String password,
			Model model) {
		// リクエストパラメータをもとに合致する顧客インスタンスを取得
		User user = userRepository.findByEmailAndPassword(email, password);
		// 種痘した顧客インスタンスによって処理を分岐
		if (user == null) {
			// ログイン失敗の場合：エラーメッセージをスコープに登録
			model.addAttribute("error", "メールアドレスとパスワードが一致しませんでした");
			// 画面遷移
			return "login";
		}
		// セッションスコープに登楼されたアカウント情報に顧客名を登録
		account.setName(user.getName());
		
		// 画面遷移
		return "redirect:/users";
	}
}
