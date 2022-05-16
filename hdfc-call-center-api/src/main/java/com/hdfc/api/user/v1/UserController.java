package com.hdfc.api.user.v1;

import java.security.Key;
import java.sql.Date;
import java.util.Calendar;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.utils.JWTokenVerify;
import com.hdfc.utils.Masking;
import com.hdfc.utils.StatusResponse;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${maskSecretKey}")
	private String maskSecretKey;

	@Autowired
	private UserService userService;

	@SuppressWarnings("rawtypes")
	@PostMapping("/save")
	public ResponseEntity<StatusResponse> saveUser(@RequestBody UserModel userModel) {
		StatusResponse statusMessage = new StatusResponse();
		System.out.println(userModel.getUserName());
		try {
			// encryptedPassword
			String password = userModel.getPassword();
			String email = userModel.getUserEmail();
			String encryptedPassword = Masking.encrypt(password, maskSecretKey);
			userModel.setPassword(encryptedPassword);
			userModel.setActive(true);
			userModel.setUserName(userModel.getUserName());
			// check email already exist
			UserModel userLoginModel = userService.getByEmail(email);
			if (userLoginModel == null) {
				userService.save(userModel);
				statusMessage.setMessage("User save sucessfully");
				statusMessage.setSuccess(true);
				statusMessage.setStatusCode(200);
				statusMessage.setName(userModel.getUserName());
				statusMessage.setUserId(userModel.getUserId());
				statusMessage.setRoleId(userModel.getRoleId());
				return new ResponseEntity<>(statusMessage, HttpStatus.CREATED);
			} else {
				// return error message
				statusMessage.setMessage("Email id: " + email + " already exist"); // change
				statusMessage.setSuccess(false);
				statusMessage.setStatusCode(200);
				return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			statusMessage.setMessage(e.getMessage());
			statusMessage.setSuccess(false);
			return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/update")
//	public ResponseEntity<StatusResponse> updateStatus(@RequestBody UserModel userModel,
//			@RequestHeader("x-access-token") String token) {
	public ResponseEntity<StatusResponse> updateStatus(@RequestBody UserModel userModel) {
		StatusResponse statusMessage = new StatusResponse();
		UserModel model = new UserModel();
		// encryptedPassword
		String password = userModel.getPassword();
		String encryptedPassword = Masking.encrypt(password, maskSecretKey);
		userModel.setPassword(encryptedPassword);
		try {
			// ONObject verify = JWTokenVerify.verifyJWT(token, secret);
			// (verify.getString("message").equals("valid")) {
			Integer userId = userModel.getUserId();
			if (userId != null) {
				model = userService.getById(userId);
				model.setUserName(userModel.getUserName());
				model.setUserEmail(userModel.getUserEmail());
				model.setPassword(userModel.getPassword());
				model.setLastName(userModel.getLastName());
				userService.save(model);
				//
				statusMessage.setMessage("update sucessfully");
				statusMessage.setSuccess(true);
				statusMessage.setStatusCode(200);
				return new ResponseEntity<>(statusMessage, HttpStatus.CREATED);
			} else {
				statusMessage.setMessage("Enter correct Information");
				statusMessage.setSuccess(true);
				statusMessage.setStatusCode(200);
				return new ResponseEntity<>(statusMessage, HttpStatus.OK);
			}
//			} else {
//				// return error message
//				statusMessage.setMessage("Token not found");
//				statusMessage.setSuccess(false);
//				statusMessage.setStatusCode(401);
//				return new ResponseEntity<>(statusMessage, HttpStatus.UNAUTHORIZED);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			statusMessage.setMessage(e.getMessage());
			statusMessage.setSuccess(false);
			statusMessage.setStatusCode(400);
			return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<StatusResponse> deleteById(@PathVariable("userId") Integer userId)
			 {
		StatusResponse statusResponse = new StatusResponse();
		try {
			
			
				Integer status = userService.softDelete(userId);
				if (status != 0) {
					statusResponse.setMessage("User Deleted sucessfully");
					statusResponse.setSuccess(true);
					statusResponse.setStatusCode(200);
					return new ResponseEntity<>(statusResponse, HttpStatus.OK);
				} else {
					statusResponse.setMessage("User not found");
					statusResponse.setSuccess(true);
					statusResponse.setStatusCode(200);
					return new ResponseEntity<>(statusResponse, HttpStatus.OK);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			statusResponse.setMessage(e.getMessage());
			statusResponse.setSuccess(false);
			statusResponse.setStatusCode(400);
			return new ResponseEntity<>(statusResponse, HttpStatus.BAD_REQUEST);
		}
	}
	@SuppressWarnings("rawtypes")
	@PostMapping("/login")
	public ResponseEntity<StatusResponse> loginUser(@RequestBody UserModel userModel) {
		StatusResponse statusMessage = new StatusResponse();
		try {
			// encryptedPassword
			String password = userModel.getPassword();
			String email = userModel.getUserEmail();
			// get data by email id
			UserModel userModelResponse = userService.getByEmail(email);
			if (userModelResponse != null) {
				String decryptedPassword = Masking.decrypt(userModelResponse.getPassword(), maskSecretKey);
				if (decryptedPassword.equals(password)) {
					String token = createJWT(email, secret);
					System.out.println(token);
					statusMessage.setMessage("login sucessfully");
					statusMessage.setToken(token);
					statusMessage.setName(userModelResponse.getUserName());
					statusMessage.setSuccess(true);
					statusMessage.setStatusCode(200);
					return new ResponseEntity<>(statusMessage, HttpStatus.CREATED);
				} else {
					statusMessage.setMessage("Password does not match ");
					statusMessage.setSuccess(false);
					statusMessage.setStatusCode(200);
					return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
				}
			} else {
				// return error message
				statusMessage.setMessage("Email id: " + email + " does not exist"); // change
				statusMessage.setSuccess(false);
				statusMessage.setStatusCode(200);
				
				return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			statusMessage.setMessage(e.getMessage());
			statusMessage.setSuccess(false);
			return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/getUser/{userId}")
	public ResponseEntity<StatusResponse> getById(@PathVariable("userId") Integer userId){
		StatusResponse statusMessage = new StatusResponse(); 
		
		UserModel exiUserId=userService.getById(userId);
		System.out.println("UserControllet"+exiUserId);
		if(exiUserId != null) {
			
			statusMessage.setName(exiUserId.getUserName());
			statusMessage.setUserId(userId);
			statusMessage.setMessage("User Get sucessfully");
			statusMessage.setSuccess(true);
			statusMessage.setStatusCode(200);
			return new ResponseEntity<>(statusMessage, HttpStatus.OK);
		}else {
			statusMessage.setMessage("This User is not exist ");
			statusMessage.setSuccess(false);
			statusMessage.setStatusCode(200);
			return new ResponseEntity<>(statusMessage, HttpStatus.NOT_FOUND);
		}
		
		
		
	}
	
	private String createJWTForFP(String email, String redirectUrl, String jwtSecretKey) {
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, 1);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(email).setIssuedAt(now).setIssuer(redirectUrl)
				.signWith(signatureAlgorithm, signingKey).setExpiration(cal.getTime()); // .

		return builder.compact();
	}

	private String createJWT(String email, String jwtSecretKey) {
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, 1);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(email).setIssuedAt(now).signWith(signatureAlgorithm, signingKey)
				.setExpiration(cal.getTime());

		return builder.compact();
	}
	
	
}
