package com.learn.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.learn.dao.UserDao;
import com.learn.modal.User;
import com.learn.vo.UserVO;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Override
	public User findOne(String userName) {
		return userDao.findByUsername(userName);
	}

	@Override
	public User findById(int id) {
		Optional<User> optionalUser = userDao.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public User save(UserVO userVO) {
		User newUser = new User();
		newUser.setFirstName(userVO.getFirstName());
		newUser.setLastName(userVO.getLastName());
		newUser.setUsername(userVO.getUsername());
		newUser.setPassword(bcryptEncoder.encode(userVO.getPassword()));
		newUser.setSalary(userVO.getSalary());
		newUser.setAge(userVO.getAge());
		return userDao.save(newUser);
	}

	@Override
	public UserVO update(UserVO userVO) {
		User user = userDao.findById(userVO.getId()).get();
		if (user != null) {
			BeanUtils.copyProperties(userVO, user, "password");
			userDao.save(user);
		}
		return userVO;
	}

	@Override
	public void delete(int id) {
		userDao.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority());
	}

	private Collection<? extends GrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
}
