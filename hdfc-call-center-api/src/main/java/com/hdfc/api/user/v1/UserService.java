package com.hdfc.api.user.v1;

public interface UserService {
	UserModel getByEmail(String email);
	void save(UserModel userLoginModel);
	UserModel getById(Integer id);
	Integer deleteById(Integer userId);
	Integer softDelete(Integer userId);
}
