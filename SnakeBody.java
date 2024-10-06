package Snake;

public class SnakeBody extends Sprite {
	
	public static enum BodyPart {
		HEAD, BODY, TAIL
	}
	
	public static final double bodySize = 25D;
	
	private static ImageLoader headImage = new ImageLoader("snake.png", 0, 0, 32, 32);
	private static ImageLoader bodyImage = new ImageLoader("snake.png", 32, 0, 32, 32);
	private static ImageLoader tailImage = new ImageLoader("snake.png", 64, 0, 32, 32);
	
	public SnakeBody(BodyPart bp, double x, double y) {
		super(x, y, bodySize);
		switch(bp) {
		case HEAD:
			setImageLoader(headImage);
			break;
		case BODY:
			setImageLoader(bodyImage);
			break;
		case TAIL:
			setImageLoader(tailImage);
		}
	}
	
}
