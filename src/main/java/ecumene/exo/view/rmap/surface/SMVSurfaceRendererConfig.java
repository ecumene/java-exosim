package ecumene.exo.view.rmap.surface;

import ecumene.exo.view.rmap.surface.feature.IFeatureFilterRenderer;
import ecumene.exo.view.rmap.surface.feature.IFeatureLayerRenderer;
import ecumene.exo.view.rmap.surface.feature.IFeatureRenderer;

import java.util.ArrayList;
import java.util.List;

public class SMVSurfaceRendererConfig {
    private List<IFeatureRenderer>       featureRenderers; // Callbacks for rendering features
    private List<IFeatureLayerRenderer>  layerRenderers;   // Callbacks for rendering layers
    private List<IFeatureFilterRenderer> filterRenderers;  // Callbacks for rendering filters

    public SMVSurfaceRendererConfig(){
        featureRenderers = new ArrayList<IFeatureRenderer>();
        layerRenderers   = new ArrayList<IFeatureLayerRenderer>();
        filterRenderers  = new ArrayList<IFeatureFilterRenderer>();
    }

    public List<IFeatureRenderer> getFeatureRenderers() {
        return featureRenderers;
    }

    public List<IFeatureLayerRenderer> getLayerRenderers() {
        return layerRenderers;
    }

    public List<IFeatureFilterRenderer> getFilterRenderers() {
        return filterRenderers;
    }
}
