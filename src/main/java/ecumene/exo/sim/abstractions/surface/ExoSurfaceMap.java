package ecumene.exo.sim.abstractions.surface;

import ecumene.exo.sim.ISimStepListener;
import ecumene.exo.sim.SimContext;

import java.util.ArrayList;
import java.util.List;

public class ExoSurfaceMap implements ISimStepListener {
    private List<ExoSFeatureLayer>  layers;
    private List<ExoSFeatureFilter> filters;

    public ExoSurfaceMap(List<ExoSFeatureLayer> layers, List<ExoSFeatureFilter> filters){
        this.layers  = layers;
        this.filters = filters;
    }

    public ExoSurfaceMap(){
        this(new ArrayList<ExoSFeatureLayer>(), new ArrayList<ExoSFeatureFilter>());
    }

    public List<ExoSFeatureLayer> getLayers(){
        return layers;
    }

    public List<ExoSFeatureFilter> getFilters(){
        return filters;
    }

    @Override
    public void onStep(SimContext context, int step) {
        for(ExoSFeatureFilter filter : filters)
            filter.onStep(context, step);
    }
}
