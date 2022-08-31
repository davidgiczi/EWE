package hu.mvmxpert.david.giczi.electricwiredisplayer.service;

import java.awt.Toolkit;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Drawer {

	public static final double MILLIMETER = 1000 / 224.0;
	public static final double MONITOR_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final double MONITOR_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final double PAGE_WIDTH =  211 * MILLIMETER;
	public static final double PAGE_HEIGHT =  MONITOR_HEIGHT - 40;
	public static final double PAGE_X = (MONITOR_WIDTH - PAGE_WIDTH) / 2;
	public static final double PAGE_Y = 10;
	public static final double MARGIN = 156 * MILLIMETER;
	public static final double HOR_SHIFT = 12;
	public static final double VER_SHIFT = 5;
	private static double START_X;
	private static double START_Y = 650.0;
	private double lengthOfHorizontalAxis;
	private int horizontalScale;
	private int verticalScale;
	private int elevationStartValue;
	
	public Drawer(double lengthOfHorizontalAxis, int horizontalScale) {
		this.lengthOfHorizontalAxis = lengthOfHorizontalAxis;
		this.horizontalScale = horizontalScale;
		START_X = getStartXValue();
		}
	
	public void setVerticalScale(int verticalScale) {
		this.verticalScale = verticalScale;
	}
	
	public void setElevationStartValue(int elevationStartValue) {
		this.elevationStartValue = elevationStartValue;
	}

	public void drawPage(Pane root) {
		Rectangle page = new Rectangle(
				PAGE_X, 
				PAGE_Y, 
				PAGE_WIDTH, 
				PAGE_HEIGHT);
		Line leftMargin = new Line(
				PAGE_X + (PAGE_WIDTH - MARGIN) / 2, 
				PAGE_Y, 
				PAGE_X +(PAGE_WIDTH - MARGIN) / 2, 
				PAGE_HEIGHT + 10);
		Line rightMargin = new Line(
				PAGE_X + (PAGE_WIDTH - MARGIN) / 2 + MARGIN,
				PAGE_Y, 
				PAGE_X +(PAGE_WIDTH - MARGIN) / 2 + MARGIN,
				PAGE_HEIGHT + 10);
		leftMargin.setStroke(Color.LIGHTGRAY);
		leftMargin.getStrokeDashArray().addAll(4d);
		rightMargin.setStroke(Color.LIGHTGRAY);
		rightMargin.getStrokeDashArray().addAll(4d);
		page.setFill(Color.WHITE);
		root.getChildren().addAll(page, leftMargin, rightMargin);
		writeText(root, "Oszlopok:", PAGE_X, PAGE_Y + 50);
	}
	
	public void drawVerticalAxis(Pane root) {
		double startY = START_Y;
		Line leftBorder = new Line(
				PAGE_X + START_X,
				PAGE_Y + START_Y, 
				PAGE_X + START_X, 
				PAGE_Y + START_Y - 100 * MILLIMETER);
		Line rightBorder = new Line(
				PAGE_X + START_X + 2 * MILLIMETER, 
				PAGE_Y + START_Y,
				PAGE_X + START_X + 2 * MILLIMETER,
				PAGE_Y + START_Y - 100 * MILLIMETER);
		Line topBorder = new Line(
				PAGE_X + START_X,
				PAGE_Y - 100 * MILLIMETER + START_Y, 
				PAGE_X + START_X + 2 * MILLIMETER,
				PAGE_Y + - 100 * MILLIMETER + START_Y);
		root.getChildren().addAll(leftBorder, rightBorder, topBorder);
		for(int i = 0; i < 10; i++) {
		Rectangle axisComponent = new Rectangle(
				PAGE_X + START_X,
				PAGE_Y + startY,
				2 * MILLIMETER,
				10 * MILLIMETER);
		if( i % 2 == 0) {
			axisComponent.setFill(Color.WHITE);
		}
		startY -= 10 * MILLIMETER;
		root.getChildren().add(axisComponent);
		}
	}
	
	public void drawHorizontalAxis(Pane root) {
		Line topBorder = new Line(
				PAGE_X + START_X + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y + VER_SHIFT * MILLIMETER, 
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER + HOR_SHIFT * MILLIMETER, 
				PAGE_Y + START_Y + VER_SHIFT * MILLIMETER);
		Line rightBorder = new Line(
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y + (VER_SHIFT  + 1 ) * MILLIMETER, 
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y + VER_SHIFT * MILLIMETER);
		Line downBorder = new Line(
				PAGE_X + START_X + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y + (VER_SHIFT  + 1 ) * MILLIMETER, 
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y + (VER_SHIFT + 1) * MILLIMETER);
		Line leftBorder = new Line(
				PAGE_X + START_X + HOR_SHIFT * MILLIMETER, 
				PAGE_Y + START_Y + VER_SHIFT * MILLIMETER, 
				PAGE_X + START_X + HOR_SHIFT * MILLIMETER, 
				PAGE_Y + START_Y + (VER_SHIFT  + 1 ) * MILLIMETER);
		root.getChildren().addAll(topBorder, rightBorder, downBorder, leftBorder);
	}
	
	public void writeElevationValueForVerticalAxis(Pane root) {
		double startY = START_Y;
		int startValue = elevationStartValue;
		for(int i = 0; i <= 10; i++) {
		Text elevationValue = new Text();
		elevationValue.setX(PAGE_X + START_X - 70);
		elevationValue.setText(String.valueOf(startValue) + "m");
		elevationValue.setY(PAGE_Y + startY);
		elevationValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 20));
		startY -= 10 * MILLIMETER;
		startValue += verticalScale;
		root.getChildren().add(elevationValue);
		}
	}
	
	public void writeDistanceValueForHorizontalAxis(Pane root) {
		Text zeroValue = new Text();
		zeroValue.setText("0");
		zeroValue.setX(PAGE_X + START_X + (HOR_SHIFT - 1) * MILLIMETER);
		zeroValue.setY(PAGE_Y + START_Y + 50);
		zeroValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 20));
		root.getChildren().add(zeroValue);
		Text lengthValue = new Text();
		lengthValue.setText(String.valueOf(lengthOfHorizontalAxis) + "m");
		lengthValue.setX(PAGE_X + START_X + getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER + (HOR_SHIFT - 8) * MILLIMETER);
		lengthValue.setY(PAGE_Y + START_Y + 50);
		lengthValue.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 20)); 
		root.getChildren().add(lengthValue);
	}
	
	public void drawPillar(Pane root, String id, double groundElevation, double topElevation, double distance, boolean isHooded) {
		Line pillar = new Line(
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(groundElevation - elevationStartValue) * MILLIMETER,
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
		pillar.setStroke(Color.BLUE);
		pillar.setStrokeWidth(3);
		root.getChildren().add(pillar);
		writePillarId(root, id, 
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + (HOR_SHIFT - 1) * MILLIMETER);
		if( isHooded ) {
			Line hood = new Line(
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + (HOR_SHIFT - 1) * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER,
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + (HOR_SHIFT + 1) * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			hood.setStroke(Color.BLUE);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
	}
	
	private void writePillarId(Pane root, String id, double x) {
		Text pillarId = new Text(id);
		pillarId.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 20));
		pillarId.setX(x);
		pillarId.setY(PAGE_Y + 30 * MILLIMETER);
		root.getChildren().add(pillarId);
	}
	
	public void drawElectricWire(Pane root, String id, double groundElev, double topElev, double distance, boolean isHooded) {
	}
	
	private void writeText(Pane root, String text, double startX, double startY) {
		Text txt = new Text(text);
		txt.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 20));
		txt.setX(startX);
		txt.setY(startY);
		root.getChildren().add(txt);
	}
	
	
	private double getHorizontalScaledDownLengthValue(double length) {
		return horizontalScale == 1000 ? length : 1000.0  * length / horizontalScale;
	}
	
	private double getVerticalScaledDownHeightValue(double height) {
		return verticalScale == 10 ? height : 10.0 * height / verticalScale;
	}
	
	private double getStartXValue() {
		return  PAGE_WIDTH / 2 - (getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER) / 2;
	}
	
}
