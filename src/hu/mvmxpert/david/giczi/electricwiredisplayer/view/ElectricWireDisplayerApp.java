package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ElectricWireDisplayerApp extends Application {

	private Drawer drawing = new Drawer(343.43, 3000);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("displayer.fxml"));
			drawing.drawPage(root);
			drawing.setVerticalScale(5);
			drawing.setElevationStartValue(140);
			drawing.drawVerticalAxis(root);
			drawing.drawHorizontalAxis(root);
			drawing.writeElevationValueForVerticalAxis(root);
			drawing.writeDistanceValueForHorizontalAxis(root);
			
			drawing.drawPillar(root, 180, 147.92, 175.13, 0, false);
			drawing.setText(root, "jobb ak.:", 4, -1, 18, -90);
			drawing.setText(root, "jobb ak.:", 4, -71, 18, -90);
			
			drawing.drawPillar(root, 181, 147.33, 171.55, drawing.getLengthOfHorizontalAxis(), false);
			drawing.setText(root, "jobb ak.:", 119, 0, 18, -90);
			drawing.setText(root, "jobb ak.:", 119, -64, 18, -90);
			
			drawing.drawElectricWire(root, "bal af.:", 146.67, 164.25, 105.05, false);
			drawing.setText(root, "jobb af.:", 39, 1, 18, -90);
			drawing.setText(root, "jobb af.:", 39, -50, 18, -90);
			
//			drawing.drawElectricWire(root, "bal af.:", 148.18, 162.39, 168.07, false);
//			drawing.setText(root, "jobb af.:", 119, 0, 18, -90);
//			drawing.setText(root, "jobb af.:", 119, -64, 18, -90);
//			
//			drawing.drawElectricWire(root, "bal af.:", 150.18, 174.39, 268.32, false);
//			drawing.setText(root, "jobb af.:", 119, 0, 18, -90);
//			drawing.setText(root, "jobb af.:", 119, -64, 18, -90);
		
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
