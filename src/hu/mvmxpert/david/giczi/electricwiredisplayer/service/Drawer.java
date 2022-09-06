package hu.mvmxpert.david.giczi.electricwiredisplayer.service;

import java.awt.Toolkit;

import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class Drawer {
	
	private final double MILLIMETER = 1000 / 224.0;
	private final double MONITOR_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final double MONITOR_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private final double PAGE_WIDTH =  211 * MILLIMETER;
	private final double PAGE_HEIGHT =  MONITOR_HEIGHT - 50;
	private final double PAGE_X = (MONITOR_WIDTH - PAGE_WIDTH) / 2;
	private final double PAGE_Y = 25;
	private final double MARGIN = 156 * MILLIMETER;
	private final double HOR_SHIFT = 12;
	private final double VER_SHIFT = 5;
	private final double START_X = 45 * MILLIMETER;
	private final double START_Y = 550.0;
	private double lengthOfHorizontalAxis;
	private int horizontalScale;
	private int verticalScale;
	private int elevationStartValue;
	private BorderPane root;
	
	public void setLengthOfHorizontalAxis(double lengthOfHorizontalAxis) {
		this.lengthOfHorizontalAxis = lengthOfHorizontalAxis;
	}

	public void setHorizontalScale(int horizontalScale) {
		this.horizontalScale = horizontalScale;
	}

	public void setVerticalScale(int verticalScale) {
		this.verticalScale = verticalScale;
	}
	
	public void setElevationStartValue(int elevationStartValue) {
		this.elevationStartValue = elevationStartValue;
	}

	public double getLengthOfHorizontalAxis() {
		return lengthOfHorizontalAxis;
	}
	
	public void setRoot(BorderPane root) {
		this.root = root;
	}

	public void drawPage() {
		Rectangle page = new Rectangle(
				PAGE_X, 
				PAGE_Y, 
				PAGE_WIDTH, 
				PAGE_HEIGHT);
		Line leftMargin = new Line(
				PAGE_X + (PAGE_WIDTH - MARGIN) / 2, 
				PAGE_Y, 
				PAGE_X +(PAGE_WIDTH - MARGIN) / 2, 
				PAGE_HEIGHT + 20);
		Line rightMargin = new Line(
				PAGE_X + (PAGE_WIDTH - MARGIN) / 2 + MARGIN,
				PAGE_Y, 
				PAGE_X +(PAGE_WIDTH - MARGIN) / 2 + MARGIN,
				PAGE_HEIGHT + 20);
		leftMargin.setStroke(Color.LIGHTGRAY);
		leftMargin.getStrokeDashArray().addAll(4d);
		rightMargin.setStroke(Color.LIGHTGRAY);
		rightMargin.getStrokeDashArray().addAll(4d);
		page.setFill(Color.WHITE);
		root.getChildren().addAll(page, leftMargin, rightMargin);
	}
	
	public void drawVerticalAxis() {
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
	
	public void drawHorizontalAxis() {
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
	
	public void writeElevationValueForVerticalAxis() {
		double startY = START_Y;
		int startValue = elevationStartValue;
		for(int i = 0; i <= 10; i++) {
		setText(String.valueOf(startValue) + "m", PAGE_X + START_X - 70, PAGE_Y + startY, 18, 0);
		startY -= 10 * MILLIMETER;
		startValue += verticalScale;
		}
		setText("Oszlopszám:", PAGE_X + 30 * MILLIMETER, PAGE_Y + START_Y + 30 * MILLIMETER, 18, 0);
	}
	
	public void writeDistanceValueForHorizontalAxis() {
		setText("0", PAGE_X + START_X + (HOR_SHIFT - 1) * MILLIMETER, PAGE_Y + START_Y + 50, 18, 0);
		setText(String.valueOf(lengthOfHorizontalAxis) + "m", 
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER + (HOR_SHIFT - 8) * MILLIMETER, 
				PAGE_Y + START_Y + 50, 18, 0);
	}
	
	public void drawPillar(int id, double groundElevation, double topElevation, double distance, boolean isHooded) {
		Line pillar = new Line(
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(groundElevation - elevationStartValue) * MILLIMETER,
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
		pillar.setStroke(Color.BLUE);
		pillar.setStrokeWidth(3);
		pillar.setCursor(Cursor.HAND);
		pillar.setOnMouseClicked( null );
		root.getChildren().add(pillar);
		writePillarId(id, 
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + (HOR_SHIFT - 1) * MILLIMETER);
		if( isHooded ) {
			Line hood = new Line(
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + (HOR_SHIFT - 1) * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER,
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + (HOR_SHIFT + 1) * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			hood.setCursor(Cursor.HAND);
			hood.setOnMouseClicked( h -> { Line line = (Line) h.getSource();
										if(line.getStroke().toString().equals("0x0000ffff"))
											line.setStroke(Color.WHITE);
										else
											line.setStroke(Color.BLUE);
										});
			hood.setStroke(Color.BLUE);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
		setText("bal ak.: Bf. " + topElevation + "m", pillar.getEndX(), pillar.getEndY() - MILLIMETER, 18, -90);
		setText("bal ak.: Bf. " + groundElevation + "m", pillar.getStartX() - MILLIMETER, pillar.getStartY() + 15 * MILLIMETER, 18, -90);
	}
	
	private void writePillarId(int id, double x) {
		setText(id + ".", x, PAGE_Y + START_Y + 50 * MILLIMETER, 18, 0);
	}
	
	public void drawElectricWire(String text, double groundElevation, double topElevation, double distance, boolean isHooded) {
		Line wire = new Line(
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(groundElevation - elevationStartValue) * MILLIMETER,
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + HOR_SHIFT * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
		wire.setStroke(Color.RED);
		wire.setStrokeWidth(3);
		wire.setCursor(Cursor.HAND);
		wire.setOnMouseClicked( null );
		root.getChildren().add(wire);
		if( isHooded ) {
			Line hood = new Line(
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + (HOR_SHIFT - 1) * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER,
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + (HOR_SHIFT + 1) * MILLIMETER,
				PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			hood.setStroke(Color.RED);
			hood.setStrokeWidth(3);
			hood.setCursor(Cursor.HAND);
			hood.setOnMouseClicked( h -> { Line line = (Line) h.getSource();
										  if(line.getStroke().toString().equals("0xff0000ff"))
										  line.setStroke(Color.WHITE);
										  else
										  line.setStroke(Color.RED);
										});
			root.getChildren().add(hood);
		}
		setText(text +  " Bf. " + topElevation + "m", wire.getEndX() - MILLIMETER, wire.getEndY() - MILLIMETER, 18, -90);
		setText(distance + "m", 
				PAGE_X + START_X + getHorizontalScaledDownLengthValue(distance) * MILLIMETER + (HOR_SHIFT - 8) * MILLIMETER, 
				PAGE_Y + START_Y + 50, 18, 0);
		setText(text + " Bf. " + groundElevation + "m", wire.getStartX() - MILLIMETER, wire.getStartY() + 15 * MILLIMETER, 18, -90);
	}
	
	public void writeText(String text, double startX, double startY, int size, double rotate) {
		setText(text, PAGE_X + START_X + (HOR_SHIFT + startX) * MILLIMETER, 
				PAGE_Y + START_Y + startY * MILLIMETER, size, rotate);
	}
	
	private void setText(String text, double startX, double startY, int size, double rotate) {
		Text txt = new Text(text);
		txt.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, size));
		txt.setX(startX);
		txt.setY(startY);
		txt.getTransforms().add(new Rotate(rotate, startX, startY));
		txt.setCursor(Cursor.HAND);
		txt.setOnMouseClicked( null );
		root.getChildren().add(txt);
	}

	private double getHorizontalScaledDownLengthValue(double length) {
		return horizontalScale == 1000 ? length : 1000.0  * length / horizontalScale;
	}
	
	private double getVerticalScaledDownHeightValue(double height) {
		return verticalScale == 10 ? height : 10.0 * height / verticalScale;
	}
	
//	private double getStartXValue() {
//		return  PAGE_WIDTH / 2 - (getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER) / 2;
//	}
	
}