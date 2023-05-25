package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import com.example.demo.data.Film;
import com.example.demo.data.Repository;
import com.example.demo.data.DataBaseConnector;
import com.example.demo.data.DataBaseRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    @FXML
    ListView listFilms;

    @FXML
    ComboBox<String> actorsCombo;

    @FXML
    ComboBox<String> formatsCombo;

    private Repository repository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //1 - Файловий репозиторій працює на додавання даних
        // як з існуючим файлов так і зі створенням нового файла
        // в поточній папці проекту
//        repository = new FileRepository("Autopark.data");

        // репозиторій з БД на сервері MySQL
        repository = new DataBaseRepository(
                new DataBaseConnector("carsShopDB"));
        updateListView();
        actorsCombo.setOnAction(event -> {
            String selectedActor = actorsCombo.getSelectionModel().getSelectedItem();
            filterByActor(selectedActor);
        });

        formatsCombo.setOnAction(event -> {
            String selectedFormat = formatsCombo.getSelectionModel().getSelectedItem();
            filterByFormat(selectedFormat);
        });
    }

    public void updateListView() {
        List<Film> films = repository.getAll();
        ObservableList<Film> filmsList = FXCollections.observableList(films);
        listFilms.setItems(filmsList);

        // Налаштування формату виводу елементів у ListView
        listFilms.setCellFactory(param -> new ListCell<Film>() {
            @Override
            protected void updateItem(Film item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("Назва: %s; Дата: %s; Актори: %s; Розширення файлу: %s; Розмір: %.2f", item.getName(), item.getDate(), String.join(", ", item.getActors()), item.getFileExtension(), item.getSize()));
                }
            }
        });




        List<String> actors = new ArrayList<>();
        actors.addAll(
                films
                        .stream()
                        .map(film -> film.getActors().stream().map(String::trim).collect(Collectors.toList()))
                        .flatMap(List::stream)
                        .distinct()
                        .collect(Collectors.toList())
        );
        actors = new ArrayList<>(new LinkedHashSet<>(actors));
        actors.add("all");
        ObservableList<String> actorsList =
                FXCollections.observableList(actors);
        actorsCombo.setItems(actorsList);
        actorsCombo.getSelectionModel().select(actors.size()-1);
        List<String> formats = films
                .stream()
                .map(Film::getFileExtension)
                .distinct()
                .collect(Collectors.toList());
        formats.add("all");
        ObservableList<String> formatsList = FXCollections.observableList(formats);
        formatsCombo.setItems(formatsList);
        formatsCombo.getSelectionModel().select(formats.size()-1);
    }



    @FXML
    public void deleteFilm(ActionEvent actionEvent) {
        Film toDelete = (Film) listFilms.getSelectionModel().getSelectedItem();
        repository.deleteFilm(toDelete.getId());
        updateListView();
    }

    @FXML
    public void addNewFilm(ActionEvent actionEvent) {
        Stage newWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(Lab_6.class.getResource(
                "add-film-form.fxml"
        )
        );
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newWindow.setTitle("Додати фільм");
        newWindow.setScene(new Scene(root, 450, 300));
        //newWindow.initModality(Modality.WINDOW_MODAL);
        AddFilmController secondController = loader.getController();
        secondController.set_repository(repository);
        secondController.set_mainController(this);
        newWindow.show();
    }

    public void filterByActor(String actor) {
        List<Film> films = null;
        if("all".equals(actor)) {
            films = repository.getAll();
        }else{
            films = repository.getAllByActor(actor);
        }
        ObservableList<Film> filmsList =
                FXCollections.observableList(films);
        listFilms.setItems(filmsList);
        formatsCombo.getSelectionModel().select("all");
    }

    public void filterByFormat(String format) {
        List<Film> films = null;
        if("all".equals(format)) {
            films = repository.getAll();
        }else{
            films = repository.getAllByFormat(format);
        }
        ObservableList<Film> filmsList =
                FXCollections.observableList(films);
        listFilms.setItems(filmsList);
        actorsCombo.getSelectionModel().select("all");
    }
}
