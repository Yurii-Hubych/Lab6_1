package com.example.demo;

import com.example.demo.data.Film;
import com.example.demo.data.Repository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;


public class AddFilmController {

    private Repository _repository;

    private MainController _mainController;

    public void set_mainController(MainController _mainController) {
        this._mainController = _mainController;
    }

    public void set_repository(Repository _repository) {
        this._repository = _repository;
    }

    @FXML
    TextField name;

    @FXML
    TextField date;

    @FXML
    TextField actors;

    @FXML
    TextField fileExtension;

    @FXML
    TextField size;

    @FXML
    CheckBox isFreeForAll;


    public void addFilmToFile(ActionEvent actionEvent) {
        String name_ = name.getText();
        String date_ = date.getText();
        List<String> actors_ = Arrays.asList(actors.getText().split(","));
        String fileExtension_ = fileExtension.getText();
        double size_ = Double.parseDouble(size.getText());
        boolean isFreeForAll_ = isFreeForAll.isSelected();

        Film newFilm = new Film(name_, date_, actors_, fileExtension_, size_, isFreeForAll_);
        _repository.addFilm(newFilm);
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        _mainController.updateListView();
        stage.close();

    }
}
