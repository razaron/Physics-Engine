import java.awt.geom.Point2D;

import org.lwjgl.opengl.GL11;


public class Vertex {
	protected Point2D.Double pos;
	protected Point2D.Double oldpos;
	protected Point2D.Double acceleration;
	protected Point2D.Double temp;
	
	protected boolean isSelected;
	
	public Vertex(double x, double y){
		pos = new Point2D.Double(x,y);
		oldpos = new Point2D.Double(x,y);
		temp = new Point2D.Double(x,y);
		acceleration = new Point2D.Double(0,0);
	}
	
	public void updateVerlet(double t){
		if(temp  == null) System.out.println("Temp is NULL");
        if(pos  == null) System.out.println("Pos is NULL");
        if(oldpos  == null) System.out.println("OldPos is NULL");
        if(acceleration  == null) System.out.println("Acceleration is NULL");
        
		temp.x=pos.x;
		temp.y=pos.y;
		
		pos.x += (pos.x - oldpos.x) + (acceleration.x*t);
		pos.y += (pos.y - oldpos.y) + (acceleration.y*t);
		
		oldpos.x=temp.x;
		oldpos.y=temp.y;
	}
	
	public void draw(){
		if(isSelected)
			GL11.glColor3f(0f, 0f, 1f);
		
		else 
			GL11.glColor3f(1f, 0f, 0f);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2d(pos.x-1, pos.y-1);
			GL11.glVertex2d(pos.x-1, pos.y+1);
			GL11.glVertex2d(pos.x+1, pos.y+1);
			GL11.glVertex2d(pos.x+1, pos.y-1);
		GL11.glEnd();
	}
}
