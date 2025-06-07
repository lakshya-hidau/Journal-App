package com.springboot.journalApp.repository;

import com.springboot.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

	User findByUsername(String username);  // Find user by username

	void deleteByUsername(String username);  // Delete user by username
}
