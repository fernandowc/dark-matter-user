package com.darkmatter.user.service.impl;

import com.darkmatter.user.model.User;
import com.darkmatter.user.repository.UserReactorRepository;
import com.darkmatter.user.repository.UserRepository;
import com.darkmatter.user.service.UserService;
import io.reactivex.rxjava3.core.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserReactorRepository userReactorRepository;

    @Override
    public Flowable<User> listar() {
        return userRepository.findAll();
    }

    @Override
    public Single<User> crear(User user) {

        return userRepository.save(user);
    }

    @Override
    public Maybe<User> actualizar(User usuario) {

        return  userRepository.save(usuario).toMaybe();
    }

    @Override
    public Maybe<User> actualizarId(int id, User usuario) {

        return buscarPorId(id).filter(user -> true)
                .doOnError(a -> log.error("Error no se encontro a" + a.toString()))
                .flatMap(a -> {
                    a.setFirstname(usuario.getFirstname());
                    a.setSecondname(usuario.getSecondname());
                    a.setLastname(usuario.getLastname());
                    a.setSurname(usuario.getSurname());
                    log.info("Se guarda-->" + a);
                    return userRepository.save(a).toMaybe();
                })
                .doOnError(a -> log.error("Error no se encontro b" + a.toString()));
    }

    public Maybe<String> mensaje() {
        String hola = "Error";
        return Maybe.just(hola);
    }

    @Override
    public Maybe<User> buscarPorId(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Completable eliminarPorId(int id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<User> actualizarImagen(User usuario) {
        return userReactorRepository.save(usuario);
    }

    @Override
    public Mono<User> buscarUserForImagen(int id) {
        return userReactorRepository.findById(id);
    }
}
