package ecumene.exo.view.rmap.planet;

import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.view.rmap.RMVPointRenderer;
import org.joml.Vector2f;

import java.awt.*;

// Now THAT's a mouthful!!!
public class RMVPlanetMoonObjectRenderer extends RMVPointRenderer {

    private JRMVPlanetRenderer parent;

    public RMVPlanetMoonObjectRenderer(JRMVPlanetRenderer parent) {
        super(parent);
        this.parent = parent;
    }

    @Override
    public void render(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos) {
        Color oldColor = graphics.getColor(); { // push color
            if(point.getName(id).equals("refPoint")) {
                graphics.setColor(new Color(255, 255, 255));
                graphics.drawLine((int) screenPos.x - 3, (int) screenPos.y, (int) screenPos.x + 3, (int) screenPos.y);
                graphics.drawLine((int) screenPos.x, (int) screenPos.y - 3, (int) screenPos.x, (int) screenPos.y + 3);
            }
        } graphics.setColor(oldColor); // pop color
    }
}
