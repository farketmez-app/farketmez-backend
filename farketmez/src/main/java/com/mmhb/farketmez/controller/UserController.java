package com.mmhb.farketmez.controller;

import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    example GET request to http://localhost:8080/users

    response: List of all users
    */
    @GetMapping
    public Object getAllUsers() {
        return this.userService.getAllUsers();
    }

    /*
    example GET request to http://localhost:8080/users/1

    response: User with id 1
    */
    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable Long id) {
        return this.userService.getUserById(id);
    }

    /*
    example POST request to http://localhost:8080/users/save with body:
    {
     "username": "test",
     "password": "test",
     "nameSurname": "test",
      "age":12,
    "gender":1,
     "longitude":"test",
     "latitude":"test"
     }

     response: "User saved" || "Eksik ya da hatalı kullanıcı bilgileri. Lütfen tüm alanları doldurun."
     */
    @PostMapping(value = "save")
    public String saveUser(@RequestBody User user) {
        return this.userService.saveUser(user);
    }

    /*
    example DELETE request to http://localhost:8080/users/1

    response: "User deleted" || "User not found"
     */
    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable Long id) {
        return this.userService.deleteUser(id);
    }


}