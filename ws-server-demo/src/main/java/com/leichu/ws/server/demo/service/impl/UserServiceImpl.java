package com.leichu.ws.server.demo.service.impl;

import com.leichu.ws.server.demo.dao.UserDao;
import com.leichu.ws.server.demo.domain.User;
import com.leichu.ws.server.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.List;

@WebService(
		name = "UserService",
		targetNamespace = "service.demo.server.ws.leichu.com",
		endpointInterface = "com.leichu.ws.server.demo.service.UserService"
)
@Service("UserService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public User getUserById(Long id) {
		return userDao.getById(id);
	}

	@Override
	public List<User> getUsers() {
		return userDao.getAll();
	}

	@Override
	public Long saveUser(User user) {
		return userDao.insert(user);
	}
}
