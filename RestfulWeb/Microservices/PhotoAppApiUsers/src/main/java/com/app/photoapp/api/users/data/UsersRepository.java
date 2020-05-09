package com.app.photoapp.api.users.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, String> {
	List<UserEntity> findByFirstName(String name);
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);

}
