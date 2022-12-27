package hu.mvmxpert.david.giczi.electricwireeditor.app;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ElectricWireEditorApp extends Application {

	public static void main(String[] args) {
		launch(args);
	
	}

	@Override
	public void start(Stage arg0) throws Exception {
		new HomeController();
	}

}
