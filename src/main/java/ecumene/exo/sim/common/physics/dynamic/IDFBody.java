package ecumene.exo.sim.common.physics.dynamic;

import ecumene.exo.sim.common.physics.IFBody;

import java.util.List;

public interface IDFBody extends IFBody {
    public List<Force> getForces();
}
