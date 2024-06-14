package com.suikagame.suika_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.*;

public class FruitManager {
	
	private static final FruitManager instance = new FruitManager();
	
	private List<Fruit> fruits;
	private Set<Integer> fruitCollide;
	private List<Particle> particles;
	private Fruit hangedFruit;
	private Fruit nextFruit;
	
	private int score;
	private int maxGrade;
	private int spaceCount;
	private int fruitIndex;
	
	private boolean isOver;
	
	private FruitManager() {
		particles = new ArrayList<>();
		reset();
	}
	
	private void reset() {
		fruits = new ArrayList<Fruit>();
		fruitCollide = new HashSet<Integer>();
		hangedFruit = new Fruit(0, "HANGED", 0);
		nextFruit = new Fruit(0, "NEXT", 1);
		
		score = 0;
		maxGrade = 0;
		spaceCount = 0;
		fruitIndex = 2;
		
		isOver = false;
	}
	
	public static FruitManager getInstance() {
		return instance;
	}
	
	public void update(double deltaTime, double cursorX) {
		
		// 1. 과일의 충돌 여부 확인
		List<Fruit> willFruits = new ArrayList<Fruit>();
		List<Boolean> isErased = new ArrayList<Boolean>(Collections.nCopies(fruits.size(), false));
		Set<Integer> willCollide = new HashSet<Integer>();
		for (Fruit fruit : fruits) {
			fruit.setIsTouch(false);
		}
		for (int i = 0; i < fruits.size(); i++) {
			Fruit fruit1 = fruits.get(i);
			for (int j = i + 1; j < fruits.size(); j++) {
				Fruit fruit2 = fruits.get(j);
				
				if (isErased.get(i) || isErased.get(j)) {
					continue;
				}
				
				if (isColliding(fruit1, fruit2)) {
					if (fruit1.getGrade() == fruit2.getGrade() && fruit1.getGrade() != 11) {
						score += Constants.FRUIT_SCORES.get(fruit1.getGrade());
						maxGrade = Math.max(maxGrade, fruit1.getGrade() + 1);
						if (fruit1.getGrade() != 10) {
							Fruit newFruit = new Fruit(fruit1, fruit2, fruitIndex);
							createParticles(newFruit.getX(), newFruit.getY(), newFruit.getColor(), newFruit.getGrade());
							willFruits.add(newFruit);
							fruitIndex += 1;
						} else {
							createParticles((fruit1.getX() + fruit2.getX()) / 2.0, (fruit1.getY() + fruit2.getY()) / 2.0,
									fruit1.getColor(), fruit1.getGrade() + 1);
						}
						//makeWave(newFruit);
						isErased.set(i, true);
						isErased.set(j, true);
					} else {
//						int minIndex = Math.min(fruit1.getIndex(), fruit2.getIndex());
//						int maxIndex = Math.max(fruit1.getIndex(), fruit2.getIndex());
//						if (!fruitCollide.contains(minIndex * 10000 + maxIndex)) {
						moveFruits(fruit1, fruit2, deltaTime);
						//willCollide.add(minIndex * 10000 + maxIndex);
						fruit1.setIsTouch(true);
						fruit2.setIsTouch(true);
						// }
					}
				}
			}
		}
		
		for (int i = 0; i < fruits.size(); i++) {
			if (!isErased.get(i)) {
				willFruits.add(fruits.get(i));
			}
		}
		fruits = willFruits;
		fruitCollide = willCollide;
		
		// 2. 과일을 움직임
		nextFruit.update(deltaTime, cursorX);
		hangedFruit.update(deltaTime, cursorX);
		for (Fruit fruit : fruits) {
			fruit.update(deltaTime, cursorX);
		}
		
		// 3. 과일을 그림
		nextFruit.draw();
		hangedFruit.draw();
		for (Fruit fruit : fruits) {
			fruit.draw();
		}
		
		List<Particle> aliveParticles = new ArrayList<>();
		for (Particle particle : particles) {
			particle.update(deltaTime);
			if (particle.isAlive()) {
				aliveParticles.add(particle);
			}
		}
		particles = aliveParticles;
		
		// 4. 게임 오버 판정
		for (Fruit fruit : fruits) {
			if (fruit.isOut()) {
				isOver = true;
			}
		}
		
		if (isOver) {
			for (Fruit fruit : fruits) {
				createParticles(fruit.getX(), fruit.getY(), fruit.getColor(), fruit.getGrade());
			}
			reset();
		}
		
		Collections.shuffle(fruits);
	}
	
