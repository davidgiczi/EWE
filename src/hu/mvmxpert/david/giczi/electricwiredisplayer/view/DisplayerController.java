package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
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
	
	
}
