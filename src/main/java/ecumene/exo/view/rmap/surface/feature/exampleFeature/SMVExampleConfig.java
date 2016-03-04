package ecumene.exo.view.rmap.surface.feature.exampleFeature;

import ecumene.exo.view.rmap.surface.SMVSurfaceRendererConfig;

public class SMVExampleConfig extends SMVSurfaceRendererConfig {
    public SMVExampleConfig(){
        this.getFeatureRenderers().add(new ExampleFeatureRenderer());
    }
}
