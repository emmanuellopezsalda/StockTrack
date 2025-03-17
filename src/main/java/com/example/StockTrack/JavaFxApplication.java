package com.example.StockTrack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;


public class JavaFxApplication extends Application{

    private static ApplicationContext springContext;

    @Override
    public void start(Stage stage) throws Exception {
        Label label = new Label("Prueba");
        Button button = new Button("Cerrar");
        button.setOnAction(e -> stage.close());

        VBox root = new VBox(10,label, button);
        Scene scene = new Scene(root, 400, 400);

        stage.setTitle("StockTrack");
        stage.setScene(scene);
        stage.show();
    }

    public static void setSpringContext(ApplicationContext springContext) {
        JavaFxApplication.springContext = springContext;
    }
}
