package ecumene.exo.sim.map.heightmap;

import java.awt.*;

@FunctionalInterface
public interface IHeightMapBIColorMethod {
    public Color getColor(float elevation);
}
