package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetLineController implements Initializable {

	private Stage stage;
	private HomeController homeController;

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	
	@FXML
	private TextField startXTextField;
	@FXML
	private TextField startYTextField;
	@FXML
	private TextField endXTextField;
	@FXML
	private TextField endYTextField;
	@FXML
	private ComboBox<String> lineTypeComboBox;
	@FXML
	private ColorPicker lineColorPicker;
	@FXML
	private ComboBox<String> lineWidthComboBox;
	@FXML
	private Button setButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lineTypeComboBox.getItems().add("folyamatos");
		lineTypeComboBox.getItems().add("szaggatott");
		lineTypeComboBox.getItems().add("pontozott");
		lineWidthComboBox.getItems().add("normál");
		lineWidthComboBox.getItems().add("vékony");
		lineWidthComboBox.getItems().add("vastag");
	}
	
	@FXML
	private void handleSettingLineButtonClick() {
		
	}
	
}
