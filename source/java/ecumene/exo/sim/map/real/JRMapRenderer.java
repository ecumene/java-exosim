package ecumene.exo.sim.map.real;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class JRMapRenderer extends JPanel {
	
	protected RMap pMap;
	protected boolean useNames = false;
	public Vector3f navigation = new Vector3f(0, 0, 800);
	
	public JRMapRenderer(RMap pMap) {
		this.pMap = pMap;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
		graphics.setColor(new Color(0, 0, 0));
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setColor(new Color(0, 255, 0));
		graphics.drawLine((int) ((getWidth() / 2) + navigation.x), 0, (int)((getWidth() / 2) + navigation.x), getHeight());
		graphics.drawLine(getWidth(), (int) ((getHeight() / 2) + navigation.y), 0, (int)((getHeight() / 2) + navigation.y));
		for(int i = 0; i < pMap.getMap().length; i++){
			Vector2f pos = new Vector2f(pMap.getMap()[i].position);
			pos.x *= Math.abs(navigation.z);
			pos.y *= Math.abs(navigation.z);
			pos.x += (getWidth() / 2)  + navigation.x;
			pos.y *= -1;
			pos.y += (getHeight() / 2) + navigation.y;
			
			graphics.drawLine((int) (pos.x), (int) (pos.y - 2), (int) (pos.x), (int) (pos.y + 2));
			graphics.drawLine((int) (pos.x + 2), (int) (pos.y), (int) (pos.x - 2), (int) (pos.y));
			if(useNames) graphics.drawString(pMap.getMap()[i].object.getName(i), (int)pos.x + 6, (int)pos.y + 4);
		}
	}
	
	public void setUseNames(boolean b){
		this.useNames = b;
	}
	
	public boolean getUseNames(){
		return useNames;
	}
}
