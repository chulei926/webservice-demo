package com.leichu.ws.server.demo.service;

import com.leichu.ws.server.demo.domain.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "UserService", targetNamespace = "service.demo.server.ws.leichu.com")
public interface UserService {

	@WebMethod
	User getUserById(@WebParam(name = "userId") Long id);

	@WebMethod
	List<User> getUsers();

	@WebMethod
	Long saveUser(@WebParam User user);

}
