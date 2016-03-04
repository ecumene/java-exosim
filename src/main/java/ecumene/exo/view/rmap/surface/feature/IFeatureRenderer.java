package ecumene.exo.view.rmap.surface.feature;

import ecumene.exo.sim.abstractions.surface.ExoSFeature;
import org.joml.Vector2f;

import java.awt.*;

public interface IFeatureRenderer {
    //                                                           Pos. on screen
    public void renderFeature(Graphics2D g, ExoSFeature feature, Vector2f screenPosition);
}
