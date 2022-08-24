package com.leichu.ws.server.demo.dao;

import com.leichu.ws.server.demo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository("userDao")
public class UserDao {

	private static final Map<Long, User> userMap = new TreeMap<>();

	public List<User> getAll() {
		return new ArrayList<>(userMap.values());
	}

	public User getById(Long id) {
		if (null == id || !userMap.containsKey(id)) {
			return null;
		}
		return userMap.get(id);
	}

	public Long insert(User user) {
		if (null == user) {
			return null;
		}
		if (userMap.size() <= 0) {
			if (null == user.getId()) {
				user.setId(1L);
			}
			userMap.put(user.getId(), user);
			return user.getId();
		}
		if (null == user.getId()) {
			Long max = new ArrayList<>(userMap.keySet()).get(userMap.size() - 1);
			user.setId(max + 1);
		}
		userMap.put(user.getId(), user);
		return user.getId();
	}


}
