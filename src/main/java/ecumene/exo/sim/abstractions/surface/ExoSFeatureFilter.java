package ecumene.exo.sim.abstractions.surface;

import ecumene.exo.sim.ISimStepListener;
import ecumene.exo.sim.SimContext;

import java.util.List;

public abstract class ExoSFeatureFilter implements ISimStepListener {
    protected ExoSFeatureLayer  layer;
    protected List<ExoSFeature> features;

    public ExoSFeatureFilter(ExoSFeatureLayer layer, List<ExoSFeature> features){
        this.layer    = layer;
        this.features = features;
    }

    public ExoSFeatureFilter(ExoSFeatureLayer layer){
        this.layer    = layer;
        this.features = null;
    }

    public ExoSFeatureLayer getLayer() {
        return layer;
    }

    public List<ExoSFeature> getFilteredFeatures() {
        return features == null ? layer.getFeatures() : features;
    }
}
