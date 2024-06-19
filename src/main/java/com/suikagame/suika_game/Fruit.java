package com.suikagame.suika_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

public class Fruit {
	
	private double x;
	private double y;
	private final int grade;
	private final double radius;
	private final Color color;
	
	private double pvx;
	private double pvy;
	private double velocityX;
	private double velocityY;
	private double accelerationX;
	private double accelerationY;
	
	private String state;
	private int index;
	private boolean isTouch;
	
	public Fruit(Fruit fruit1, Fruit fruit2, int index) {
		this.x = (fruit1.x + fruit2.x) / 2.0;
		this.y = (fruit1.y + fruit2.y) / 2.0;
		this.grade = fruit1.grade + 1;
		this.radius = Constants.FRUIT_SIZES.get(this.grade);
		this.color = Constants.FRUIT_COLORS.get(this.grade);
		
		this.pvx = 0.0;
		this.pvy = 0.0;
		this.velocityX = 0.0;
		this.velocityY = 0.0;
		this.accelerationX = 0.0;
		this.accelerationY = Constants.GRAVITY;
		
		this.state = "NORMAL";
		this.index = index;
		this.isTouch = false;
	}
	
	public Fruit(int grade, String state, int index) {
		if (state.equals("HANGED")) {
			this.x = Constants.FRUIT_INIT_X;
			this.y = Constants.FRUIT_INIT_Y;
		} else if (state.equals("NEXT")) {
			this.x = Constants.FRUIT_NEXT_X;
			this.y = Constants.FRUIT_NEXT_Y;
		}
		
		this.grade = grade;
		this.radius = Constants.FRUIT_SIZES.get(grade);
		this.color = Constants.FRUIT_COLORS.get(grade);
		
		this.pvx = 0.0;
		this.velocityX = 0.0;
		this.velocityY = 0.0;
		this.accelerationX = 0.0;
		this.accelerationY = Constants.GRAVITY;
		
		this.state = state;
		this.index = index;
		this.isTouch = false;
	}
	
	public Fruit(double x, double y, int grade, int index) {
		this.x = x;
		this.y = y;
		this.grade = grade;
		this.radius = Constants.FRUIT_SIZES.get(grade);
		this.color = Constants.FRUIT_COLORS.get(grade);
		
		this.pvx = 0.0;
		this.pvy = 0.0;
		this.velocityX = 0.0;
		this.velocityY = 0.0;
		this.accelerationX = 0.0;
		this.accelerationY = Constants.GRAVITY;
		
		this.state = "CYCLE";
		this.index = index;
		this.isTouch = false;
	}
	
