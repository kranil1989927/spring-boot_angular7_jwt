
package com.learn.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learn.modal.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

	User findByUsername(String username);
}
