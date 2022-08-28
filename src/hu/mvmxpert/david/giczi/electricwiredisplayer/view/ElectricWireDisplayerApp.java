package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ElectricWireDisplayerApp extends Application {

	private Drawer drawing = new Drawer();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("displayer.fxml"));
			drawing.drawPage(root);
			drawing.drawVerticalAxis(root, 100, 200);
			drawing.drawHorizontalAxis(root, 120, 650, 412.356, 4000);
			drawing.writeElevationValueForVerticalAxis(root, 50, 120, 10);
			drawing.writeDistanceValueForHorizontalAxis(root, 115, 412.356, 4000);
			Scene scene = new Scene(root);
			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);
			primaryStage.setTitle("MVMX Pert Zrt. - Elektromos Távvezeték Dokumentáló Rendszer");
			primaryStage.getIcons().add(new Image("/logo/MVM.jpg"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
