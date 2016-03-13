package ecumene.exo.sim.util.heightmap;

import ecumene.exo.sim.abstractions.surface.feature.height.HeightMap;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class HeightMapBI implements Callable {
    private IHeightMapBIColorMethod method;
    private HeightMap               map;
    private int                     color;

    public HeightMapBI(HeightMap map, IHeightMapBIColorMethod method, int biColor){
        this.method = method;
        this.map    = map;
        this.color  = biColor;
    }

    @Override
    public Object call() throws Exception {
        BufferedImage out = new BufferedImage(map.getElevation().width, map.getElevation().height, color);
        for(int x = 0; x < map.getElevation().width; x++)
            for(int y = 0; y < map.getElevation().height; y++) {
                out.setRGB(x, y, method.getColor(map.elevation.getPixel(x, y)).getRGB());
            }
        return out;
    }

    public IHeightMapBIColorMethod getMethod() {
        return method;
    }
}
