package ecumene.exo.sim.abstractions.surface.exampleFeature;

import ecumene.exo.sim.abstractions.surface.ExoSFeatureLayer;
import ecumene.exo.sim.map.real.RPoint;
import org.joml.Vector2f;

public class ExoSLExample extends ExoSFeatureLayer {
    public ExoSLExample(){
        this.features.add(new ExoSFExample(new RPoint("Don't test me boy! (1)", new Vector2f(0, 0))));
        this.features.add(new ExoSFExample(new RPoint("Don't test me boy! (2)", new Vector2f(0, 10))));
    }
}
