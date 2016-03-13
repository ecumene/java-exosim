package ecumene.exo.view.rmap.surface.feature;

import ecumene.exo.sim.abstractions.surface.ExoSFeature;
import ecumene.exo.view.rmap.surface.JSMVSurfaceRenderer;
import org.joml.Vector2f;

import java.awt.*;

public interface IFeatureRenderer {
    public void renderFeature(JSMVSurfaceRenderer renderer, Graphics2D g, ExoSFeature feature, Vector2f screenPosition);
}
