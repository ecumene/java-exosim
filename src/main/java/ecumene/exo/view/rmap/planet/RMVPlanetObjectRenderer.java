package ecumene.exo.view.rmap.planet;

import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.view.rmap.RMVPointRenderer;
import org.joml.Vector2f;

import java.awt.*;

public class RMVPlanetObjectRenderer extends RMVPointRenderer {

    private JRMVPlanetRenderer parent;

    public RMVPlanetObjectRenderer(JRMVPlanetRenderer parent) {
        super(parent);
        this.parent = parent;
    }

    @Override
    public void render(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos) {
            Color oldColor = graphics.getColor(); { // push color
                if(!point.getName(id).toUpperCase().contains("REFPOINT")) {
                    graphics.setColor(parent.getPrimaryColor());
                    float massDiam = parent.getMap().getObjects().get(id).getMass() * parent.navigation.z;
                    graphics.fillOval((int) (screenPos.x - (massDiam / 2)), (int) (screenPos.y - (massDiam / 2)),
                            (int) (massDiam), (int) (massDiam));
                    graphics.setColor(parent.getSecondaryColor());
                    graphics.drawString(point.getName(id), (int) screenPos.x + massDiam + 4, (int) screenPos.y + 4);
                }
        } graphics.setColor(oldColor); // pop color
    }
}
