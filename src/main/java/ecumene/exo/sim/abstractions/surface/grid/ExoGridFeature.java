package ecumene.exo.sim.abstractions.surface.grid;

import ecumene.exo.sim.abstractions.surface.ExoSFeature;
import ecumene.exo.sim.common.map.real.RPoint;
import org.joml.Vector2f;
import org.joml.Vector2i;

public abstract class ExoGridFeature extends ExoSFeature {
    private ExoGridLayer parent;
    private Vector2i     index;
    private float        scale;

    public ExoGridFeature(){
        index = new Vector2i(-1, -1);
        scale = -1;
    }

    public ExoGridFeature setParent(ExoGridLayer layer, Vector2i index){
        this.parent = layer;
        this.index  = index;
        this.scale  = layer.getScale();
        return this;
    }

    public float getScale() {
        return scale;
    }

    public Vector2i getIndex() {
        return index;
    }

    @Override
    public RPoint getRPoint() {
        return new RPoint(getName(), new Vector2f(index.x * scale, index.y * scale));
    }

    public abstract String getName();
}
