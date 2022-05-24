package com.darkmatter.user.service;

import com.darkmatter.user.model.User;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import reactor.core.publisher.Flux;

public interface UserService {

    Flowable<User> listar();
    Single<User> crear(User user);
}
