package com.example.demo.data;

import java.util.List;

public interface Repository {

    List<Film> getAll();

    Film getById(int id);

    boolean addFilm(Film car);

    boolean deleteFilm(int id);

    List<Film> getAllByActor(String actor);

    List<Film> getAllByFormat(String format);
}
