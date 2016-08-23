package com.epam.springadvanced.repository;

import java.util.Collection;

import com.epam.springadvanced.entity.User;

public interface UserRepository
{
    User save(User user);

    void delete(long id);

    User findById(long id);

    User findByEmail(String email);

    User findByName(String name);

    Collection<User> getAll();
}
