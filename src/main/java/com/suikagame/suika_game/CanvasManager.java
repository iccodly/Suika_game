package com.suikagame.suika_game;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CanvasManager {
	
	private static CanvasManager instance = new CanvasManager();
	
	private Canvas canvas;
	private GraphicsContext gc;
	
	private CanvasManager() {
		canvas = new Canvas();
		gc = canvas.getGraphicsContext2D();
	}
	
	public static CanvasManager getInstance() {
		return instance;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public GraphicsContext getGc() {
		return gc;
	}
	
	public void clearCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	public void setProperty(Scene scene) {
		this.canvas.widthProperty().bind(scene.widthProperty());
		this.canvas.heightProperty().bind(scene.heightProperty());
	}
	
	public void drawScore(int score, int spaceCount) {
		gc.setFill(Color.BLACK);
		gc.setFont(new javafx.scene.text.Font(52));
		if (spaceCount == 0) {
			gc.fillText("Score: " + score, Constants.SCORE_LEFT, Constants.SCORE_UPPER);
		} else {
			gc.fillText("Score: " + score + "(" + spaceCount + ")", Constants.SCORE_LEFT, Constants.SCORE_UPPER);
		}
		
		gc.fillText("NEXT", Constants.FRUIT_NEXT_X - Constants.BOX_NEXT_SIZE / 2.0 + 40, Constants.FONT_NEXT_Y);
	}
	
	public void drawCursor(double x, double y) {
		gc.setFill(Color.CYAN);
		
		double[] xPoints = {x, x + 10.0, x + 40.0};
		double[] yPoints = {y, y + 40.0, y + 10.0};
		gc.fillPolygon(xPoints, yPoints, 3);
	}
}
