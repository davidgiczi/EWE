package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SaveWireCoordsController {

	
	@FXML
	public TextField startX;
	@FXML
	public TextField startY;
	@FXML
	public TextField endX;
	@FXML
	public TextField endY;
	private boolean is2DWindow;
	
	
	public void setIs2DWindow(boolean is2dWindow) {
		is2DWindow = is2dWindow;
	}
	
	
	
}
