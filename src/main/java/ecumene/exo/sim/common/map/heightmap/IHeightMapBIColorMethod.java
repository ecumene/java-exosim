package ecumene.exo.sim.common.map.heightmap;

import java.awt.*;

@FunctionalInterface
public interface IHeightMapBIColorMethod {
    public Color getColor(float elevation);
}
