package ecumene.exo.view.rmap.surface.feature;

import ecumene.exo.sim.abstractions.surface.ExoSFeatureLayer;

import java.awt.*;

public interface IFeatureLayerRenderer {
    public void renderLayer(Graphics g, ExoSFeatureLayer layer);
}
