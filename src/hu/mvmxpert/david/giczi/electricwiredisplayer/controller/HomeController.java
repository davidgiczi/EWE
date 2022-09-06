package hu.mvmxpert.david.giczi.electricwiredisplayer.controller;

import java.util.Optional;

import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.HomeWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetDrawingSystemDataWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {

	public HomeWindow homeWindow;
	public static String PROJECT_NAME;
	private Drawer drawer = new Drawer();
	private SetDrawingSystemDataWindow setCoordSystemWindow;
	
	
	public HomeController() {
		homeWindow = new HomeWindow(this);
	}
	
	public Drawer getDrawer() {
		return drawer;
	}
	
	public void createSetCoordSystemWindow() {
		if( setCoordSystemWindow != null ) {
			setCoordSystemWindow.getStage().show();
		}
		else {
			this.setCoordSystemWindow = new SetDrawingSystemDataWindow(this);
		}
	}
	
	public SetDrawingSystemDataWindow getSetCoordSystemWindow() {
		return setCoordSystemWindow;
	}

	public String setInputText(String title, String text) {
		TextInputDialog input = new TextInputDialog();
		input.setTitle(title);
		input.setHeaderText(text);
		input.showAndWait();
		return input.getEditor().getText();
	}
	
	public void setTitle(BorderPane root) {
		((Stage) root.getScene().getWindow()).setTitle(Validate.isValidProjectName(PROJECT_NAME) ? 
				PROJECT_NAME + " - Elektromos távvezeték szabad magasságának dokumentálása" 
				: "Elektromos távvezeték szabad magasságának dokumentálása");
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
	
	public void getInfoAlert(String title, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(text);
		alert.show();
	}
	
	public void getWarningAlert(String title, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(text);
		alert.show();
	}
}
