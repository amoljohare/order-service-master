package com.hdfc.api.user.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hdfc.utils.OffsetLimitRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserModel getByEmail(String email) {
		return userRepository.findByUserEmail(email);
	}

	@Override
	public void save(UserModel userLoginModel) {
		userRepository.save(userLoginModel);
	}

	@Override
	public UserModel getById(Integer id) {
		return userRepository.findById(id).get();
	}

	@Override
	public Integer deleteById(Integer userId) {
		UserModel userModel = userRepository.findById(userId).get();
		if (userModel != null) {
			userRepository.deleteById(userId);
			return 1;
		}
		return 0;
	}

	@Override
	public Integer softDelete(Integer userId) {
		UserModel exiUser = userRepository.findById(userId).get();
		if (exiUser != null) {
			exiUser.setActive(false);
			userRepository.save(exiUser);
			return 1;
		}
		return 0;
	}

	@Override
	public Page<UserModel> findAllUsers(Integer limit, Integer offset,String columnName, String orderBy) {
		Pageable pageable;
		if (offset != 0) {
			pageable = new OffsetLimitRequest(limit, offset, columnName, orderBy);
		} else {
			pageable = new OffsetLimitRequest(0, 10, columnName, orderBy);
		}
		Page<UserModel> result = null;
		result = userRepository.findAllUsers(pageable);
		return result;
	}

}
