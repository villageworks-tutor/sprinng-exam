package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * 顧客名のキーワード検索（部分一致検索）
	 * SELECT * FROM users WHERE name LIKE ? 
	 */
	List<User> findByNameContaining(String keyword);

	/**
	 * メールアドレスとパスワードが一致する顧客を取得する
	 * SELECT * FROM users WHERE email = ? AND password = ? 
	 */
	User findByEmailAndPassword(String email, String password);

}
