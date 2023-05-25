package com.example.demo.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import com.example.demo.data.DataBaseRepository;

public class FileRepository implements Repository{

    private String fileName;

    private List<Film> films;

    public FileRepository(String fileName) {
        this.films = new ArrayList<>();
        this.fileName = fileName;
    }

    @Override
    public List<Film> getAll() {
        reloadData();
        return films;
    }

    private void reloadData() {
        if ((new File(fileName)).exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                    films = (List<Film>) objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }
    }


    @Override
    public Film getById(int id) {
        return null;
    }

    @Override
    public boolean addFilm(Film film) {
        int id = 0;
        if(this.films.size()>0) {
            OptionalInt maxId = this.films.stream().mapToInt(c -> c.getId()).max();
            if (maxId != null) {
                id = maxId.getAsInt();
            }
        }
        film.setId(id+1);
        films.add(film);

        try {
            save();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private void save() throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)){
            try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
                objectOutputStream.writeObject(this.films);
            }
        }
    }

    @Override
    public List<Film> getAllByActor(String actor) {
        return null;
    }

    @Override
    public boolean deleteFilm(int id) {
        return false;
    }

    @Override
    public List<Film> getAllByFormat(String format){
        return null;
    }
}
