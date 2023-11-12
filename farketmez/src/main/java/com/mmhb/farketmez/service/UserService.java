package com.mmhb.farketmez.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        if (id == null) {
            return null;
        }

        return userRepository.findById(id).orElse(null);
    }

    public String saveUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String name = user.getName();
        String surname = user.getSurname();
        int age = user.getAge();
        int gender = user.getGender();
        String longitude = user.getLongitude();
        String latitude = user.getLatitude();

        if (username == null || username.isEmpty() || password == null || password.isEmpty() || name == null
                || name.isEmpty() || surname == null || surname.isEmpty() || age <= 0 || gender <= 0
                || longitude == null || longitude.isEmpty() || latitude == null || latitude.isEmpty()) {
            return "Missing or incorrect user information. Please fill in all fields";
        }

        User userToSave = new User(username, password, name, surname, age, gender, longitude, latitude);

        userRepository.save(userToSave);
        return "User saved";
    }

    public String deleteUser(Long id) {
        if (id == null) {
            return "User id is null";
        }

        userRepository.deleteById(id);
        return "User deleted";
    }
}
