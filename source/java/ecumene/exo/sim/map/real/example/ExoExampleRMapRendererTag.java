package ecumene.exo.sim.map.real.example;

import java.beans.ExceptionListener;

import org.joml.Vector2f;

import ecumene.exo.IExoRunnableTag;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RObject;
import ecumene.exo.sim.map.real.RPoint;

public class ExoExampleRMapRendererTag implements IExoRunnableTag {
	@Override
	public String getIdentifier() {
		return "Example, RMap renderer";
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		RMap map = new RMap(
				new RPoint(new RObject(), new Vector2f(20, 20)),
				new RPoint(new RObject(), new Vector2f(40, 40)),
				new RPoint(new RObject(), new Vector2f(60, 60)),
				new RPoint(new RObject(), new Vector2f(80, 80)));
		return new ExoExampleRMapRenderer(id, listener, map, args);
	}
	
}
