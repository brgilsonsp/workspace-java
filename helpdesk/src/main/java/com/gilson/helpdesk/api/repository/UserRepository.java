package com.gilson.helpdesk.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gilson.helpdesk.api.entity.User;
import com.gilson.helpdesk.api.enums.ProfileEnum;

public interface UserRepository extends MongoRepository<User, String>{

	User findByEmailIgnoreCase(String email);
	
	User findByProfile(ProfileEnum profile);
}
