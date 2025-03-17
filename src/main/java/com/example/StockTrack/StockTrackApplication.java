package com.example.StockTrack;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockTrackApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(StockTrackApplication.class, args);

		JavaFxApplication.setSpringContext(context);
		Application.launch(JavaFxApplication.class, args);

	}

}