	public Fruit() {
		this.x = Constants.FRUIT_INIT_X;
		this.y = Constants.FRUIT_INIT_Y;
		this.grade = 0;
		this.radius = Constants.FRUIT_SIZES.get(grade);
		this.color = Constants.FRUIT_COLORS.get(grade);
		
		this.pvx = 0.0;
		this.velocityX = 0.0;
		this.velocityY = 0.0;
		this.accelerationX = 0.0;
		this.accelerationY = Constants.GRAVITY;
		
		this.pvy = 0.0;
		this.state = "NORMAL";
		this.index = 0;
		this.isTouch = false;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public Color getColor() {
		return color;
	}
	
	public double getVelocityX() {
		return velocityX;
	}
	
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	
	public double getVelocityY() {
		return velocityY;
	}
	
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	
	public double getAccelerationX() {
		return accelerationX;
	}
	
	public void setAccelerationX(double accelerationX) {
		this.accelerationX = accelerationX;
	}
	
	public double getAccelerationY() {
		return accelerationY;
	}
	
	public void setAccelerationY(double accelerationY) {
		this.accelerationY = accelerationY;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public void setState(String state) {
		if (state.equals("NORMAL") || state.equals("HANGED") || state.equals("NEXT") || state.equals("CYCLE")) {
			this.state = state;
		}
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean getIsTouch() {
		return this.isTouch;
	}
	
	public void setIsTouch(boolean isTouch) {
		this.isTouch = isTouch;
	}
	
	public void draw() {
		double proportion = state.equals("CYCLE") ? 0.32 : 1.0;
		GraphicsContext gc = CanvasManager.getInstance().getGc();
		//if (!isTouch) {
		gc.setFill(this.color);
		gc.fillOval(this.x - this.radius * proportion, this.y - this.radius * proportion,
				this.radius * 2 * proportion, this.radius * 2 * proportion);
		//} else {

//		String filePath = "src/main/images/KakaoTalk_20240516_101238536.jpg";
//
//		// Load the image
//		Image image = new Image(new File(filePath).toURI().toString());
//		drawCircularImage(gc, image, this.x - this.radius, this.y - this.radius, this.radius * 1.0);
		
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(0.05 * this.radius * proportion);
		gc.strokeOval(this.x - this.radius * proportion, this.y - this.radius * proportion,
				this.radius * (2 - 0.025) * proportion, this.radius * (2 - 0.025) * proportion);
		//}
	}
	
	public void update(double deltaTime, double cursorX) {
		makeInBox(deltaTime);
		if (state.equals("NORMAL")) {
//			for (int i = 0; i < 100; i++) {
//				double dx = velocityX * deltaTime / 100.0;
//				double dy = velocityY * deltaTime / 100.0;
//				x += dx;
//				y += dy;
//				if (FruitManager.getInstance().isColliding(this)) {
//					break;
//				}
//			}
			
			x += velocityX * deltaTime + 0.5 * accelerationX * deltaTime * deltaTime;
			y += velocityY * deltaTime + 0.5 * accelerationY * deltaTime * deltaTime;
			
			velocityX += accelerationX * deltaTime;
			velocityY += accelerationY * deltaTime;
			accelerationX = 0.0;
			accelerationY = Constants.GRAVITY;
		} else if (state.equals("HANGED")) {
			x = cursorX;
			y = Constants.FRUIT_INIT_Y;
		} else if (state.equals("NEXT")) {
			x = Constants.FRUIT_NEXT_X;
			y = Constants.FRUIT_NEXT_Y;
		} else if (state.equals("CYCLE")) {
			return;
		}
		makeInBox(deltaTime);
	}
	
	public void makeInBox(double deltaTime) {
		if (state.equals("NORMAL")) {
			double velocity = Math.hypot(velocityX, velocityY) * 0.8;
			double acceleration = Math.hypot(accelerationX, accelerationY);
			
			if (isLeftEdge()) {
				double dx = x - Constants.BOX_LEFT;
				double dy = y - Constants.BOX_UPPER;
				
				double distance = Math.hypot(dx, dy);
				double overlap = radius - distance;
				
				double cos = dx / distance;
				double sin = dy / distance;
				
				x += overlap * cos;
				y += overlap * sin;
				
				velocityX = velocity * cos;
				velocityY = velocity * sin;
				
				accelerationX = acceleration * cos;
				accelerationY = acceleration * sin;

//				velocityX = 0.0;
//				velocityY = 0.0;
//
//				accelerationX = 0.0;
//				accelerationY = 0.0;
				return;
			}
			
			if (isRightEdge()) {
				double dx = x - Constants.BOX_RIGHT;
				double dy = y - Constants.BOX_UPPER;
				
				double distance = Math.hypot(dx, dy);
				double overlap = radius - distance;
				
				double cos = dx / distance;
				double sin = dy / distance;
				
				x += overlap * cos;
				y += overlap * sin;
				
				velocityX = velocity * cos;
				velocityY = velocity * sin;
				
				accelerationX = acceleration * cos;
				accelerationY = acceleration * sin;

//				velocityX = 0.0;
//				velocityY = 0.0;
//
//				accelerationX = 0.0;
//				accelerationY = 0.0;
				return;
			}
			
			if (y < Constants.BOX_UPPER
					|| x + radius < Constants.BOX_LEFT
					|| x - radius > Constants.BOX_RIGHT) {
				return;
			}
			if (x - radius < Constants.BOX_LEFT + 0.5) {
				x = Constants.BOX_LEFT + radius;
				if (accelerationX < 0.0) {
					accelerationX = 0.0;
				}
				if (velocityX < 0.0) {
					//accelerationX += velocityX / deltaTime;
					velocityX = 0.0;
				}
			}
			if (x + radius > Constants.BOX_RIGHT - 0.5) {
				x = Constants.BOX_RIGHT - radius;
				if (accelerationX > 0.0) {
					accelerationX = 0.0;
				}
				if (velocityX > 0.0) {
					//accelerationX -= velocityX / deltaTime;
					velocityX = 0.0;
				}
			}
			if (y + radius > Constants.BOX_LOWER - 0.5) {
				y = Constants.BOX_LOWER - radius;
				if (accelerationY > 0.0) {
					accelerationY = 0.0;
				}
				if (velocityY > 0.0) {
					//accelerationY -= velocityY / deltaTime;
					velocityY = 0.0;
				}
			}
		} else if (state.equals("HANGED")) {
			if (x - radius < Constants.BOX_LEFT) {
				x = Constants.BOX_LEFT + radius;
			}
			if (x + radius > Constants.BOX_RIGHT) {
				x = Constants.BOX_RIGHT - radius;
			}
		} else if (state.equals("NEXT")) {
		} else if (state.equals("CYCLE")) {
		}
	}
	
	public boolean isOut() {
		return this.y > Constants.DEADLINE || this.y < -300.0;
	}
	
	private boolean isLeftEdge() {
		double distance = Math.hypot(x - Constants.BOX_LEFT, y - Constants.BOX_UPPER);
		return distance < radius;
	}
	
	private boolean isRightEdge() {
		double distance = Math.hypot(x - Constants.BOX_RIGHT, y - Constants.BOX_UPPER);
		return distance < radius;
	}
	
	private void drawCircularImage(GraphicsContext gc, Image image, double x, double y, double diameter) {
		// Save the current state of the GraphicsContext
		gc.save();
		
		// Create a circular clip
		gc.beginPath();
		gc.arc(x + diameter / 2, y + diameter / 2, diameter / 2, diameter / 2, 0, 360);
		gc.closePath();
		gc.clip();
		
		// Draw the image inside the circular clip
		gc.drawImage(image, x, y, diameter, diameter);
		
		// Restore the original state of the GraphicsContext
		gc.restore();
		
		// Optionally, draw a circular border around the image
		gc.setStroke(Color.BLACK);
		gc.strokeArc(x, y, diameter, diameter, 0, 360, ArcType.OPEN);
	}
}
