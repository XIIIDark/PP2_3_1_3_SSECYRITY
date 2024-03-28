package ru.kata.spring.boot_security.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

//@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("SELECT DISTINCT u FROM User u left join fetch u.roleList where u.firstName = :firstname")
    User findUserByFirstName(String firstName);

}
