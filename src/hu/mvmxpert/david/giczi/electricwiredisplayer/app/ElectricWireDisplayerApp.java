package hu.mvmxpert.david.giczi.electricwiredisplayer.app;

import hu.mvmxpert.david.giczi.electricwiredisplayer.view.HomeWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class ElectricWireDisplayerApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		new HomeWindow();
	}

}
