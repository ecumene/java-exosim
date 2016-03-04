package ecumene.exo.sim.abstractions.surface;

import java.util.ArrayList;
import java.util.List;

public abstract class ExoSFeatureLayer {
    protected List<ExoSFeature> features;

    public ExoSFeatureLayer(){
        features = new ArrayList<ExoSFeature>();
    }

    public List<ExoSFeature> getFeatures(){
        return features;
    }
}
