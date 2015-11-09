package ecumene.exo.sim.galaxy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.KeyAdapter;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;

import ecumene.exo.sim.map.real.JRMapRenderer;
import ecumene.exo.sim.map.real.RMap;

public class JExoGalaxyRenderer extends JRMapRenderer {
	
	public JExoGalaxyRenderer(RMap pMap) {
		super(pMap);
		setFocusable(true);
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				navigation.z += (float) e.getPreciseWheelRotation() * -.2f;
				repaint();
			}
		});
		
		addKeyListener(new KeyListener() {
			@Override public void keyTyped(KeyEvent e) { }
			@Override public void keyReleased(KeyEvent e) { }
			@Override 
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_N){
					useNames = !useNames;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					navigation.y += 4;
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					navigation.y -= 4;
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					navigation.x += 4;
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					navigation.x -= 4;
				}
				repaint();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		graphics.drawString("Galactic Abstraction", 0, 10);
		graphics.drawString("Press N to toggle names", 0, 20);
		graphics.drawString("Count: " + pMap.getMap().length, 0, 30);
	}
}
