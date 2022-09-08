package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.SetDrawingSystemDataController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SetDrawingSystemDataWindow  {

	private Stage stage;
	
	public Stage getStage() {
		return stage;
	}

	public SetDrawingSystemDataWindow(HomeController homeController) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SetDrawingSystemData.fxml"));
			AnchorPane root = loader.load();
			SetDrawingSystemDataController controller =	(SetDrawingSystemDataController) loader.getController();
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
