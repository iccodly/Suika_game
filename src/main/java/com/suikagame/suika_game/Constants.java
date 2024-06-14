package com.suikagame.suika_game;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Constants {
	final static public double CANVAS_WIDTH = 1706.66;
	final static public double CANVAS_HEIGHT = 1066.66;
	
	final static public double FRUIT_INIT_X = CANVAS_WIDTH / 2.0;
	final static public double FRUIT_INIT_Y = 300.0;
	
	final static public double FRUIT_NEXT_X = 1375.0;
	final static public double FRUIT_NEXT_Y = 350.0;
	
	final static public double BOX_LEFT = CANVAS_WIDTH / 2.0 - 230;
	final static public double BOX_RIGHT = CANVAS_WIDTH / 2.0 + 230;
	final static public double BOX_UPPER = 400.0;
	final static public double BOX_LOWER = 910.0;
	
	final static public double BOX_NEXT_SIZE = 220.0;
	
	final static public double FONT_NEXT_Y = 200.0;
	
	final static public double DEADLINE = 1050.0;
	
	final static public double SCORE_LEFT = 230.0;
	final static public double SCORE_UPPER = 420.0;
	
	final static public double GRAVITY = 0.0008;
	
	final static public double BUMP = 0.999;
	
	final static public double eps = 1e-1;
	
	final static public double CYCLE_CENTER_X = 350.0;
	final static public double CYCLE_CENTER_Y = 710.0;
	
	final static public double CHERRY_SIZE = (BOX_RIGHT - BOX_LEFT) / 26.5;
	
	final static public List<Double> FRUIT_SIZES = new ArrayList<Double>() {{
		add(CHERRY_SIZE);
		add(CHERRY_SIZE * 1.3);
		add(CHERRY_SIZE * 1.8);
		add(CHERRY_SIZE * 2.2);
		add(CHERRY_SIZE * 2.7);
		
		add(CHERRY_SIZE * 3.5);
		add(CHERRY_SIZE * 3.9);
		add(CHERRY_SIZE * 4.8);
		add(CHERRY_SIZE * 5.5);
		add(CHERRY_SIZE * 6.8);
		
		add(CHERRY_SIZE * 8);
		add(CHERRY_SIZE);
	}};
	
	final static public List<Color> FRUIT_COLORS = new ArrayList<Color>() {{
		add(Color.web("#D50807"));
		add(Color.web("#F8657E"));
		add(Color.web("#A56AFF"));
		add(Color.web("#FDAF02"));
		add(Color.web("#FD8B2A"));
		
		add(Color.RED);
		add(Color.web("#FDEE7F"));
		add(Color.web("#FFB7B1"));
		add(Color.YELLOW);
		add(Color.web("#9BDB14"));
		
		add(Color.web("#17980D"));
		add(Color.GRAY);
	}};
	
	final static public List<Integer> FRUIT_SCORES = new ArrayList<Integer>() {{
		add(1);
		add(3);
		add(6);
		add(10);
		add(15);
		
		add(21);
		add(28);
		add(36);
		add(45);
		add(55);
		
		add(66);
	}};
}
