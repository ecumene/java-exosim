package ecumene.exo.view.runtime;

import java.beans.ExceptionListener;

import ecumene.exo.runtime.viewer.IViewerTag;

import javax.swing.*;

public class RuntimeViewerTag implements IViewerTag {

	@Override
	public String getIdentifier() {
		return "Runtime Analyzer";
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		return new NeoRuntimeViewer(id, listener);
	}
	
}
