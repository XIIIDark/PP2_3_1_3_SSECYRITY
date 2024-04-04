package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserByID(Long id);

    User updateUser(User user);

    void deleteUserByID(Long id);

    User createUser(User user);


}
