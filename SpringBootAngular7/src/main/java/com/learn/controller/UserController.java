package com.learn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.modal.User;
import com.learn.service.UserService;
import com.learn.vo.ApiResponse;
import com.learn.vo.UserVO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ApiResponse<User> save(@RequestBody UserVO userVO) {
		User savedUser = userService.save(userVO);
		return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.", savedUser);
	}

	@GetMapping
	public ApiResponse<List<User>> listUser() {
		List<User> userList = userService.findAll();
		return new ApiResponse<>(HttpStatus.OK.value(), "User list fetched successfully.", userList);
	}

	@GetMapping("/{id}")
	public ApiResponse<User> getOne(@PathVariable int id) {
		User user = userService.findById(id);
		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", user);
	}

	@PutMapping("/{id}")
	public ApiResponse<UserVO> update(@RequestBody UserVO userVO) {
		UserVO updatedUser = userService.update(userVO);
		return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", updatedUser);
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable int id) {
		userService.delete(id);
		return new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully.", null);
	}
}