	public boolean isColliding(Fruit fruit1, Fruit fruit2) {
		double dx = Math.abs(fruit1.getX() - fruit2.getX());
		double dy = Math.abs(fruit1.getY() - fruit2.getY());
		double distance = Math.hypot(dx, dy);
		return distance <= fruit1.getRadius() + fruit2.getRadius();
	}
	
	public void throwFruit() {
		if (isOver) {
			return;
		}
		hangedFruit.setState("NORMAL");
		fruits.add(hangedFruit);
		
		hangedFruit = nextFruit;
		hangedFruit.setState("HANGED");
		makeNextFruit();
	}
	
	private void makeNextFruit() {
		if (maxGrade >= 11) {
			int newGrade = (int) (Math.random() * 6);
			int random = (int) (Math.random() * 100);
			if (random < 3) {
				newGrade = 11;
			}
			nextFruit = new Fruit(newGrade, "NEXT", fruitIndex);
			fruitIndex += 1;
		} else {
			nextFruit = new Fruit((int) (Math.random() * 5), "NEXT", fruitIndex);
			fruitIndex += 1;
		}
	}
	
	private void moveFruits(Fruit fruit1, Fruit fruit2, double deltaTime) {
		
		double x1 = fruit1.getX();
		double y1 = fruit1.getY();
		double x2 = fruit2.getX();
		double y2 = fruit2.getY();
		
		double dx = x1 - x2;
		double dy = y1 - y2;
		
		double distance = Math.hypot(dx, dy);
		double overlap = Math.max(fruit1.getRadius() + fruit2.getRadius() - distance, 0.0);
		
		double cos = dx / distance;
		double sin = dy / distance;
		
		double v1x = fruit1.getVelocityX();
		double v1y = fruit1.getVelocityY();
		double v2x = fruit2.getVelocityX();
		double v2y = fruit2.getVelocityY();
		
		double v1x2 = ((v2x * cos + v2y * sin) * cos - (-v1x * sin + v1y * cos) * sin);
		double v1y2 = ((v2x * cos + v2y * sin) * sin + (-v1x * sin + v1y * cos) * cos);
		double v2x2 = ((v1x * cos + v1y * sin) * cos - (-v2x * sin + v2y * cos) * sin);
		double v2y2 = ((v1x * cos + v1y * sin) * sin + (-v2x * sin + v2y * cos) * cos);
		
		double p = 0.99;
		
		fruit1.setVelocityX(v1x2 * p);
		fruit1.setVelocityY(v1y2 * p);
		fruit2.setVelocityX(v2x2 * p);
		fruit2.setVelocityY(v2y2 * p);

//		double a1x = (v1x2 - v1x) / deltaTime * p;
//		double a1y = (v1y2 - v1y) / deltaTime * p;
//		double a2x = (v2x2 - v2x) / deltaTime * p;
//		double a2y = (v2y2 - v2y) / deltaTime * p;

//		double r = 0.0;
//		fruit1.setAccelerationX(fruit1.getAccelerationX() + overlap * cos / 2.0 / deltaTime / deltaTime * r);
//		fruit1.setAccelerationY(fruit1.getAccelerationY() + overlap * sin / 2.0 / deltaTime / deltaTime * r);
//		fruit2.setAccelerationX(fruit2.getAccelerationX() + overlap * -cos / 2.0 / deltaTime / deltaTime * r);
//		fruit2.setAccelerationY(fruit2.getAccelerationY() + overlap * -sin / 2.0 / deltaTime / deltaTime * r);
		
		double q = 0.55;
		fruit1.setX(x1 + overlap * cos * q);
		fruit1.setY(y1 + overlap * sin * q);
		fruit2.setX(x2 - overlap * cos * q);
		fruit2.setY(y2 - overlap * sin * q);
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getSpaceCount() {
		return this.spaceCount;
	}
	
	
	public void drawAimLine(GraphicsContext gc, double x) {
		if (x - hangedFruit.getRadius() < Constants.BOX_LEFT) {
			x = Constants.BOX_LEFT + hangedFruit.getRadius();
		}
		if (x + hangedFruit.getRadius() > Constants.BOX_RIGHT) {
			x = Constants.BOX_RIGHT - hangedFruit.getRadius();
		}
		
		double y = Constants.BOX_LOWER;
		for (Fruit fruit : fruits) {
			if (Math.abs(x - fruit.getX()) < fruit.getRadius()) {
				double dx = Math.abs(x - fruit.getX());
				y = Math.min(y, fruit.getY()
						- Math.sqrt(fruit.getRadius() * fruit.getRadius() - dx * dx));
			}
		}
		gc.setStroke(Color.LIGHTGREY);
		gc.strokeLine(x, Constants.FRUIT_INIT_Y, x, y);
	}
	
	public void drawAim(GraphicsContext gc, double x) {
		if (x - hangedFruit.getRadius() < Constants.BOX_LEFT) {
			x = Constants.BOX_LEFT + hangedFruit.getRadius();
		}
		if (x + hangedFruit.getRadius() > Constants.BOX_RIGHT) {
			x = Constants.BOX_RIGHT - hangedFruit.getRadius();
		}
		
		double y = Constants.BOX_LOWER;
		for (Fruit fruit : fruits) {
			if (Math.abs(x - fruit.getX()) < fruit.getRadius()) {
				double dx = Math.abs(x - fruit.getX());
				y = Math.min(y, fruit.getY()
						- Math.sqrt(fruit.getRadius() * fruit.getRadius() - dx * dx));
			}
		}
		gc.setFill(Color.BLACK);
		gc.fillOval(x - 7.0, y - 7.0, 14.0, 14.0);
	}
	
	private void createParticles(double x, double y, Color color, int grade) {
		for (int i = 0; i < 25 + 2 * grade; i++) {
			double velocityX = (Math.random() - 0.5) * 1.1e-1 * (grade + 1);
			double velocityY = (Math.random() - 0.5) * 1.1e-1 * (grade + 1);
			double size = (10.0 + Math.random() * 15.0) * (grade + 1);
			double lifetime = (500.0 + Math.random() * 1000.0) * (grade + 1);
			double alpha = Math.max(0.08, (0.3 + Math.random() * 0.5) / (grade + 1));
			particles.add(new Particle(x, y, velocityX, velocityY, size, new Color(color.getRed(), color.getBlue(), color.getGreen(), alpha), lifetime));
		}
	}
	
	public void drawParticles(GraphicsContext gc, double deltaTime) {
		for (Particle particle : particles) {
			particle.draw(gc);
			particle.update(deltaTime);
		}
	}
	
	public void doReset() {
		for (Fruit fruit : fruits) {
			createParticles(fruit.getX(), fruit.getY(), fruit.getColor(), fruit.getGrade());
		}
		reset();
	}
	
	public void doShake() {
		spaceCount += 1;
		for (Fruit fruit : fruits) {
			fruit.setVelocityY(fruit.getVelocityY() - 0.5 - Math.random() * 0.3);
			fruit.setAccelerationY(fruit.getAccelerationY() - Constants.GRAVITY * 4);
			fruit.setVelocityX(fruit.getVelocityX() + (Math.random() - 0.5) * 1.5);
		}
	}

//	public void makeWave(Fruit newFruit) {
//		for (Fruit fruit : fruits) {
//			if (!isColliding(fruit, newFruit)) {
//				continue;
//			}
//
//			double x1 = fruit.getX();
//			double y1 = fruit.getY();
//
//			double dx = fruit.getX() - newFruit.getX();
//			double dy = fruit.getY() - newFruit.getY();
//
//			double distance = Math.hypot(dx, dy);
//			double overlap = fruit.getRadius() + newFruit.getRadius() - distance;
//
//			double cos = dx / distance;
//			double sin = dy / distance;
//
//			double velocity = Math.hypot(fruit.getVelocityX(), fruit.getVelocityY());
//			double acceleration = Math.hypot(fruit.getAccelerationX(), fruit.getAccelerationY() + Constants.GRAVITY);
//
//			fruit.setX(fruit.getX() + overlap * cos);
//			fruit.setY(fruit.getY() + overlap * sin);
//
//			double velocity1 = velocity + overlap / fruit.getRadius() * 0.25;
//
//			fruit.setVelocityX(velocity1 * cos);
//			fruit.setVelocityY(velocity1 * sin);
//			fruit.setAccelerationX(acceleration * cos);
//			fruit.setAccelerationY(acceleration * sin);
//			fruit.setX(x1 + overlap * cos * 1.5);
//			fruit.setY(y1 + overlap * sin * 1.5);
//
//			fruit.makeInBox();
//		}
//	}
}
