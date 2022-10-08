package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import java.io.IOException;

import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.SetTextController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SetTextWindow {
	
	private Stage stage;
	private TextField inputTextField;
	private TextField inputTextXField;
	private TextField inputTextYField;
	private SetTextController controller;

	
	public Stage getStage() {
		return stage;
	}
	
	public TextField getInputTextField() {
		return inputTextField;
	}

	public void setInputTextField(TextField inputTextField) {
		this.inputTextField = inputTextField;
	}

	public void setInputTextXField(TextField inputTextXField) {
		this.inputTextXField = inputTextXField;
	}

	public void setInputTextYField(TextField inputTextYField) {
		this.inputTextYField = inputTextYField;
	}

	public TextField getInputTextXField() {
		return inputTextXField;
	}

	public TextField getInputTextYField() {
		return inputTextYField;
	}
	
	public SetTextController getController() {
		return controller;
	}

	public SetTextWindow(HomeController homeController) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SetText.fxml"));
			AnchorPane root = loader.load();
			controller = (SetTextController) loader.getController();
			controller.setHomeController(homeController);
			setInputTextField(controller.inputTextField);
			setInputTextXField(controller.inputTextXField);
			setInputTextYField(controller.inputTextYField);
			stage = new Stage();
			stage.setX((homeController.getDrawer().getRoot().widthProperty().get() - root.getPrefWidth()) / 2);
			stage.setY(150);
			controller.setStage(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Új felirat hozzáadása");
			stage.getIcons().add(new Image("/logo/MVM.jpg"));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
