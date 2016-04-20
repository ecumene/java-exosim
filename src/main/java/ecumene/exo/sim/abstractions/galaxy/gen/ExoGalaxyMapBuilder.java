package ecumene.exo.sim.abstractions.galaxy.gen;

import ecumene.exo.sim.abstractions.galaxy.ExoGCluster;
import ecumene.exo.sim.abstractions.galaxy.ExoGOrbiter;
import ecumene.exo.sim.abstractions.galaxy.ExoGSingularity;
import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.utils.OpenSimplexNoise;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class ExoGalaxyMapBuilder {
    private OpenSimplexNoise noise;
    private List<ExoGCluster> clusters;
    private ExoGSingularity   singularity;

    public ExoGalaxyMapBuilder(long seed){
        this.noise = new OpenSimplexNoise(seed);
        clusters = new ArrayList<>();
    }

    public ExoGalaxyMapBuilder addCluster(ExoGCluster cluster){
        clusters.add(cluster);
        return this;
    }

    public ExoGalaxyMapBuilder addCluster(String name, List<ExoGOrbiter> cluster){
        clusters.add(new ExoGCluster(name, cluster));
        return this;
    }

    public ExoGalaxyMapBuilder setSingularity(ExoGSingularity singularity){
        this.singularity = singularity;
        return this;
    }

    public ExoGalaxyMapBuilder genCluster(String name, int mass, float size, Vector2f minMaxOrbiterMass, Vector2f minMaxSpeed, Vector2f position){
        List<ExoGOrbiter> orbiters = new ArrayList<>();
        for(int i = 0; i < mass; i++){
            float orbiterMass = (float) (noise.eval(0, i) * minMaxOrbiterMass.y) + minMaxOrbiterMass.x;
            float orbiterAngle  =  (float)noise.eval(1, (i * 2) + 0) * 360f;
            float orbiterRadius = ((float)noise.eval(1, (i * 2) + 1) * size);
            Vector2f orbiterPosition = new Vector2f(orbiterRadius * (float)Math.cos(orbiterAngle),
                    orbiterRadius * (float)Math.sin(orbiterAngle));
            if(position.x != 0 && position.y != 0) orbiterPosition.add(position);
            float grav = ((6.67f * (float)Math.pow(10, -3)) * mass * singularity.getMass()) / (float) Math.pow(position.length(), 2) * 5;
            Vector2f velocity = new Vector2f(orbiterPosition).perpendicular().normalize().mul(grav * (float) (noise.eval(0, i) * minMaxSpeed.y) + minMaxSpeed.x);
            orbiters.add(new ExoGOrbiter(singularity, name + (" #" + i), orbiterMass, orbiterPosition, velocity));
        }
        addCluster(new ExoGCluster(name, orbiters));
        return this;
    }

    public ExoGalaxyMap build(){
        return new ExoGalaxyMap(singularity, clusters.toArray(new ExoGCluster[clusters.size()]));
    }

}
