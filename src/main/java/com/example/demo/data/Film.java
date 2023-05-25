package com.example.demo.data;

import java.io.Serializable;
import java.util.List;

public class Film implements Serializable {

    private int id;
    private final String name;
    private final String date;
    private final List<String> actors;
    private String fileExtension;
    private double size;

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    private boolean isFreeForAll;

    public Film(int id, String name, String date, List<String> actors, String fileExtension, double size, boolean isFreeForAll) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.actors = actors;
        this.fileExtension = fileExtension;
        this.size = size;
        this.isFreeForAll = isFreeForAll;
    }

    public Film(String name, String date, List<String> actors, String fileExtension, double size, boolean isFreeForAll) {
        this.id = 0;
        this.name = name;
        this.date = date;
        this.actors = actors;
        this.fileExtension = fileExtension;
        this.size = size;
        this.isFreeForAll = isFreeForAll;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getActors() {
        return actors;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFile_extension(String file_extension) {
        this.fileExtension = fileExtension;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isFreeForAll() {
        return isFreeForAll;
    }

    public void setFreeForAll(boolean freeForAll) {
        isFreeForAll = freeForAll;
    }

    @Override
    public String toString() {
        return "Film{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", actors=" + actors +
                ", file_extension='" + fileExtension + '\'' +
                ", size=" + size +
                ", isFreeForAll=" + isFreeForAll +
                '}';
    }
}
