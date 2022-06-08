package com.darkmatter.user.service;

import com.darkmatter.user.model.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flowable<User> listar();
    Single<User> crear(User user);
    Maybe<User> actualizar(User usuario);
    Maybe<User> actualizarId(int id, User user);
    Maybe<User> buscarPorId(int id);
    Completable eliminarPorId(int id);
    Mono<User> actualizarImagen(User usuario);
    Mono<User> buscarUserForImagen(int id);
}
