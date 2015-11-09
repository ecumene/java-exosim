package ecumene.exo.impl;

import java.beans.ExceptionListener;

import ecumene.exo.IExoRunnableTag;

public class ImplExoRunnableTag implements IExoRunnableTag {

	private String ident;
	
	public ImplExoRunnableTag(String identifier) {
		this.ident = identifier;
	}
	
	@Override
	public String getIdentifier() {
		return ident;
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) {
		return new PlaceHolderRunnable(id, listener, args);
	}
	
}
