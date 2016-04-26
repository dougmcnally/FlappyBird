package flappy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bird extends Rectangle {

	private static final long serialVersionUID = 1L;
	private int speed;
	private Color color;
	
	public Bird(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.color = Color.RED; // default color is red
		this.speed = 0;
	}
	
	public void drawBird(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	public void setSpeed(int s) {
		this.speed = s;
	}
	
	public void move() {
		this.y += speed;
	}
	
	public int getSpeed() {
		return speed;
	}
}