package org.example.limitchecker.repository;

import org.example.limitchecker.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UserStorage {

    private final List<User> userList = new ArrayList<>();

    public UserStorage() {
        userList.add(new User("John"));
        userList.add(new User("Mike"));
        userList.add(new User("Sam"));
        userList.add(new User("Charlie"));
        userList.add(new User("Robert"));
        userList.add(new User("Steven"));
        userList.add(new User("Corey"));
        userList.add(new User("Barbara"));
        userList.add(new User("Michele"));
        userList.add(new User("Sarah"));
    }

    public User getByName(String name) {
        return userList.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public User getRandom() {
        Random random = new Random();
        return userList.get(random.nextInt(userList.size()));
    }

    public List<User> getUserList() {
        return Collections.unmodifiableList(userList);
    }
}