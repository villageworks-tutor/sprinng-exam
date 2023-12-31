package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UsersController {
	
	@Autowired
	UserRepository userRepository;
	
	// 顧客一覧画面表示
	@GetMapping("/users")
	public String index(
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			Model model) {
		// 顧客リストを初期化
		List<User> list = null;
		if (keyword.isEmpty()) {
			// キーワードが入力されていない場合：すべての顧客を取得
			list = userRepository.findAll();
		} else {
			// キーワードが入力されている場合：部分一致検索
			list = userRepository.findByNameContaining(keyword);
			model.addAttribute("keyword", keyword);
		}
		// 取得した顧客リストをスコープに登録
		model.addAttribute("userList", list);
		// 画面遷移
		return "usersView";
	}
	
	// 新規登録画面表示
	@GetMapping("/users/add")
	public String create() {
		// 画面遷移
		return "userForm";
	}
	
	// 新規登録処理
	@PostMapping("/users/add")
	public String store(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("password") String password) {
		// リクエストパラメータをもとに顧客インスタンスを生成
		User user = new User(name, email, password);
		// 顧客インスタンスの永続化
		userRepository.save(user);
		// 画面遷移
		return "redirect:/users";
	}
	
	// 更新画面表示
	@GetMapping("/users/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			Model model) {
		// パスパラメータをもとに顧客インスタンスを取得
		User user = userRepository.findById(id).get();
		// 取得した顧客インスタンスをスコープに登録
		model.addAttribute("user", user);
		// 画面遷移
		return "userEdit";
	}
	
	// 更新処理
	@PostMapping("/users/{id}/edit")
	public String  update(
			@PathVariable("id") Integer id,
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("password") String password) {
		// パスパラメータとリクエストパラメータをもとに顧客インスタンスを生成
		User user = new User(id, name, email, password);
		// 生成した顧客インスタンスを永続化
		userRepository.save(user);
		// 画面遷移
		return "redirect:/users";
	}
	
	// 削除処理
	@PostMapping("/users/{id}/delete")
	public String delete(@PathVariable("id") Integer id) {
		// パスパラメータをもとに顧客を削除
		userRepository.deleteById(id); // TODO: パスパラメータをもとに顧客を取得し、そのインスタンスを指定して削除でも可
		// 画面遷移
		return "redirect:/users";
	}
}
