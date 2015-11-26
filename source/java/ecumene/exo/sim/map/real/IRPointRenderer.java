package ecumene.exo.sim.map.real;

import java.awt.Graphics2D;

import org.joml.Vector2f;

public interface IRPointRenderer {
	public void render(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos);
}
