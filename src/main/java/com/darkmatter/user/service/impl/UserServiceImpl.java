package com.darkmatter.user.service.impl;

import com.darkmatter.user.model.User;
import com.darkmatter.user.repository.UserRepository;
import com.darkmatter.user.service.UserService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public Flowable<User> listar() {
        return userRepository.findAll();
    }

    @Override
    public Single<User> crear(User user) {

//        user.setIduser(UUID.randomUUID().toString());

        return userRepository.save(user);
    }
}
