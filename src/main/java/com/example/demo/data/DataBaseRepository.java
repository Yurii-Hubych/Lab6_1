package com.example.demo.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseRepository implements  Repository{

    private DataBaseConnector dataBaseConnector;

    public DataBaseRepository(DataBaseConnector dataBaseConnector) {

        this.dataBaseConnector = dataBaseConnector;
        try (Connection conn = dataBaseConnector.getConnection()) {
            String tableCreateStr =
                    "CREATE TABLE IF NOT EXISTS Films (\n" +
                            "    id INT NOT NULL AUTO_INCREMENT,\n" +
                            "    Name VARCHAR(50),\n" +
                            "    Date VARCHAR(50),\n" +
                            "    Actors TEXT,\n" +
                            "    FileExtension VARCHAR(50),\n" +
                            "    Size DOUBLE,\n" +
                            "    IsFreeForAll BOOLEAN,\n" +
                            "    PRIMARY KEY (id)\n" +
                            ");";

            Statement createTable = conn.createStatement();
            createTable.execute(tableCreateStr);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Film> getAll() {
        List<Film> films = new ArrayList<>();

        try(Connection connection = dataBaseConnector.getConnection()){

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Films");
            while(rs.next()){
                String actors = rs.getString(4);
                List<String> list = Arrays.asList(actors.split(","));
                films.add(new Film(rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                list,
                rs.getString(5),
                rs.getInt(6),
                rs.getBoolean(7)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
       }
        return films;
    }

    @Override
    public Film getById(int id) {
        Film film = null;
        try(Connection connection = dataBaseConnector.getConnection()){
            PreparedStatement statement = connection.prepareStatement("select * from Films where id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                String actors = rs.getString(4);
                List<String> list = Arrays.asList(actors.split(","));
                 film = new Film(
                         rs.getInt(1),
                         rs.getString(2),
                         rs.getString(3),
                         list,
                         rs.getString(5),
                         rs.getInt(6),
                         rs.getBoolean(7));
            }
            rs.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            return film;
        }
    }

    @Override
    public boolean addFilm(Film film) {
        int updCount = 0;
        String actors = String.join(",", film.getActors());
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Films (Name, Date, Actors, FileExtension, Size, IsFreeForAll) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getDate());
            preparedStatement.setString(3, actors);
            preparedStatement.setString(4, film.getFileExtension());
            preparedStatement.setDouble(5, film.getSize());
            preparedStatement.setBoolean(6, film.isFreeForAll());
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount>0;
    }

    @Override
    public boolean deleteFilm(int id) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM Films WHERE id = ?");
            preparedStatement.setInt(1, id);
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount>0;
    }

    @Override
    public List<Film> getAllByActor(String actor) {
        List<Film> films = new ArrayList<>();
        try (Connection connection = dataBaseConnector.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(
                            "select * from Films where Actors Like(?)"
                    );
            statement.setString(1, "%" + actor + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String actors = rs.getString(4);
                List<String> list = Arrays.asList(actors.split(","));
                films.add(new Film(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        list,
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getBoolean(7)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return films;
    }

    public List<Film> getAllByFormat(String FileExtension){
        List<Film> films = new ArrayList<>();
        try (Connection connection = dataBaseConnector.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(
                            "select * from Films where FileExtension Like(?)"
                    );
            statement.setString(1, "%" + FileExtension + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String actors = rs.getString(4);
                List<String> list = Arrays.asList(actors.split(","));
                films.add(new Film(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        list,
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getBoolean(7)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return films;
    }
}
