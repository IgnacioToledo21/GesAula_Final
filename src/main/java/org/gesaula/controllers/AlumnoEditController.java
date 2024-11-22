package org.gesaula.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.gesaula.model.Alumno;
import org.gesaula.model.Sexo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AlumnoEditController implements Initializable {

    // Modelo: Propiedades para los datos del alumno.

    private StringProperty nombre = new SimpleStringProperty(); // Propiedad para el nombre del alumno.
    private StringProperty apellidos = new SimpleStringProperty(); // Propiedad para los apellidos del alumno.
    private ObjectProperty<LocalDate> nacimiento = new SimpleObjectProperty<>(); // Propiedad para la fecha de nacimiento.
    private ObjectProperty<Sexo> sexo = new SimpleObjectProperty<>(); // Propiedad para el sexo del alumno.
    private BooleanProperty repite = new SimpleBooleanProperty(); // Propiedad para indicar si el alumno repite curso.

    private ObjectProperty<Alumno> alumno = new SimpleObjectProperty<>(); // Propiedad para el alumno actual.

    // Vista: Componentes definidos en el archivo FXML.

    @FXML
    private GridPane view; // Panel principal de la vista.

    @FXML
    private TextField nombreText; // Campo de texto para el nombre del alumno.

    @FXML
    private TextField apellidosText; // Campo de texto para los apellidos del alumno.

    @FXML
    private DatePicker nacimientoDatePicker; // Selector de fecha para la fecha de nacimiento.

    @FXML
    private ComboBox<Sexo> sexoCombo; // ComboBox para seleccionar el sexo del alumno.

    @FXML
    private CheckBox repiteCheck; // CheckBox para indicar si el alumno repite curso.

    // Constructor: Carga la vista desde el archivo FXML.
    public AlumnoEditController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AlumnoEditView.fxml"));
        loader.setController(this);
        loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Enlaces (bindings) entre las propiedades del modelo y los componentes de la vista.

        nombreText.textProperty().bindBidirectional(nombre); // Enlace bidireccional con el campo de texto para el nombre.
        apellidosText.textProperty().bindBidirectional(apellidos); // Enlace bidireccional con el campo de texto para los apellidos.
        nacimientoDatePicker.valueProperty().bindBidirectional(nacimiento); // Enlace bidireccional con el selector de fecha.
        sexoCombo.valueProperty().bindBidirectional(sexo); // Enlace bidireccional con el ComboBox de sexo.
        repiteCheck.selectedProperty().bindBidirectional(repite); // Enlace bidireccional con el CheckBox de repetición.

        // Añade las opciones de sexo al ComboBox.
        sexoCombo.getItems().addAll(Sexo.values());

        // Listener para detectar cambios en el alumno seleccionado.
        alumno.addListener(this::onAlumnoChanged);
    }

    /**
     * Método que se ejecuta cuando cambia el alumno seleccionado.
     * Desvincula las propiedades del modelo anterior y vincula las del nuevo alumno.
     *
     * @param o  ObservableValue que representa la propiedad del alumno.
     * @param ov Alumno anterior.
     * @param nv Nuevo alumno.
     */
    private void onAlumnoChanged(ObservableValue<? extends Alumno> o, Alumno ov, Alumno nv) {

        if (ov != null) {
            // Desvincula las propiedades del alumno anterior.
            nombre.unbindBidirectional(ov.nombreProperty());
            apellidos.unbindBidirectional(ov.apellidosProperty());
            nacimiento.unbindBidirectional(ov.fechaNacimientoProperty());
            sexo.unbindBidirectional(ov.sexoProperty());
            repite.unbindBidirectional(ov.repiteProperty());
        }

        if (nv != null) {
            // Vincula las propiedades al nuevo alumno.
            nombre.bindBidirectional(nv.nombreProperty());
            apellidos.bindBidirectional(nv.apellidosProperty());
            nacimiento.bindBidirectional(nv.fechaNacimientoProperty());
            sexo.bindBidirectional(nv.sexoProperty());
            repite.bindBidirectional(nv.repiteProperty());
        }
    }

    // Devuelve la vista principal.
    public GridPane getView() {
        return view;
    }

    // Devuelve la propiedad del alumno.
    public ObjectProperty<Alumno> alumnoProperty() {
        return this.alumno;
    }

    // Obtiene el alumno actual.
    public Alumno getAlumno() {
        return this.alumnoProperty().get();
    }

    // Establece un nuevo alumno.
    public void setAlumno(final Alumno alumno) {
        this.alumnoProperty().set(alumno);
    }
}