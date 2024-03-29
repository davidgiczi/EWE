package hu.mvmxpert.david.giczi.electricwireeditor.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwireeditor.controller.SetCalculatedWireDataController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SetCalculatedWireDataWindow {

	private Stage stage;
	public SetCalculatedWireDataController controller;
	
	public Stage getStage() {
		return stage;
	}
	
	public SetCalculatedWireDataWindow(HomeController homeController) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SetCalculatedWireData.fxml"));
			loader.setControllerFactory( c -> new SetCalculatedWireDataController(homeController) );
			AnchorPane root = loader.load();
			controller = (SetCalculatedWireDataController) loader.getController();
			controller.showWireCheckBox.setSelected(true);
			stage = new Stage();
			Scene scene = new Scene(root);
			stage.initOwner(homeController.homeWindow.primaryStage);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Sodrony adatok megadása");
			stage.getIcons().add(new Image("/logo/MVM.jpg"));
			stage.show();
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
