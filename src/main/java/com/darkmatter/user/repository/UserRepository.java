package com.darkmatter.user.repository;

import com.darkmatter.user.model.User;
import org.springframework.data.repository.reactive.RxJava3CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends RxJava3CrudRepository<User, Integer> {
}
