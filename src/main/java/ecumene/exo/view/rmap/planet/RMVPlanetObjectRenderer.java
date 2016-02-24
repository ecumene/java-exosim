package ecumene.exo.view.rmap.planet;

import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.planet.ExoPlanet;
import ecumene.exo.sim.solar.IExoSolarObject;
import ecumene.exo.view.rmap.RMVPointRenderer;
import ecumene.exo.view.rmap.solar.JRMVSolarRenderer;
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
                if(!point.getName(id).equals("refPoint")) {
                    graphics.setColor(new Color(255, 0, 0));
                    float massDiam = parent.getMap().getObjects().get(id).getMass() * parent.navigation.z;
                    graphics.fillOval((int) (screenPos.x - (massDiam / 2)), (int) (screenPos.y - (massDiam / 2)),
                            (int) (massDiam), (int) (massDiam));
                    graphics.setColor(new Color(255, 255, 255));
                    graphics.drawString(point.getName(id), (int) screenPos.x + 6, (int) screenPos.y + 4);
                }
        } graphics.setColor(oldColor); // pop color
    }
}
