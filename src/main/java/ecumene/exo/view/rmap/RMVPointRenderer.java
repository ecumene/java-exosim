package ecumene.exo.view.rmap;

import java.awt.Color;
import java.awt.Graphics2D;

import org.joml.Vector2f;

import ecumene.exo.sim.map.real.RPoint;

public class RMVPointRenderer {
	
	private JRMViewer parent;
	
	public RMVPointRenderer(JRMViewer parent) {
		this.parent = parent;
	}
	
	public void render(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos) {
		graphics.setColor(new Color(0, 255, 0));
		graphics.drawLine((int) (screenPos.x), (int) (screenPos.y - 2), (int) (screenPos.x), (int) (screenPos.y + 2));
		graphics.drawLine((int) (screenPos.x + 2), (int) (screenPos.y), (int) (screenPos.x - 2), (int) (screenPos.y));
		
		if(parent.useNames) graphics.drawString(point.getName(id), (int)screenPos.x + 6, (int)screenPos.y + 4);
	}
}
