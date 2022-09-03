package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ElectricWireDisplayerApp extends Application {

	private DisplayerController displayerController = new DisplayerController();
	
	private void createMenu(BorderPane root) {
		MenuBar menuBar = new MenuBar();
		menuBar.setCursor(Cursor.HAND);
		Menu openProject = new Menu("Projekt megnyitása");
		MenuItem setProjectName = new MenuItem("Projekt nevének megadása");
		setProjectName.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			String projectName = displayerController.setInputText("Projekt nevének megadása", "Add meg a projekt nevét:");
			displayerController.setTitle(root, projectName);
			}
		});
		
		MenuItem setCoordSystem = new MenuItem("Rajzi rendszer megadása");
		setCoordSystem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				displayerController.getDrawer().drawPage(root);
				displayerController.getDrawer().setHorizontalScale(3000);
				displayerController.getDrawer().setLengthOfHorizontalAxis(343.56);
				displayerController.getDrawer().setVerticalScale(5);
				displayerController.getDrawer().setElevationStartValue(140);
				displayerController.getDrawer().drawVerticalAxis(root);
				displayerController.getDrawer().drawHorizontalAxis(root);
				displayerController.getDrawer().writeElevationValueForVerticalAxis(root);
				displayerController.getDrawer().writeDistanceValueForHorizontalAxis(root);
			}
		});
		MenuItem setPillarData = new MenuItem("Oszlop adatok megadása");
		setPillarData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			displayerController.getDrawer().drawPillar(root, 180, 147.92, 175.13, 0, false);
			displayerController.getDrawer().drawPillar(root, 181, 147.33, 171.55, 
					displayerController.getDrawer().getLengthOfHorizontalAxis(), false);
			}
		});
		MenuItem setWireData = new MenuItem("Vezeték adatok megadása");
		setWireData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				displayerController.getDrawer().drawElectricWire(root, "bal af.:", 146.67, 164.25, 105.05, false);
				displayerController.getDrawer().drawElectricWire(root, "bal af.:", 148.18, 162.39, 168.07, false);
				displayerController.getDrawer().drawElectricWire(root, "bal af.:", 150.18, 174.39, 268.32, false);
			}
		});
		Menu createProject = new Menu("Projekt létrehozása");
		createProject.getItems().addAll(setProjectName, new SeparatorMenuItem(), 
										setCoordSystem, new SeparatorMenuItem(), 
										setPillarData, new SeparatorMenuItem(),
										setWireData);
		Menu modifyDrawing = new Menu("Rajzelemek módosítása");
		MenuItem modifyCoordSystem = new MenuItem("Rajzi rendszer módosítása");
		MenuItem modifyText = new MenuItem("Feliratok módosítása");
		MenuItem modifyLine = new MenuItem("Oszlop/vezeték vonalak módosítása");
		modifyDrawing.getItems().addAll(modifyCoordSystem, new SeparatorMenuItem(), modifyText, new SeparatorMenuItem(), modifyLine);
		menuBar.getMenus().addAll(openProject, createProject, modifyDrawing);
		root.setTop(menuBar);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = FXMLLoader.load(getClass().getResource("displayer.fxml"));
			createMenu(root);
			Scene scene = new Scene(root);
			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Elektromos távvezeték szabad magasságának dokumentálása");
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
