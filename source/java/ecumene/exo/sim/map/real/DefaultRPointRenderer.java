package ecumene.exo.sim.map.real;

import java.awt.Color;
import java.awt.Graphics2D;

import org.joml.Vector2f;

public class DefaultRPointRenderer implements IRPointRenderer {
	
	private JRMapRenderer parent;
	
	public DefaultRPointRenderer(JRMapRenderer parent) {
		this.parent = parent;
	}
	
	@Override
	public void render(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos,
			Vector2f screenPos) {
		graphics.setColor(new Color(0, 255, 0));
		graphics.drawLine((int) (screenPos.x), (int) (screenPos.y - 2), (int) (screenPos.x), (int) (screenPos.y + 2));
		graphics.drawLine((int) (screenPos.x + 2), (int) (screenPos.y), (int) (screenPos.x - 2), (int) (screenPos.y));
		
		if(parent.useNames) graphics.drawString(point.object.getName(id), (int)screenPos.x + 6, (int)screenPos.y + 4);
	}
}
