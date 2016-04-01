package ecumene.exo.sim.abstractions.surface.feature.height;

import ecumene.exo.sim.map.heightmap.channel.HeightChannel;

public class HeightMap {
    public HeightChannel elevation; // In XY form

    public HeightMap(HeightChannel channel){this.elevation = channel;}

    public HeightChannel getElevation() {
        return elevation;
    }

}
