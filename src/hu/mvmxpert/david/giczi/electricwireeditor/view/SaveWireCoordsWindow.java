package hu.mvmxpert.david.giczi.electricwireeditor.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SaveWireCoordsWindow {

	
	private Stage stage;
	
	public Stage getStage() {
		return stage;
	}
	
	public SaveWireCoordsWindow(HomeController homeController) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SaveWireCoords.fxml"));
			AnchorPane root = loader.load();
			stage = new Stage();
			Scene scene = new Scene(root);
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
