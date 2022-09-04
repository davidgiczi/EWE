package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class SetNewTextWindow {

	public SetNewTextWindow() {
		Stage stage = new Stage();
		FlowPane root = new FlowPane();
		Scene scene = new Scene(root, 500, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Új felirat hozzáadása");
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		stage.show();
	}
	
}
