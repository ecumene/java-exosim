package ecumene.exo.analyze;

import java.beans.ExceptionListener;

import ecumene.exo.IExoRunnableTag;
import ecumene.exo.sim.ESContext;

public class ExoRuntimeAnalyzerTag implements IExoRunnableTag {

	@Override
	public String getIdentifier() {
		return "Runtime Analyzer";
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		return new ExoRuntimeAnalyzer(id, listener, args);
	}
	
}
