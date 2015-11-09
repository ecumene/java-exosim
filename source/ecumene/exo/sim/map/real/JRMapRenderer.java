package ecumene.exo.sim.map.real;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.joml.Vector2f;

public class JRMapRenderer extends JPanel {
	
	private RMap pMap;
	
	public JRMapRenderer(RMap pMap) {
		this.pMap = pMap;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(0, 255, 0));
		g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g.drawLine(getWidth(), getHeight() / 2, 0, getHeight() / 2);
		for(int i = 0; i < pMap.map.length; i++){
			Vector2f pos = new Vector2f(pMap.map[i].position);
			pos.x += (getWidth() / 2);
			pos.y *= -1;
			pos.y += (getHeight() / 2);

			g.drawLine((int) (pos.x), (int) (pos.y - 2), (int) (pos.x), (int) (pos.y + 2));
			g.drawLine((int) (pos.x + 2), (int) (pos.y), (int) (pos.x - 2), (int) (pos.y));
			g.drawString(pMap.map[i].object.getClass().getSimpleName(), (int)pos.x + 6, (int)pos.y + 4);
		}
	}
}
