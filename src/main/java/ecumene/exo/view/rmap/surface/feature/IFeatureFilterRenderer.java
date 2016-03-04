package ecumene.exo.view.rmap.surface.feature;

import ecumene.exo.sim.abstractions.surface.ExoSFeatureFilter;

import java.awt.*;

public interface IFeatureFilterRenderer {
    public void renderFilter(Graphics2D g, ExoSFeatureFilter filter);
}
