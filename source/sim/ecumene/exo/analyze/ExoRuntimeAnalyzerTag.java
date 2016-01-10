package ecumene.exo.analyze;

import java.beans.ExceptionListener;

import ecumene.exo.sim.SimContext;
import ecumene.exo.view.IViewerTag;

public class ExoRuntimeAnalyzerTag implements IViewerTag {

	@Override
	public String getIdentifier() {
		return "Runtime Analyzer";
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		return new ExoRuntimeAnalyzer(id, listener, args);
	}
	
}
