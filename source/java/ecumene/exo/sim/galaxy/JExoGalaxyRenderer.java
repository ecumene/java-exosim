package ecumene.exo.sim.galaxy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import ecumene.exo.sim.ESContext;
import ecumene.exo.sim.IESContextListener;
import ecumene.exo.sim.map.real.JRMapRenderer;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RPoint;

public class JExoGalaxyRenderer extends JRMapRenderer implements IESContextListener {
	
	private float rotate;
	
	public JExoGalaxyRenderer(final RMap pMap) {
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
				boolean rotated = false;
				if(e.getKeyCode() == KeyEvent.VK_R){
					rotate = 10f;
					rotated = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_E){
					rotate = 10f;
					rotated = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					rotate = 0;
					rotated = true;
				}

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
				
				if(rotated){
					for(RPoint point : pMap.getMap()){
						System.out.println(rotate);
						float rotateR = (float)Math.toRadians(rotate);
						float c = (float) Math.cos(rotateR), s = (float) Math.sin(rotateR);
						float x = point.position.x, y = point.position.y;
						
						point.position.x = x * c - y * s;
						point.position.y = x * s + y * c;
					}
				}
				
				rotated = false;
				repaint();
			}
		});
	}
	
	public RMap getRMap(){
		return pMap;
	}
	
	public void setRMap(RMap pMap){
		this.pMap = pMap;
	}
	
	@Override
	protected void paintComponent(Graphics g) {		
		super.paintComponent(g);
		
		Graphics2D graphics = (Graphics2D) g;
		graphics.drawString("Galactic Abstraction", 0, 10);
		graphics.drawString("Press N to toggle names", 0, 20);
		graphics.drawString("Count: " + pMap.getMap().length, 0, 30);
	}

	@Override
	public void onContextChanged(ESContext context) {
		repaint();
	}
}
