package ecumene.exo.view.rmap.surface.feature.exampleFeature;

import ecumene.exo.sim.abstractions.surface.ExoSFeature;
import ecumene.exo.view.rmap.surface.feature.IFeatureRenderer;
import org.joml.Vector2f;

import java.awt.*;

public class ExampleFeatureRenderer implements IFeatureRenderer {
    @Override
    public void renderFeature(Graphics2D g, ExoSFeature feature, Vector2f screenPosition) {
        g.drawString(feature.getRPoint().name, screenPosition.x, screenPosition.y);
    }
}
