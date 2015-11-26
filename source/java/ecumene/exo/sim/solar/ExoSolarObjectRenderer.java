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
		if(parent.getUseNames()) graphics.drawString(point.object.getName(id), (int)screenPos.x + 6, (int)screenPos.y + 4);
		
		if(point.getObject() instanceof IExoSolarObject){ // Is a solar object?		
			RObject object = point.getObject();
			for(int i = 0; i < ((IExoSolarObject) object).getDisplacements().size(); i++){
				renderDisplacement(false, ((IExoSolarObject) object).getDisplacements().get(i), graphics, screenPos);
			}
			
			renderDisplacement(true, new ESDisplacement(((IExoSolarObject) object).getVelocity(), "d", new Color(0, 255, 0)), graphics, screenPos);
			Vector2f position = ((IExoSolarObject) object).getPosition();
			float mass = ((IExoSolarObject) object).getMass();
			
			
//          yeah, no. making the solar objects ovals aint workn'.
//			graphics.setColor(new Color(255, 0, 0));
//			graphics.fillOval((int) screenPos.x, (int) screenPos.y, (int) (mass * 10), (int) (mass * 10));
		}
	}
	
	public void renderDisplacement(boolean distance, ESDisplacement displacement, Graphics2D graphics, Vector2f screenSpace){
		// boolean d = distance or displacement
		
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
