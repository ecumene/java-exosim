package ecumene.exo.utils;

public class OpenSimplexNoiseRandom extends OpenSimplexNoise {
    private int itrx = 0, itry = 0, itrz = 0;

    public OpenSimplexNoiseRandom(long seed){
        super(seed);
    }

    public double nextDouble(){
        next();
        return eval(itrx, itry);
    }

    public float nextFloat(){
        next();
        return (float) eval(itrx, itry);
    }

    private void next(){
        itrx++;itry++;itrz++;
    }
}
