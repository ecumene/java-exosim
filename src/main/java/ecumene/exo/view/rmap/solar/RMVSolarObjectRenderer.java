package ecumene.exo.view.rmap.solar;

import java.awt.Color;
import java.awt.Graphics2D;

import org.joml.Vector2f;

import ecumene.exo.sim.common.map.real.RPoint;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import ecumene.exo.view.rmap.RMVPointRenderer;

public class RMVSolarObjectRenderer extends RMVPointRenderer {
	
	private JRMVSolarRenderer parent;
	
	public RMVSolarObjectRenderer(JRMVSolarRenderer parent) {
		super(parent);
		this.parent = parent;
	}

	@Override
	public void render(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos) {
		Color oldColor = graphics.getColor(); { // push color
			if(point instanceof IExoSolarObject){ // Is a solar object?
				RPoint object = point;
	
				graphics.setColor(parent.getPrimaryColor());
				float massDiam = ((IExoSolarObject) object).getMass() * parent.navigation.z;
				graphics.fillOval((int) (screenPos.x - (massDiam / 2)), (int) (screenPos.y - (massDiam / 2)), 
				                  (int) (massDiam),                     (int) (massDiam));
				graphics.setColor(parent.getPrimaryColor());
				graphics.drawString(point.getName(id), (int)screenPos.x + massDiam + 4, (int)screenPos.y + 4);
				
				if(parent.getShowVectors()){
					Vector2f finalVelocity = new Vector2f(((IExoSolarObject) object).getVelocity());
					Color oldOldColor = graphics.getColor(); { // push color
						finalVelocity.x *= parent.navigation.z;
						finalVelocity.y *= -parent.navigation.z;
						finalVelocity = finalVelocity.add(screenPos);
						graphics.setColor(parent.getSecondaryColor());
						graphics.drawLine((int) (screenPos.x),     (int) (screenPos.y),
								          (int) (finalVelocity.x), (int) (finalVelocity.y));
					} graphics.setColor(oldOldColor); // pop color
				}
				if(parent.getShowMaterials()){
					Vector2f finalVelocity = new Vector2f(((IExoSolarObject) object).getVelocity());
					Color oldOldColor = graphics.getColor(); { // push color
						finalVelocity.x *= parent.navigation.z;
						finalVelocity.y *= -parent.navigation.z;
						finalVelocity = finalVelocity.add(screenPos);
						graphics.setColor(parent.getSecondaryColor());
						graphics.drawLine((int) (screenPos.x),     (int) (screenPos.y),
								(int) (finalVelocity.x), (int) (finalVelocity.y));
					} graphics.setColor(oldOldColor); // pop color
				}
			}
		} graphics.setColor(oldColor); // pop color
	}
}
