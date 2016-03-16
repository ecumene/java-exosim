package ecumene.exo.sim.util;

public class OpenSimplexLoop {
   private OpenSimplexNoise noise;
   private int sizex, sizey;
   
   public OpenSimplexLoop(OpenSimplexNoise noise, int sizex, int sizey) {
      this.noise = noise;
      this.sizex = sizex;
      this.sizey = sizey;
   }
   
   public double loopedNoise(double radx, double rady, double interpx, double interpy){
      double s = (radx) / sizex;
      double t = (rady) / sizey;
      
      double x1 = -interpx, x2 = interpx;
      double y1 = -interpy, y2 = interpy;
      
      double dx = x2 - x1;
      double dy = y2 - y1;
      
      double x = x1 + Math.cos(s * 2 * Math.PI) * dx / (2 * Math.PI);
      double y = y1 + Math.cos(t * 2 * Math.PI) * dy / (2 * Math.PI);
      double z = x1 + Math.sin(s * 2 * Math.PI) * dx / (2 * Math.PI);
      double w = y1 + Math.sin(t * 2 * Math.PI) * dy / (2 * Math.PI);
      
      return noise.eval(x, y, z, w);
   }
}