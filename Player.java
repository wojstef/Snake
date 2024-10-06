package Snake;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Snake.SnakeBody.BodyPart;

public class Player {
	
	public static enum Dir {
		UP, DOWN, RIGHT, LEFT
	}
	
	private Dir direction;
	private double x, y;
	private ArrayList<SnakeBody> body = new ArrayList<>();
	private int length;
	private double speed;
	private static final double bodyDistance =15D;
	private int score, fruitCounter;

	public Player() {
		x = Game.WIDTH / 2;
		y = Game.HEIGHT / 2;
		speed = 1.8;
		length = 2;
		score = 0;
		fruitCounter = 0;
		direction = Dir.LEFT;
		body.add(new SnakeBody(SnakeBody.BodyPart.HEAD, x, y));
		for(int i = 1; body.size() < length; i++) {
			body.add(new SnakeBody(SnakeBody.BodyPart.BODY, x + i * bodyDistance, y));
		}
		body.add(new SnakeBody(SnakeBody.BodyPart.TAIL, x + length * bodyDistance, y));
		update();
	}
	
	public void draw(Graphics2D g2d) {
		for(int i = body.size() - 1; i >= 0; i--) {
			body.get(i).draw(g2d);
		}
	}
	
	public void update() {
		x = body.get(0).getX();
		y = body.get(0).getY();
		
		//poruszanie
		switch(direction) {
		case UP:
			body.get(0).moveY(-speed);
			break;
		case DOWN:
			body.get(0).moveY(speed);
			break;
		case RIGHT:
			body.get(0).moveX(speed);
			break;
		case LEFT:
			body.get(0).moveX(-speed);
			break;
		}
		
		//dodawanie kolejnego elementu ciala
		if(body.size() < length) {
			body.add(body.size()-2, new SnakeBody(BodyPart.BODY, body.get(body.size()-2).getX(), body.get(body.size()-2).getY()));
		}
		
		//obracanie wszystkich elementow ciala
		double dx = x - body.get(1).getX();
		double dy = body.get(1).getY() - y;
		body.get(0).setAngle(Math.atan2(dx, dy));
		for(int i = body.size() - 1; i > 0; i--) {
			dx = body.get(i-1).getX() - body.get(i).getX();
			dy = body.get(i).getY() - body.get(i-1).getY();
			body.get(i).setAngle(Math.atan2(dx, dy));
		}
		
		//przesuwanie wszystkich elementow ciala
		for(int i = 1; i < body.size(); i++) {
			dx = body.get(i-1).getX() - body.get(i).getX();
			dy = body.get(i-1).getY() - body.get(i).getY();
			double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			if(dist > bodyDistance) {
				body.get(i).moveX(dx*(dist-bodyDistance)/dist);
				body.get(i).moveY(dy*(dist-bodyDistance)/dist);
			}
		}
		
		//kolizja glowy z reszta ciala
		for(int i = 4; i < body.size(); i++) {
			if(Sprite.collision(body.get(0), body.get(i))) {
				Game.gameOver();
			}
		}
		
		//wyjscie poza plansze
		if (x - SnakeBody.bodySize / 2 < 0 ||
			x + SnakeBody.bodySize / 2 > Game.WIDTH ||
			y - SnakeBody.bodySize / 2 < 0 ||
			y + SnakeBody.bodySize / 2 > Game.HEIGHT) {
			Game.gameOver();
		}
	}
	
	
	
	public Dir getDirection() {
		return direction;
	}

	public void setDirection(Dir direction) {
		this.direction = direction;
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

	public int getLength() {
		return length;
	}

	public void increaseLength(int i) {
		this.length += i;
	}
	
	public void increaseSpeed(double s) {
		speed += s;
	}
	
	public Sprite getHead() {
		return body.get(0);
	}
	
	public void addScore(int s) {
		score += s;
	}
	
	public int getScore() {
		return score;
	}

	public void addFruit() {
		fruitCounter++;
	}
	
	public int getFruits() {
		return fruitCounter;
	}
}
