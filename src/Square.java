import org.lwjgl.opengl.GL11;


public class Square{
	
	Polygon poly;
	
	public Square(double x, double y, int rot, PhysicsSystem world){
		
		Vertex[] vertex = new Vertex[4];
		
		switch(rot){
		case(1):
			vertex[0]=new Vertex(x,y);
			vertex[1]=new Vertex(x+30,y);
			vertex[2]=new Vertex(x+30,y+30);
			vertex[3]=new Vertex(x,y+30);
			break;
		case(2):
			vertex[0]=new Vertex(x,y);
			vertex[1]=new Vertex(x,y+30);
			vertex[2]=new Vertex(x-30,y+30);
			vertex[3]=new Vertex(x-30,y);
			break;
		case(3):
			vertex[0]=new Vertex(x,y);
			vertex[1]=new Vertex(x-30,y);
			vertex[2]=new Vertex(x-30,y-30);
			vertex[3]=new Vertex(x,y-30);
			break;
		case(4):
			vertex[0]=new Vertex(x,y);
			vertex[1]=new Vertex(x,y-30);
			vertex[2]=new Vertex(x+30,y-30);
			vertex[3]=new Vertex(x+30,y);
			break;
		
		}
		
		
		
		for(int i=0;i<4;i++)
			vertex[i].acceleration.y=200;
		
		
		Edge[] edge= new Edge[4];
		edge[0]=new Edge(vertex[0],vertex[1]);
		edge[1]=new Edge(vertex[1],vertex[2]);
		edge[2]=new Edge(vertex[2],vertex[3]);
		edge[3]=new Edge(vertex[3],vertex[0]);
		
		
		Edge[] stick = new Edge[2];
		stick[0] = new Edge(vertex[0], vertex[2]);
		stick[1] = new Edge(vertex[1], vertex[3]);
		
		poly = new Polygon(vertex, edge, stick, 0.3);
		
		world.polygon.add(poly);
	}
	
	public static void ghost(int x, int y, int rot){
		GL11.glColor3d(0, 1, 0);
		
		GL11.glBegin(GL11.GL_LINE_LOOP);
		
		switch(rot){
		case(1):
			
			GL11.glVertex2d(x,y);
			GL11.glVertex2d(x+30,y);
			GL11.glVertex2d(x+30,y+30);
			GL11.glVertex2d(x,y+30);
			break;
			
		case(2):
			GL11.glVertex2d(x,y);
			GL11.glVertex2d(x,y+30);
			GL11.glVertex2d(x-30,y+30);
			GL11.glVertex2d(x-30,y);
			break;
			
		case(3):
			GL11.glVertex2d(x,y);
			GL11.glVertex2d(x-30,y);
			GL11.glVertex2d(x-30,y-30);
			GL11.glVertex2d(x,y-30);
			break;
			
		case(4):
			GL11.glVertex2d(x,y);
			GL11.glVertex2d(x,y-30);
			GL11.glVertex2d(x+30,y-30);
			GL11.glVertex2d(x+30,y);
			break;
		}
		
		GL11.glEnd();
	}
	
}
