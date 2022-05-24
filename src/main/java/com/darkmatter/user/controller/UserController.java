package com.darkmatter.user.controller;

import com.darkmatter.user.model.User;
import com.darkmatter.user.service.UserService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {


    @Autowired
    private UserService userService;


//    @GetMapping()
//    public Flowable<User> listarUsuarios() {
//
//        return userService.listar();
//    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public ResponseEntity<Flowable<User>> listar()
    {
        Flowable<User> fxUsuarios = userService.listar();
        return ResponseEntity
                .ok()
                .body(fxUsuarios);
    }

    @PostMapping
    public Single<User> guardarUsuario(@RequestBody User user) {
        return userService.crear(user);
    }


}
