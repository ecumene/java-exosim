package ecumene.exo.view.rmap.surface.feature;

import ecumene.exo.sim.abstractions.surface.ExoSFeatureFilter;
import ecumene.exo.view.rmap.surface.JSMVSurfaceRenderer;

import java.awt.*;

public interface IFeatureFilterRenderer {
    public void renderFilter(JSMVSurfaceRenderer renderer, Graphics2D g, ExoSFeatureFilter filter);
}
