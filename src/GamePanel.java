import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener{

	Image img = Toolkit.getDefaultToolkit().getImage("Image/board.jpg");
	static final int WIDTH = 650;
	static final int HEIGHT = 700;
	static final int SIZE = 25;
	static final int GAME_UNITS = (WIDTH * HEIGHT)/SIZE;
	int DELAY = 150;
	final int [] x = new int [GAME_UNITS]; // x cords of snake
	final int [] y = new int [GAME_UNITS]; // y cords of snake
	int body = 6; // start with 6 body parts
	int eaten = 0; // what has the snake consumed
	int foodX;
	int foodY;
	char direction = 'R'; // direction snake is travelling
	boolean running = false;
	Timer timer;
	Random random;


	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// this refers to panel
		this.setBackground(Color.WHITE);
		this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
		startGame();
	}

	public void startGame() {
		food();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		if(running) {
			g.drawImage(img, 0, 0, null);
			g.setColor(Color.green);
			g.fillRect(foodX, foodY, SIZE, SIZE);

			for(int i=0; i<body;i++) {
				if(i==0) {
					g.setColor(Color.black);
					g.fillRect(x[i], y[i], SIZE, SIZE);
				}
				else {
					g.setColor(Color.cyan);
					g.fillOval(x[i], y[i], SIZE, SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD,15));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("SCORE " + eaten , (WIDTH - metrics.stringWidth("SCORE: "))/2, 30);
		}
		else gameOver(g);

	}
	public void food() {
		foodX = random.nextInt((int)WIDTH/SIZE)*SIZE;
		foodY = random.nextInt((int)HEIGHT/SIZE)*SIZE;
	}
	public void move() {
		for(int i=body; i>0; i--) { // used to move the body parts of the snake
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		

		switch(direction) {
		case 'R':
			x[0] = x[0] + SIZE;
			break;
		case 'U':
			y[0] = y[0] - SIZE;
			break;
		case 'L':
			x[0] = x[0] - SIZE;
			break;
		case 'D':
			y[0] = y[0] + SIZE;
			break;

		}
	}
	public void checkApple() {
		if(x[0] == foodX && y[0] == foodY) {
			body++;
			eaten++;
			food();
			DELAY-=10;
		}
	}
	public void checkCollisions() {
		for(int i=body; i>0;i--) {
			if(x[0]==x[i] && y[0]==y[i]) {
				running = false;
			}
			if(x[0] < 0 || x[0]> WIDTH || y[0] < 0 || y[0] > HEIGHT) {
				running = false;
			}
			if(!running) timer.stop();
		}

	}
	public void gameOver(Graphics g) {
		//GameOver text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD,76));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over"))/2, HEIGHT/2);
		g.setFont(new Font("Ink Free", Font.BOLD,30));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("SCORE " + eaten , (WIDTH - metrics1.stringWidth("SCORE: "))/2, (HEIGHT/2)+50);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}

	public class myKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!= 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction!= 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction!= 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction!= 'U') {
					direction = 'D';
				}
				break;
			}

		}

	}

}
