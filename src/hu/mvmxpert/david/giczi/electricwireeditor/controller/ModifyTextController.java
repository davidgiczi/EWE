package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import java.net.URL;
import java.util.ResourceBundle;

import hu.mvmxpert.david.giczi.electricwireeditor.service.Drawer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModifyTextController implements Initializable {

	private Text inputText;
	private Drawer drawer;
	private Stage stage;
	
	public ModifyTextController() {
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

	@FXML
	public TextField inputTextField;
	@FXML
	public ComboBox<String> textSizeComboBox;
	
	@FXML
	public void modifyInputText() {
		if( inputTextField.getText().isBlank() ) {
			inputTextField.setText(inputText.getText());
			return;
		}
		drawer.modifyText(inputText, inputTextField.getText());
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
	public void rotateInputText() {
		drawer.rotateText(inputText);
	}
	
	@FXML
	public void deleteInputText() {
		if( drawer.deleteText(inputText) )
		stage.hide();
	}
	
	@FXML
	public void setSizeInputText() {
		drawer.setTextSize(inputText, Integer.valueOf(textSizeComboBox.getValue()));
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for(int i = 24; i > 8; i-- )
		textSizeComboBox.getItems().add(String.valueOf(i));
	}
	
	
}
