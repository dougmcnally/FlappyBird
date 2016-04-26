package flappy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Pipe {
	public static final int MIN_HEIGHT = 50;
	
	private Rectangle top; 
	private Rectangle bottom;
	private boolean passed;
	
	public Pipe(int x, int width, int space) {
		Random rand = new Random();
		int height = MIN_HEIGHT + rand.nextInt(300);
		top = new Rectangle(x, 0, width, height );
		bottom = new Rectangle(x, height + space, width,
				FlappyBird.HEIGHT - height - space - FlappyBird.BOTTOM);
		passed = false;
	}
	
	public Rectangle getTop() {
		return top;
	}
	
	public Rectangle getBottom() {
		return bottom;
	}
	
	public int getX() { return top.x; }
	
	public int getWidth() { return top.width; }
	
	public void paintPipe(Graphics g) {
		g.setColor(Color.BLUE.darker());
		g.fillRect(top.x, top.y, top.width, top.height);
		g.fillRect(bottom.x, bottom.y, bottom.width, bottom.height);
	}
	
	public void move(int amount) {
		top.x -= amount;
		bottom.x -= amount;
	}
	
	public boolean intersects(Rectangle r) {
		return top.intersects(r) || bottom.intersects(r);
	}
	
	public boolean passedFirstTime(Rectangle r) {
		if (passed) return false;
		else if (getX() + getWidth() < r.x) {
			passed = true;
			return true;
		}
		return false;
	}
}