package ecumene.exo.sim.solar;

import java.awt.Color;
import java.awt.Graphics2D;

import org.joml.Vector2f;

import ecumene.exo.sim.map.real.IRPointRenderer;
import ecumene.exo.sim.map.real.RObject;
import ecumene.exo.sim.map.real.RPoint;

public class ExoSolarObjectRenderer implements IRPointRenderer {
	
	private JExoSolarRenderer parent;
	
	public ExoSolarObjectRenderer(JExoSolarRenderer parent) {
		this.parent = parent;
	}

	@Override
	public void render(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos) {
		Color oldColor = graphics.getColor(); { // push color
			if(point.getObject() instanceof IExoSolarObject){ // Is a solar object?
				RObject object = point.getObject();
	
				graphics.setColor(new Color(255, 0, 0));
				float massDiam = ((IExoSolarObject) object).getMass() * parent.navigation.z;
				graphics.fillOval((int) (screenPos.x - (massDiam / 2)), (int) (screenPos.y - (massDiam / 2)), 
				                  (int) (massDiam),                     (int) (massDiam));
				graphics.setColor(new Color(255, 255, 255));
				if(!parent.getUseNames()) graphics.drawString(point.object.getName(id), (int)screenPos.x + 6, (int)screenPos.y + 4);
	
				if(parent.getUseNames()){
					for(int i = 0; i < ((IExoSolarObject) object).getDisplacements().size(); i++){
						renderDisplacement(((IExoSolarObject) object).getDisplacements().get(i), graphics, screenPos);
					}
					Vector2f finalVelocity = new Vector2f(((IExoSolarObject) object).getVelocity());
					finalVelocity.add(((IExoSolarObject) object).getLastGravity());
					renderDisplacement(new ESDisplacement(finalVelocity, "^V", new Color(0, 255, 0)), graphics, screenPos);
				}
			}
		} graphics.setColor(oldColor); // pop color
	}
	
	public void renderDisplacement(ESDisplacement displacement, Graphics2D graphics, Vector2f screenSpace){
		Color oldColor = graphics.getColor(); { // push color
			Vector2f dV = new Vector2f(displacement.getDisplacement());
			dV.x *= parent.navigation.z;
			dV.y *= -parent.navigation.z;
			dV = dV.add(screenSpace);
			graphics.setColor(new Color(255, 255, 255));
			graphics.drawString(displacement.getIdentifier(), dV.x + 6, dV.y + 4);
			graphics.setColor(displacement.color);
			graphics.drawLine((int) (screenSpace.x), (int) (screenSpace.y), (int) (dV.x), (int) (dV.y));
		} graphics.setColor(oldColor); // pop color
	}
}
