package ecumene.exo.view.fbd;

import java.awt.*;

@FunctionalInterface
public interface IFreeBodyShape {
    public void render(Graphics g, FreeBody body);
}
