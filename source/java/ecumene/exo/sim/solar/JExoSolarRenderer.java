package ecumene.exo.sim.solar;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.joml.Vector2f;

import ecumene.exo.sim.ESContext;
import ecumene.exo.sim.IESContextListener;
import ecumene.exo.sim.map.real.JRMapRenderer;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RPoint;

public class JExoSolarRenderer extends JRMapRenderer implements IESContextListener {

	private ExoSolarMap map;
	
	public JExoSolarRenderer(ExoSolarMap map) {
		super(new RMap());
		
		this.map = map;
		
		addKeyListener(new KeyListener() {
			@Override public void keyTyped(KeyEvent e)    {}
			@Override public void keyReleased(KeyEvent e) {}
			@Override public void keyPressed(KeyEvent e)  {}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics = (Graphics2D) g;
		graphics.drawString("Solar Abstraction", 0, 10);
		graphics.drawString("Press P to toggle positions", 0, 20);
		graphics.drawString("Press V to toggle vectors", 0, 30);
		graphics.drawString("Artifact Count (+ sun): " + pMap.getMap().length, 0, 40);
	}
	
	@Override
	protected void drawPoint(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos) {
		super.drawPoint(graphics, id, point, realPos, navPos, screenPos);
		
		Vector2f screenVelocity = new Vector2f(((IExoSolarObject) point.object).getVelocity()).mul(navigation.z);
		screenVelocity = screenVelocity.add(screenPos); // Screenspace & nav calc done already! I'm a genius!
		graphics.drawLine((int) (screenPos.x), (int) (screenPos.y), (int) (screenVelocity.x), (int) (screenVelocity.y));
	}
	
	public void onStep(){
		pMap = map.step();
	}

	@Override
	public void onContextChanged(ESContext context) {
		repaint();
	}

	public void setSolarMap(ExoSolarMap solarMap) {
		this.map = solarMap;
	}
	
}
