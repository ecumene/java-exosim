package ecumene.exo.sim.abstractions.surface.feature.height;

public class HeightMap {
    public float[][] elevation; // In XY form

    public HeightMap(){}

    public float[][] getElevation() {
        return elevation;
    }

    public HeightMap setElevation(float[][] elevations, boolean flip){
        if(flip) {
            elevation = new float[elevations.length][elevations[0].length];
            for(int x = 0; x < elevations.length; x++)
                for(int y = 0; y < elevations[x].length; y++)
                    this.elevation[y][x] = elevations[x][y];
        }else this.elevation = elevations;
        return this;
    }
}
