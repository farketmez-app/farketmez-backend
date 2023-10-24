package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null) {
            return null;
        }

        if(id == null) {
            return null;
        }

        return userRepository.findById(id).orElse(null);
    }

    public String saveUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String nameSurname = user.getNameSurname();
        int age = user.getAge();
        int gender = user.getGender();
        String longitude = user.getLongitude();
        String latitude = user.getLatitude();

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                nameSurname == null || nameSurname.isEmpty() ||
                age <= 0 || gender <= 0 ||
                longitude == null || longitude.isEmpty() ||
                latitude == null || latitude.isEmpty()) {
            return "Eksik ya da hatalı kullanıcı bilgileri. Lütfen tüm alanları doldurun.";
        }

        User userToSave = new User(username,
                password, nameSurname, age, gender, longitude, latitude);

        userRepository.save(userToSave);
        return "User saved";
    }

    public String deleteUser(Long id) {
        if(id == null) {
            return "User id is null";
        }

        userRepository.deleteById(id);
        return "User deleted";
    }
}