package ecumene.exo;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.util.OpenSimplexNoise;
import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.abstractions.galaxy.gen.ExoGalaxyMapGen;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.sim.abstractions.planet.TrackingParameters;
import ecumene.exo.sim.abstractions.planet.gen.ExoPlanetMapBuilder;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.gen.ExoSolarMapBuilder;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureFilter;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureLayer;
import ecumene.exo.sim.abstractions.surface.ExoSurfaceMap;
import ecumene.exo.sim.abstractions.surface.feature.height.ExoSHeightLayer;
import ecumene.exo.sim.abstractions.surface.feature.height.HeightMap;
import ecumene.exo.sim.util.heightmap.channel.HeightChannel;
import ecumene.exo.sim.util.heightmap.random.NoiseChannel;
import ecumene.exo.sim.util.heightmap.random.TiledNoiseChannel;
import org.apache.commons.cli.ParseException;
import org.joml.Vector2f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        // In the future, all these builders will be accompanied by XML or .prop files containing
        // exact data on simulation stuffs. This will allow you to "build from file" and essentially
        // enter in a few values.
        // I was planning to make different files showing stuff from movies like interstellar and the martian
        // ~~ IT'S GONNA BE LIT ~~

        // I'll make a builder for this... eventually.
        ExoGalaxyMap galaxy = new ExoGalaxyMapGen(System.currentTimeMillis()).genGalaxy(1, 2, 1, 100, 400).getSource();

        ExoSolarMapBuilder solarBuilder = new ExoSolarMapBuilder(System.currentTimeMillis());
        solarBuilder.genObject(new Vector2f(4, 4), new Vector2f(0, 0), new Vector2f(0, 0)); // Generate sun
        solarBuilder.genObjectsOrbiting(9,     // Number of orbiters
                new Vector2f(0,0),             // Point to orbit
                new Vector2f(0.001f, 0.0015f), // Orbiter Masses
                new Vector2f(-500, 500),       // Orbiter Position (polar coordinate radius)
                new Vector2f(0, 360),          // Orbiter angle    (polar coordinate angle)
                new Vector2f(0.01f, 0.025f));  // Initial velocity (0 will cause the orbiters to go DIRECTLY towards the sun and die a horrible death)
        // solarBuilder.genObjects(9, new Vector2f(0.001f, 0.0015f), new Vector2f(-500, 500), new Vector2f(-0.25f, 0.25f)); // Generate planets
        ExoSolarMap solar = solarBuilder.build();

        //                                                          Set seed to current time
        ExoPlanetMapBuilder planetBuilder = new ExoPlanetMapBuilder(System.currentTimeMillis());
        planetBuilder.genPlanet(new Vector2f(2, 3)); // Generate planet within mass range of X and Y
        planetBuilder.genMoons(1,                    // Generate # of moons
                new Vector2f(0.5f, 1),               // Generate moon mass
                new Vector2f(50, 200),               // Generate moon diameter from planet
                new Vector2f(0, 360),                // Generate moon angle
                new Vector2f(-0.15f, 0.15f));        // Generate moon beginning velocity
        ExoPlanetMap planet = planetBuilder.build(); // Finish generating and save to exoplanet
        planet.getPlanet().setTracking(0, new TrackingParameters("0xFF00FF", 100, false));// tracking data for moons

        // This should be in a builder!! GIMMIE A MINUTE!
        List<ExoSFeatureLayer> featureLayers = new ArrayList<ExoSFeatureLayer>();
        List<ExoSFeatureFilter> featureFilters = new ArrayList<ExoSFeatureFilter>();

        //VoronoiPoint[] points = new VoronoiPoint[]{
        //        new VoronoiPoint(1.1f, new Vector2f(0.1f, 0.2f)),
        //        new VoronoiPoint(1.3f, new Vector2f(0.1f, 0.6f)),
        //        new VoronoiPoint(1.2f, new Vector2f(0.9f, 0.3f)),
        //        new VoronoiPoint(1.5f, new Vector2f(0.13f, 0.1f)),
        //};
        //HeightChannel channel = VoronoiWeightedWrapFactory.wrapDistance(1024, 1024, true, points);
        //Voronoi voronoi = new VoronoiEuclid(new Vector2i(1024, 1024), true, points);

        HeightChannel channel = new TiledNoiseChannel(1000, 500,
                                                      new OpenSimplexNoise(),
                                                      new Vector2f(1, 1));
        HeightMap heightMap = new HeightMap(channel);

        featureLayers.add(new ExoSHeightLayer(heightMap, 20));

        ExoSurfaceMap surface = new ExoSurfaceMap(featureLayers, featureFilters);

        // Finish generating, save to context for simulation
        SimContext context = new SimContext(galaxy, solar, planet, surface);

        ExoRuntime.INSTANCE = new ExoRuntime(args, context);
        ExoRuntime.INSTANCE.run();
        System.exit(0);
    }

}
