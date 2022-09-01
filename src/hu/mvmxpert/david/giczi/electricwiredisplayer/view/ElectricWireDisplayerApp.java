package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ElectricWireDisplayerApp extends Application {

	private Drawer drawing = new Drawer(120, 1000);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("displayer.fxml"));
			drawing.drawPage(root);
			drawing.setVerticalScale(10);
			drawing.setElevationStartValue(160);
			drawing.drawVerticalAxis(root);
			drawing.drawHorizontalAxis(root);
			drawing.writeElevationValueForVerticalAxis(root);
			drawing.writeDistanceValueForHorizontalAxis(root);
			drawing.drawPillar(root,"A", 180, 210, 0, true);
			drawing.drawPillar(root,"B", 190, 230, 120, true);
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
