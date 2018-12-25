package com.learn.service;

import java.util.List;

import com.learn.modal.User;
import com.learn.vo.UserVO;

public interface UserService {

	User findOne(String username);

	User findById(int id);

	List<User> findAll();

	User save(UserVO userVO);

	UserVO update(UserVO userVO);

	void delete(int id);
}
