package ecumene.exo.view.fbd;

import ecumene.exo.sim.common.physics.instant.InsFBody;

import java.awt.*;

@FunctionalInterface
public interface IFreeBodyShape {
    public void render(Graphics g, InsFBody body);
}
