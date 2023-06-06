package hu.mvmxpert.david.giczi.electricwireeditor.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwireeditor.controller.SaveWireCoordsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SaveWireCoordsWindow {

	
	private Stage stage;
	public SaveWireCoordsController controller;
	
	public Stage getStage() {
		return stage;
	}
	
	public SaveWireCoordsWindow(HomeController homeController) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SaveWireCoords.fxml"));
			AnchorPane root = loader.load();
			controller = (SaveWireCoordsController) loader.getController();
			controller.setHomeController(homeController);
			stage = new Stage();
			Scene scene = new Scene(root);
			stage.initOwner(homeController.homeWindow.primaryStage);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Távvezeték pontok fájlba mentése");
			stage.getIcons().add(new Image("/logo/MVM.jpg"));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
