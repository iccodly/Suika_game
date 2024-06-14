package com.suikagame.suika_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {
	private double x;
	private double y;
	private double velocityX;
	private double velocityY;
	private double size;
	private Color color;
	private double lifetime;
	
	public Particle(double x, double y, double velocityX, double velocityY, double size, Color color, double lifetime) {
		this.x = x;
		this.y = y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.size = size;
		this.color = color;
		this.lifetime = lifetime;
	}
	
	public boolean isAlive() {
		return lifetime > 0.0;
	}
	
	public void update(double deltaTime) {
		x += velocityX * deltaTime;
		y += velocityY * deltaTime;
		velocityX *= (lifetime - deltaTime) / lifetime;
		velocityY *= (lifetime - deltaTime) / lifetime;
		lifetime -= deltaTime;
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillOval(x - size / 2.0, y - size / 2.0, size, size);
	}
}
