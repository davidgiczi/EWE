package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.SetWireDataController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SetWireDataWindow {

	private Stage stage;
	
	public Stage getStage() {
		return stage;
	}
	
	public SetWireDataWindow(HomeController homeController) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SetWireData.fxml"));
			AnchorPane root = loader.load();
			SetWireDataController controller = (SetWireDataController) loader.getController();
			controller.setHomeController(homeController);
			stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Távvezeték adatok megadása");
			stage.getIcons().add(new Image("/logo/MVM.jpg"));
			stage.show();
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
