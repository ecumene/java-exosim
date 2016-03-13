package ecumene.exo.view.rmap.surface.feature;

import ecumene.exo.sim.abstractions.surface.ExoSFeatureLayer;
import ecumene.exo.view.rmap.surface.JSMVSurfaceRenderer;

import java.awt.*;

public interface IFeatureLayerRenderer {
    public void renderLayer(JSMVSurfaceRenderer renderer, Graphics g, ExoSFeatureLayer layer);
}
