package ecumene.exo.view.rmap.galaxy;

import java.awt.Color;
import java.awt.Graphics2D;

import org.joml.Vector2f;

import ecumene.exo.sim.abstractions.galaxy.ExoGOrbiter;
import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.view.rmap.RMVPointRenderer;

public class RMVGalaxyOrbiterRenderer extends RMVPointRenderer {

	private JRMVGalaxyRenderer parent;
	
	public RMVGalaxyOrbiterRenderer(JRMVGalaxyRenderer parent) {
		super(parent);
		this.parent = parent;
	}
	
	@Override
	public void render(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos) {
		if(point instanceof ExoGOrbiter){
			Color oldColor = graphics.getColor(); { // push color
				graphics.setColor(new Color(255, 128, 128));
				graphics.drawLine((int) screenPos.x - 3, (int) screenPos.y,     (int) screenPos.x + 3, (int) screenPos.y);
				graphics.drawLine((int) screenPos.x,     (int) screenPos.y - 3, (int) screenPos.x,     (int) screenPos.y + 3);
				graphics.drawString(point.getName(id), screenPos.x + 10, screenPos.y  + 3);
			} graphics.setColor(oldColor); // pop color
		}
	}

}
