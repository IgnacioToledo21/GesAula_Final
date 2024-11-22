package org.gesaula.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.gesaula.model.Grupo;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GrupoController implements Initializable {

    // Modelo: Propiedades para gestionar los datos del grupo.
    private StringProperty denominacion = new SimpleStringProperty(); // Denominación del grupo.
    private ObjectProperty<LocalDate> inicioCurso = new SimpleObjectProperty<>(); // Fecha de inicio del curso.
    private ObjectProperty<LocalDate> finCurso = new SimpleObjectProperty<>(); // Fecha de fin del curso.
    private DoubleProperty examenes = new SimpleDoubleProperty(); // Peso de los exámenes.
    private DoubleProperty practicas = new SimpleDoubleProperty(); // Peso de las prácticas.
    private DoubleProperty actitud = new SimpleDoubleProperty(); // Peso de la actitud.

    private ObjectProperty<Grupo> grupo = new SimpleObjectProperty<>(); // Grupo actual.

    // Vista: Elementos de la interfaz de usuario definidos en el FXML.

    @FXML
    private GridPane view; // Panel principal.

    @FXML
    private TextField denominacionText; // Campo de texto para la denominación del grupo.

    @FXML
    private DatePicker inicioCursoDatePicker; // Selector de fecha para el inicio del curso.

    @FXML
    private DatePicker finCursoDatePicker; // Selector de fecha para el fin del curso.

    @FXML
    private Slider examenesSlider; // Control deslizante para el peso de los exámenes.

    @FXML
    private Slider practicasSlider; // Control deslizante para el peso de las prácticas.

    @FXML
    private Slider actitudSlider; // Control deslizante para el peso de la actitud.

    @FXML
    private Label examenesLabel; // Etiqueta para mostrar el porcentaje de exámenes.

    @FXML
    private Label practicasLabel; // Etiqueta para mostrar el porcentaje de prácticas.

    @FXML
    private Label actitudLabel; // Etiqueta para mostrar el porcentaje de actitud.

    // Constructor: Carga la vista desde el archivo FXML.
    public GrupoController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GrupoView.fxml"));
        loader.setController(this);
        loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Enlaces (bindings) entre el modelo y la vista.

        denominacionText.textProperty().bindBidirectional(denominacion); // Enlace para la denominación.
        inicioCursoDatePicker.valueProperty().bindBidirectional(inicioCurso); // Enlace para la fecha de inicio del curso.
        finCursoDatePicker.valueProperty().bindBidirectional(finCurso); // Enlace para la fecha de fin del curso.
        examenesSlider.valueProperty().bindBidirectional(examenes); // Enlace para el peso de los exámenes.
        practicasSlider.valueProperty().bindBidirectional(practicas); // Enlace para el peso de las prácticas.
        actitudSlider.valueProperty().bindBidirectional(actitud); // Enlace para el peso de la actitud.

        // Enlace para mostrar el valor del slider como texto en las etiquetas.
        examenesLabel.textProperty().bind(examenes.asString("%.0f").concat("%"));
        practicasLabel.textProperty().bind(practicas.asString("%.0f").concat("%"));
        actitudLabel.textProperty().bind(actitud.asString("%.0f").concat("%"));

        // Listener para detectar cambios en el grupo actual.
        grupo.addListener(this::onGrupoChanged);
    }

    /**
     * Método que se ejecuta al cambiar el grupo actual.
     * @param o ObservableValue que representa la propiedad del grupo.
     * @param ov Valor anterior del grupo.
     * @param nv Nuevo valor del grupo.
     */
    private void onGrupoChanged(ObservableValue<? extends Grupo> o, Grupo ov, Grupo nv) {

        // Si había un grupo previo, desvincula sus propiedades.
        if (ov != null) {
            denominacion.unbindBidirectional(ov.denominacionProperty());
            inicioCurso.unbindBidirectional(ov.iniCursoProperty());
            finCurso.unbindBidirectional(ov.finCursoProperty());
            examenes.unbindBidirectional(ov.getPesos().examenesProperty());
            practicas.unbindBidirectional(ov.getPesos().practicasProperty());
            actitud.unbindBidirectional(ov.getPesos().actitudProperty());
        }

        // Si hay un nuevo grupo, vincula sus propiedades.
        if (nv != null) {
            denominacion.bindBidirectional(nv.denominacionProperty());
            inicioCurso.bindBidirectional(nv.iniCursoProperty());
            finCurso.bindBidirectional(nv.finCursoProperty());
            examenes.bindBidirectional(nv.getPesos().examenesProperty());
            practicas.bindBidirectional(nv.getPesos().practicasProperty());
            actitud.bindBidirectional(nv.getPesos().actitudProperty());
        }
    }

    // Devuelve la vista principal.
    public GridPane getView() {
        return view;
    }

    // Devuelve la propiedad del grupo (observable).
    public ObjectProperty<Grupo> grupoProperty() {
        return this.grupo;
    }

    // Obtiene el grupo actual.
    public Grupo getGrupo() {
        return this.grupoProperty().get();
    }

    // Establece un nuevo grupo.
    public void setGrupo(final Grupo grupo) {
        this.grupoProperty().set(grupo);
    }
}