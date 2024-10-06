package Snake;

import java.awt.Graphics2D;
import java.text.SimpleDateFormat;

public class BonusFruit extends Fruit{
	private long time;
	private int score;
	
	public BonusFruit() {
		super();
		time = 10 * 1000;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		
	}
	
	public void update() {
		super.update();
		if(time > 0) {
			time -= Game.tickTime/1000000;			
			score = (int)(time/1000 + 10);
		} else {
			time = 0;
			score = 0;
			setIsEaten(true);
		}
	}
	
	@Override
	public int getScore() {
		return score;
	}
}
