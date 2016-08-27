package com.epam.springadvanced.repository;

import java.util.List;

import com.epam.springadvanced.entity.Win;

public interface WinsRepository
{
    Win save(Win win);

    List<Win> getAll();

    List<Win> getByUserId(long userId);

    void delete(long userId);
}
