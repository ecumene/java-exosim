package ecumene.exo.sim.util.heightmap;

import java.awt.*;

@FunctionalInterface
public interface IHeightMapBIColorMethod {
    public Color getColor(float elevation);
}
