package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void addNewUser(User user);

    public User findById(Integer id);

    public void updateUser(User user);

    public void deleteUser(Integer id);

    public User findByUsername(String username);

}
