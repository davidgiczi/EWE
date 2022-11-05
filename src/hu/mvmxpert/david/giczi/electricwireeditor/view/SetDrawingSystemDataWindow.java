package hu.mvmxpert.david.giczi.electricwireeditor.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwireeditor.controller.SetDrawingSystemDataController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SetDrawingSystemDataWindow  {

	private Stage stage;
	private SetDrawingSystemDataController controller;
	
	public Stage getStage() {
		return stage;
	}
	
	public SetDrawingSystemDataController getController() {
		return controller;
	}

	public SetDrawingSystemDataWindow(HomeController homeController) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SetDrawingSystemData.fxml"));
			AnchorPane root = loader.load();
			controller =	(SetDrawingSystemDataController) loader.getController();
			controller.setHomeController(homeController);
			stage = new Stage();
			controller.setStage(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Rajzi rendszer beállítások megadása");
			stage.getIcons().add(new Image("/logo/MVM.jpg"));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}
