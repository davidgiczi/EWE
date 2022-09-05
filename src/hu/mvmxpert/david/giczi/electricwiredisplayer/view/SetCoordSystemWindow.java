package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SetCoordSystemWindow  {

		
	public SetCoordSystemWindow() {
		
		try {
			Pane root = FXMLLoader.load(getClass().getResource("/Setcoordsystem.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle(Validate.isValidProjectName(HomeController.PROJECT_NAME) ? 
					 HomeController.PROJECT_NAME + " - Rajzi rendszer beállítások megadása" : "Rajzi rendszer beállítások megadása");
			stage.getIcons().add(new Image("/logo/MVM.jpg"));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}
