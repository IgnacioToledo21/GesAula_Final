package org.gesaula;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.gesaula.controllers.RootController;

public class GesAulaApp extends Application {

    // Referencia estática al Stage principal para facilitar su acceso desde otras partes de la aplicación.
    public static Stage primaryStage;

    // Controlador principal que gestiona la vista raíz de la aplicación.
    private RootController rootController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Asigna el Stage principal a la variable estática.
        GesAulaApp.primaryStage = primaryStage;

        // Crea e inicializa el controlador de la vista raíz.
        rootController = new RootController();

        // Crea una escena asociando la vista raíz proporcionada por el controlador.
        Scene scene = new Scene(rootController.getView(), 640, 480);

        // Configura un icono para la aplicación desde un archivo de recursos.
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/app-icon-64x64.png").toExternalForm()));

        // Establece el título de la ventana principal.
        primaryStage.setTitle("GesAula");

        // Asocia la escena al Stage principal y muestra la ventana.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metodo principal para lanzar la aplicación.
    public static void main(String[] args) {
        launch(args); // Inicia la aplicación JavaFX.
    }
}
