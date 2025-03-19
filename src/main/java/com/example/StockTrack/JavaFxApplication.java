package com.example.StockTrack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;


public class JavaFxApplication extends Application{

    private static ApplicationContext springContext;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sale_view.fxml"));
        loader.setControllerFactory(springContext::getBean);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("StockTrack");
        stage.show();
    }


    public static void setSpringContext(ApplicationContext springContext) {
        JavaFxApplication.springContext = springContext;
    }
}
