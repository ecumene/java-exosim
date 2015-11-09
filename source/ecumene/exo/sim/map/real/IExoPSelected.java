package ecumene.exo.sim.map.real;

public interface IExoPSelected {
	public void onSelected(ExoRMapRenderer renderer);
	public void onDeselected(ExoRMapRenderer renderer, RObject selected);
}
