package hu.mvmxpert.david.giczi.electricwireeditor.service;


import java.text.DecimalFormat;
import java.util.List;
import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwireeditor.model.LineData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.MeasPoint;
import hu.mvmxpert.david.giczi.electricwireeditor.model.PillarData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.TextData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireDifference;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WirePoint;
import hu.mvmxpert.david.giczi.electricwireeditor.view.ModifyTextWindow;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class Drawer {
	
	private BorderPane root;
	public static final double MILLIMETER = 1000 / 224.0;
	public static final double A4_WIDTH =  211 * MILLIMETER;
	public static final double START_X = 45 * MILLIMETER;
	public static final double HOR_SHIFT = 12;
	public static final double START_Y = 550.0;
	public static final double PAGE_Y = 25;
	public final double MARGIN = 156 * MILLIMETER;
	public static double X_DISTANCE;
	private final double VER_SHIFT = 5;
	private double lengthOfHorizontalAxis;
	private int horizontalScale;
	private int verticalScale;
	private int elevationStartValue;
	public ModifyTextWindow modifyTextWindow;
	private HomeController homeController;
	private DecimalFormat df = new DecimalFormat("0.000");
	private ArchivFileBuilder archivFileBuilder;
	
	
	public void setArchivFileBuilder(ArchivFileBuilder archivFileBuilder) {
		this.archivFileBuilder = archivFileBuilder;
	}

	public void setLengthOfHorizontalAxis(double lengthOfHorizontalAxis) {
		this.lengthOfHorizontalAxis = lengthOfHorizontalAxis;
	}
	
	public int getHorizontalScale() {
		return horizontalScale;
	}

	public void setHorizontalScale(int horizontalScale) {
		this.horizontalScale = horizontalScale;
	}

	public void setVerticalScale(int verticalScale) {
		this.verticalScale = verticalScale;
	}
	
	public int getVerticalScale() {
		return verticalScale;
	}

	public void setElevationStartValue(int elevationStartValue) {
		this.elevationStartValue = elevationStartValue;
	}
	
	public int getElevationStartValue() {
		return elevationStartValue;
	}

	public double getLengthOfHorizontalAxis() {
		return lengthOfHorizontalAxis;
	}

	public BorderPane getRoot() {
		return root;
	}

	public void setRoot(BorderPane root) {
		this.root = root;
	}

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}
	
	public void clearRoot(){
		
		for(int i = root.getChildren().size() - 1; i >= 0; i--) {
			if( root.getChildren().get(i) instanceof MenuBar ) {
				continue;
			}
			root.getChildren().remove(i);
		}
	}
	
	public void drawPage() {
		Rectangle page = new Rectangle();
		page.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2));
		page.setY(PAGE_Y);
		page.setWidth(A4_WIDTH);
		page.heightProperty().bind(root.heightProperty().subtract(30));
		Line leftMargin = new Line();
		leftMargin.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add((A4_WIDTH - MARGIN) / 2));
		leftMargin.setStartY(PAGE_Y);
		leftMargin.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add((A4_WIDTH - MARGIN) / 2));
		leftMargin.endYProperty().bind(root.heightProperty().add(20));
		Line rightMargin = new Line();
		rightMargin.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add((A4_WIDTH - MARGIN) / 2).add(MARGIN));
		rightMargin.setStartY(PAGE_Y);
		rightMargin.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add((A4_WIDTH - MARGIN) / 2).add(MARGIN));
		rightMargin.endYProperty().bind(root.heightProperty().add(20));
		leftMargin.setStroke(Color.LIGHTGRAY);
		leftMargin.getStrokeDashArray().addAll(4d);
		rightMargin.setStroke(Color.LIGHTGRAY);
		rightMargin.getStrokeDashArray().addAll(4d);
		page.setFill(Color.WHITE);
		root.getChildren().addAll(page, leftMargin, rightMargin);
	}
	
	public void drawVerticalAxis() {
		double startY = START_Y - 10 * MILLIMETER;
		Line leftBorder = new Line();
		leftBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X));
		leftBorder.setStartY(PAGE_Y + START_Y);
		leftBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X));
		leftBorder.setEndY(PAGE_Y + START_Y - 100  * MILLIMETER);
		Line rightBorder = new Line();
		rightBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(2 * MILLIMETER));
		rightBorder.setStartY(PAGE_Y + START_Y);
		rightBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(2 * MILLIMETER));
		rightBorder.setEndY(PAGE_Y + START_Y - 100  * MILLIMETER);
		Line topBorder = new Line();
		topBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X));
		topBorder.setStartY(PAGE_Y + START_Y - 100 * MILLIMETER);
		topBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(2 * MILLIMETER));
		topBorder.setEndY(PAGE_Y + START_Y - 100 * MILLIMETER);
		root.getChildren().addAll(leftBorder, rightBorder, topBorder);
		for(int i = 1; i < 11; i++) {
		Rectangle axisComponent = new Rectangle();
		axisComponent.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X));
		axisComponent.setY(PAGE_Y + startY);
		axisComponent.setWidth(2 * MILLIMETER);
		axisComponent.setHeight(10 * MILLIMETER);
		if( i % 2 == 0) {
			axisComponent.setFill(Color.WHITE);
		}
		startY -= 10 * MILLIMETER;
		root.getChildren().add(axisComponent);
		}
	}
	
	public void drawHorizontalAxis() {
		Line topBorder = new Line();
		topBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER));
		topBorder.setStartY(PAGE_Y + START_Y + VER_SHIFT * MILLIMETER);
		topBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		topBorder.setEndY(PAGE_Y + START_Y + VER_SHIFT * MILLIMETER);
		Line rightBorder = new Line();
		rightBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		rightBorder.setStartY(PAGE_Y + START_Y + (VER_SHIFT  + 1 ) * MILLIMETER);
		rightBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		rightBorder.setEndY(PAGE_Y + START_Y + VER_SHIFT * MILLIMETER);
		Line downBorder = new Line();
		downBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER));
		downBorder.setStartY(PAGE_Y + START_Y + (VER_SHIFT + 1) * MILLIMETER);
		downBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		downBorder.setEndY(PAGE_Y + START_Y + (VER_SHIFT + 1) * MILLIMETER);
		Line leftBorder = new Line();
		leftBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER));
		leftBorder.setStartY(PAGE_Y + START_Y + VER_SHIFT * MILLIMETER);
		leftBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER));
		leftBorder.setEndY(PAGE_Y + START_Y + (VER_SHIFT + 1) * MILLIMETER);
		root.getChildren().addAll(topBorder, rightBorder, downBorder, leftBorder);
	}
	
	public void writeElevationValueForVerticalAxis() {
		double startY = START_Y;
		int startValue = elevationStartValue;
		for(int i = 0; i <= 10; i++) {
		setText(-1, String.valueOf(startValue) + "m", - 70, PAGE_Y + startY, 18, 0, false, false, 0, 0, 0, 1);
		startY -= 10 * MILLIMETER;
		startValue += verticalScale;
		}
		setText(-1, "Oszlopszám:", - 17 * MILLIMETER, PAGE_Y + START_Y + 30 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
	}
	
	public void writeDistanceValueForHorizontalAxis() {
		setText(-1, "0", (HOR_SHIFT - 1) * MILLIMETER, PAGE_Y + START_Y + 50, 18, 0, false, false, 0, 0, 0, 1);
		setText(-1, String.valueOf(lengthOfHorizontalAxis) + "m", 
				getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER, 
				PAGE_Y + START_Y + 50, 18, 0, false, false, 0, 0, 0, 1);
	}
	
	public void drawPillar(String id, double groundElevation, double topElevation, double distance, boolean leftHand, boolean rightHand) {
		Line pillar = new Line();
		pillar.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		pillar.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(groundElevation - elevationStartValue) * MILLIMETER);
		pillar.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		pillar.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
		pillar.setStroke(Color.BLUE);
		pillar.setStrokeWidth(3);
		pillar.setCursor(Cursor.HAND);
		pillar.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		root.getChildren().add(pillar);
		
		PillarData pillarData = new PillarData(groundElevation, topElevation, distance, leftHand, rightHand);
		pillarData.setId(ArchivFileBuilder.addID());
		pillar.setId(String.valueOf(pillarData.getId()));
		archivFileBuilder.addPillar(pillarData);
		
		if( leftHand || rightHand ) {
			Line hood = new Line();
			
			hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER).add(HOR_SHIFT * MILLIMETER));
			hood.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			hood.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			if( leftHand && !rightHand ) {
			
			hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
					.add((HOR_SHIFT - 1) * MILLIMETER));
			}
			else if( !leftHand &&  rightHand) {
				
			hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
						.add(START_X)
						.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
						.add((HOR_SHIFT + 1) * MILLIMETER));
				
			}
			else if( leftHand && rightHand ) {
				hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
						.add(START_X)
						.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER).add((HOR_SHIFT -1) * MILLIMETER));
				hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
						.add(START_X)
						.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
						.add((HOR_SHIFT + 1) * MILLIMETER));
			}
			
			hood.setCursor(Cursor.HAND);
			hood.setOnMouseClicked( h -> {
				Line line = (Line) h.getSource();
				setDrawLineWindowData(line);
				deleteLine(line);
				});
			hood.setId(String.valueOf(pillarData.getId()));
			hood.setStroke(Color.BLUE);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
		
		setText(pillarData.getId(), id + ".", 
				(getHorizontalScaledDownLengthValue(distance)  + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y + 30 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);	
		setText(pillarData.getId(), "bal ak.: Bf. " + df.format(groundElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, pillar.getStartY(), 18, -90, true, false, 0, 0, 0, 1);
		setText(pillarData.getId(), "bal ak.: Bf. " + df.format(topElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, pillar.getEndY(), 18, -90, true, true, 0, 0, 0, 1);
		setText(pillarData.getId(), df.format(distance).replace(",", ".") + "m", 
		(getHorizontalScaledDownLengthValue(distance)  + HOR_SHIFT - VER_SHIFT) * MILLIMETER, PAGE_Y + START_Y + 50, 18, 0, false, false, 0, 0, 0, 1);
	}
	
	
	private void drawLeftHood(String pillarId, double pillarDistance, double elevation) {
		Line hood = new Line();
		hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(pillarDistance) * MILLIMETER).add(HOR_SHIFT * MILLIMETER));
		hood.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(elevation - elevationStartValue) * MILLIMETER);
		hood.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(elevation - elevationStartValue) * MILLIMETER);
		hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(pillarDistance) * MILLIMETER)
				.add((HOR_SHIFT - 1) * MILLIMETER));
		hood.setCursor(Cursor.HAND);
		hood.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		hood.setId(pillarId);
		hood.setStroke(Color.BLUE);
		hood.setStrokeWidth(3);
		root.getChildren().add(hood);
	}
	
	private void drawRightHood(String pillarId, double pillarDistance, double elevation) {
		Line hood = new Line();
		hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(pillarDistance) * MILLIMETER).add(HOR_SHIFT * MILLIMETER));
		hood.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(elevation - elevationStartValue) * MILLIMETER);
		hood.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(elevation - elevationStartValue) * MILLIMETER);
		hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(pillarDistance) * MILLIMETER)
				.add((HOR_SHIFT + 1) * MILLIMETER));
		hood.setCursor(Cursor.HAND);
		hood.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		hood.setId(pillarId);
		hood.setStroke(Color.BLUE);
		hood.setStrokeWidth(3);
		root.getChildren().add(hood);
	}
	
	private void writeDistances(List<Double> distances, double pillarDistance, int pillarId) {
		
		if( distances == null ) {
			return;
		}
			DecimalFormat df = new DecimalFormat("0.00");
			if( distances.get(0) != 0 && distances.get(1) == 0 && distances.get(2) != 0 ) {	
				setText(pillarId, "bal " + df.format(distances.get(0)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "jobb " + df.format(distances.get(2)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT  - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 15 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) != 0 && distances.get(1) != 0 && distances.get(2) != 0 ) {
				setText(pillarId, "bal " + df.format(distances.get(0)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "közép " + df.format(distances.get(1)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 17) * MILLIMETER, 
						PAGE_Y + START_Y + 15 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "jobb " + df.format(distances.get(2)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT  - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 20 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				
			}
			else if( distances.get(3) != 0 && distances.get(4) != 0 && distances.get(5) != 0 && distances.get(6) != 0 ) {
				setText(pillarId, "bal külső " + df.format(distances.get(3)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "bal belső " + df.format(distances.get(4)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 15 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "jobb belső " + df.format(distances.get(5)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT  - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 20 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "jobb külső " + df.format(distances.get(6)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT  - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 25 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) != 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) == 0 && 
					distances.get(4) == 0 && distances.get(5) == 0 && distances.get(6) == 0 ) {
				setText(pillarId, "bal " + df.format(distances.get(0)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) != 0 && distances.get(2) == 0 && distances.get(3) == 0 && 
					distances.get(4) == 0 && distances.get(5) == 0 && distances.get(6) == 0 ) {
				setText(pillarId, "közép " + df.format(distances.get(1)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) != 0 && distances.get(3) == 0 && 
					distances.get(4) == 0 && distances.get(5) == 0 && distances.get(6) == 0 ) {
				setText(pillarId, "jobb " + df.format(distances.get(2)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) != 0 && 
					distances.get(4) == 0 && distances.get(5) == 0 && distances.get(6) == 0 ) {
				setText(pillarId, "bal " + df.format(distances.get(3)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) == 0 && 
					distances.get(4) != 0 && distances.get(5) == 0 && distances.get(6) == 0 ) {
				setText(pillarId, "bal " + df.format(distances.get(4)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) == 0 && 
					distances.get(4) == 0 && distances.get(5) != 0 && distances.get(6) == 0 ) {
				setText(pillarId, "jobb " + df.format(distances.get(5)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) == 0 && 
					distances.get(4) == 0 && distances.get(5) == 0 && distances.get(6) != 0 ) {
				setText(pillarId, "jobb " + df.format(distances.get(6)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) != 0 && 
					distances.get(4) != 0 && distances.get(5) == 0 && distances.get(6) == 0 ) {
				setText(pillarId, "bal " + df.format(distances.get(3)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "bal " + df.format(distances.get(4)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT  - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 15 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) == 0 && 
					distances.get(4) == 0 && distances.get(5) != 0 && distances.get(6) != 0 ) {
				setText(pillarId, "jobb " + df.format(distances.get(5)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "jobb " + df.format(distances.get(6)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT  - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 15 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) != 0 && 
					distances.get(4) == 0 && distances.get(5) == 0 && distances.get(6) != 0 ) {
				setText(pillarId, "bal " + df.format(distances.get(3)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "jobb " + df.format(distances.get(6)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT  - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 15 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) == 0 && 
					distances.get(4) != 0 && distances.get(5) != 0 && distances.get(6) == 0 ) {
				setText(pillarId, "bal " + df.format(distances.get(4)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "jobb " + df.format(distances.get(5)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT  - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 15 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) != 0 && 
					distances.get(4) == 0 && distances.get(5) != 0 && distances.get(6) == 0 ) {
				setText(pillarId, "bal " + df.format(distances.get(3)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "jobb " + df.format(distances.get(5)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT  - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 15 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
			else if( distances.get(0) == 0 && distances.get(1) == 0 && distances.get(2) == 0 && distances.get(3) == 0 && 
					distances.get(4) != 0 && distances.get(5) == 0 && distances.get(6) != 0 ) {
				setText(pillarId, "bal " + df.format(distances.get(4)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 12) * MILLIMETER, 
						PAGE_Y + START_Y + 10 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
				setText(pillarId, "jobb " + df.format(distances.get(6)).replace(",", ".") + "m", 
						(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT  - VER_SHIFT - 15) * MILLIMETER, 
						PAGE_Y + START_Y + 15 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);
			}
	}
	
	private void writeElevations(List<MeasPoint> measPointList, double pillarDistance, Line pillar) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		if( measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[3])) &&
			measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[4])) &&
			measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[3])) &&
			measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[4])) ) {
			
			setText(Integer.parseInt(pillar.getId()), "bal külső ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 5) * MILLIMETER, 
					pillar.getStartY() - 23 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal külső ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 5) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 23) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			
			setText(Integer.parseInt(pillar.getId()), "bal belső ak.: Bf. " + df.format(measPointList.get(4).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER, 
					pillar.getStartY() - 23 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal belső ak.: Bf. " + df.format(measPointList.get(5).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(5).pointZ - elevationStartValue) + 23) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
		
			setText(Integer.parseInt(pillar.getId()), "jobb belső ak.: Bf. " + df.format(measPointList.get(6).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER, 
					pillar.getStartY() - 24 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb belső ak.: Bf. " + df.format(measPointList.get(7).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(7).pointZ - elevationStartValue) + 24) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			
			setText(Integer.parseInt(pillar.getId()), "jobb külső ak.: Bf. " + df.format(measPointList.get(8).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 17) * MILLIMETER, 
					pillar.getStartY() - 24 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb külső ak.: Bf. " + df.format(measPointList.get(9).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 17) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(9).pointZ - elevationStartValue) + 24) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(3).pointZ);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(5).pointZ);
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(7).pointZ);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(9).pointZ);
			
		}
		else if( measPointList.stream().anyMatch(m -> m != null && m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0])) &&
			measPointList.stream().anyMatch(m -> m != null && m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1])) &&
			measPointList.stream().anyMatch(m -> m != null && m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[2])) ) {
			
			setText(Integer.parseInt(pillar.getId()), "bal ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 3) * MILLIMETER, 
					pillar.getStartY() - 15 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 2) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 16) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			
			setText(Integer.parseInt(pillar.getId()), "közép ak.: Bf. " + df.format(measPointList.get(4).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 6) * MILLIMETER, 
					pillar.getStartY() - 17 * MILLIMETER, 18, -90, false, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "közép ak.: Bf. " + df.format(measPointList.get(5).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 6) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(5).pointZ - elevationStartValue) + 18) * MILLIMETER, 
					18, -90, false, true, 0, 0, 0, 1);
			
			setText(Integer.parseInt(pillar.getId()), "jobb ak.: Bf. " + df.format(measPointList.get(6).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 12) * MILLIMETER, 
					pillar.getStartY() - 16 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb ak.: Bf. " + df.format(measPointList.get(7).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 12) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(5).pointZ - elevationStartValue) + 17) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(3).pointZ);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(5).pointZ);
			
			
		}
		else if( measPointList.stream().anyMatch(m -> m != null && m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0])) &&
				measPointList.stream().anyMatch(m -> m != null && m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1])) ) {
			setText(Integer.parseInt(pillar.getId()), "bal ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 3) * MILLIMETER, 
					pillar.getStartY() - 15 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 2) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 16) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb ak.: Bf. " + df.format(measPointList.get(4).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 7) * MILLIMETER, 
					pillar.getStartY() - 16 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb ak.: Bf. " + df.format(measPointList.get(5).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 7) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(5).pointZ - elevationStartValue) + 17) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(3).pointZ);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(5).pointZ);
		}
		else if( measPointList.size() == 4 && 
				 measPointList.stream().anyMatch(m -> m != null && m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0])) ) {
			setText(Integer.parseInt(pillar.getId()), "bal ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 3) * MILLIMETER, 
					pillar.getStartY() - 15 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 2) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 16) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(3).pointZ);
		}
		else if( measPointList.size() == 4 &&
				measPointList.stream().anyMatch(m -> m != null && m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1]))) {
			setText(Integer.parseInt(pillar.getId()), "jobb ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 7) * MILLIMETER, 
					pillar.getStartY() - 16 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 7) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 17) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(3).pointZ);
		}
		else if( measPointList.size() == 4 && 
				measPointList.stream().anyMatch(m -> m != null && m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[2])) ) {
			setText(Integer.parseInt(pillar.getId()), "közép ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 6) * MILLIMETER, 
					pillar.getStartY() - 17 * MILLIMETER, 18, -90, false, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "közép ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - HOR_SHIFT + 6) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 18) * MILLIMETER, 
					18, -90, false, true, 0, 0, 0, 1);
		}
		else if( measPointList.size() == 4 &&
				 measPointList.stream().anyMatch(m -> m != null && 
					m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[3]))) {
			setText(Integer.parseInt(pillar.getId()), "bal külső ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER, 
					pillar.getStartY() - 23 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal külső ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 23) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(3).pointZ);
		}
		else if( measPointList.size() == 4 && 
				measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[4]))) {
			setText(Integer.parseInt(pillar.getId()), "bal belső ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER, 
					pillar.getStartY() - 23 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal belső ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 23) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(3).pointZ);
		}
		else if( measPointList.size() == 4 &&
				measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[3]))) {
			setText(Integer.parseInt(pillar.getId()), "jobb belső ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER, 
					pillar.getStartY() - 24 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb belső ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 24) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(3).pointZ);
		}
		else if( measPointList.size() == 4 &&
				measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[4]))) {
			setText(Integer.parseInt(pillar.getId()), "jobb külső ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER, 
					pillar.getStartY() - 24 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb külső ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 24) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(3).pointZ);
		}
		else if( measPointList.size() == 6 &&  measPointList.stream().anyMatch(m -> m != null && 
					m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[3])) &&
				measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[4]))) {
			setText(Integer.parseInt(pillar.getId()), "bal külső ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 5) * MILLIMETER, 
					pillar.getStartY() - 23 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal külső ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 5) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 23) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			
			setText(Integer.parseInt(pillar.getId()), "bal belső ak.: Bf. " + df.format(measPointList.get(4).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER, 
					pillar.getStartY() - 23 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal belső ak.: Bf. " + df.format(measPointList.get(5).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(5).pointZ - elevationStartValue) + 23) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(3).pointZ);
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(5).pointZ);
		}
		else if( measPointList.size() == 6 && measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[3])) &&
			measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[4]))){
			
			setText(Integer.parseInt(pillar.getId()), "jobb belső ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER, 
					pillar.getStartY() - 24 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb belső ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 24) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			
			setText(Integer.parseInt(pillar.getId()), "jobb külső ak.: Bf. " + df.format(measPointList.get(4).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 17) * MILLIMETER, 
					pillar.getStartY() - 24 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb külső ak.: Bf. " + df.format(measPointList.get(5).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 17) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(5).pointZ - elevationStartValue) + 24) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(3).pointZ);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(5).pointZ);
		}
		else if( measPointList.size() == 6 && measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[3])) &&
				measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[4]))) {
			
			setText(Integer.parseInt(pillar.getId()), "bal külső ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER, 
					pillar.getStartY() - 23 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal külső ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 23) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb külső ak.: Bf. " + df.format(measPointList.get(4).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER, 
					pillar.getStartY() - 24 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb külső ak.: Bf. " + df.format(measPointList.get(5).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(5).pointZ - elevationStartValue) + 24) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(3).pointZ);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(5).pointZ);
		}
		else if( measPointList.size() == 6 && measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[4])) &&
				measPointList.stream().anyMatch(m -> m != null && 
				m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1] + "-" + CollectPillarSectionMeasurementData.POINT_TYPE[3]))) {
			setText(Integer.parseInt(pillar.getId()), "bal belső ak.: Bf. " + df.format(measPointList.get(2).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER, 
					pillar.getStartY() - 23 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "bal belső ak.: Bf. " + df.format(measPointList.get(3).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 9) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 23) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb belső ak.: Bf. " + df.format(measPointList.get(4).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER, 
					pillar.getStartY() - 24 * MILLIMETER, 18, -90, true, false, 0, 0, 0, 1);
			setText(Integer.parseInt(pillar.getId()), "jobb belső ak.: Bf. " + df.format(measPointList.get(5).pointZ).replace(",", ".") + "m", 
					(getHorizontalScaledDownLengthValue(pillarDistance) - 2 * HOR_SHIFT + 13) * MILLIMETER,
					PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(measPointList.get(3).pointZ - elevationStartValue) + 24) * MILLIMETER, 
					18, -90, true, true, 0, 0, 0, 1);
			drawLeftHood(pillar.getId(), pillarDistance, measPointList.get(3).pointZ);
			drawRightHood(pillar.getId(),pillarDistance, measPointList.get(5).pointZ);
		}
	}

	private void writeTopElevation(MeasPoint topPoint, double pillarDistance, int pillarId, boolean isEndPillar) {
		if( topPoint == null ) {
			return;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		setText(pillarId, "Csúcs", 
				(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(topPoint.pointZ - elevationStartValue) + 20) * MILLIMETER, 
				18, 0, false, true, 0, 0, 0, 1);
		setText(pillarId, "Bf. " + df.format(topPoint.pointZ).replace(",", ".") + "m", 
				isEndPillar ? (getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 11) * MILLIMETER :
					(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 4) * MILLIMETER, 
				 PAGE_Y + START_Y - (getVerticalScaledDownHeightValue(topPoint.pointZ - elevationStartValue) + 15) * MILLIMETER,
				18, 0, false, true, 0, 0, 0, 1);
		
	}
	
	public void drawPillarAutomatically(String pillarId, double pillarDistance, List<MeasPoint> measPointList, List<Double> distances) {
		Line pillar = new Line();
		pillar.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(pillarDistance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		pillar.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(measPointList.get(0).pointZ - elevationStartValue) * MILLIMETER);
		pillar.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(pillarDistance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		double maxTopElevation = measPointList.stream()
				.filter(e -> e != null && measPointList.indexOf(e) > 1 && e.isUpper)
				.mapToDouble(e -> e.pointZ).max().getAsDouble();
		if( measPointList.get(1) == null ) {
		pillar.setEndY(PAGE_Y + START_Y -
				getVerticalScaledDownHeightValue(maxTopElevation - elevationStartValue) * MILLIMETER);
		}
		else {
		pillar.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(measPointList.get(1).pointZ - elevationStartValue) * MILLIMETER);
		}
		pillar.setStroke(Color.BLUE);
		pillar.setStrokeWidth(3);
		pillar.setCursor(Cursor.HAND);
		pillar.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		root.getChildren().add(pillar);
		
		PillarData pillarData = new PillarData(measPointList.get(0).pointZ, 
				measPointList.get(1) == null ? maxTopElevation : measPointList.get(1).pointZ , pillarDistance, 
				measPointList.stream().anyMatch(m -> m != null && m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[0])), 
				measPointList.stream().anyMatch(m -> m != null && m.pointId.startsWith(CollectPillarSectionMeasurementData.POINT_TYPE[1])));
		pillarData.setId(ArchivFileBuilder.addID());
		pillar.setId(String.valueOf(pillarData.getId()));
		archivFileBuilder.addPillar(pillarData);
		
		setText(pillarData.getId(), pillarId + ".", 
				(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y + 30 * MILLIMETER, 18, 0, false, false, 0, 0, 0, 1);	
		setText(pillarData.getId(), df.format(pillarDistance).replace(",", ".") + "m", 
				distances == null ? (getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT) * MILLIMETER :
					(getHorizontalScaledDownLengthValue(pillarDistance)  + HOR_SHIFT - VER_SHIFT - 7) * MILLIMETER, 
				PAGE_Y + START_Y + 20, 18, 0, false, false, 0, 0, 0, 1);	
		writeDistances(distances, pillarDistance, pillarData.getId());
		writeElevations(measPointList, pillarDistance, pillar);
		writeTopElevation(measPointList.get(1), pillarDistance, pillarData.getId(), distances != null);
	}
	
	public void drawWireAutomatically(String id) {
		
	}
	
	public void drawElectricWire(String text, double groundElevation, double topElevation, double distance, boolean leftHand, boolean rightHand) {
		Line wire = new Line();
		wire.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		wire.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(groundElevation - elevationStartValue) * MILLIMETER);
		wire.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		wire.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
		wire.setStroke(Color.RED);
		wire.setStrokeWidth(3);
		wire.setCursor(Cursor.HAND);
		wire.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		root.getChildren().add(wire);
		
		WireData wireData = new WireData(groundElevation, topElevation, distance, leftHand, rightHand);
		wireData.setId(ArchivFileBuilder.addID());
		wire.setId(String.valueOf(wireData.getId()));
		archivFileBuilder.addWire(wireData);
		
		if( leftHand || rightHand ) {
			Line hood = new Line();
			hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
					.add(HOR_SHIFT * MILLIMETER));
			hood.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			hood.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			
			if( leftHand && !rightHand ) {
				
				hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
						.add(START_X)
						.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
						.add((HOR_SHIFT - 1) * MILLIMETER));
				}
				else if( !leftHand &&  rightHand) {
					
				hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
							.add(START_X)
							.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
							.add((HOR_SHIFT + 1) * MILLIMETER));
					
				}
				else if( leftHand && rightHand ) {
					hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
							.add(START_X)
							.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER).add((HOR_SHIFT -1) * MILLIMETER));
					hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
							.add(START_X)
							.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
							.add((HOR_SHIFT + 1) * MILLIMETER));
				}
			hood.setCursor(Cursor.HAND);
			hood.setStroke(Color.RED);
			hood.setId(String.valueOf(wireData.getId()));
			hood.setStrokeWidth(3);
			hood.setCursor(Cursor.HAND);
			hood.setOnMouseClicked( h -> {
				Line line = (Line) h.getSource();
				setDrawLineWindowData(line);
				deleteLine(line);
				});
			root.getChildren().add(hood);
		}
		
		setText(wireData.getId(), text, (getHorizontalScaledDownLengthValue(distance) + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y + 65, 18, 0, false, false, 0, 0, 0, 1);
		setText(wireData.getId(), "bal af.: Bf. " + df.format(groundElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, wire.getStartY(), 18, -90, true, false, 0, 0, 0, 1);
		setText(wireData.getId(), "bal af.: Bf. " + df.format(topElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, wire.getEndY(), 18, -90, true, true, 0, 0, 0, 1);
		setText(wireData.getId(), df.format(distance).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance) + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y + 50, 18, 0, false, false, 0, 0, 0, 1);
	}
	
	public void writeText(String text, double startX, double startY, TextData chosenTextData) {
		Text txt = new Text(text);
		TextData textData = new TextData();
		double xDistance; 
		if( chosenTextData != null ) {
		txt.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, chosenTextData.getSize())); 
		txt.setRotate(chosenTextData.getDirection());
		txt.setFill(new Color(chosenTextData.getRed(), chosenTextData.getGreen(), chosenTextData.getBlue(), chosenTextData.getOpacity()));
		textData.setAtTop(chosenTextData.isAtTop());
		textData.setRed(chosenTextData.getRed());
		textData.setGreen(chosenTextData.getGreen());
		textData.setBlue(chosenTextData.getBlue());
		textData.setOpacity(chosenTextData.getOpacity());
		xDistance = startX * MILLIMETER - (root.widthProperty().get() - A4_WIDTH) / 2 - START_X + 7 * MILLIMETER;;
		}
		else {
		txt.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 18));
		xDistance = startX * MILLIMETER - (root.widthProperty().get() - A4_WIDTH) / 2 - START_X;
		}
		textData.setTextValue(text);
		textData.setSize((int) txt.getFont().getSize());
		textData.setDirection((int) txt.getRotate());
		txt.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(xDistance));
		txt.setY(startY * MILLIMETER);
		textData.setX(txt.xProperty().get() - X_DISTANCE);
		textData.setY(txt.yProperty().get());
		textData.setId(ArchivFileBuilder.addID());
		if( chosenTextData != null )
		archivFileBuilder.addChosenTextToOwnerTextList(textData, chosenTextData.getId());
		else
		archivFileBuilder.addChosenTextToOwnerTextList(textData, 0);	
		txt.setCursor(Cursor.HAND);
		txt.setId(String.valueOf(textData.getId()));
		txt.setOnMouseClicked( t -> {
		Text inputText = (Text) t.getSource();
		showModifyTextWindow(inputText); });
		root.getChildren().add(txt);
	}
	
	private void showModifyTextWindow(Text text) {
		
		addChosenTextToSetTextWindow(text);
		
		if( modifyTextWindow == null ) {
			modifyTextWindow = new ModifyTextWindow(homeController, this);
		}
		else {
			modifyTextWindow.getStage().show();
		}
		modifyTextWindow.getStage().setAlwaysOnTop(true);
		modifyTextWindow.setInputText(text);
		modifyTextWindow.setRotateValue(Math.abs(text.getRotate()));
		TextData textData = archivFileBuilder.getTextData(Integer.parseInt(text.getId()));
		modifyTextWindow.getController().getColorPicker()
		.setValue(new Color(textData.getRed(), textData.getGreen(), textData.getBlue(), textData.getOpacity()));
	}
	
	private void addChosenTextToSetTextWindow(Text text) {
		homeController.showSetTextWindow();
		homeController.setTextWindow.getInputTextField().setText(text.getText());
		if( text.getRotate() == -90 && text.getText().startsWith("bal"))
		homeController.setTextWindow.getInputTextField().setText("jobb" + text.getText().substring(3));
		homeController.setTextWindow.getController().setChosenTextID(Integer.parseInt(text.getId()));
		DecimalFormat df = new DecimalFormat("0.0");
		String XPosition = df.format(text.xProperty().get() / MILLIMETER).replace(',', '.');
		String YPosition = df.format(text.yProperty().get() / MILLIMETER).replace(',', '.');
		homeController.setTextWindow.getInputTextXField().setText(XPosition);
		homeController.setTextWindow.getInputTextYField().setText(YPosition);
	}
	
	public void setText(int id, String text, double startX, double startY, int size, int rotate, 
			boolean isOnLeftSide, boolean isAtTop, double red, double green, double blue, double opacity) {
		Text txt = new Text(text);
		txt.setFill(new Color(red, green, blue, opacity));
		txt.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, size));
		txt.setRotationAxis(Rotate.Z_AXIS);
		txt.setRotate(rotate);
		txt.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(startX));
		txt.setY(startY);
		
		int pillarTextId = getPillarTextId(id, text, startX, startY, size, rotate, isOnLeftSide, isAtTop, red, blue, green, opacity);
		int wireTextId = getWireTextId(id, text, startX, startY, size, rotate, isOnLeftSide, isAtTop, red, blue, green, opacity);
		
		if( pillarTextId != -1 ) {
			txt.setId(String.valueOf(pillarTextId));
		}
		else if ( wireTextId != -1 ) {
			txt.setId(String.valueOf(wireTextId));
		}
		else if ( id != - 1){
		TextData singleText = new TextData(text, startX, startY, size, rotate, "SingleText");
		singleText.setRed(red);
		singleText.setGreen(green);
		singleText.setBlue(blue);
		singleText.setOpacity(opacity);
		singleText.setId(ArchivFileBuilder.addID());
		archivFileBuilder.addText(singleText);
		txt.setId(String.valueOf(singleText.getId()));
		}
		else if( id == -1 ) {
		TextData baseText = new TextData(text, startX, startY, size, rotate, "SingleText");
		baseText.setId(id);
		baseText.setRed(red);
		baseText.setGreen(green);
		baseText.setBlue(blue);
		baseText.setOpacity(opacity);
		archivFileBuilder.addText(baseText);
		txt.setId("-1");
		}
		txt.setCursor(Cursor.HAND); 
		txt.setOnMouseClicked( t -> {
		Text inputText = (Text) t.getSource();
		showModifyTextWindow(inputText); });
		root.getChildren().add(txt);
	}
	
	private int getPillarTextId(int id, String text, double startX, double startY, int size, int rotate, 
			boolean isOnLeftSide, boolean isAtTop, double red, double green, double blue, double opacity) {
		PillarData pillar = archivFileBuilder.getPillarData(id);
		if( pillar == null )
			return -1;
		TextData pillarText = new TextData(text, startX, startY, size, rotate, "PillarText");
		pillarText.setId(ArchivFileBuilder.addID());
		pillarText.setOnLeftSide(isOnLeftSide);
		pillarText.setAtTop(isAtTop);
		pillarText.setRed(red);
		pillarText.setGreen(green);
		pillarText.setBlue(blue);
		pillarText.setOpacity(opacity);
		pillar.getPillarTextList().add(pillarText);
		return pillarText.getId();
	}
	
	private int getWireTextId(int id, String text, double startX, double startY, int size, int rotate,
			boolean isOnLeftSide, boolean isAtTop, double red, double green, double blue, double opacity) {
		WireData wire = archivFileBuilder.getWireData(id);
		if( wire == null )
			return -1;
		TextData wireText = new TextData(text, startX, startY, size, rotate, "WireText");
		wireText.setId(ArchivFileBuilder.addID());
		wireText.setOnLeftSide(isOnLeftSide);
		wireText.setAtTop(isAtTop);
		wireText.setRed(red);
		wireText.setGreen(green);
		wireText.setBlue(blue);
		wireText.setOpacity(opacity);
		wire.getWireTextList().add(wireText);
		return wireText.getId();
	}
	
	public boolean deleteText(Text text) {
		if( homeController.getConfirmationAlert("Felirat törlése", "Biztos, hogy törlöd a kiválaszott feliratot?") ) {
			int id = Integer.valueOf(text.getId());
			archivFileBuilder.removeText(id);
			archivFileBuilder.removeWireText(id);
			archivFileBuilder.removePillarText(id);
			root.getChildren().remove(text);
			return true;
		}
		return false;
		}

	public void modifyText(Text text, String txt, Color color, int rotate, int size) {
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		if( textData != null ) {
		double xDistance = text.getX() - root.widthProperty().divide(2).subtract(A4_WIDTH / 2).get();	
		textData.setDirection(-1 * rotate);	
		textData.setTextValue(txt);
		textData.setSize(size);
		textData.setRed(color.getRed());
		textData.setGreen(color.getGreen());
		textData.setBlue(color.getBlue());
		textData.setX(text.xProperty().get() - X_DISTANCE);
		textData.setY(text.yProperty().get());
		text.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, size));
		text.setFill(color);
		text.setText(txt);
		text.setRotationAxis(Rotate.Z_AXIS);
		text.setRotate(textData.getDirection());
		text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(xDistance));
		text.setY(text.getY());
		}
	}
	
	public void moveTextLeft(Text text) {
		double actualXPosition = text.getX();
		double xDistance = actualXPosition - root.widthProperty().divide(2).subtract(A4_WIDTH / 2).get();
		text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(xDistance).subtract(MILLIMETER));
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		textData.setX(text.xProperty().get() - X_DISTANCE);
		textData.setY(text.yProperty().get());
	}
	
	public void moveTextRight(Text text) {
		double actualXPosition = text.getX();
		double xDistance = actualXPosition - root.widthProperty().divide(2).subtract(A4_WIDTH / 2).get();
		text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(xDistance).add(MILLIMETER));
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		textData.setX(text.xProperty().get() - X_DISTANCE);
		textData.setY(text.yProperty().get());
	}
	
	public void moveTextUp(Text text) {
		text.setY(text.getY() - MILLIMETER);
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		textData.setX(text.xProperty().get() - X_DISTANCE);
		textData.setY(text.yProperty().get());
	}
	
	public void moveTextDown(Text text) {
		text.setY(text.getY() + MILLIMETER);
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		textData.setX(text.xProperty().get() - X_DISTANCE);
		textData.setY(text.yProperty().get());
	}
	
	public void setTextSize(Text text, int size) {
		Font font = Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, size);
		text.setFont(font);
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		if( textData != null ) {
			textData.setSize(size);
		}
	}
	
	public void drawInputPillar(int id) {
		
		PillarData pD = archivFileBuilder.getPillarData(id);
		Line pillar = new Line();
		pillar.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		pillar.setStartY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(pD.getGroundElevation() - 
						archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
		pillar.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		pillar.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(pD.getTopElevetaion() - 
				archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
		pillar.setStroke(Color.BLUE);
		pillar.setStrokeWidth(3);
		pillar.setCursor(Cursor.HAND);
		pillar.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		pillar.setId(String.valueOf(id));
		root.getChildren().add(pillar);
		
		if( pD.isLeftHand() || pD.isRightHand() ) {
			Line hood = new Line();
			hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER)
					.add(HOR_SHIFT * MILLIMETER));
			hood.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(pD.getTopElevetaion() - 
					archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
			hood.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(pD.getTopElevetaion() - 
					archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
			
				if( pD.isLeftHand() && !pD.isRightHand() ) {
				
				hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
						.add(START_X)
						.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER)
						.add((HOR_SHIFT - 1) * MILLIMETER));
				}
				else if( !pD.isLeftHand() &&  pD.isRightHand()) {
					
				hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
							.add(START_X)
							.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER)
							.add((HOR_SHIFT + 1) * MILLIMETER));
					
				}
				else if( pD.isLeftHand() && pD.isRightHand() ) {
					hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
							.add(START_X)
							.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER).add((HOR_SHIFT - 1) * MILLIMETER));
					hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
							.add(START_X)
							.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER)
							.add((HOR_SHIFT + 1) * MILLIMETER));
				}	
			hood.setCursor(Cursor.HAND);
			hood.setOnMouseClicked( h -> {
				Line line = (Line) h.getSource();
				setDrawLineWindowData(line);
				deleteLine(line);
				});
			hood.setId(String.valueOf(id));
			hood.setStroke(Color.BLUE);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
		
	}
	
	public void drawInputPillarText(PillarData pillarData, double shiftY1) {
		
		for (TextData textData : pillarData.getPillarTextList()) {
			if( textData.getDirection() == -90 && textData.isOnLeftSide() && !textData.getTextValue().startsWith("közép"))
			textData.setX((getHorizontalScaledDownLengthValue(pillarData.getDistanceOfPillar()) - HOR_SHIFT) * MILLIMETER);
			else if( textData.getDirection() == -90 && !textData.isOnLeftSide() && !textData.getTextValue().startsWith("közép"))
			textData.setX((getHorizontalScaledDownLengthValue(pillarData.getDistanceOfPillar()) - VER_SHIFT) * MILLIMETER);
			else if( textData.getDirection() == -90 && textData.isOnLeftSide() && textData.getTextValue().startsWith("közép"))
			textData.setX((getHorizontalScaledDownLengthValue(pillarData.getDistanceOfPillar()) - 10) * MILLIMETER);
			else if( textData.getDirection() == -90 && !textData.isOnLeftSide() && textData.getTextValue().startsWith("közép"))
			textData.setX((getHorizontalScaledDownLengthValue(pillarData.getDistanceOfPillar()) - 10) * MILLIMETER);
			else
			textData.setX((getHorizontalScaledDownLengthValue(pillarData.getDistanceOfPillar()) + HOR_SHIFT - VER_SHIFT) * MILLIMETER);
			
			Text text = new Text(textData.getTextValue());
			text.setId(String.valueOf(textData.getId()));
			text.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, textData.getSize()));
			text.setFill(new Color(textData.getRed(), textData.getGreen(), textData.getBlue(), textData.getOpacity()));
			if( textData.getDirection() == -90 ) {
				text.setRotationAxis(Rotate.Z_AXIS);
				text.setRotate(-90);
				}
			text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(textData.getX()));
			
			String[] values = textData.getTextValue().split("\\s+");
			
			if( textData.getTextValue().startsWith("bal") && shiftY1 != 0 && values.length > 2)
				textData.setY(textData.getY() + shiftY1);
			else if( textData.getTextValue().startsWith("közép") && shiftY1 != 0 && values.length > 2)
				textData.setY(textData.getY() + shiftY1);
			else if( textData.getTextValue().startsWith("jobb") && shiftY1 != 0 && values.length > 2)
				textData.setY(textData.getY() + shiftY1);
			else if( textData.getTextValue().startsWith("bal") && textData.isAtTop() && values.length > 2)
				textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(pillarData.getTopElevetaion() - elevationStartValue) * MILLIMETER);
			else if( textData.getTextValue().startsWith("bal") && !textData.isAtTop() && values.length > 2)
				textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(pillarData.getGroundElevation() - elevationStartValue) * MILLIMETER);
			else if( textData.getTextValue().startsWith("jobb") && textData.isAtTop() && values.length > 2)
					textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(pillarData.getTopElevetaion() - elevationStartValue) * MILLIMETER);
			else if( textData.getTextValue().startsWith("jobb") && !textData.isAtTop() && values.length > 2)
					textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(pillarData.getGroundElevation() - elevationStartValue) * MILLIMETER);
			else if( textData.getTextValue().startsWith("közép") && textData.isAtTop() && values.length > 2)
				textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(pillarData.getTopElevetaion() - elevationStartValue) * MILLIMETER);
			else if( textData.getTextValue().startsWith("közép") && !textData.isAtTop() && values.length > 2)
				textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(pillarData.getGroundElevation() - elevationStartValue) * MILLIMETER);	
			text.setY(textData.getY());
				text.setOnMouseClicked( t -> {
					Text inputText = (Text) t.getSource();
					showModifyTextWindow(inputText); });
				text.setCursor(Cursor.HAND);
				root.getChildren().add(text);
		}
	}
	
	
	public void drawInputWire(int id) {
		
		WireData wD = archivFileBuilder.getWireData(id);
		Line wire = new Line();
		wire.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		wire.setStartY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(wD.getGroundElevation() - 
						archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
		wire.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		wire.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(wD.getTopElevetaion() - 
				archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
		wire.setStroke(Color.RED);
		wire.setStrokeWidth(3);
		wire.setCursor(Cursor.HAND);
		wire.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		wire.setId(String.valueOf(id));
		root.getChildren().add(wire);
		
		if( wD.isLeftHand() || wD.isRightHand() ) {
			Line hood = new Line();
			hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER)
					.add(HOR_SHIFT * MILLIMETER));
			hood.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(wD.getTopElevetaion() - 
					archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
			hood.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(wD.getTopElevetaion() - 
					archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
				if( wD.isLeftHand() && !wD.isRightHand() ) {
				
				hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
						.add(START_X)
						.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER)
						.add((HOR_SHIFT - 1) * MILLIMETER));
				}
				else if( !wD.isLeftHand() &&  wD.isRightHand()) {
					
				hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
							.add(START_X)
							.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER)
							.add((HOR_SHIFT + 1) * MILLIMETER));
					
				}
				else if( wD.isLeftHand() && wD.isRightHand() ) {
					hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
							.add(START_X)
							.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER).add((HOR_SHIFT - 1) * MILLIMETER));
					hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
							.add(START_X)
							.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER)
							.add((HOR_SHIFT + 1) * MILLIMETER));
				}
			hood.setCursor(Cursor.HAND);
			hood.setOnMouseClicked( h -> {
				Line line = (Line) h.getSource();
				setDrawLineWindowData(line);
				deleteLine(line);
				});
			hood.setId(String.valueOf(id));
			hood.setStroke(Color.RED);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
	}
	
	public void drawInputWireText(WireData wireData, double shiftY) {
		
		for (TextData textData : wireData.getWireTextList()) {
			if( textData.getDirection() == -90 && textData.isOnLeftSide() && !textData.getTextValue().startsWith("közép"))
			textData.setX((getHorizontalScaledDownLengthValue(wireData.getDistanceOfWire()) - HOR_SHIFT) * MILLIMETER);
			else if( textData.getDirection() == -90 && !textData.isOnLeftSide() && !textData.getTextValue().startsWith("közép"))
			textData.setX((getHorizontalScaledDownLengthValue(wireData.getDistanceOfWire()) - VER_SHIFT) * MILLIMETER);
			else if( textData.getDirection() == -90 && textData.isOnLeftSide() && textData.getTextValue().startsWith("közép"))
			textData.setX((getHorizontalScaledDownLengthValue(wireData.getDistanceOfWire()) - 10) * MILLIMETER);
			else if( textData.getDirection() == -90 && !textData.isOnLeftSide() && textData.getTextValue().startsWith("közép"))
			textData.setX((getHorizontalScaledDownLengthValue(wireData.getDistanceOfWire()) - 10) * MILLIMETER);
			else
			textData.setX((getHorizontalScaledDownLengthValue(wireData.getDistanceOfWire()) + HOR_SHIFT - VER_SHIFT) * MILLIMETER);
			Text text = new Text(textData.getTextValue());
			text.setId(String.valueOf(textData.getId()));
			text.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, textData.getSize()));
			text.setFill(new Color(textData.getRed(), textData.getGreen(), textData.getBlue(), textData.getOpacity()));
			if( textData.getDirection() == -90 ) {
				text.setRotationAxis(Rotate.Z_AXIS);
				text.setRotate(-90);
				}
				text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(textData.getX()));
				String[] values = textData.getTextValue().split("\\s+");
				if( textData.getTextValue().startsWith("bal") && shiftY != 0 && values.length > 2 )
				textData.setY(textData.getY() + shiftY);
				else if( textData.getTextValue().startsWith("közép") && shiftY != 0 && values.length > 2 )
					textData.setY(textData.getY() + shiftY);
				else if( textData.getTextValue().startsWith("jobb") && shiftY != 0 && values.length > 2 )
				textData.setY(textData.getY() + shiftY);
				else if( textData.getTextValue().startsWith("bal") && textData.isAtTop() && values.length > 2 )
					textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(wireData.getTopElevetaion() - elevationStartValue) * MILLIMETER);
				else if( textData.getTextValue().startsWith("bal") && !textData.isAtTop() && values.length > 2 )
					textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(wireData.getGroundElevation() - elevationStartValue) * MILLIMETER);
				else if( textData.getTextValue().startsWith("jobb") && textData.isAtTop() && values.length > 2)
						textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(wireData.getTopElevetaion() - elevationStartValue) * MILLIMETER);
				else if( textData.getTextValue().startsWith("jobb") && !textData.isAtTop() && values.length > 2)
						textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(wireData.getGroundElevation() - elevationStartValue) * MILLIMETER);
				else if( textData.getTextValue().startsWith("közép") && textData.isAtTop() && values.length > 2)
					textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(wireData.getTopElevetaion() - elevationStartValue) * MILLIMETER);
				else if( textData.getTextValue().startsWith("közép") && !textData.isAtTop() && values.length > 2)
					textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(wireData.getGroundElevation() - elevationStartValue) * MILLIMETER);
				text.setY(textData.getY());
				text.setOnMouseClicked( t -> {
					Text inputText = (Text) t.getSource();
					showModifyTextWindow(inputText); });
				text.setCursor(Cursor.HAND);
				root.getChildren().add(text);
		}
	}
	
	
	public void drawInputText(TextData textData) {
		Text text = new Text(textData.getTextValue());
		text.setId(String.valueOf(textData.getId()));
		text.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, textData.getSize()));
		text.setRotationAxis(Rotate.Z_AXIS);
		text.setRotate(textData.getDirection());
		text.setFill(new Color(textData.getRed(), textData.getGreen(), textData.getBlue(), textData.getOpacity()));
		text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(textData.getX()));
		text.setY(textData.getY());
		text.setCursor(Cursor.HAND);
		text.setOnMouseClicked( t -> {
		Text inputText = (Text) t.getSource();
				showModifyTextWindow(inputText); });
		root.getChildren().add(text);
	}
	
	public void drawCalculatedWire(List<WirePoint> wirePoints, String wireType) {
		
		double scale = horizontalScale / 1000d;
		PillarData beginnerPillar = archivFileBuilder.getBeginnerPillar();
		double beginnerPillarElevation = archivFileBuilder.getElevation(beginnerPillar.getPillarTextList(), wireType);
		String id = homeController.calculator.getWireIDAsString(wireType);
		for (int i = 0; i < wirePoints.size(); i += scale) {
			Circle dot = new Circle();
			dot.setId(id);
			setWireColor(dot);
			dot.setRadius(1);
			dot.setCursor(Cursor.HAND);
			dot.setOnMouseClicked( d -> {
			Circle spot = (Circle) d.getSource();
			deleteCalculatedWireAndActualData(spot.getId());
			if( !isAnyVisibleWire() )
				deleteBackgroundCell();
			});
			dot.centerXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER)
					.add(getHorizontalScaledDownLengthValue(wirePoints.get(i).getDistanceOfWirePoint()) * MILLIMETER));
			dot.setCenterY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(beginnerPillarElevation - 
					archivFileBuilder.getSystemData().getElevationStartValue() + wirePoints.get(i).getElevationOfWirePoint()) * MILLIMETER);
			root.getChildren().add(dot);
		}
		
	}
	
	private void setWireColor(Circle dot) {
		
		switch (ElectricWireCalculator.wireID) {
		case 2:
			dot.setStroke(Color.GOLD);
			homeController.calculator.setWireColor(Color.GOLD);
			break;
		case 3:
			dot.setStroke(Color.CRIMSON);
			homeController.calculator.setWireColor(Color.CRIMSON);
			break;
		case 4:
			dot.setStroke(Color.AQUA);
			homeController.calculator.setWireColor(Color.AQUA);
			break;
		case 5:
			dot.setStroke(Color.CORNFLOWERBLUE);
			homeController.calculator.setWireColor(Color.CORNFLOWERBLUE);
			break;
		case 6:
			dot.setStroke(Color.DARKGREEN);
			homeController.calculator.setWireColor(Color.DARKGREEN);
			break;
		case 7:
			dot.setStroke(Color.MEDIUMPURPLE);
			homeController.calculator.setWireColor(Color.MEDIUMPURPLE);
			break;
		case 8:
			dot.setStroke(Color.RED);
			homeController.calculator.setWireColor(Color.RED);
			break;
		case 9:
			dot.setStroke(Color.BLUE);
			homeController.calculator.setWireColor(Color.BLUE);
			break;
		case 10:
			dot.setStroke(Color.SPRINGGREEN);
			homeController.calculator.setWireColor(Color.SPRINGGREEN);
			break;
		default:
			dot.setStroke(Color.MAGENTA);
			homeController.calculator.setWireColor(Color.MAGENTA);
		}
}

	private void deleteCalculatedWireAndActualData(String wireID) {
		
	if(homeController.calculator != null && 
				wireID.equals(homeController.calculator.wireType + "_" + ElectricWireCalculator.wireID)) {
		
		if(homeController.getConfirmationAlert("Sodrony törlése",
				"Biztos, hogy törlöd az aktuális sodronyt?\nA kijelzett adatok is törlésre kerülnek.")){
		
		for(int i = root.getChildren().size() - 1; i >= 0; i--) {
			if(wireID.equals(root.getChildren().get(i).getId())) {
				root.getChildren().remove(i);
			}
		}
		deletePreResultsData();
		deleteWireDifferences(wireID);
		homeController.calculator = null;
		}
}
	else if( homeController.getConfirmationAlert("Sodrony törlése", "Biztos, hogy törlöd a kiválasztott sodronyt?") ) {
		for(int i = root.getChildren().size() - 1; i >= 0; i--) {
			if(wireID.equals(root.getChildren().get(i).getId())) {
				root.getChildren().remove(i);
			}
		}
		deleteWireDifferences(wireID);
	}
		
}
	

	private void deleteLine(Line line) {
	if( homeController.getConfirmationAlert("Vonal rajzi elem törlése", 
			"Biztos, hogy törlöd a kiválasztott vonalat és feliratait?") ) {
		int id = Integer.valueOf(line.getId());
		archivFileBuilder.removePillar(id, root);
		archivFileBuilder.removeWire(id, root);
		archivFileBuilder.removeLine(id, root);
	}
}
	private void setDrawLineWindowData(Line line) {
		homeController.showSetLineDataWindow();
		int id = Integer.valueOf(line.getId());
		LineData lineData = archivFileBuilder.getLineData(id);
		PillarData pillarData = archivFileBuilder.getPillarData(id);
		WireData wireData = archivFileBuilder.getWireData(id);
		if( lineData != null) {
		homeController.setLineWindow.getController().getStartXTextField().setText(String.valueOf(lineData.getStartX()));
		homeController.setLineWindow.getController().getStartYTextField().setText(String.valueOf(lineData.getStartY()));
		homeController.setLineWindow.getController().getEndXTextField().setText(String.valueOf(lineData.getEndX()));
		homeController.setLineWindow.getController().getEndYTextField().setText(String.valueOf(lineData.getEndY()));
		homeController.setLineWindow.getController().getLineTypeComboBox().setValue(lineData.getType());
		homeController.setLineWindow.getController().getLineColorPicker()
		.setValue(new Color(lineData.getRed(), lineData.getGreen(), lineData.getBlue(), lineData.getOpacity()));
		homeController.setLineWindow.getController().getLineWidthComboBox().setValue(lineData.getWidth());
		}
		else if( pillarData != null ) {
		homeController.setLineWindow.getController().getStartXTextField().setText(String.valueOf(pillarData.getDistanceOfPillar()));
		homeController.setLineWindow.getController().getStartYTextField().setText(String.valueOf(pillarData.getGroundElevation()));
		homeController.setLineWindow.getController().getEndXTextField().setText(String.valueOf(pillarData.getDistanceOfPillar()));
		homeController.setLineWindow.getController().getEndYTextField().setText(String.valueOf(pillarData.getTopElevetaion()));
		homeController.setLineWindow.getController().getLineTypeComboBox().setValue("folyamatos");
		homeController.setLineWindow.getController().getLineColorPicker().setValue(Color.BLUE);
			homeController.setLineWindow.getController().getLineWidthComboBox().setValue("3");
		}
		else if( wireData != null ) {
		homeController.setLineWindow.getController().getStartXTextField().setText(String.valueOf(wireData.getDistanceOfWire()));
		homeController.setLineWindow.getController().getStartYTextField().setText(String.valueOf(wireData.getGroundElevation()));
		homeController.setLineWindow.getController().getEndXTextField().setText(String.valueOf(wireData.getDistanceOfWire()));
		homeController.setLineWindow.getController().getEndYTextField().setText(String.valueOf(wireData.getTopElevetaion()));
		homeController.setLineWindow.getController().getLineTypeComboBox().setValue("folyamatos");
		homeController.setLineWindow.getController().getLineColorPicker().setValue(Color.RED);
		homeController.setLineWindow.getController().getLineWidthComboBox().setValue("3");	
		}
	}
	
	public void drawLine(double startX, double startY, double endX, double endY, String type, Color color, String width) {
		
		/*if( 0 > startX || startX > lengthOfHorizontalAxis ) {
			homeController.getWarningAlert("Nem megfelelő StartX koordináta érték",
					"Az X koordináta értéke: X >= 0 és " + lengthOfHorizontalAxis + " >= X");
			return;
		}
		if( 0 > endX || endX > lengthOfHorizontalAxis ) {
			homeController.getWarningAlert("Nem megfelelő VégeX koordináta érték",
					"Az X koordináta értéke: X >= 0 és " + lengthOfHorizontalAxis + " >= X");
			return;
		}
		if( elevationStartValue > startY  ||  startY > elevationStartValue + 10 * verticalScale) {
			homeController.getWarningAlert("Nem megfelelő StartY koordináta érték", 
					"Az Y koordináta értéke: Y >= " + elevationStartValue + " és " +
				(elevationStartValue + 10 * verticalScale) + " >= Y");
			return;
		}
		if( elevationStartValue > endY  ||  endY > elevationStartValue + 10 * verticalScale) {
			homeController.getWarningAlert("Nem megfelelő VégeY koordináta érték", 
					"Az Y koordináta értéke: Y >= " + elevationStartValue + " és " +
				(elevationStartValue + 10 * verticalScale) + " >= Y");
			return;
		}*/
		LineData lineData = new LineData(startX, startY, endX, endY, type, 
				color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity(), width);
		lineData.setId(ArchivFileBuilder.addID());
		archivFileBuilder.addLine(lineData);
		Line newLine = new Line();
		newLine.setStrokeWidth(1);
		switch (width) {
		case "0.5":
			newLine.setStrokeWidth(0.5);
			break;
		case "3":
			newLine.setStrokeWidth(3);
			break;
		
		}
		switch (type) {
		case "szaggatott":
			newLine.getStrokeDashArray().addAll(4d, 5d);
			break;
		case "pontozott":
			newLine.getStrokeDashArray().addAll(1d, 4d);
			break;
		
		}
		newLine.setStyle("-fx-stroke:" + toHexString(color) + ";");
		newLine.setCursor(Cursor.HAND);
		newLine.setId(String.valueOf(lineData.getId()));
		newLine.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		newLine.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X).add((getHorizontalScaledDownLengthValue(startX) + HOR_SHIFT) * MILLIMETER));
		newLine.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(startY - elevationStartValue) * MILLIMETER);
		newLine.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X).add((getHorizontalScaledDownLengthValue(endX) + HOR_SHIFT) * MILLIMETER));
		newLine.setEndY(PAGE_Y + START_Y  - getVerticalScaledDownHeightValue(endY - elevationStartValue) * MILLIMETER);
		root.getChildren().add(newLine);
	}
	
	public void drawInputLine(int id, double startX, double startY, double endX, double endY, String type, Color color, String width) {
		Line newLine = new Line();
		newLine.setStrokeWidth(1);
		switch (width) {
		case "0.5":
			newLine.setStrokeWidth(0.5);
			break;
		case "3":
			newLine.setStrokeWidth(3);
			break;
		}
		switch (type) {
		case "szaggatott":
			newLine.getStrokeDashArray().addAll(4d, 5d);
			break;
		case "pontozott":
			newLine.getStrokeDashArray().addAll(1d, 4d);
			break;
		
		}
		newLine.setStyle("-fx-stroke:" + toHexString(color) + ";");
		newLine.setCursor(Cursor.HAND);
		newLine.setId(String.valueOf(id));
		newLine.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		newLine.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X).add((getHorizontalScaledDownLengthValue(startX) + HOR_SHIFT) * MILLIMETER));
		newLine.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(startY - elevationStartValue) * MILLIMETER);
		newLine.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X).add((getHorizontalScaledDownLengthValue(endX) + HOR_SHIFT) * MILLIMETER));
		newLine.setEndY(PAGE_Y + START_Y  - getVerticalScaledDownHeightValue(endY - elevationStartValue) * MILLIMETER);
		root.getChildren().add(newLine);
	}
	
	private String toHexString(Color color) {
		  int r = ((int) Math.round(color.getRed()     * 255)) << 24;
		  int g = ((int) Math.round(color.getGreen()   * 255)) << 16;
		  int b = ((int) Math.round(color.getBlue()    * 255)) << 8;
		  int a = ((int) Math.round(color.getOpacity() * 255));
		  return String.format("#%08X", (r + g + b + a));
		}
	
	public double getHorizontalScaledDownLengthValue(double length) {
		return horizontalScale == 1000 ? length : 1000.0  * length / horizontalScale;
	}
	
	public double getVerticalScaledDownHeightValue(double height) {
		return verticalScale == 10 ? height : 10.0 * height / verticalScale;
	}
	
	public void drawHangingArrow(double distance, double hangingValue, double deltaPillarElevation, String wireType) {
		PillarData beginnerPillar = archivFileBuilder.getBeginnerPillar();
		PillarData lastPillar = archivFileBuilder.getLastPillar();
		double beginnerElevation = archivFileBuilder.getElevation(beginnerPillar.getPillarTextList(), wireType);
		double lastPillarDistance = 
				archivFileBuilder.getDistance(lastPillar.getPillarTextList(), wireType) == null ? 
						lastPillar.getDistanceOfPillar() : archivFileBuilder.getDistance(lastPillar.getPillarTextList(), wireType);
		double lastPillarElevation = archivFileBuilder.getElevation(lastPillar.getPillarTextList(), wireType);
		Line betweenPillarsLine = new Line();
		betweenPillarsLine.setStrokeWidth(2);
		betweenPillarsLine.setStroke(Color.DARKORANGE);
		betweenPillarsLine.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(beginnerPillar.getDistanceOfPillar()) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		betweenPillarsLine.setStartY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(beginnerElevation 
						- archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
		betweenPillarsLine.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(lastPillarDistance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		betweenPillarsLine.setEndY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(lastPillarElevation 
						- archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
		betweenPillarsLine.setId("line");
		Line line = new Line();
		line.setStroke(Color.DARKORANGE);
		line.setStrokeWidth(2);
		line.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		line.setStartY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(beginnerElevation 
						- archivFileBuilder.getSystemData().getElevationStartValue() + deltaPillarElevation) * MILLIMETER);
		line.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		line.setEndY(PAGE_Y + START_Y 
				- getVerticalScaledDownHeightValue(beginnerElevation 
						- archivFileBuilder.getSystemData().getElevationStartValue() - hangingValue + deltaPillarElevation) * MILLIMETER);
		line.setId("arrow");
		Line leftArrow = new Line();
		leftArrow.setStroke(Color.DARKORANGE);
		leftArrow.setStrokeWidth(2);
		leftArrow.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		leftArrow.setStartY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(beginnerElevation - archivFileBuilder.getSystemData().getElevationStartValue() -
						hangingValue + deltaPillarElevation) * MILLIMETER);
		leftArrow.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add((HOR_SHIFT - 2) * MILLIMETER));
		leftArrow.setEndY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(beginnerElevation - archivFileBuilder.getSystemData().getElevationStartValue() -
						hangingValue + deltaPillarElevation) * MILLIMETER - 2 * MILLIMETER);
		leftArrow.setId("arrow");
		Line rightArrow = new Line();
		rightArrow.setStroke(Color.DARKORANGE);
		rightArrow.setStrokeWidth(2);
		rightArrow.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		rightArrow.setStartY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(beginnerElevation - archivFileBuilder.getSystemData().getElevationStartValue() -
						hangingValue + deltaPillarElevation) * MILLIMETER);
		rightArrow.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add((HOR_SHIFT + 2) * MILLIMETER));
		rightArrow.setEndY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(beginnerElevation - archivFileBuilder.getSystemData().getElevationStartValue() -
						hangingValue + deltaPillarElevation) * MILLIMETER - 2 * MILLIMETER);
		rightArrow.setId("arrow");
		root.getChildren().addAll(betweenPillarsLine, line, leftArrow, rightArrow);
	}
	
	public void deleteHangingArrow() {
		
		for (int i = root.getChildren().size() - 1; i >= 0; i--) {
			if( "arrow".equals(root.getChildren().get(i).getId()) || "line".equals(root.getChildren().get(i).getId()))
				root.getChildren().remove(i);
		}
		
	}
	
	public void showPreResultsData() {
		
		deletePreResultsData();
		Rectangle backgroundCell = new Rectangle();
		backgroundCell.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).add((VER_SHIFT - 2) * MILLIMETER));
		backgroundCell.setY(PAGE_Y);
		backgroundCell.setWidth(60 * MILLIMETER);
		backgroundCell.heightProperty().bind(root.heightProperty().subtract(30));
		backgroundCell.setFill(Color.WHITE);
		backgroundCell.setId("preResult");
		
		DecimalFormat df = new DecimalFormat("0.00");
		Text position = new Text("Sodrony helye:");
		position.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		position.setY(50);
		position.setId("preResult");
		Text wirePosition = new Text(homeController.calculator.wireType);
		wirePosition.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wirePosition.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wirePosition.getText().length()) / 2 ));
		wirePosition.setY(50);
		wirePosition.setId("preResult");
		
		Text name = new Text("Sodrony típusa:");
		name.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		name.setY(68);
		name.setId("preResult");
		Text wireTypeName = new Text(homeController.calculator.wireTypeName);
		wireTypeName.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireTypeName.xProperty().
		bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).add(MILLIMETER * (63 - wireTypeName.getText().length()) / 2 ));
		wireTypeName.setY(68);
		wireTypeName.setId("preResult");
		
		Text color = new Text("Sodrony színe:");
		color.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		color.setY(86);
		color.setId("preResult");
		Rectangle wireColorCell = new Rectangle();
		wireColorCell.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).add(30 * MILLIMETER));
		wireColorCell.setY(73);
		wireColorCell.setWidth(20);
		wireColorCell.setHeight(20);
		wireColorCell.setFill(homeController.calculator.wireColor);
		wireColorCell.setId("preResult");
		
		Text length = new Text("Sodrony hossza:");
		length.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		length.setY(104);
		length.setId("preResult");
		Text wireLength = new Text(df.format(homeController.calculator.sodrony_hossza).replace(",", ".") + " (m)");
		wireLength.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireLength.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireLength.getText().length()) / 2 ));
		wireLength.setY(104);
		wireLength.setId("preResult");
		
		df.applyPattern("0.0");
		Text temperature = new Text("Hőmérséklet:");
		temperature.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		temperature.setY(122);
		temperature.setId("preResult");
		Text wireTemperature = new Text(df.format(homeController.calculator.t).replace(",", ".") + " (°C)");
		wireTemperature.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireTemperature.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireTemperature.getText().length()) / 2 ));
		wireTemperature.setY(122);
		wireTemperature.setId("preResult");
		
		df.applyPattern("0.00");
		Text keresztmetszet = new Text("Keresztmetszet:");
		keresztmetszet.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		keresztmetszet.setY(155);
		keresztmetszet.setId("preResult");
		Text wireKeresztmetszet = new Text(df.format(homeController.calculator.wireData.getKeresztMetszet()).replace(",", ".") + " (mm2)");
		wireKeresztmetszet.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireKeresztmetszet.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireKeresztmetszet.getText().length()) / 2 ));
		wireKeresztmetszet.setY(155);
		wireKeresztmetszet.setId("preResult");
		
		Text diameter = new Text("Átmérő:");
		diameter.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		diameter.setY(173);
		diameter.setId("preResult");
		Text wireDiameter = new Text(df.format(homeController.calculator.wireData.getAtmero()).replace(",", ".") + " (mm)");
		wireDiameter.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireDiameter.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireDiameter.getText().length()) / 2 ));
		wireDiameter.setY(173);
		wireDiameter.setId("preResult");
		
		df.applyPattern("0.0000");
		Text weight1 = new Text("Súly:");
		weight1.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		weight1.setY(191);
		weight1.setId("preResult");
		Text wireWeight1 = new Text(df.format(homeController.calculator.wireData.getSuly_kgPerMeter()).replace(",", ".") + " (kg/m)");
		wireWeight1.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireWeight1.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireWeight1.getText().length()) / 2 ));
		wireWeight1.setY(191);
		wireWeight1.setId("preResult");
		
		df.applyPattern("0.00000");
		Text weight2 = new Text("Súly:");
		weight2.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		weight2.setY(209);
		weight2.setId("preResult");
		Text wireWeight2 = new Text(df.format(homeController.calculator.wireData.getSuly_NPerMeter()).replace(",", ".") + " (N/m)");
		wireWeight2.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireWeight2.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireWeight2.getText().length()) / 2 ));
		wireWeight2.setY(209);
		wireWeight2.setId("preResult");
		
		df.applyPattern("0.000");
		Text potteher = new Text("Pótteher:");
		potteher.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		potteher.setY(227);
		potteher.setId("preResult");
		Text wirePotteher = new Text(df.format(homeController.calculator.wireData.getPotTeher()).replace(",", ".") + " (N/m)");
		wirePotteher.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wirePotteher.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wirePotteher.getText().length()) / 2 ));
		wirePotteher.setY(227);
		wirePotteher.setId("preResult");
		
		df.applyPattern("0");
		Text rugalmassagiModulus = new Text("Rugalmas. mod.:");
		rugalmassagiModulus.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		rugalmassagiModulus.setY(245);
		rugalmassagiModulus.setId("preResult");
		Text wireRugalmassagiModulus = 
				new Text(df.format(homeController.calculator.wireData.getRugalmassagiModulusz()).replace(",", ".") + " (N/mm2)");
		wireRugalmassagiModulus.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireRugalmassagiModulus.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireRugalmassagiModulus.getText().length()) / 2 ));
		wireRugalmassagiModulus.setY(245);
		wireRugalmassagiModulus.setId("preResult");
		
		df.applyPattern("0.00000000");
		Text hofokTenyezo = new Text("Hőfoktényező:");
		hofokTenyezo.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		hofokTenyezo.setY(263);
		hofokTenyezo.setId("preResult");
		Text wireHofokTenyezo = 
				new Text(df.format(homeController.calculator.wireData.getHofokTenyezo()).replace(",", ".") + " (1/°C)");
		wireHofokTenyezo.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireHofokTenyezo.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireHofokTenyezo.getText().length()) / 2 ));
		wireHofokTenyezo.setY(263);
		wireHofokTenyezo.setId("preResult");
		
		df.applyPattern("0.00");
		Text oszlopkozHossza = new Text("Oszlopköz hossza:");
		oszlopkozHossza.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		oszlopkozHossza.setY(296);
		oszlopkozHossza.setId("preResult");
		Text wireOszlopkozHossza = 
				new Text(df.format(homeController.calculator.oszlopkoz_hossza).replace(",", ".") + " (m)");
		wireOszlopkozHossza.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireOszlopkozHossza.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireOszlopkozHossza.getText().length()) / 2 ));
		wireOszlopkozHossza.setY(296);
		wireOszlopkozHossza.setId("preResult");
		
	
		Text magassag_kulonbseg = new Text("Δm:");
		magassag_kulonbseg.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		magassag_kulonbseg.setY(314);
		magassag_kulonbseg.setId("preResult");
		Text wireMagassagKulonbseg = 
				new Text(df.format(homeController.calculator.magassag_kulonbseg).replace(",", ".") + " (m)");
		wireMagassagKulonbseg.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireMagassagKulonbseg.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireMagassagKulonbseg.getText().length()) / 2 ));
		wireMagassagKulonbseg.setY(314);
		wireMagassagKulonbseg.setId("preResult");
		
		df.applyPattern("0.00");
		Text hanging = new Text("Belógás fél távon:");
		hanging.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		hanging.setY(332);
		hanging.setId("preResult");
		Text wire_hanging = 
				new Text(df.format(homeController.calculator.belogas).replace(",", ".") + " (m)");
		wire_hanging.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_hanging.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_hanging.getText().length()) / 2 ));
		wire_hanging.setY(332);
		wire_hanging.setId("preResult");
		
		Text felfuggesztesiKoz = new Text("Felfüggesztési köz:");
		felfuggesztesiKoz.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		felfuggesztesiKoz.setY(350);
		felfuggesztesiKoz.setId("preResult");
		Text wireFelfuggesztesiKoz = 
				new Text(df.format(homeController.calculator.felfuggesztesi_koz).replace(",", ".") + " (m)");
		wireFelfuggesztesiKoz.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireFelfuggesztesiKoz.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireFelfuggesztesiKoz.getText().length()) / 2 ));
		wireFelfuggesztesiKoz.setY(350);
		wireFelfuggesztesiKoz.setId("preResult");
		
		Text mertekadoOszlopkoz = new Text("Mértékadó oszl.köz:");
		mertekadoOszlopkoz.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		mertekadoOszlopkoz.setY(368);
		mertekadoOszlopkoz.setId("preResult");
		Text mertekadoOszlopkozHossza = 
				new Text(df.format(homeController.calculator.mertekado_oszlopkoz).replace(",", ".") + " (m)");
		mertekadoOszlopkozHossza.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		mertekadoOszlopkozHossza.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - mertekadoOszlopkozHossza.getText().length()) / 2 ));
		mertekadoOszlopkozHossza.setY(368);
		mertekadoOszlopkozHossza.setId("preResult");
		
		Text kritikusOszlopkoz = new Text("Kritikus oszlopköz:");
		kritikusOszlopkoz.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		kritikusOszlopkoz.setY(386);
		kritikusOszlopkoz.setId("preResult");
		Text kritikusOszlopkozHossza = 
				new Text(df.format(homeController.calculator.kritikus_oszlopkoz).replace(",", ".") + " (m)");
		kritikusOszlopkozHossza.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		kritikusOszlopkozHossza.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - kritikusOszlopkozHossza.getText().length()) / 2 ));
		kritikusOszlopkozHossza.setY(386);
		kritikusOszlopkozHossza.setId("preResult");
		
		df.applyPattern("0.00000000");
		Text kozepesFerdeseg = new Text("Közepes ferdeség:");
		kozepesFerdeseg.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		kozepesFerdeseg.setY(420);
		kozepesFerdeseg.setId("preResult");
		Text wireKozepesFerdeseg = 
				new Text(df.format(homeController.calculator.kozepes_ferdeseg).replace(",", "."));
		wireKozepesFerdeseg.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireKozepesFerdeseg.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireKozepesFerdeseg.getText().length()) / 2 ));
		wireKozepesFerdeseg.setY(420);
		wireKozepesFerdeseg.setId("preResult");
		
		df.applyPattern("0.00");
		Text szigma_b = new Text("σB:");
		szigma_b.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		szigma_b.setY(438);
		szigma_b.setId("preResult");
		Text wireSzigma_b = 
				new Text(df.format(homeController.calculator.szigma_b).replace(",", ".") + " (N/mm2)");
		wireSzigma_b.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireSzigma_b.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireSzigma_b.getText().length()) / 2 ));
		wireSzigma_b.setY(438);
		wireSzigma_b.setId("preResult");
		
		Text szigma_hz = new Text("σHz:");
		szigma_hz.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		szigma_hz.setY(456);
		szigma_hz.setId("preResult");
		Text wireSzigma_hz = 
				new Text(df.format(homeController.calculator.szigma_hz).replace(",", ".") + " (N/mm2)");
		wireSzigma_hz.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireSzigma_hz.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireSzigma_hz.getText().length()) / 2 ));
		wireSzigma_hz.setY(456);
		wireSzigma_hz.setId("preResult");
		
		Text szigma_kz = new Text("σkz:");
		szigma_kz.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		szigma_kz.setY(474);
		szigma_kz.setId("preResult");
		Text wireSzigma_kz = 
				new Text(df.format(homeController.calculator.szigma_kz).replace(",", ".") + " (N/mm2)");
		wireSzigma_kz.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireSzigma_kz.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireSzigma_kz.getText().length()) / 2 ));
		wireSzigma_kz.setY(474);
		wireSzigma_kz.setId("preResult");
		
		Text szigma_k = new Text("σk:");
		szigma_k.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		szigma_k.setY(492);
		szigma_k.setId("preResult");
		Text wireSzigma_k = 
				new Text(df.format(homeController.calculator.szigma_k).replace(",", ".") + " (N/mm2)");
		wireSzigma_k.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wireSzigma_k.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wireSzigma_k.getText().length()) / 2 ));
		wireSzigma_k.setY(492);
		wireSzigma_k.setId("preResult");
		
		df.applyPattern("0");
		Text t0 = new Text("t0:");
		t0.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		t0.setY(510);
		t0.setId("preResult");
		Text wire_t0 = 
				new Text(df.format(homeController.calculator.t0).replace(",", ".") + " (°C)");
		wire_t0.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_t0.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_t0.getText().length()) / 2 ));
		wire_t0.setY(510);
		wire_t0.setId("preResult");
		
		df.applyPattern("0.000");
		Text G = new Text("G:");
		G.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		G.setY(538);
		G.setId("preResult");
		Text wire_G = 
				new Text(df.format(homeController.calculator.G).replace(",", "."));
		wire_G.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_G.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_G.getText().length()) / 2 ));
		wire_G.setY(538);
		wire_G.setId("preResult");
		
		Text Gz = new Text("Gz:");
		Gz.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		Gz.setY(556);
		Gz.setId("preResult");
		Text wire_Gz = 
				new Text(df.format(homeController.calculator.G_z).replace(",", "."));
		wire_Gz.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_Gz.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_Gz.getText().length()) / 2 ));
		wire_Gz.setY(556);
		wire_Gz.setId("preResult");
		
		df.applyPattern("0.00");
		Text d = new Text("d:");
		d.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		d.setY(574);
		d.setId("preResult");
		Text wire_d = 
				new Text(df.format(homeController.calculator.d).replace(",", "."));
		wire_d.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_d.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_d.getText().length()) / 2 ));
		wire_d.setY(574);
		wire_d.setId("preResult");
		
		df.applyPattern("0.0000");
		Text upszilon = new Text("Ƴ:");
		upszilon.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		upszilon.setY(592);
		upszilon.setId("preResult");
		Text wire_upszilon = 
				new Text(df.format(homeController.calculator.upszilon).replace(",", ".") + " (N/(m x mm2))");
		wire_upszilon.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_upszilon.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_upszilon.getText().length()) / 2 ));
		wire_upszilon.setY(592);
		wire_upszilon.setId("preResult");
		
		Text upszilon_z = new Text("Ƴz:");
		upszilon_z.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		upszilon_z.setY(610);
		upszilon_z.setId("preResult");
		Text wire_upszilon_z = 
				new Text(df.format(homeController.calculator.upszilon_z).replace(",", ".") + " (N/(m x mm2))");
		wire_upszilon_z.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_upszilon_z.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_upszilon_z.getText().length()) / 2 ));
		wire_upszilon_z.setY(610);
		wire_upszilon_z.setId("preResult");
		
		df.applyPattern("0.000000");
		Text T = new Text("T:");
		T.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		T.setY(628);
		T.setId("preResult");
		Text wire_T = 
				new Text(df.format(homeController.calculator.T).replace(",", "."));
		wire_T.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_T.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_T.getText().length()) / 2 ));
		wire_T.setY(628);
		wire_T.setId("preResult");
		
		df.applyPattern("0.0000");
		Text b = new Text("b:");
		b.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		b.setY(646);
		b.setId("preResult");
		Text wire_b = 
				new Text(df.format(homeController.calculator.b).replace(",", "."));
		wire_b.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_b.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_b.getText().length()) / 2 ));
		wire_b.setY(646);
		wire_b.setId("preResult");
		
		df.applyPattern("0.00");
		Text A = new Text("A:");
		A.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		A.setY(664);
		A.setId("preResult");
		Text wire_A = 
				new Text(df.format(homeController.calculator.A).replace(",", "."));
		wire_A.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_A.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_A.getText().length()) / 2 ));
		wire_A.setY(664);
		wire_A.setId("preResult");
		
		df.applyPattern("0");
		Text B = new Text("B:");
		B.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		B.setY(682);
		B.setId("preResult");
		Text wire_B = 
				new Text(df.format(homeController.calculator.B).replace(",", "."));
		wire_B.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_B.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_A.getText().length()) / 2 ));
		wire_B.setY(682);
		wire_B.setId("preResult");
		
		Text delta = new Text("Δ:");
		delta.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		delta.setY(700);
		delta.setId("preResult");
		Text wire_delta = 
				new Text(df.format(homeController.calculator.delta).replace(",", "."));
		wire_delta.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_delta.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_delta.getText().length()) / 2 ));
		wire_delta.setY(700);
		wire_delta.setId("preResult");
		
		df.applyPattern("0.00");
		Text p = new Text("p:");
		p.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		p.setY(718);
		p.setId("preResult");
		Text wire_p = 
				new Text(df.format(homeController.calculator.p).replace(",", "."));
		wire_p.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_p.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_p.getText().length()) / 2 ));
		wire_p.setY(718);
		wire_p.setId("preResult");
		
		df.applyPattern("0.0000");
		Text at = new Text("at:");
		at.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		at.setY(736);
		at.setId("preResult");
		Text wire_at = 
				new Text(df.format(homeController.calculator.at).replace(",", "."));
		wire_at.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_at.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_at.getText().length()) / 2 ));
		wire_at.setY(736);
		wire_at.setId("preResult");
		
		Text ar = new Text("ar:");
		ar.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		ar.setY(754);
		ar.setId("preResult");
		Text wire_ar = 
				new Text(df.format(homeController.calculator.ar).replace(",", "."));
		wire_ar.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_ar.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_ar.getText().length()) / 2 ));
		wire_ar.setY(754);
		wire_ar.setId("preResult");
		
		Text xA = new Text("xA:");
		xA.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		xA.setY(772);
		xA.setId("preResult");
		Text wire_xA = 
				new Text(df.format(homeController.calculator.XA).replace(",", "."));
		wire_xA.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_xA.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_xA.getText().length()) / 2 ));
		wire_xA.setY(772);
		wire_xA.setId("preResult");
		
		Text xB = new Text("xB:");
		xB.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2).subtract(PAGE_Y * MILLIMETER));
		xB.setY(790);
		xB.setId("preResult");
		Text wire_xB = 
				new Text(df.format(homeController.calculator.XB).replace(",", "."));
		wire_xB.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 10));
		wire_xB.xProperty().bind(root.widthProperty().divide(2).add(A4_WIDTH / 2)
				.add(MILLIMETER * (63 - wire_xB.getText().length()) / 2 ));
		wire_xB.setY(790);
		wire_xB.setId("preResult");
	
		root.getChildren().addAll(backgroundCell, position, wirePosition, name, 
				wireTypeName, color, wireColorCell, length, wireLength, temperature,
				wireTemperature, keresztmetszet, wireKeresztmetszet, diameter, wireDiameter,
				weight1, wireWeight1, weight2, wireWeight2, potteher, wirePotteher,
				rugalmassagiModulus, wireRugalmassagiModulus, hofokTenyezo, wireHofokTenyezo,
				oszlopkozHossza, wireOszlopkozHossza, magassag_kulonbseg, wireMagassagKulonbseg, 
				felfuggesztesiKoz, wireFelfuggesztesiKoz, mertekadoOszlopkoz, mertekadoOszlopkozHossza,
				kritikusOszlopkoz, kritikusOszlopkozHossza, kozepesFerdeseg, wireKozepesFerdeseg, szigma_b,
				wireSzigma_b, szigma_hz, wireSzigma_hz, szigma_kz, wireSzigma_kz, szigma_k, wireSzigma_k, t0,
				wire_t0, G, wire_G, Gz, wire_Gz, d, wire_d, upszilon, wire_upszilon, upszilon_z, wire_upszilon_z,
				hanging, wire_hanging, T, wire_T, b, wire_b, A, wire_A, B, wire_B, delta, wire_delta, p, wire_p,
				at, wire_at, ar, wire_ar, xA, wire_xA, xB, wire_xB);
	}
	
	public void deletePreResultsData() {
		
		for (int i = root.getChildren().size() - 1; i >= 0; i--) {
			if( "preResult".equals(root.getChildren().get(i).getId()))
				root.getChildren().remove(i);
		}
		
	}
	
	public void deleteAllCalculatedWires() {
		for (int i = root.getChildren().size() - 1; i >= 0; i--) {
			
			if( root.getChildren().get(i) instanceof Circle)
				root.getChildren().remove(i);
		}
	}

	public void showDifferencesOfWires() {
		
		if( !isBackgroundCellExisted() ) {
		Rectangle backgroundCell = new Rectangle();
		backgroundCell.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).subtract(63 * MILLIMETER));
		backgroundCell.setY(PAGE_Y);
		backgroundCell.setWidth(60 * MILLIMETER);
		backgroundCell.heightProperty().bind(root.heightProperty().subtract(30));
		backgroundCell.setFill(Color.WHITE);
		backgroundCell.setId("backgroundCell");
		root.getChildren().add(backgroundCell);
	}	
		deleteWireDifferences(homeController.calculator.wireType + "_" + ElectricWireCalculator.wireID);
		
		if( homeController.calculator != null && homeController.calculator.wireType.startsWith("bal")) {
			showLeftWireDifferences();
		}
		else if( homeController.calculator != null && homeController.calculator.wireType.startsWith("közép")) {
			showMediumWireDifferences();
		}
		else if( homeController.calculator != null && homeController.calculator.wireType.startsWith("jobb")) {
			showRightWireDifferences();
		}
	}
	
	private void showLeftWireDifferences() {
		List<WireDifference> leftWireDiffs = 
				homeController.getElevationDifferenceOfWires();
		if( leftWireDiffs.isEmpty() ) {
			homeController.getWarningAlert("Bal sodrony eltérései nem számíthatók.", 
					"Bal sodronyra vonatkozó mérési adatok nem találhatók.");
			return;
		}
		Text leftWire = new Text("Bal sodrony eltérései:");
		leftWire.setFill(homeController.calculator.wireColor);
		leftWire.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
		leftWire.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.subtract(60 * MILLIMETER));
		leftWire.setY(50);
		leftWire.setId("leftDiffs_" + ElectricWireCalculator.wireID);
		root.getChildren().add(leftWire);
		Text szigmaValue = new Text("σ= " + homeController.calculator.szigma_b + " N/mm2");
		szigmaValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
		szigmaValue.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(MILLIMETER));
		szigmaValue.setY(50);
		szigmaValue.setId("leftDiffs_" + ElectricWireCalculator.wireID);
		root.getChildren().add(szigmaValue);
		int rowValue = 90;
		for (WireDifference leftWireDiff : leftWireDiffs) {
			Text diffID = new Text(leftWireDiff.getId());
			diffID.setFont(Font.font("ariel", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14));
			diffID.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.subtract(60 * MILLIMETER));
			diffID.setY(rowValue);
			diffID.setId("leftDiffs_" + ElectricWireCalculator.wireID);
			Text diffValue = new Text(df.format(leftWireDiff.getDifference()).replace(",", ".") + "m");
			diffValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
			diffValue.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.subtract(20 * MILLIMETER));
			diffValue.setY(rowValue);
			diffValue.setId("leftDiffs_" + ElectricWireCalculator.wireID);
			Text limitValue = new Text("|" + leftWireDiff.getDifferenceLimit() + "cm|");
			limitValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
			if( 0 >= leftWireDiff.getDifference() && Math.abs(leftWireDiff.getDifference() * 100.0) < leftWireDiff.getDifferenceLimit())
				limitValue.setFill(Color.GREEN);
			else
				limitValue.setFill(Color.RED);
			limitValue.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(MILLIMETER));
			limitValue.setY(rowValue);
			limitValue.setId("leftDiffs_" + ElectricWireCalculator.wireID);
			rowValue += 20;
			root.getChildren().addAll(diffID, diffValue, limitValue);
		}
	}
	
	private void showRightWireDifferences() {
		List<WireDifference> rightWireDiffs = 
				homeController.getElevationDifferenceOfWires();
		if( rightWireDiffs.isEmpty() ) {
			homeController.getWarningAlert("Jobb sodrony eltérései nem számíthatók.", 
					"Jobb sodronyra vonatkozó mérési adatok nem találhatók.");
			return;
		}
		Text rightWire = new Text("Jobb sodrony eltérései:");
		rightWire.setFill(homeController.calculator.wireColor);
		rightWire.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
		rightWire.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.subtract(60 * MILLIMETER));
		rightWire.setY(290);
		rightWire.setId("rightDiffs_" + ElectricWireCalculator.wireID);
		root.getChildren().add(rightWire);
		Text szigmaValue = new Text("σ= " + homeController.calculator.szigma_b + " N/mm2");
		szigmaValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
		szigmaValue.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(MILLIMETER));
		szigmaValue.setY(290);
		szigmaValue.setId("rightDiffs_" + ElectricWireCalculator.wireID);
		root.getChildren().add(szigmaValue);
		int rowValue = 330;
		for (WireDifference rightWireDiff : rightWireDiffs) {
			Text diffID = new Text(rightWireDiff.getId());
			diffID.setFont(Font.font("ariel", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14));
			diffID.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.subtract(60 * MILLIMETER));
			diffID.setY(rowValue);
			diffID.setId("rightDiffs_" + ElectricWireCalculator.wireID);
			Text diffValue = new Text(df.format(rightWireDiff.getDifference()).replace(",", ".") + "m");
			diffValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
			diffValue.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.subtract(20 * MILLIMETER));
			diffValue.setY(rowValue);
			diffValue.setId("rightDiffs_" + ElectricWireCalculator.wireID);
			Text limitValue = new Text("|" + rightWireDiff.getDifferenceLimit() + "cm|");
			limitValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
			if( 0 >= rightWireDiff.getDifference() && Math.abs(rightWireDiff.getDifference() * 100.0) < rightWireDiff.getDifferenceLimit())
				limitValue.setFill(Color.GREEN);
			else
				limitValue.setFill(Color.RED);
			limitValue.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(MILLIMETER));
			limitValue.setY(rowValue);
			limitValue.setId("rightDiffs_" + ElectricWireCalculator.wireID);
			rowValue += 20;
			root.getChildren().addAll(diffID, diffValue, limitValue);
		}
		
	}
	
	private void  showMediumWireDifferences() {
		List<WireDifference> mediumWireDiffs = 
				homeController.getElevationDifferenceOfWires();
		if( mediumWireDiffs.isEmpty() ) {
			homeController.getWarningAlert("Középső sodrony eltérései nem számíthatók.", 
					"Középső sodronyra vonatkozó mérési adatok nem találhatók.");
			return;
		}
		Text mediumWire = new Text("Középső sodrony eltérései:");
		mediumWire.setFill(homeController.calculator.wireColor);
		mediumWire.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
		mediumWire.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.subtract(60 * MILLIMETER));
		mediumWire.setY(530);
		mediumWire.setId("mediumDiffs_" + ElectricWireCalculator.wireID);
		root.getChildren().add(mediumWire);
		Text szigmaValue = new Text("σ= " + homeController.calculator.szigma_b + " N/mm2");
		szigmaValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
		szigmaValue.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(MILLIMETER));
		szigmaValue.setY(530);
		szigmaValue.setId("mediumDiffs_" + ElectricWireCalculator.wireID);
		root.getChildren().add(szigmaValue);
		int rowValue = 570;
		for (WireDifference mediumWireDiff : mediumWireDiffs) {
			Text diffID = new Text(mediumWireDiff.getId());
			diffID.setFont(Font.font("ariel", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14));
			diffID.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.subtract(60 * MILLIMETER));
			diffID.setY(rowValue);
			diffID.setId("mediumDiffs_" + ElectricWireCalculator.wireID);
			Text diffValue = new Text(df.format(mediumWireDiff.getDifference()).replace(",", ".") + "m");
			diffValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
			diffValue.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.subtract(20 * MILLIMETER));
			diffValue.setY(rowValue);
			diffValue.setId("mediumDiffs_" + ElectricWireCalculator.wireID);
			Text limitValue = new Text("|" + mediumWireDiff.getDifferenceLimit() + "cm|");
			limitValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 14));
			if( 0 >= mediumWireDiff.getDifference() && Math.abs(mediumWireDiff.getDifference() * 100.0) < mediumWireDiff.getDifferenceLimit())
				limitValue.setFill(Color.GREEN);
			else
				limitValue.setFill(Color.RED);
			limitValue.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(MILLIMETER));
			limitValue.setY(rowValue);
			limitValue.setId("mediumDiffs_" + ElectricWireCalculator.wireID);
			rowValue += 20;
			root.getChildren().addAll(diffID, diffValue, limitValue);
		}
	}
	
	private boolean isBackgroundCellExisted() {
		for (Node node : root.getChildren()) {
			if("backgroundCell".equals(node.getId()))
				return true;
		}
		return false;
	}
	
	private void deleteWireDifferences(String wireID) {
		
		String[] idComponents = wireID.split("_");
		String id = "leftDiffs";
		
		if( idComponents[0].startsWith("közép") )
			id = "mediumDiffs";
		else if( idComponents[0].startsWith("jobb") )
			id = "rightDiffs";
		
		for (int i = root.getChildren().size() - 1; i >= 0; i--) {
			if( root.getChildren().get(i).getId() != null && 
					( root.getChildren().get(i).getId().startsWith(id) || 
					(id + "_" + idComponents[1]).equals(root.getChildren().get(i).getId() )) ) {
				root.getChildren().remove(i);
			}
		}
	}
	
	private boolean isAnyVisibleWire() {
		
		for (int i = root.getChildren().size() - 1; i >= 0; i--) {
			if(root.getChildren().get(i).getId() != null && 
					(root.getChildren().get(i).getId().startsWith("leftDiffs") ||
							root.getChildren().get(i).getId().startsWith("mediumDiffs") ||
								root.getChildren().get(i).getId().startsWith("rightDiffs") )) {
				return true;
			}
		}
		return false;
	}
	
	private void deleteBackgroundCell() {
		for (int i = root.getChildren().size() - 1; i >= 0; i--) {
			if("backgroundCell".equals(root.getChildren().get(i).getId())) {
				root.getChildren().remove(i);
			}
		}
	}
	
