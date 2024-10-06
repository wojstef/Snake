package Snake;

import java.awt.Graphics2D;

public abstract class Sprite {
	
	private double x, y, angle, d;
	private ImageLoader img;
	
	public Sprite(double x, double y, double d) {
		this.x = x;
		this.y = y;
		this.d = d;
		this.angle = 0D;
		this.img = null;
	}
	
	public void draw(Graphics2D g2d) {
		if(img != null) {
			g2d.rotate(angle, x, y);
			g2d.drawImage(img.getImg(), (int)(x-d/2), (int)(y-d/2), (int)d, (int)d, null);	
			g2d.rotate(-angle, x, y);
		}
	}
	
	public static boolean collision(Sprite e1, Sprite e2) {
		double distance = Math.sqrt(Math.pow(e1.getX()-e2.getX(), 2) + Math.pow(e1.getY()-e2.getY(), 2));
		if(distance <= e1.getD()/2 + e2.getD()/2) {
			return true;
		}
		return false;
	}
	
	public void moveX(double dx) {
		this.x += dx;
	}

	public void moveY(double dy) {
		this.y += dy;
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

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public ImageLoader getImageLoader() {
		return img;
	}
	
	public void setImageLoader(ImageLoader img) {
		this.img = img;
	}
}
