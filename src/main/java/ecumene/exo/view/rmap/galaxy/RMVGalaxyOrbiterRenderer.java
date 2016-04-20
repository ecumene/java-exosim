package ecumene.exo.view.rmap.galaxy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ecumene.exo.sim.abstractions.galaxy.ExoGCluster;
import ecumene.exo.sim.abstractions.galaxy.ExoGSingularity;
import org.joml.Vector2f;

import ecumene.exo.sim.abstractions.galaxy.ExoGOrbiter;
import ecumene.exo.sim.common.map.real.RPoint;
import ecumene.exo.view.rmap.RMVPointRenderer;

public class RMVGalaxyOrbiterRenderer extends RMVPointRenderer {

	private JRMVGalaxyRenderer parent;
	private Map<ExoGCluster, Color> colorMap;
	private Random random = new Random(System.currentTimeMillis());

	public RMVGalaxyOrbiterRenderer(JRMVGalaxyRenderer parent) {
		super(parent);
		this.parent = parent;
		this.colorMap = new HashMap<>();
	}
	
	@Override
	public void render(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos) {
		if(!(point instanceof ExoGSingularity)){ // Render all except for singularity
			Color oldColor = graphics.getColor(); { // push color
				if (!colorMap.containsKey(((ExoGOrbiter) point).getCluster()))
					colorMap.put(((ExoGOrbiter) point).getCluster(), new Color(
							(int) (random.nextFloat() * 255f),
							(int) (random.nextFloat() * 255f),
							(int) (random.nextFloat() * 255f)));
				graphics.setColor(colorMap.get(((ExoGOrbiter) point).getCluster()));

				graphics.drawLine((int) screenPos.x - 3, (int) screenPos.y,     (int) screenPos.x + 3, (int) screenPos.y);
				graphics.drawLine((int) screenPos.x,     (int) screenPos.y - 3, (int) screenPos.x,     (int) screenPos.y + 3);
				graphics.drawString(point.getName(id), screenPos.x + 10, screenPos.y  + 3);
			} graphics.setColor(oldColor); // pop color
		}
	}

}
