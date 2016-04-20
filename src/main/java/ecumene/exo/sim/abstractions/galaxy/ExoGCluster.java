package ecumene.exo.sim.abstractions.galaxy;

import java.util.List;

public class ExoGCluster {
    private String            name;
    private List<ExoGOrbiter> orbiters;

    public ExoGCluster(String name, List<ExoGOrbiter> orbiters){
        this.orbiters = orbiters;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExoGOrbiter> getOrbiters() {
        return orbiters;
    }
}
