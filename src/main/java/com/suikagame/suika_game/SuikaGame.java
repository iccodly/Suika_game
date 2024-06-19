package com.suikagame.suika_game;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SuikaGame extends Application {
	
	private double cursorX = 0.0;
	private double cursorY = 0.0;
	private long then = 0;
	private long throwTime = 0;
	private boolean throwable = true;
	
	public void start(Stage primaryStage) throws Exception {
		
		CanvasManager canvasManager = CanvasManager.getInstance();
		FruitManager fruitManager = FruitManager.getInstance();
		BoxManager boxManager = BoxManager.getInstance();
		CycleManager cycleManager = CycleManager.getInstance();
		
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(canvasManager.getCanvas());
		
		Scene scene = new Scene(stackPane);
		
		canvasManager.setProperty(scene);
		
		scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
			cursorX = event.getX();
			cursorY = event.getY();
		});
		
		scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
			cursorX = event.getX();
			cursorY = event.getY();
		});
		
		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
			if (!throwable) {
				return;
			}
			throwable = false;
			throwTime = then;
			fruitManager.throwFruit();
			fruitManager.makeNewFruit();
		});
		
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.R) {
				fruitManager.doReset();
			} else if (event.getCode() == KeyCode.SPACE) {
				fruitManager.doShake();
			} else if (event.getCode() == KeyCode.H) {
				fruitManager.changeHard();
			}
		});
		
		scene.setCursor(Cursor.NONE);
		
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				canvasManager.clearCanvas();
				boxManager.drawBox(canvasManager.getGc());
				
				if (!throwable && then >= throwTime + 7.5e8) {
					throwable = true;
				}
				if (throwable) {
					fruitManager.drawAimLine(canvasManager.getGc(), cursorX);
				}
				
				fruitManager.update((now - then) / 1e6, cursorX);
				canvasManager.drawScore(fruitManager.getScore(), fruitManager.getSpaceCount());
				if (throwable) {
					fruitManager.drawAim(canvasManager.getGc(), cursorX);
				}
				
				fruitManager.drawParticles(canvasManager.getGc(), (now - then) / 1e6);
				
				cycleManager.drawCycle(canvasManager.getGc());
				
				then = now;
			}
		};
		timer.start();
		
		primaryStage.setTitle("Suika Game");
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}