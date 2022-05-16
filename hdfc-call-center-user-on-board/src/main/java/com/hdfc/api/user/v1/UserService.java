package com.hdfc.api.user.v1;

import org.springframework.data.domain.Page;

public interface UserService {
	UserModel getByEmail(String email);
	void save(UserModel userLoginModel);
	UserModel getById(Integer id);
	Integer deleteById(Integer userId);
	Integer softDelete(Integer userId);
//	UserModel getRoleId(Integer roleId);
	public Page<UserModel> findAllUsers(Integer limit, Integer offset,String columnName, String orderBy);

	
	
	
}
