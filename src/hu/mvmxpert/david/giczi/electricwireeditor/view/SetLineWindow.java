package hu.mvmxpert.david.giczi.electricwireeditor.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwireeditor.controller.SetLineController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SetLineWindow {

	private Stage stage;
	private SetLineController controller;
	
	public Stage getStage() {
		return stage;
	}
	
	public SetLineController getController() {
		return controller;
	}

	public SetLineWindow(HomeController homeController) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SetLineData.fxml"));
			AnchorPane root = loader.load();
			controller = (SetLineController) loader.getController();
			controller.setHomeController(homeController);
			stage = new Stage();
			stage.setX((homeController.getDrawer().getRoot().widthProperty().get() - root.getPrefWidth()) / 2);
			stage.setY(50);
			Scene scene = new Scene(root);
			stage.initOwner(homeController.homeWindow.primaryStage);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setAlwaysOnTop(true);
			stage.setTitle("Vonal hozzáadása");
			stage.getIcons().add(new Image("/logo/MVM.jpg"));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
