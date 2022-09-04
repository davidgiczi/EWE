package hu.mvmxpert.david.giczi.electricwiredisplayer.controller;

import java.util.Optional;

import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.ModifyTextWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetCoordSystemWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetNewTextWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetPillarDataWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetWireDataWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {

	
	public static String PROJECT_NAME;
	private Drawer drawer = new Drawer();
	private ModifyTextWindow modifyTextWindow;
	private SetCoordSystemWindow setCoordSystemWindow;
	private SetNewTextWindow setNewTextWindow;
	private SetPillarDataWindow setPillarDataWindow;
	private SetWireDataWindow setWireDataWindow;

	public Drawer getDrawer() {
		return drawer;
	}
	
	public ModifyTextWindow getModifyTextWindow() {
		this.modifyTextWindow = new ModifyTextWindow(this);
		return modifyTextWindow;
	}

	public SetCoordSystemWindow getSetCoordSystemWindow() {
		this.setCoordSystemWindow = new SetCoordSystemWindow(this);
		return setCoordSystemWindow;
	}

	public SetNewTextWindow getSetNewTextWindow() {
		this.setNewTextWindow = new SetNewTextWindow(this);
		return setNewTextWindow;
	}

	public SetPillarDataWindow getSetPillarDataWindow() {
		this.setPillarDataWindow = new SetPillarDataWindow(this);
		return setPillarDataWindow;
	}

	public SetWireDataWindow getSetWireDataWindow() {
		this.setWireDataWindow = new SetWireDataWindow(this);
		return setWireDataWindow;
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
