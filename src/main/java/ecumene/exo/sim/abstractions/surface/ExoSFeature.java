package ecumene.exo.sim.abstractions.surface;

import ecumene.exo.sim.common.map.real.RPoint;

public abstract class ExoSFeature {
    protected RPoint point;

    public ExoSFeature(){}

    public RPoint getRPoint(){
        return point;
    }
}
