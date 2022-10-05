package com.darkmatter.user.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.darkmatter.user.model.User;
import com.darkmatter.user.service.impl.UserServiceImpl;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping(value = "/lista" )
    public ResponseEntity<Flowable<User>> listarUsuarios() {

        return  ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.listar());

    }


    @PostMapping(value = "/crear")
    public Single<ResponseEntity<User>> nuevo(@Validated @RequestBody User user) {
        return userService.crear(user)
                .map(p -> ResponseEntity.created(URI.create(p.getIduser()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(user));
    }

    @GetMapping(value = "/buscar/{id}")
    public Single<ResponseEntity<User>> buscarPorId(@PathVariable int id) {
        return userService.buscarPorId(id)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/actualizar")
    public Maybe<ResponseEntity<User>>  actualizarUsuario(@Validated @RequestBody User user) {

        return userService.actualizar(user)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .onErrorReturnItem(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/actualizar/{id}")
    public Single<ResponseEntity<User>> actualizarUsuario2(@PathVariable int id, @RequestBody User user) {
        return userService.actualizarId(id, user)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/eliminar/{id}")
    public Single<ResponseEntity<Completable>> eliminarUsuario(@PathVariable("id") int id) {

        return userService.buscarPorId(id).filter(p -> true)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.eliminarPorId(Integer.parseInt(p.getIduser()))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    //metodo realizado con REACTOR
    @PostMapping("/subir/{id}")
    public Mono<ResponseEntity<User>> subirImagen(@PathVariable int id, @RequestPart FilePart file) throws IOException {

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqccbt9ko",
                "api_key", "738857697228694",
                "api_secret", "QS64lW23ZvbLlVcJvVFxPz78nmU"));

        File f = Files.createTempFile("temp", file.filename()).toFile();
        return file.transferTo(f)
                .then(userService.buscarUserForImagen(id)
                        .flatMap(c -> {
                            Map response;
                            try {
                                response = cloudinary.uploader().upload(f, ObjectUtils.asMap("resource_type", "auto"));
                                JSONObject json = new JSONObject(response);
                                String url = json.getAsString("url");

                                c.setUrlfoto(url);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return userService.actualizarImagen(c).then(Mono.just(ResponseEntity.ok().body(c)));
                        })
                        .defaultIfEmpty(ResponseEntity.notFound().build()
                        ));
    }


}
