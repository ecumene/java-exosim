package ecumene.exo.view.rmap.surface.feature;

import ecumene.exo.view.rmap.surface.SMVSurfaceRendererConfig;
import ecumene.exo.view.rmap.surface.feature.heightmap.SMVHeightMapRenderer;

public class SMVGenericConfig extends SMVSurfaceRendererConfig {
    public SMVGenericConfig(){
        this.getLayerRenderers().add(new SMVHeightMapRenderer());
    }
}
