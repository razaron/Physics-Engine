import java.awt.geom.Point2D;

import org.lwjgl.opengl.GL11;


public class AABB {
	protected Point2D.Double p1;
	protected Point2D.Double p2;
	protected double minX, maxX, minY, maxY;
	
	public AABB(Point2D.Double p1, Point2D.Double p2){
		this.p1=p1;
		this.p2=p2;
	}
	
	public boolean compareAABB(AABB aabb){
		boolean temp=false;
		boolean temp1=true;
		boolean temp2=true;
		
		
		if((p1.x >= aabb.p2.x || p2.x <= aabb.p1.x) || (p1.y >= aabb.p2.y || p2.y <= aabb.p1.y)){
			temp1=false;
		}
		
		if((aabb.p1.x >= p2.x || aabb.p2.x <= p1.x) || (aabb.p1.y >= p2.y || aabb.p2.y <= p1.y)){
			temp2=false;
		}
		
		if(temp1 && temp2){
			temp=true;
		}
		
		
		return temp;
	}
	
	public void transformAABB(Vertex[] vertex){
		double x=vertex[0].pos.x;
		double y=vertex[0].pos.y;
		
		for(int i=0;i<vertex.length;i++){
			if(vertex[i].pos.x<x)
				x=vertex[i].pos.x;
			if(vertex[i].pos.y<y)
				y=vertex[i].pos.y;
		}
		
		p1= new Point2D.Double(x, y);
		
		for(int i=0;i<vertex.length;i++){
			if(vertex[i].pos.x>x)
				x=vertex[i].pos.x;
			if(vertex[i].pos.y>y)
				y=vertex[i].pos.y;
		}
		p2= new Point2D.Double(x, y);		
	}
	
	public void draw(boolean coll){
		if(!coll)
			GL11.glColor3d(0 /255, 255 /255, 0 /255);
		else
			GL11.glColor3d(255 /255, 0 /255, 0 /255);
		
		GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glVertex2d(p1.x, p1.y);
			GL11.glVertex2d(p2.x, p1.y);
			GL11.glVertex2d(p2.x, p2.y);
			GL11.glVertex2d(p1.x, p2.y);
		GL11.glEnd();
	}
}
