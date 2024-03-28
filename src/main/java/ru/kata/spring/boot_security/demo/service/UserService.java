package ru.kata.spring.boot_security.demo.service;



import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserByID(Long id);

    void updateUser(User user);

    void deleteUserByID(Long id);

    void createUser(User user);


}
