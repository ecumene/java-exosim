package ecumene.exo.view.runtime;

import java.beans.ExceptionListener;

import ecumene.exo.runtime.viewer.IViewerTag;

public class RuntimeManagerTag implements IViewerTag {

	@Override
	public String getIdentifier() {
		return "Runtime Analyzer";
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		return new RuntimeManager(id, listener);
	}
	
}
