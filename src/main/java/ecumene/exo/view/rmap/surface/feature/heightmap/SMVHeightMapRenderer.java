package ecumene.exo.view.rmap.surface.feature.heightmap;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureLayer;
import ecumene.exo.sim.abstractions.surface.feature.height.ExoSHeightLayer;
import ecumene.exo.sim.common.map.heightmap.HeightMapBI;
import ecumene.exo.view.rmap.surface.JSMVSurfaceRenderer;
import ecumene.exo.view.rmap.surface.feature.IFeatureLayerRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SMVHeightMapRenderer implements IFeatureLayerRenderer {
    @Override
    public void renderLayer(JSMVSurfaceRenderer renderer, Graphics g, ExoSFeatureLayer layer) {
        if(layer instanceof ExoSHeightLayer){
            ExoSHeightLayer heightLayer = (ExoSHeightLayer) layer;
            HeightMapBI biRenderer = new HeightMapBI(heightLayer.getHeightMap(),
                    // Converting (-1 to 1) range to (0 and 1)
                    (elevation -> new Color((clamp(elevation, -1,1) + 1) / 2, (clamp(elevation, -1,1) + 1) / 2, (clamp(elevation, -1,1) + 1) / 2)),
                    BufferedImage.TYPE_INT_RGB);
            try {
                g.drawImage((BufferedImage) biRenderer.call(), (int) renderer.getNavigation().x, (int) renderer.getNavigation().y,
                        (int) (heightLayer.getHeightMap().elevation.width  * renderer.getNavigation().z),
                        (int) (heightLayer.getHeightMap().elevation.height * renderer.getNavigation().z), null);
            } catch(Exception e){
                ExoRuntime.INSTANCE.getExceptionListener().exceptionThrown(e);
            }
        }
    }

    private static float clamp(float x, float min, float max){
        if(x > min && x < max) return x;
        else return Math.min(max, Math.max(min, x));
    }
}
