package org.gesaula.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import org.gesaula.GesAulaApp;
import org.gesaula.model.Grupo;

public class ToolbarController implements Initializable {

    // model

    private StringProperty nombreFichero = new SimpleStringProperty();
    private ObjectProperty<Grupo> grupo = new SimpleObjectProperty<>();

    // view

    @FXML
    private ToolBar view;

    @FXML
    private Button nuevoButton;

    @FXML
    private TextField ficheroText;

    @FXML
    private Button guardarButton;

    //Constructor que carga la vista desde el archivo FXML y establece este controlador
    public ToolbarController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ToolbarView.fxml"));
        loader.setController(this);
        loader.load();
    }

    //Metodo de inicializacion que vincula el nombre del fichero al campo de texto
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nombreFichero.bind(ficheroText.textProperty());

    }
    //Devuelve la vista de la barra de herramientas
    public ToolBar getView() {
        return view;
    }
    //Accion que se ejecuta al pulsar el boton guardar
    @FXML
    void onGuardarAction(ActionEvent event) {
        String ruta = nombreFichero.get();
        if (ruta != null && !ruta.isEmpty()) {
            try {
                getGrupo().save(new File(ruta));

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.initOwner(GesAulaApp.primaryStage);
                alert.setTitle("Guardar grupo");
                alert.setHeaderText("Se ha guardado el grupo correctamente.");
                alert.setContentText("El grupo " + getGrupo().getDenominacion() + " se ha guardado en el fichero '" + ruta + "'.");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(GesAulaApp.primaryStage);
                alert.setTitle("Guardar grupo");
                alert.setHeaderText("Error al guardar el grupo.");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(GesAulaApp.primaryStage);
            alert.setTitle("Guardar grupo");
            alert.setHeaderText("Error al guardar el grupo.");
            alert.setContentText("Debe especificar la ruta del fichero donde se guardar� el grupo.");
            alert.showAndWait();
        }
    }
    //Accion que se ejecuta al pulsar el boton nuevo
    @FXML
    void onNuevoAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(GesAulaApp.primaryStage);
        alert.setTitle("Nuevo grupo");
        alert.setHeaderText("Va a crear un grupo nuevo.");
        alert.setContentText("¿Está seguro?");
        Optional<ButtonType> opcion = alert.showAndWait();
        if (opcion.get().equals(ButtonType.OK)) {
            grupo.set(new Grupo());
        }
    }
    //Devuelve la propiedad observable del grupo
    public ObjectProperty<Grupo> grupoProperty() {
        return this.grupo;
    }
    //Devuelve el grupo actual
    public Grupo getGrupo() {
        return this.grupoProperty().get();
    }
    //Establece un nuevo grupo
    public void setGrupo(final Grupo grupo) {
        this.grupoProperty().set(grupo);
    }

}