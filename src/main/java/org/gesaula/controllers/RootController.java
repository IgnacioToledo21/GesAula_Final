package org.gesaula.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import org.gesaula.model.Grupo;

public class RootController implements Initializable {

    // Modelo: Propiedad para gestionar el grupo actual.
    private ObjectProperty<Grupo> grupo = new SimpleObjectProperty<>();

    // Vista: Panel principal de la aplicación.
    @FXML
    private BorderPane view;

    // Vista: Pestaña para la gestión del grupo.
    @FXML
    private Tab grupoTab;

    // Vista: Pestaña para la gestión de los alumnos.
    @FXML
    private Tab alumnosTab;

    // Subcontroladores: Controlador de la barra de herramientas.
    private ToolbarController toolbarController = new ToolbarController();
    // Subcontroladores: Controlador para la gestión del grupo.
    private GrupoController grupoController = new GrupoController();
    // Subcontroladores: Controlador para la gestión de los alumnos.
    private AlumnosController alumnosController = new AlumnosController();

    // Constructor: Carga la vista principal desde el archivo FXML.
    public RootController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
        loader.setController(this);
        loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Composición de la vista con los subcontroladores.
        view.setTop(toolbarController.getView()); // Establece la barra de herramientas en la parte superior.
        grupoTab.setContent(grupoController.getView()); // Asocia la vista del controlador de grupo a su pestaña.
        alumnosTab.setContent(alumnosController.getView()); // Asocia la vista del controlador de alumnos a su pestaña.

        // Enlaza la propiedad del grupo entre el controlador de barra y el controlador de grupo.
        toolbarController.grupoProperty().bindBidirectional(grupo);
        grupoController.grupoProperty().bind(grupo);

        // Añade un listener para reaccionar a cambios en la propiedad grupo.
        grupo.addListener(this::onGrupoChanged);

        // Inicializa el modelo con un grupo vacío.
        grupo.set(new Grupo());
    }

    /**
     * Listener que se activa cuando cambia el grupo actual.
     * @param o ObservableValue que representa la propiedad del grupo.
     * @param ov Valor antiguo del grupo.
     * @param nv Nuevo valor del grupo.
     */
    private void onGrupoChanged(ObservableValue<? extends Grupo> o, Grupo ov, Grupo nv) {
        // Si había un grupo previo, desvincula su lista de alumnos.
        if (ov != null) {
            alumnosController.alumnosProperty().unbind();
        }

        // Si hay un nuevo grupo, vincula su lista de alumnos al controlador de alumnos.
        if (nv != null) {
            alumnosController.alumnosProperty().bind(nv.alumnosProperty());
        }
    }

    // Devuelve la vista principal.
    public BorderPane getView() {
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