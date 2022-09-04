package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class SetPillarDataWindow {

	private HomeController homeController;
	
	public SetPillarDataWindow(HomeController homeController) {
		this.homeController = homeController;
		Stage stage = new Stage();
		FlowPane root = new FlowPane();
		Scene scene = new Scene(root, 500, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle(Validate.isValidProjectName(HomeController.PROJECT_NAME) ? 
				 HomeController.PROJECT_NAME + " - Távvezeték oszlop adatok megadása" : "Távvezeték oszlop adatok megadása");
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		stage.show();
	}
	
}
