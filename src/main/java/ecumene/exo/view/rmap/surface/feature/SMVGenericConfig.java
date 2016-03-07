package ecumene.exo.view.rmap.surface.feature;

import ecumene.exo.view.rmap.surface.SMVSurfaceRendererConfig;
import ecumene.exo.view.rmap.surface.feature.elevation.SElevationRenderer;

public class SMVGenericConfig extends SMVSurfaceRendererConfig {
    public SMVGenericConfig(){
        this.getFeatureRenderers().add(new SElevationRenderer());
    }
}
