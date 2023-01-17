package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import hu.mvmxpert.david.giczi.electricwireeditor.service.FileProcess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class SetCalculatedWireDataController implements Initializable {

	private HomeController homeController;
	
	@FXML
	private ComboBox<String> wireTypes;

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	List<String> wireType =	FileProcess.getWireTypeFileData();
	for(int i = 2; i < wireType.size(); i++) {
		String[] data = wireType.get(i).split(";");
		wireTypes.getItems().add(data[0]);
	}
	}
	

}
