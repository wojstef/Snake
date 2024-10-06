package Snake;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	private BufferedImage img;
	
	public ImageLoader() {
		img = null;
	}
	
	public ImageLoader(String path) {
		load(path);
	}
	
	public ImageLoader(String path, int x, int y, int w, int h) {
		load(path, x, y, w, h);
	}
	
	public ImageLoader(BufferedImage img) {
		load(img);
	}
	
	public ImageLoader(BufferedImage img, int x, int y, int w, int h) {
		load(img, x, y, w, h);
	}
	
	public void load(String path) {
		try {
			img = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void load(String path, int x, int y, int w, int h) {
		load(path);
		cut(x, y, w, h);
	}
	
	public void load(BufferedImage img) {
		this.img = img;
	}
	
	public void load(BufferedImage img, int x, int y, int w, int h) {
		load(img);
		cut(x,y,w,h);
	}
	
	public void cut(int x, int y, int w, int h) {
		img = img.getSubimage(x, y, w, h);
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
	public int getWidth() {
		return img.getWidth();
	}
	
	public int getHeight() {
		return img.getHeight();
	}
}
