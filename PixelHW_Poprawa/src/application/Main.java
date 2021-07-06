package application;

import java.awt.Dimension;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import window.View;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		View view = new View();
		view.setPrefSize(screenSize.getWidth() - 200, screenSize.getHeight() - 200);

		view.setMinSize(700, 400);

		Scene scene = new Scene(view);
		

		view.setScene(scene);
		view.setWorkspace();
		view.setNavBar();
		view.setMenuBar();

		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
