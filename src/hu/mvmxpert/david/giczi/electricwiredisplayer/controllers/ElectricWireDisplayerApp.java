package hu.mvmxpert.david.giczi.electricwiredisplayer.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class ElectricWireDisplayerApp extends Application {

	private void drawLine(StackPane root) {
		Line line = new Line(100, 100, 500, 500);
		line.getStrokeDashArray().addAll(25d, 10d);
		line.setStrokeWidth(5);
		line.setStroke(Color.RED);
		root.getChildren().add(line);
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			StackPane root = FXMLLoader.load(getClass().getResource("displayer.fxml"));
			drawLine(root);
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
