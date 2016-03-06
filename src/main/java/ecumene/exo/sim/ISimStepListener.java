package ecumene.exo.sim;

/** Represents a global sim function to listen to */
public interface ISimStepListener {
	/**
	 * Called when the simulation was stepped
	 * @param context The context being simulated
	 * @param step    The delta of steps since the beginning
     */
	public void onStep(SimContext context, int step);
}
