package com.darkmatter.user.repository;

import com.darkmatter.user.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReactorRepository extends ReactiveCrudRepository<User, Integer> {

}
