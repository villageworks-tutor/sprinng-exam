package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UsersController {
	
	@Autowired
	UserRepository userRepository;
	
	// 顧客一覧画面表示
	@GetMapping("/users")
	public String index(Model model) {
		// すべての顧客を取得
		List<User> list = userRepository.findAll();
		// 取得した顧客リストをスコープに登録
		model.addAttribute("userList", list);
		// 画面遷移
		return "usersView";
	}
}
