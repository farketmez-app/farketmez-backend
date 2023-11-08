package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.UserDTO;
import com.mmhb.farketmez.model.User;

public class UserMapper {
    private UserMapper() {

    }

    public static UserDTO toUserDto(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getAge(),
                user.getGender(), user.getLongitude(), user.getLatitude(), user.getUserType(), user.getCreatedAt(),
                user.getUpdatedAt(), user.getDeletedAt());
    }


    public static User fromUserDto(UserDTO userDto) {
        return new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getName(), userDto.getSurname(), userDto.getAge(), userDto.getGender(),
                userDto.getLongitude(), userDto.getLatitude(), userDto.getCreatedAt(), userDto.getUpdatedAt(), userDto.getDeletedAt(), userDto.getUserType());

    }
}