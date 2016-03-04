package ecumene.exo.sim.abstractions.surface.exampleFeature;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.abstractions.surface.ExoSFeature;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureFilter;

public class ExoSFilterExampleMoveRight extends ExoSFeatureFilter {
    public ExoSFilterExampleMoveRight(ExoSLExample exampleLayer){
        super(exampleLayer);
    }

    @Override
    public void onStep(SimContext context, int step) {
        for(ExoSFeature feature : getFilteredFeatures()){
            feature.getRPoint().getPosition().x += 1;
        }
    }
}
