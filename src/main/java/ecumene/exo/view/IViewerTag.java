package ecumene.exo.view;

import java.beans.ExceptionListener;

public interface IViewerTag {
	public String getIdentifier();
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable;
}
