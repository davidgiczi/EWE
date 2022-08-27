package hu.mvmxpert.david.giczi.electricwiredisplayer.service;

import java.awt.Toolkit;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Drawing {

public static final double CM = 1000 / 22.4;
public static final double MONITOR_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
public static final double MONITOR_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
public static final double PAGE_WIDTH =  21.1 * CM;
public static final double PAGE_HEIGHT =  MONITOR_HEIGHT - 50;
public static final double PAGE_X = (MONITOR_WIDTH - PAGE_WIDTH) / 2;
public static final double PAGE_Y = 10;
	
	public void drawPage(Pane root) {
		Rectangle page = new Rectangle(PAGE_X, PAGE_Y, PAGE_WIDTH, PAGE_HEIGHT);
		page.setFill(Color.WHITE);
		root.getChildren().add(page);
	}
	
	public void drawVerticalAxis(Pane root, double startX, double startY) {
		Line leftBorder = new Line(PAGE_X + startX, PAGE_Y + startY, PAGE_X + startX, PAGE_Y + startY + 10 * CM);
		Line rightBorder = new Line(PAGE_X + startX + 0.2 * CM, PAGE_Y + startY, PAGE_X + startX + 0.2 * CM, PAGE_Y + startY + 10 * CM);
		Line topBorder = new Line(PAGE_X + startX, PAGE_Y + startY, PAGE_X + startX + 0.2 * CM, PAGE_Y + startY);
		root.getChildren().addAll(leftBorder, rightBorder, topBorder);
		for(int i = 0; i < 10; i++) {
		Rectangle axisComponent = new Rectangle(PAGE_X + startX, PAGE_Y + startY, 0.2 * CM, 1 * CM);
		if( i % 2 == 0) {
			axisComponent.setFill(Color.WHITE);
		}
		root.getChildren().add(axisComponent);
		startY += 1 * CM;
		}
	}
	
	public void drawHorizontalAxis(Pane root, double startX, double startY, double length) {
		Line topBorder = new Line(PAGE_X + startX, PAGE_Y + startY, PAGE_X + startX + length * CM, PAGE_Y + startY);
		Line rightBorder = new Line(PAGE_X + startX + length * CM, PAGE_Y + startY + 0.1 * CM, PAGE_X + startX + length * CM, PAGE_Y + startY);
		Line downBorder = new Line(PAGE_X + startX + length * CM, PAGE_Y + startY + 0.1 * CM, PAGE_X + startX, PAGE_Y + startY + 0.1 * CM);
		Line leftBorder = new Line(PAGE_X + startX, PAGE_Y + startY, PAGE_X + startX, PAGE_Y + startY + 0.1 * CM);
		root.getChildren().addAll(topBorder, rightBorder, downBorder, leftBorder);
	}
}
