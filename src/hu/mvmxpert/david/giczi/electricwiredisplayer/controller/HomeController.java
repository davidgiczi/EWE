package hu.mvmxpert.david.giczi.electricwiredisplayer.controller;

import java.util.Optional;

import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.HomeWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetDrawingSystemDataWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetPillarDataWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetWireDataWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {

	public HomeWindow homeWindow;
	public static String PROJECT_NAME;
	private Drawer drawer;
	private SetDrawingSystemDataWindow setCoordSystemWindow;
	private SetPillarDataWindow setPillarDataWindow;
	private SetWireDataWindow setWireDataWindow;
	
	public HomeController() {
		createDrawer();
		homeWindow = new HomeWindow(this);
	}
	
	public Drawer getDrawer() {
		return drawer;
	}
	
	public void createDrawer() {
		if( drawer == null) {
			drawer = new Drawer();
		}
	}
	
	public void createSetCoordSystemWindow() {
		if( setCoordSystemWindow != null ) {
			setCoordSystemWindow.getStage().show();
		}
		else {
			this.setCoordSystemWindow = new SetDrawingSystemDataWindow(this);
		}
	}
	
	public void createSetPillarDataWindow() {
		if( setPillarDataWindow != null ) {
			setPillarDataWindow.getStage().show();
		}
		else {
			this.setPillarDataWindow = new SetPillarDataWindow(this);
		}
	}
	
	public void createSetWireDataWindow() {
		if( setWireDataWindow != null ) {
			setWireDataWindow.getStage().show();
		}
		else {
			this.setWireDataWindow = new SetWireDataWindow(this);
		}
	}
	
	public SetDrawingSystemDataWindow getSetCoordSystemWindow() {
		return setCoordSystemWindow;
	}
	
	public SetPillarDataWindow getSetPillarDataWindow() {
		return setPillarDataWindow;
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
		alert.setHeaderText("Biztos, hogy kilépsz a programból?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    System.exit(0);
		}
	}
	
	public static void getInfoAlert(String title, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		alert.setTitle(title);
		alert.setHeaderText(text);
		alert.show();
	}
	
	public static void getWarningAlert(String title, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		alert.setTitle(title);
		alert.setHeaderText(text);
		alert.show();
	}
	
	public static boolean getConfirmationAlert(String title, String text) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		alert.setTitle(title);
		alert.setHeaderText(text);
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get() == ButtonType.OK) {
			return true;
		}
		else {
			return false;
		}
	}
}
