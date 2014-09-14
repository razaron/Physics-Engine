import org.lwjgl.opengl.GL11;


public class Triangle{
	
	Polygon poly;
	
	public Triangle(double x, double y, int rot, PhysicsSystem world){
		
		Vertex[] vertex = new Vertex[3];
		
		switch(rot){
		case(1):
			vertex[0]=new Vertex(x,y);
			vertex[1]=new Vertex(x+300,y+300);
			vertex[2]=new Vertex(x-300,y+300);
			break;
		case(2):
			vertex[0]=new Vertex(x,y);
			vertex[1]=new Vertex(x-30,y+30);
			vertex[2]=new Vertex(x-30,y-30);
			break;
		case(3):
			vertex[0]=new Vertex(x,y);
			vertex[1]=new Vertex(x-30,y-30);
			vertex[2]=new Vertex(x+30,y-30);
			break;
		case(4):
			vertex[0]=new Vertex(x,y);
			vertex[1]=new Vertex(x+30,y-30);
			vertex[2]=new Vertex(x+30,y+30);
			break;
		
		}
		
		
		
		for(int i=0;i<3;i++)
			vertex[i].acceleration.y=200;
		
		
		Edge[] edge= new Edge[3];
		edge[0]=new Edge(vertex[0],vertex[1]);
		edge[1]=new Edge(vertex[1],vertex[2]);
		edge[2]=new Edge(vertex[2],vertex[0]);
		
		
		Edge[] stick = null;
		
		poly = new Polygon(vertex, edge, stick, 1);
		
		world.polygon.add(poly);
	}
	
	public static void ghost(int x, int y, int rot){
		GL11.glColor3d(0, 1, 0);
		
		GL11.glBegin(GL11.GL_LINE_LOOP);
		
		switch(rot){
		case(1):
			
			GL11.glVertex2d(x,y);
			GL11.glVertex2d(x+300,y+300);
			GL11.glVertex2d(x-300,y+300);
			break;
			
		case(2):
			GL11.glVertex2d(x,y);
			GL11.glVertex2d(x-30,y+30);
			GL11.glVertex2d(x-30,y-30);
			break;
			
		case(3):
			GL11.glVertex2d(x,y);
			GL11.glVertex2d(x-30,y-30);
			GL11.glVertex2d(x+30,y-30);
			break;
			
		case(4):
			GL11.glVertex2d(x,y);
			GL11.glVertex2d(x+30,y-30);
			GL11.glVertex2d(x+30,y+30);
			break;
		}
		
		GL11.glEnd();
	}
	
}
