package hu.mvmxpert.david.giczi.electricwiredisplayer.controller;

import java.util.Optional;

import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DisplayerController {

	
	private Drawer drawer = new Drawer();
	

	public Drawer getDrawer() {
		return drawer;
	}
	
	public String setInputText(String title, String text) {
		TextInputDialog input = new TextInputDialog();
		input.setTitle(title);
		input.setHeaderText(text);
		input.showAndWait();
		return input.getEditor().getText();
	}
	
	public void setTitle(BorderPane root, String projectName) {
		((Stage) root.getScene().getWindow()).setTitle(projectName.isEmpty() ? 
				"Elektromos távvezeték szabad magasságának dokumentálása" :
				projectName + " - Elektromos távvezeték szabad magasságának dokumentálása");
	}

	public void exit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Kilépés a programból");
		alert.setHeaderText("Biztos, hogy kilép a programból?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    System.exit(0);
		}
	}
		
}
