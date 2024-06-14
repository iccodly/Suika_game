package com.suikagame.suika_game;

import javafx.scene.canvas.GraphicsContext;

public class BoxManager {
	
	private static BoxManager boxManager = new BoxManager();
	
	public static BoxManager getInstance() {
		return boxManager;
	}
	
	private BoxManager() {
	}
	
	public void drawBox(GraphicsContext gc) {
		gc.setLineWidth(2.0);
		gc.strokeLine(Constants.BOX_LEFT, Constants.BOX_UPPER, Constants.BOX_LEFT, Constants.BOX_LOWER);
		gc.strokeLine(Constants.BOX_LEFT, Constants.BOX_LOWER, Constants.BOX_RIGHT, Constants.BOX_LOWER);
		gc.strokeLine(Constants.BOX_RIGHT, Constants.BOX_LOWER, Constants.BOX_RIGHT, Constants.BOX_UPPER);
		
		gc.strokeRoundRect(Constants.FRUIT_NEXT_X - Constants.BOX_NEXT_SIZE / 2.0, Constants.FRUIT_NEXT_Y - Constants.BOX_NEXT_SIZE / 2.0,
				Constants.BOX_NEXT_SIZE, Constants.BOX_NEXT_SIZE, 50.0, 50.0);
	}
}
