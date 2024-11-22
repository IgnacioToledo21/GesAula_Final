package org.gesaula.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import org.gesaula.GesAulaApp;
import org.gesaula.model.Alumno;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

public class AlumnosController implements Initializable {

    // Controlador para la edición de alumnos.
    private AlumnoEditController alumnoEditController = new AlumnoEditController();

    // Modelo: Lista de alumnos y alumno seleccionado.
    private ListProperty<Alumno> alumnos = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ObjectProperty<Alumno> seleccionado = new SimpleObjectProperty<>();

    // Vista: Componentes de la interfaz definidos en el FXML.

    @FXML
    private SplitPane view; // Vista principal.

    @FXML
    private TableView<Alumno> alumnosTable; // Tabla que muestra la lista de alumnos.

    @FXML
    private TableColumn<Alumno, String> nombreColumn; // Columna para el nombre de los alumnos.

    @FXML
    private TableColumn<Alumno, String> apellidosColumn; // Columna para los apellidos de los alumnos.

    @FXML
    private TableColumn<Alumno, LocalDate> nacimientoColumn; // Columna para la fecha de nacimiento de los alumnos.

    @FXML
    private Button nuevoButton; // Botón para agregar un nuevo alumno.

    @FXML
    private Button eliminarbutton; // Botón para eliminar un alumno seleccionado.

    @FXML
    private BorderPane rightPane; // Panel derecho que muestra detalles del alumno seleccionado.

    @FXML
    private VBox noAlumnoPane; // Vista que se muestra cuando no hay un alumno seleccionado.

    // Constructor: Carga la vista desde el archivo FXML.
    public AlumnosController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AlumnosView.fxml"));
        loader.setController(this);
        loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Enlaces (bindings) entre el modelo y la vista.

        alumnosTable.itemsProperty().bind(alumnos); // Enlace entre la tabla y la lista de alumnos.
        seleccionado.bind(alumnosTable.getSelectionModel().selectedItemProperty()); // Enlace para el alumno seleccionado.

        // Configuración de las fábricas de celdas para las columnas.

        nombreColumn.setCellValueFactory(v -> v.getValue().nombreProperty()); // Obtiene el nombre del alumno.
        apellidosColumn.setCellValueFactory(v -> v.getValue().apellidosProperty()); // Obtiene los apellidos del alumno.
        nacimientoColumn.setCellValueFactory(v -> v.getValue().fechaNacimientoProperty()); // Obtiene la fecha de nacimiento.

        // Configuración para que la columna de fecha muestre datos con formato adecuado.
        nacimientoColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

        // Vincula el alumno seleccionado al controlador de edición.
        alumnoEditController.alumnoProperty().bind(seleccionado);

        // Listener para manejar cambios en el alumno seleccionado.
        seleccionado.addListener(this::onSeleccionadoChanged);
    }

    /**
     * Método que se ejecuta al cambiar el alumno seleccionado.
     * Muestra el panel de edición si hay un alumno seleccionado; de lo contrario, muestra un mensaje.
     *
     * @param o ObservableValue que representa la propiedad del alumno seleccionado.
     * @param ov Valor anterior del alumno seleccionado.
     * @param nv Nuevo valor del alumno seleccionado.
     */
    private void onSeleccionadoChanged(ObservableValue<? extends Alumno> o, Alumno ov, Alumno nv) {
        if (nv != null) {
            rightPane.setCenter(alumnoEditController.getView()); // Muestra el formulario de edición.
        } else {
            rightPane.setCenter(noAlumnoPane); // Muestra un panel indicando que no hay selección.
        }
    }

    // Devuelve la vista principal.
    public SplitPane getView() {
        return view;
    }

    /**
     * Acción del botón para eliminar el alumno seleccionado.
     * Si no hay selección, muestra un mensaje de error. Si hay selección, confirma la eliminación.
     */
    @FXML
    void onEliminarAlumnoAction(ActionEvent event) {
        Alumno seleccionado = alumnosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            // Muestra un mensaje de error si no hay selección.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(GesAulaApp.primaryStage);
            alert.setTitle("Eliminar alumno");
            alert.setHeaderText("Error al intentar eliminar un alumno.");
            alert.setContentText("No se ha seleccionado ningún alumno.");
            alert.showAndWait();
        } else {
            // Confirma la eliminación del alumno seleccionado.
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initOwner(GesAulaApp.primaryStage);
            alert.setTitle("Eliminar alumno");
            alert.setHeaderText("Se va a eliminar al alumno '" + seleccionado + "'.");
            alert.setContentText("¿Está seguro?");
            Optional<ButtonType> opcion = alert.showAndWait();
            if (opcion.get().equals(ButtonType.OK)) {
                this.alumnos.remove(seleccionado); // Elimina al alumno de la lista.
            }
        }
    }

    /**
     * Acción del botón para agregar un nuevo alumno.
     * Se crea un alumno con valores por defecto y se añade a la lista.
     */
    @FXML
    void onNuevoAlumnoAction(ActionEvent event) {
        Alumno nuevo = new Alumno();
        nuevo.setNombre("Sin nombre");
        nuevo.setApellidos("Sin apellidos");
        this.alumnos.add(nuevo); // Añade el nuevo alumno a la lista.
    }

    // Devuelve la propiedad de la lista de alumnos (observable).
    public ListProperty<Alumno> alumnosProperty() {
        return this.alumnos;
    }

    // Obtiene la lista de alumnos.
    public ObservableList<Alumno> getAlumnos() {
        return this.alumnosProperty().get();
    }

    // Establece una nueva lista de alumnos.
    public void setAlumnos(final ObservableList<Alumno> alumnos) {
        this.alumnosProperty().set(alumnos);
    }
}