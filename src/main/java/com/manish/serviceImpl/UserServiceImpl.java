package com.manish.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.DTO.Request.UserRequest;
import com.manish.DTO.Response.UserResponse;
import com.manish.entity.User;
import com.manish.exception.BadRequestException;
import com.manish.exception.ResourceNotFoundException;
import com.manish.repository.UserRepository;
import com.manish.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserResponse createUser(UserRequest request)  {
		
	//check already email exist or not
	if(userRepository.existsByEmail(request.getEmail())) {
		throw new BadRequestException("Email already regsistered::"+request.getEmail());
	}
	
	//DTO-->Entity
	User user = User.builder()
			.name(request.getName())
			.email(request.getEmail())
			.phone(request.getPhone())
			.address(request.getAddress())
			.build();

           //save data
	User saved = userRepository.save(user);
	return toResponse(saved);
}
	
	//convert Entity-->DTO
	private UserResponse toResponse(User user) {
		return UserResponse.builder()
           .id(user.getId())
           .name(user.getName())
           .email(user.getEmail())
           .phone(user.getPhone())
           .address(user.getAddress())
           .build();
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	@Override
	public List<UserResponse> getAllUsers() {
		List<User> all = userRepository.findAll();
		return all.stream()
			.map(user  ->  toResponse(user))
			.toList();
	}

	@Override
	public UserResponse getUserById(Long id) {
		User byId = userRepository.findById(id).orElseThrow(()  ->  new ResourceNotFoundException("User Not found with"+id));
		return toResponse(byId);
		
	}

	@Override
	public UserResponse updateUser(Long id, UserRequest request) {
		User existinguser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with::"+id));
		
		existinguser.setName(request.getName());
		existinguser.setEmail(request.getEmail());
		existinguser.setPhone(request.getPhone());
		existinguser.setAddress(request.getAddress());
		
		User updatedUser = userRepository.save(existinguser);
		
		return toResponse(updatedUser);
		
		
	}

	@Override
	public void deleteUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id not found with::"+id));
		userRepository.delete(user);
	}

	@Override
	public UserResponse findByemail(String email) {
		User findByemail = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User email not found with::"+email));
		return toResponse(findByemail);
	}

	@Override
	public UserResponse findByName(String name) {
		User findByname = userRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("User name not found with ::"+name));
		return toResponse(findByname);
	}

	@Override
	public List<UserResponse> createMultipleUsers(List<UserRequest> request) {
	List<User> users = new ArrayList<User>();
	for(UserRequest requests:request) {
		if(userRepository.existsByEmail(requests.getEmail())) {
			throw new BadRequestException("Email already exists::"+requests.getEmail());
		
		}
		
		User user = User.builder()
				.name(requests.getName())
				.email(requests.getEmail())
				.phone(requests.getPhone())
				.address(requests.getAddress())
				.build();
		
		users.add(user);
	}
	List<User> saveAll = userRepository.saveAll(users);
	
	
	  List<UserResponse> responseList =
	            new ArrayList<>();

	    for (User user : saveAll) {

	        responseList.add(toResponse(user));
	    }

	    return responseList;
	}

	
	
	
	
}
