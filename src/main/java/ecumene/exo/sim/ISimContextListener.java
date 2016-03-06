package ecumene.exo.sim;

/** Represents a global context change function to listen to */
public interface ISimContextListener {
	/**
	 * Called when the context has been changed
	 * @param context The new context
     */
	public void onContextChanged(SimContext context);
}
