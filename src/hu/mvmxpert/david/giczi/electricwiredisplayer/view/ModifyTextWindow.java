package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.ModifyTextController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModifyTextWindow {
	
	private Stage stage;
	
	public Stage getStage() {
		return stage;
	}
	
	public ModifyTextWindow(HomeController homeController) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyText.fxml"));
			AnchorPane root = loader.load();
			ModifyTextController controller = (ModifyTextController) loader.getController();
			controller.setHomeController(homeController);
			stage = new Stage();
			Scene scene = new Scene(root);
			stage.setX(Drawer.PAGE_X);
			stage.setY(50);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Felirat módosítása");
			stage.getIcons().add(new Image("/logo/MVM.jpg"));
			stage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
