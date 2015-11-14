package ecumene.exo;

import java.beans.ExceptionListener;

public interface IExoRunnableTag {
	public String getIdentifier();
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable;
}