//	public void drawLeftWireCurve(List<WirePoint> pointsOfWire) {
//	
//if( pointsOfWire.size() == 2) {
//drawWireByTwoPoints(pointsOfWire, "-2");
//}
//else {
//drawWire(pointsOfWire, "-2");
//}
//}
//
//public void deleteLeftWire() {
//
//for(int i = root.getChildren().size() - 1; i >= 0; i--) {
//if( "-2".equals(root.getChildren().get(i).getId()) ) {
//	root.getChildren().remove(i);
//}
//}
//}
//
//private void drawWireByTwoPoints(List<WirePoint> pointsOfWire, String id) {
//Line wire = new Line();
//if( "-2".equals(id) )
//wire.setStroke(Color.MAGENTA);
//else
//wire.setStroke(Color.GREEN);	
//wire.setStrokeWidth(1.5);
//wire.getStrokeDashArray().addAll(1d, 4d);
//wire.setId(id);
//wire.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
//	.add(START_X).add(HOR_SHIFT * MILLIMETER)
//	.add(getHorizontalScaledDownLengthValue(pointsOfWire.get(0).getDistanceOfWirePoint()) * MILLIMETER));
//wire.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(pointsOfWire.get(0).getElevationOfWirePoint()) * MILLIMETER);
//wire.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
//	.add(START_X).add(HOR_SHIFT * MILLIMETER)
//	.add(getHorizontalScaledDownLengthValue(pointsOfWire.get(1).getDistanceOfWirePoint())* MILLIMETER));
//wire.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(pointsOfWire.get(1).getElevationOfWirePoint()) * MILLIMETER);
//root.getChildren().add(wire);
//}
//
//private void drawWire(List<WirePoint> pointsOfWire, String id) { 
//try {
//	double verticalShift =  PAGE_Y + START_Y;
//	double scale = horizontalScale / 1000d;
//	for(int i = 0; i < pointsOfWire.size() - 1; i++) {
//		
//	HalfParabola parabola = new HalfParabola(pointsOfWire.get(i), pointsOfWire.get(i + 1));
//	
//	if( pointsOfWire.get(i).getElevationOfWirePoint() - pointsOfWire.get(i + 1).getElevationOfWirePoint() > 0) {
//		for(double y = 0; y <= Math.abs(parabola.getOrigo().getDistanceOfWirePoint()); y += scale) {
//		Circle dot = new Circle();
//		dot.centerXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER)
//		.add(getHorizontalScaledDownLengthValue(pointsOfWire.get(i + 1).getDistanceOfWirePoint()) * MILLIMETER)
//		.subtract(getHorizontalScaledDownLengthValue(y) * MILLIMETER));
//		dot.setCenterY(verticalShift - 
//		getVerticalScaledDownHeightValue(pointsOfWire.get(i + 1).getElevationOfWirePoint()) * MILLIMETER -
//		getVerticalScaledDownHeightValue(parabola.getElevationOfHalfParabolaPoint(y)) * MILLIMETER);
//		dot.setRadius(1);
//		dot.setId(id);
//		if( "-2".equals(id) )
//		dot.setStroke(Color.MAGENTA);
//		else
//		dot.setStroke(Color.GREEN);
//		root.getChildren().add(dot);
//		
//		}
//	}
//	else {	
//		parabola = new HalfParabola(pointsOfWire.get(i + 1), pointsOfWire.get(i));
//		for(double y = 0; y <= Math.abs(parabola.getOrigo().getDistanceOfWirePoint()); y += scale) {
//			Circle dot = new Circle();
//			dot.centerXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER)
//		.add(getHorizontalScaledDownLengthValue(pointsOfWire.get(i).getDistanceOfWirePoint()) * MILLIMETER)
//		.add(getHorizontalScaledDownLengthValue(y) * MILLIMETER));
//		dot.setCenterY(verticalShift - 
//		getVerticalScaledDownHeightValue(pointsOfWire.get(i).getElevationOfWirePoint()) * MILLIMETER -
//		getVerticalScaledDownHeightValue(parabola.getElevationOfHalfParabolaPoint(y)) * MILLIMETER);
//		dot.setRadius(1);
//		dot.setId(id);
//		if( "-2".equals(id) )
//		dot.setStroke(Color.MAGENTA);
//		else
//		dot.setStroke(Color.GREEN);	
//		root.getChildren().add(dot);
//		}
//	}
//}
//} 
//catch (InvalidAttributesException e) {
//	HomeController.getWarningAlert("Hibás sodrony adatok", "A megadott bemeneti adatokból sodrony nem rajzolható.");
//}
//}
//
//public void drawRightWireCurve(List<WirePoint> pointsOfWire) {
//
//if( pointsOfWire.size() == 2) {
//drawWireByTwoPoints(pointsOfWire, "-3");
//}
//else {
//drawWire(pointsOfWire, "-3");
//}
//}
//
//public void deleteRightWire() {
//
//for(int i = root.getChildren().size() - 1; i >= 0; i--) {
//	if( "-3".equals(root.getChildren().get(i).getId()) ) {
//		root.getChildren().remove(i);
//	}
//}
//} 
//
//
//public void writeDifferenceOfWireCurve(List<WirePoint> pointsOfWire, int minimumPlace, String id)  {
//try {
//HalfParabola leftCurve = new HalfParabola(pointsOfWire.get(0), pointsOfWire.get(minimumPlace));
//HalfParabola rightCurve = new HalfParabola(pointsOfWire.get(pointsOfWire.size() - 1), pointsOfWire.get(minimumPlace));
//DecimalFormat df = new DecimalFormat("+0.00;-0.00");
//int vShift = "-2".equals(id) ? 12 : 5;
//for(int i = 1; i < pointsOfWire.size() - 1; i++) {
//	if( i != minimumPlace && pointsOfWire.get(i).getDistanceOfWirePoint() < 
//			(pointsOfWire.get(0).getDistanceOfWirePoint() - pointsOfWire.get(pointsOfWire.size() - 1).getDistanceOfWirePoint()) / 2) {
//Text diffText =  new Text(df.format(leftCurve
//	.getElevationOfHalfParabolaPoint(
//	pointsOfWire.get(i).getDistanceOfWirePoint() 
//	- pointsOfWire.get(minimumPlace).getDistanceOfWirePoint())
//	- pointsOfWire.get(i).getElevationOfWirePoint()
//	+ pointsOfWire.get(minimumPlace).getElevationOfWirePoint()).replace(',', '.'));
//	diffText.setId(id);
//	if( "-2".equals(id))
//		diffText.setStroke(Color.MAGENTA);
//	else
//	diffText.setStroke(Color.GREEN);
//	diffText.setRotationAxis(Rotate.Z_AXIS);
//	diffText.setRotate(-90);
//	diffText.xProperty()
//	.bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
//		.add(START_X).add((getHorizontalScaledDownLengthValue(pointsOfWire.get(i).getDistanceOfWirePoint()) + 9) * MILLIMETER));
//	diffText.setY(PAGE_Y + START_Y 
//			- (getVerticalScaledDownHeightValue(pointsOfWire.get(i).getElevationOfWirePoint()) + vShift) * MILLIMETER);
//	root.getChildren().add(diffText);
//	}
//	else if( i != minimumPlace && pointsOfWire.get(i).getDistanceOfWirePoint() >= 
//			(pointsOfWire.get(0).getDistanceOfWirePoint() - pointsOfWire.get(pointsOfWire.size() - 1).getDistanceOfWirePoint()) / 2) {
//	Text diffText = new Text(df.format(rightCurve
//		.getElevationOfHalfParabolaPoint(
//		pointsOfWire.get(i).getDistanceOfWirePoint() 
//		- pointsOfWire.get(minimumPlace).getDistanceOfWirePoint()) 
//		- pointsOfWire.get(i).getElevationOfWirePoint()
//		+ pointsOfWire.get(minimumPlace).getElevationOfWirePoint()).replace(',', '.'));
//	diffText.setId(id);
//	if( "-2".equals(id))
//	diffText.setStroke(Color.MAGENTA);
//	else
//	diffText.setStroke(Color.GREEN);
//	diffText.setRotationAxis(Rotate.Z_AXIS);
//	diffText.setRotate(-90);
//	diffText.xProperty()
//	.bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
//			.add(START_X).add((getHorizontalScaledDownLengthValue(pointsOfWire.get(i).getDistanceOfWirePoint()) + 9) * MILLIMETER));
//	diffText.setY(PAGE_Y + START_Y 
//			- (getVerticalScaledDownHeightValue(pointsOfWire.get(i).getElevationOfWirePoint()) + vShift) * MILLIMETER);
//	root.getChildren().add(diffText);
//	}
//	
//}
//
//} catch (InvalidAttributesException e) {
//HomeController.getWarningAlert("Hibás sodrony adatok", "A megadott bemeneti adatokból sodrony nem rajzolható.");
//}
//}
}
		

