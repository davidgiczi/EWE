package hu.mvmxpert.david.giczi.electricwireeditor.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwireeditor.controller.ShowCalculatedWireDataController;
import hu.mvmxpert.david.giczi.electricwireeditor.service.Drawer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShowCalculatedWireDataWindow {

	
	private Stage stage;
	
	public Stage getStage() {
		return stage;
	}
	
	public ShowCalculatedWireDataWindow(Drawer drawer) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowCalculatedWireData.fxml"));
			AnchorPane root = loader.load();
			ShowCalculatedWireDataController controller = (ShowCalculatedWireDataController) loader.getController();
			stage = new Stage();
			Scene scene = new Scene(root);
			stage.setX(drawer.getRoot().widthProperty().get() / 2 - Drawer.A4_WIDTH / 2 + drawer.MARGIN +  (Drawer.A4_WIDTH - drawer.MARGIN) / 2);
			stage.setY(50);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Sodrony adatok");
			stage.getIcons().add(new Image("/logo/MVM.jpg"));
			stage.show();
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
