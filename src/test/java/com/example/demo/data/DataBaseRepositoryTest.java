package com.example.demo.data;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseRepositoryTest {


    @Test
    void testDriver() {
        DataBaseConnector testConnector = new DataBaseConnector(("testDB"));
        assertTrue((testConnector.testDriver()));
    }

    @Test
    void getConnection() {
        DataBaseConnector testConnector = new DataBaseConnector(("testDB"));
        try {
            assertNotNull((testConnector.getConnection()));
        } catch (SQLException e) {
            fail();
        }
    }


    /*@Test
    void addFilm() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("test2"));
        repository.addFilm(new Film("test",
                "2023", new ArrayList<>(Arrays.asList("1", "2", "3")),".test",0, true));
        repository.addFilm(new Film("test1",
                "2022", new ArrayList<>(Arrays.asList("4", "5", "6")),".test",0, false));
        assertNotNull(repository.getById(2));
        java.util.List<Film> films = repository.getAll();
        assertTrue(films.size()>0);
    }*/

    /*@Test
    void deleteFilm() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("test2"));
        repository.deleteFilm(28);
        assertNull(repository.getById(28));
    }*/

    @Test
    void getAll() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("test2"));
        //System.out.println(repository.getAll());

    }

    @Test
    void getAllByActor(){
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("test2"));
        //System.out.println(repository.getAllByActor("4").size());
    }

    @Test
    void getAllByFormat(){
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("test2"));
        System.out.println(repository.getAllByFormat(".test"));
    }
}