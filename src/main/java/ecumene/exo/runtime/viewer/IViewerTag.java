package ecumene.exo.runtime.viewer;

import ecumene.exo.runtime.workspace.ExoWorkspace;

import java.beans.ExceptionListener;

public interface IViewerTag {
	public String getIdentifier();
	public void parseWorkspace(ExoWorkspace workspace) throws Throwable;
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable;
}
