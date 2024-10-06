package Snake;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final int 
			WIDTH = 800,
			HEIGHT = 600,
			CENTER_X = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().x,
			CENTER_Y = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().y,
			bonusChance = 40;
	public static final double amountOfTicks = 60D,
			tickTime = 1000000000 / amountOfTicks;
	public static Player player;
	public static Fruit fruit;
	public static JFrame frame;
	public static JPanel gamePanel, menuPanel;
	public static Scoreboard scoreboard;
	private static Thread gameThread;
	private static ImageLoader map = new ImageLoader("map.png");
	private static boolean running = false,
			gameOver = false,
			frozen = false;
	private static Font font1 = new Font("Arial", Font.PLAIN, 20),
			font2 = new Font("Arial", Font.BOLD, 15),
			font3 = new Font("Arial", Font.BOLD, 70);
	
	public void init() {
		addKeyListener(new KeyManager());
	}
	
	public void tick() {
		if(gameOver) {
			setFrozen(true);
		}
		
		if(!isFrozen()) {
			player.update();
			
			if(fruit.getIsEaten()) {
				player.addScore(fruit.getScore());
				player.addFruit();
				player.increaseLength(2);
				player.increaseSpeed(0.1);
				if(Math.random() * 100 <= bonusChance) {
					fruit = new BonusFruit();
				} else {
					fruit = new NormalFruit();
				}
			} else {
				fruit.update();			
			}
			
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		//-------------------------------------------
		g2d.drawImage(map.getImg(), 0, 0, WIDTH, HEIGHT, null);
		
		g2d.setFont(font2);
		fruit.draw(g2d);
		player.draw(g2d);
		
		g2d.setFont(font1);
		g2d.drawString("Punkty: "+player.getScore(), 440, 550);
		g2d.drawString("Zebrane owoce : "+player.getFruits(), 250, 550);
		
		if(isFrozen()) {
			if(gameOver) {
				g2d.setFont(font3);
				g2d.drawString("Koniec Gry", WIDTH/2-180, HEIGHT/2);
				g2d.setFont(font1);
				g2d.drawString("Aby wyjsc nacisnij Esc", WIDTH/2-90, HEIGHT/2+20);	
			} else {
				g2d.setFont(font3);
				g2d.drawString("Pauza", WIDTH/2-100, HEIGHT/2);
			}	
		}
		//-------------------------------------------
		g.dispose();
		bs.show();
	}
	
	public synchronized void start() {
		if(running) return;
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public synchronized void stop() {
		if(!running) return;
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double delta = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / tickTime;
			lastTime = now;
			if(delta >= 1) {
				tick();
				delta--;
			}
			render();
		}
		stop();
	}

	public static void main(String[] args) throws FileNotFoundException {
		final Game game = new Game();
		frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		gamePanel = new JPanel();
		gamePanel.add(game);
		gamePanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		gamePanel.setLayout(new GridLayout(1,1));
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(4,1,10,10));
		JLabel label = new JLabel("Menu Gry",SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(200,40));
		menuPanel.add(label);
		JButton newGameButton = new JButton("Nowa Gra");
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGame();
				showPanel(gamePanel);
				game.start();
				game.requestFocus();
			}
		});
		menuPanel.add(newGameButton);
		JButton scoreboardButton = new JButton("Wyniki");
		scoreboardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showPanel(scoreboard.getScoreboardPanel());
			}
		});
		menuPanel.add(scoreboardButton);
		JButton exitButton = new JButton("Zamknij");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menuPanel.add(exitButton);
		scoreboard = new Scoreboard();
		showPanel(menuPanel);
	}
	
	public static void gameOver() {
		gameOver = true;
	}
	
	public static boolean isGameOver() {
		return gameOver;
	}
	
	public static boolean isFrozen() {
		return frozen;
	}
	
	public static void setFrozen(boolean f) {
		frozen = f;
	}
	
	public static void newGame() {
		player = new Player();
		fruit = new NormalFruit();
		gameOver = false;
		frozen = false;
	}

	public static void showPanel(JPanel panel) {
		frame.getContentPane().removeAll();
		frame.add(panel);
		int w = panel.getPreferredSize().width;
		int h = panel.getPreferredSize().height;
		frame.setBounds(CENTER_X-w/2, CENTER_Y-h/2, w, h);
	}
}
