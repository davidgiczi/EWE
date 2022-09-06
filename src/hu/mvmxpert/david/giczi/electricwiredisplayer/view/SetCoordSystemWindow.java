package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.SetCoordSystemController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SetCoordSystemWindow  {

	@SuppressWarnings("unused")
	private HomeController homeController;
	private Stage stage;
	
	public Stage getStage() {
		return stage;
	}

	public SetCoordSystemWindow(HomeController homeController) {
		
		this.homeController = homeController;
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SetDrawSystemData.fxml"));
			AnchorPane root = loader.load();
			SetCoordSystemController controller =	(SetCoordSystemController) loader.getController();
			controller.setHomeController(homeController);
			stage = new Stage();
			controller.setStage(stage);
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
