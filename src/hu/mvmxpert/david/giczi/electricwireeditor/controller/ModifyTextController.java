package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import java.net.URL;
import java.util.ResourceBundle;

import hu.mvmxpert.david.giczi.electricwireeditor.service.Drawer;
import hu.mvmxpert.david.giczi.electricwireeditor.service.Validate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModifyTextController implements Initializable {

	private Text inputText;
	private Drawer drawer;
	private Stage stage;
	private HomeController homeController;
	
	public ModifyTextController() {
	}
	
	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	public void setInputText(Text inputText) {
		this.inputText = inputText;
	}

	public void setDrawer(Drawer drawer) {
		this.drawer = drawer;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public ColorPicker getColorPicker() {
		return colorPicker;
	}

	@FXML
	public TextField inputTextField;
	@FXML
	public TextField rotateValueTextField;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	public ComboBox<String> textSizeComboBox;
	
	@FXML
	public void modifyInputText() {
		if( inputTextField.getText().isBlank() ) {
			inputTextField.setText(inputText.getText());
			return;
		}
		int rotateValue;
		try {
			rotateValue = Validate.isValidTextRotateValue(rotateValueTextField.getText());
		} catch (NumberFormatException e) {
			homeController.getWarningAlert("Nem megfelelő felirat forgatási érték", 
					"A felirat forgatási értéke csak egész szám lehet: forgatási érték >= 0 és forgatási érték < 360");
			return;
		}
		drawer.modifyText(inputText, inputTextField.getText(), colorPicker.getValue(),
				rotateValue, Integer.valueOf(textSizeComboBox.getValue()));
	}
	
	@FXML
	public void moveInputTextLeft() {
		drawer.moveTextLeft(inputText);
	}
	
	@FXML
	public void moveInputTextUp() {
		drawer.moveTextUp(inputText);
	}
	
	@FXML
	public void moveInputTextDown() {
		drawer.moveTextDown(inputText);
	}
	
	@FXML
	public void moveInputTextRight() {
		drawer.moveTextRight(inputText);
	}
	
	@FXML
	public void deleteInputText() {
		if( drawer.deleteText(inputText) )
		stage.hide();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for(int i = 24; i > 8; i-- )
		textSizeComboBox.getItems().add(String.valueOf(i));
	}
	
	
}
