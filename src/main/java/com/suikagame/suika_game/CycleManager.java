package com.suikagame.suika_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.ArrayList;
import java.util.List;

public class CycleManager {
	
	public static CycleManager instance = new CycleManager();
	
	private final List<Fruit> cycleFruits = new ArrayList<Fruit>();
	
	public CycleManager() {
		double radiusSum = 0.0;
		for (int i = 0; i < 11; i++) {
			radiusSum += Constants.FRUIT_SIZES.get(i);
		}
		double deltaTheta = Math.PI * 2 / radiusSum;
		double theta = 0.0;
		for (int i = 0; i < 11; i++) {
			cycleFruits.add(new Fruit(Constants.CYCLE_CENTER_X + Math.cos(theta) * 150,
					Constants.CYCLE_CENTER_Y + Math.sin(theta) * 150, i, -1));
			theta += Constants.FRUIT_SIZES.get(i) * deltaTheta;
		}
	}
	
	public static CycleManager getInstance() {
		return instance;
	}
	
	public void drawCycle(GraphicsContext gc) {
		gc.setStroke(new Color(0.0, 1.0, 1.0, 0.4));
		gc.setLineWidth(25.0);
		gc.strokeArc(Constants.CYCLE_CENTER_X - 150.0, Constants.CYCLE_CENTER_Y - 150.0,
				300.0, 300.0, 45.0, 320.0, ArcType.OPEN);
		gc.setStroke(Color.BLACK);
		
		gc.setFill(new Color(0.0, 1.0, 1.0, 0.4));
		
		double[] xPoints = {495.0, 434.7, 492.7};
		double[] yPoints = {578.7, 640.0, 642.0};
		gc.fillPolygon(xPoints, yPoints, 3);
		for (final Fruit fruit : cycleFruits) {
			fruit.draw();
		}
	}
}
