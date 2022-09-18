package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.ModifyTextController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModifyTextWindow {
	
	private Stage stage;
	private ModifyTextController controller;
	
	public Stage getStage() {
		return stage;
	}
	
	public void setInputText(Text inputText) {
		controller.setInputText(inputText);
		controller.inputTextField.setText(inputText.getText());
		controller.textSizeComboBox.setValue(String.valueOf((int)inputText.getFont().getSize()));
	}

	public ModifyTextWindow(Drawer drawer) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyText.fxml"));
			AnchorPane root = loader.load();
			controller = (ModifyTextController) loader.getController();
			controller.setDrawer(drawer);
			stage = new Stage();
			controller.setStage(stage);
			Scene scene = new Scene(root);
			stage.setX((drawer.getRoot().widthProperty().get() - root.getPrefWidth()) / 2);
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
