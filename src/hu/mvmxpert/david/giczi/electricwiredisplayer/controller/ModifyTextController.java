package hu.mvmxpert.david.giczi.electricwiredisplayer.controller;

import java.net.URL;
import java.util.ResourceBundle;

import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
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
	private ComboBox<String> textSizeComboBox;
	
	@FXML
	public void modifyInputText() {
		
	}
	
	@FXML
	public void moveInputTextLeft() {
		
	}
	
	@FXML
	public void moveInputTextUp() {
		
	}
	
	@FXML
	public void moveInputTextDown() {
		
	}
	
	@FXML
	public void moveInputTextRight() {
		
	}
	
	@FXML
	public void rotateInputText() {
		
	}
	
	@FXML
	public void deleteInputText() {
		if( drawer.deleteText(inputText) )
		stage.hide();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for(int i = 18; i > 8; i-- )
		textSizeComboBox.getItems().add(String.valueOf(i));
	}
	
	
}
