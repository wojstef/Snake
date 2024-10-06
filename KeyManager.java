package Snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(!Game.isFrozen()) {
			//poruszanie
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				Game.player.setDirection(Player.Dir.RIGHT);
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				Game.player.setDirection(Player.Dir.DOWN);
			}
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				Game.player.setDirection(Player.Dir.UP);
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				Game.player.setDirection(Player.Dir.LEFT);
			}
		}
		
		//pauza
		if(e.getKeyCode() == KeyEvent.VK_P) {
			Game.setFrozen(!Game.isFrozen());
		}
		
		//wyjscie
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(Game.isGameOver() && Game.scoreboard.checkScore(Game.player.getScore()) >= 0 && Game.player.getScore() > 0) {
				Game.showPanel(Game.scoreboard.getTextFieldPanel());
			} else {
				Game.showPanel(Game.menuPanel);
				Game.gameOver();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}

}
