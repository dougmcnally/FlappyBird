package flappy;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800, HEIGHT = 800, BOTTOM = 120;
	private static int speed = 10, jumpAmount = 10;
	private Bird bird;
	private List<Pipe> pipes;
	private int score;
	private boolean started, gameOver;
	
	public FlappyBird() {
		this.setBackground(Color.CYAN);		
		bird = new Bird(WIDTH / 2 - 15, (HEIGHT - BOTTOM) / 2 - 15, 30, 30);
		pipes = new ArrayList<Pipe>();
		
		// adds a timer that ticks every 20 ms
		Timer clock = new Timer(20, this);
		clock.start();
		score = 0;
		started = false;
		gameOver = false;
		addPipe(4); // populate with 4 pipes initially		
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Flappy Bird");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		FlappyBird panel = new FlappyBird();
		frame.addKeyListener(panel);
		Container c = frame.getContentPane();
		c.add(panel);
		
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.repaint(g);
	}
	
	private void addPipe(int num) {
		int space = 300;
		int gap = 600;
		int width = 100;
		for (int i = 0; i < num; i++) {
			if (pipes.size() == 0) {
				pipes.add(new Pipe(WIDTH, width, space));
			}
			else {
				pipes.add(new Pipe(
						pipes.get(pipes.size()-1).getX() + gap,
						width, space)
						);
			}
		}
	}
	
	private void repaint(Graphics g) {
		// draw the ground:
		g.setColor(new Color(139, 69, 19));
		g.fillRect(0, HEIGHT - 100, WIDTH, 100);
		g.setColor(Color.GREEN);
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);
		
		bird.drawBird(g);		
		
		for (Pipe pipe : pipes) {
			pipe.paintPipe(g);
		}
		
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("Arial", 1, 50));
		
		if (!started) {
			g.drawString("Press space to start.", 150, HEIGHT / 2 - 100);
		} 
		else {
			g.drawString(Integer.toString(score), 10, 45);
		}
		if (gameOver) {
			g.drawString("Game over!", 225, HEIGHT / 2 - 150);
			g.drawString("Press Enter to Restart", 125, HEIGHT / 2 - 100);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (started) {
			if (bird.getSpeed() < 10) {
				bird.setSpeed(bird.getSpeed() + 1);
			}
			
			for (int i = 0; i < pipes.size(); i++) {
				Pipe pipe = pipes.get(i);
				pipe.move(speed);
				
				if (pipe.getX() + pipe.getWidth() < 0) {
					pipes.remove(pipe);	
					addPipe(1);
				}
			}
			
			// check if the bird hit a pipe:
			for (Pipe p : pipes) {
				// check if the bird hit a pipe
				if (p.intersects(bird)) {
					bird.x = p.getX() - bird.width;
					gameOver = true;
				}
				// check if the bird went through a pipe
				if (p.passedFirstTime(bird)) {
					score++;
				}				
			}			
						
			// check if the bird hit the ground:
			if (bird.y + bird.getSpeed() >= HEIGHT - BOTTOM || bird.y > HEIGHT - BOTTOM ) {
				gameOver = true;
				bird.y = HEIGHT - BOTTOM - bird.height;
			}
			
			// update the bird's position
			if (bird.y != HEIGHT - BOTTOM - bird.height) {
				// condition neeeded to prevent jitter
				bird.move();
			}			
			repaint();
		}
	}
	
	private void jump() {
		if (!started && !gameOver) {
			started = true;
		} 
		else {
			if (bird.getSpeed() > 0) {
				bird.setSpeed(0);
			}
			bird.setSpeed(bird.getSpeed() - jumpAmount);
		}
	}
	
	private void newGame() {
		bird = new Bird((WIDTH - bird.width) / 2, (HEIGHT - BOTTOM - bird.width) / 2,
				bird.width, bird.height);
		pipes.clear();
		score = 0;
		addPipe(4);
		gameOver = false;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			newGame();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}