package ecumene.exo.view.rmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.joml.Vector2f;
import org.joml.Vector3f;

import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RPoint;

public class JRMViewer extends JPanel {
	
	protected RMap pMap;
	protected boolean useNames = false;
	public Vector3f navigation = new Vector3f(0, 0, 1);
	private List<RMVPointRenderer> rendererList;
	protected RMVPointRenderer pointRenderer;
	
	public JRMViewer(RMap pMap) {
		this.pMap = pMap;
		rendererList = new ArrayList<RMVPointRenderer>();
		rendererList.add(pointRenderer = new RMVPointRenderer(this));
		
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
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
		graphics.setColor(new Color(0, 0, 0));
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setColor(new Color(0, 255, 0));
		
		if(pMap != null){
			for(int i = 0; i < pMap.getMap().length; i++){
			    if(pMap.getMap()[i] != null){
	    			Vector2f navPos = new Vector2f(pMap.getMap()[i].position);
	    			Vector2f screenPos;
	    			navPos.x    *= Math.abs(navigation.z);
	    			navPos.y    *= -Math.abs(navigation.z);
	    			navPos.x    += navigation.x;
	    			navPos.y    += navigation.y;
	    			screenPos    = new Vector2f(navPos);
	    			screenPos.x += (getWidth() / 2);
	    			screenPos.y += (getHeight() / 2);
	    			
	    			drawPoint(graphics, i, pMap.getMap()[i], pMap.getMap()[i].position, navPos, screenPos);
			    }
			}
	
			graphics.drawLine((int) ((getWidth() / 2) + navigation.x), 0, (int)((getWidth() / 2) + navigation.x), getHeight());
			graphics.drawLine(getWidth(), (int) ((getHeight() / 2) + navigation.y), 0, (int)((getHeight() / 2) + navigation.y));
		} else graphics.drawString(getNullPMapError(), 0, 10);
	}
	
//	 realPos   - Position on real plane
//	 navPos    - Position on the nav plane
//	 screenPos - Position in screen plane
	protected void drawPoint(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos){
		// Point draw
		for(int i = 0; i < rendererList.size(); i++){
			rendererList.get(i).render(graphics, id, point, realPos, navPos, screenPos);
		}
	}
	
	public String getNullPMapError(){
		return "PMAP context is empty";
	}
	
	public List<RMVPointRenderer> getRendererList() {
		return rendererList;
	}
	
	public RMVPointRenderer getDefaultRPointRenderer(){
		return this.pointRenderer;
	}
	
	public void setUseNames(boolean b){
		this.useNames = b;
	}
	
	public boolean getUseNames(){
		return useNames;
	}
}
