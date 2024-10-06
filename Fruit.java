package Snake;
public abstract class Fruit extends Sprite{
	
	private static ImageLoader img[] = {
		new ImageLoader("fruits.png", 0, 0, 32, 32),
		new ImageLoader("fruits.png", 32, 0, 32, 32),
		new ImageLoader("fruits.png", 64, 0, 32, 32),
		new ImageLoader("fruits.png", 96, 0, 32, 32)};
	
	private boolean isEaten;
	
	public final boolean getIsEaten() {
		return isEaten;
	}
	
	public final void setIsEaten(boolean e) {
		isEaten = e;
	}
	
	public void update() {
		if(Sprite.collision(Game.player.getHead(), this)) {
			setIsEaten(true);
		}
	}
	
	public abstract int getScore();
	
	public Fruit() {
		super(30 + Math.random()*(Game.WIDTH-60), 30 + Math.random()*(Game.HEIGHT-60), 32);
		isEaten = false;
		setImageLoader(img[(int)(Math.random() * img.length)]);
	}
	

}
