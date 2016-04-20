package ecumene.exo;

import ecumene.exo.runtime.ExoArgParse;
import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.abstractions.galaxy.ExoGSingularity;
import ecumene.exo.sim.abstractions.galaxy.gen.ExoGalaxyMapBuilder;
import ecumene.exo.sim.abstractions.galaxy.io.ExoGalaxyMapParser;
import ecumene.exo.sim.abstractions.planet.io.ExoPlanetMapParser;
import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.io.ExoSolarMapParser;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureFilter;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureLayer;
import ecumene.exo.sim.abstractions.surface.ExoSurfaceMap;
import ecumene.exo.sim.abstractions.surface.feature.height.ExoSHeightLayer;
import ecumene.exo.sim.abstractions.surface.feature.height.HeightMap;
import ecumene.exo.sim.common.map.heightmap.channel.HeightChannel;
import ecumene.exo.sim.common.map.heightmap.random.TiledNoiseChannel;
import ecumene.exo.sim.common.map.heightmap.voronoi.VoronoiPoint;
import ecumene.exo.utils.OpenSimplexNoise;
import org.jdom2.input.SAXBuilder;
import org.joml.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Throwable {
        //ExoSolarMapBuilder solarBuilder = new ExoSolarMapBuilder(System.currentTimeMillis());
        //solarBuilder.genObject(new Vector2f(4, 4), new Vector2f(0, 0), new Vector2f(0, 0)); // Generate sun
        //solarBuilder.genObjectsOrbiting(9,     // Number of orbiters
        //        new Vector2f(0,0),             // Point to orbit
        //        new Vector2f(0.001f, 0.0015f), // Orbiter Masses
        //        new Vector2f(-500, 500),       // Orbiter Position (polar coordinate radius)
        //        new Vector2f(0, 360),          // Orbiter angle    (polar coordinate angle)
        //        new Vector2f(0.01f, 0.025f));  // Initial velocity (0 will cause the orbiters to go DIRECTLY towards the sun and die a horrible death)
        //ExoSolarMap solar = solarBuilder.build();
        //ExoPlanetMapBuilder planetBuilder = new ExoPlanetMapBuilder(System.currentTimeMillis());
        //planetBuilder.genPlanet(new Vector2f(50, 100)); // Generate planet within mass range of X and Y
        //planetBuilder.genMoons(4,                       // Generate # of moons
        //        new Vector2f(10f, 20f),                 // Generate moon mass
        //        new Vector2f(500,2000),                 // Generate moon diameter from planet
        //        new Vector2f(0, 360),                   // Generate moon angle
        //        new Vector2f(-0.01f, 0.01f));           // Generate moon beginning velocity
        //ExoPlanetMap planet = planetBuilder.build();    // Finish generating and save to exoplanet

        SplashScreen splash = SplashScreen.getSplashScreen();

        ExoGalaxyMapParser galaxyParse = new ExoGalaxyMapParser(new SAXBuilder().build(new File("examples/galactic-melt/metacluster-2.xml")).getRootElement());
        ExoSolarMapParser  solarParse  = new ExoSolarMapParser(new SAXBuilder().build(new File("examples/gliese581g/solar.xml")).getRootElement());
        ExoPlanetMapParser planetParse = new ExoPlanetMapParser(new SAXBuilder().build(new File("examples/gliese581g/planet.xml")).getRootElement());
        ExoGalaxyMap galaxy = galaxyParse.getGalaxyMap();
        ExoSolarMap solar   = solarParse.getSolarMap();
        ExoPlanetMap planet = planetParse.getPlanetMap();

        //planet.getPlanet().setTracking(0, new TrackingParameters("0xFF00FF", 100, true));// tracking data for moons

        // This should be in a builder!! GIMMIE A MINUTE!
        List<ExoSFeatureLayer> featureLayers = new ArrayList<ExoSFeatureLayer>();
        List<ExoSFeatureFilter> featureFilters = new ArrayList<ExoSFeatureFilter>();

        VoronoiPoint[] points = new VoronoiPoint[]{
                new VoronoiPoint(1.1f, new Vector2f(0.1f, 0.2f)),
                new VoronoiPoint(1.3f, new Vector2f(0.1f, 0.6f)),
                new VoronoiPoint(1.2f, new Vector2f(0.9f, 0.3f)),
                new VoronoiPoint(1.5f, new Vector2f(0.13f, 0.1f)),
        };
        //HeightChannel channel = VoronoiWeightedWrapFactory.wrapDistance(1024, 1024, true, points);
        //Voronoi voronoi = new VoronoiEuclid(new Vector2i(1024, 1024), true, points);
        HeightChannel channel = new TiledNoiseChannel(1000, 500,
                                                      new OpenSimplexNoise(),
                                                      new Vector2f(1, 1));
        HeightMap heightMap = new HeightMap(channel);
        featureLayers.add(new ExoSHeightLayer(heightMap, 20));
        ExoSurfaceMap surface = new ExoSurfaceMap(featureLayers, featureFilters);

        ExoArgParse commands = new ExoArgParse(args);

        // Finish generating, save to context for simulation
        SimContext context = new SimContext(galaxy, solar, planet, surface);

        if(splash != null) splash.close();

        ExoRuntime.INSTANCE = new ExoRuntime(commands, context);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            ExoRuntime.INSTANCE.getExceptionListener().exceptionThrown(e);
        }
        ExoRuntime.INSTANCE.run();
        System.exit(0);
    }

}
