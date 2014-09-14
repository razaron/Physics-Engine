import org.lwjgl.opengl.GL11;


public class Edge {
	protected Vertex v1, v2;
	protected double length;
	
	public Edge(Vertex va, Vertex vb){
		v1=va;
		v2=vb;
		length=Math.sqrt(Math.pow(vb.pos.x-va.pos.x, 2) + Math.pow(vb.pos.y-va.pos.y, 2));
	}
	
	public void constrain(){
		double diff = Math.sqrt(Math.pow(v2.pos.x-v1.pos.x, 2) + Math.pow(v2.pos.y-v1.pos.y, 2));
		double k = (length-diff)/length;
		double xDiff = v2.pos.x-v1.pos.x;
		double yDiff = v2.pos.y-v1.pos.y;
		
		if(length==0)
			k=1;
		
		v1.pos.x -= xDiff*k*0.5f;
		v1.pos.y -= yDiff*k*0.5f;
			
		v2.pos.x += xDiff*k*0.5f;
		v2.pos.y += yDiff*k*0.5f;
	
	}
	
	public void draw(boolean coll){
		if(!coll)
			GL11.glColor3f(1f, 1f, 1f);
		else
			GL11.glColor3f(1f, 0f, 0f);
		
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2d(v1.pos.x, v1.pos.y);
			GL11.glVertex2d(v2.pos.x, v2.pos.y);
		GL11.glEnd();
	}
	
}