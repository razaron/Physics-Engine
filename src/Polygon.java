import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Polygon {
	protected Vertex[] vertex;
	protected Edge[] edge;
	protected Edge[] stick;
	protected Point2D.Double center;
	
	protected Point2D.Double p1;
	protected Point2D.Double p2;
	protected AABB box;
	protected boolean aabbCollision;
	protected boolean polyCollision;
	
	double mu;
	
	public MTV mtv;
	
	
	public Polygon(Vertex[] vertex, Edge[] edge, Edge[] stick, double mu){
		
		this.vertex=vertex;
		this.edge=edge;
		this.stick=stick;
		this.mu=mu;
		
		//AABB CONSTRUCTOR
		double x=vertex[0].pos.x;
		double y=vertex[0].pos.x;
		
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
		
		box = new AABB(p1,p2);
		
		
		calcCenter();
	}
	
	public boolean collides(Polygon poly){
		boolean check = true;
		double temp=0;
		Projection p1;
		Projection p2;
		Point2D.Double axis;
		double minAxis=1000000000;
		
		
		mtv =  new MTV(1000000, null, null, false);
		
		for(int i=0;i<edge.length+poly.edge.length;i++){
			if(check){
				
				//CHECKS THIS SHAPE
				if(i<edge.length){
					double min=100000,max=-100000;
					double dx=edge[i].v2.pos.x - edge[i].v1.pos.x;
					double dy=edge[i].v2.pos.y - edge[i].v1.pos.y;
					
					double x=(dx)/Math.sqrt((dx*dx) + (dy*dy));
					double y=(dy)/Math.sqrt((dx*dx) + (dy*dy));
					axis = new Point2D.Double(y, -x);
					
					//project this shape
					for(int j=0;j<vertex.length;j++){
						temp= (axis.x*vertex[j].pos.x)+(axis.y*vertex[j].pos.y);
						if(temp<min){
							min=temp;
						}
						if(temp>max){
							max=temp;
						}
					}
					p1 = new Projection(min,max);
					
					
					min=100000; max=-100000;
					//project other shape
					for(int j=0;j<poly.vertex.length;j++){
						temp= (axis.x*poly.vertex[j].pos.x)+(axis.y*poly.vertex[j].pos.y);
						if(temp<min){
							min=temp;
						}
						if(temp>max){
							max=temp;
						}
					}
					p2 = new Projection(min,max);
					
					if(!p1.overlap(p2)){
						check=false;
						return false;
					}
					else if(p1.magnitude(p2)<minAxis){
						mtv = new MTV(p1.magnitude(p2), axis, edge[i], false);
						minAxis=p1.magnitude(p2);
					}
				}
				
				//CHECKS OTHER SHAPE
				else{
					double min=100000,max=-100000;
					double dx=poly.edge[i-edge.length].v2.pos.x - poly.edge[i-edge.length].v1.pos.x;
					double dy=poly.edge[i-edge.length].v2.pos.y - poly.edge[i-edge.length].v1.pos.y;
					
					double x=(dx)/Math.sqrt((dx*dx) + (dy*dy));
					double y=(dy)/Math.sqrt((dx*dx) + (dy*dy));
					axis = new Point2D.Double(y, -x);
					
					//project this shape
					for(int j=0;j<poly.vertex.length;j++){
						temp= (axis.x*poly.vertex[j].pos.x)+(axis.y*poly.vertex[j].pos.y);
						if(temp<min){
							min=temp;
						}
						if(temp>max){
							max=temp;
						}
					}
					p1 = new Projection(min,max);
					
					
					min=100000; max=-100000;
					//project other shape
					for(int j=0;j<vertex.length;j++){
						temp= (axis.x*vertex[j].pos.x)+(axis.y*vertex[j].pos.y);
						if(temp<min){
							min=temp;
						}
						if(temp>max){
							max=temp;
						}
					}
					p2 = new Projection(min,max);
					
					if(!p1.overlap(p2))
						check=false;
					else if(p1.magnitude(p2)<minAxis){
						mtv = new MTV(p1.magnitude(p2), axis, poly.edge[i-edge.length], true);
						minAxis=p1.magnitude(p2);
					}
				}
				
			}
		}
		
		return check;
	}
	
	public void translate(Polygon poly1, Polygon poly2){
		if(mtv.axis  == null) System.out.println("axis is NULL");
		
		
		Polygon p1, p2;
		Vertex v = null;
		double t=0;
		double m;
		double minD=1000000;
		
		if(mtv.other){
			p1=poly1;
			p2=poly2;
		}
		else{
			p1=poly2;
			p2=poly1;
		}
		
		for(int i=0; i<p1.vertex.length; i++){
			double disX= mtv.axis.x*(p1.vertex[i].pos.x-p2.center.x);
			double disY= mtv.axis.y*(p1.vertex[i].pos.y-p2.center.y);
			double dis= Math.sqrt((disX*disX) + (disY*disY));
			if(dis<minD){
				minD=dis;
				v=p1.vertex[i];
			}
		}
		
		
		if(Math.abs(mtv.edge.v2.pos.x-mtv.edge.v1.pos.x) > Math.abs(mtv.edge.v2.pos.y-mtv.edge.v1.pos.y)){
			t=(v.pos.x-mtv.edge.v1.pos.x)/(mtv.edge.v2.pos.x-mtv.edge.v1.pos.x);
		}
		else{
			t=(v.pos.y-mtv.edge.v1.pos.y)/(mtv.edge.v2.pos.y-mtv.edge.v1.pos.y);
		}
		
		v.pos.x += 0.5*mtv.axis.x*mtv.magnitude;
		v.pos.y += 0.5*mtv.axis.y*mtv.magnitude;
		
		mtv.edge.v1.pos.x -= (1-t)*0.5*mtv.axis.x*mtv.magnitude;
		mtv.edge.v1.pos.y -= (1-t)*0.5*mtv.axis.y*mtv.magnitude;
		
		mtv.edge.v2.pos.x -= t*0.5*mtv.axis.x*mtv.magnitude;
		mtv.edge.v2.pos.y -= t*0.5*mtv.axis.y*mtv.magnitude;
		
		
		Point2D.Double temp = new Point2D.Double(-mtv.axis.y, mtv.axis.x);
		double fric=p1.mu*p2.mu;
			
		
		double vdotaxis=(temp.x*v.pos.x)+(temp.y*v.pos.y);
		double udotaxis=(temp.x*v.oldpos.x)+(temp.y*v.oldpos.y);
		double v1dotaxis=(temp.x*mtv.edge.v1.pos.x)+(temp.y*mtv.edge.v1.pos.y);
		double u1dotaxis=(temp.x*mtv.edge.v1.oldpos.x)+(temp.y*mtv.edge.v1.oldpos.y);
		double v2dotaxis=(temp.x*mtv.edge.v2.pos.x)+(temp.y*mtv.edge.v2.pos.y);
		double u2dotaxis=(temp.x*mtv.edge.v2.oldpos.x)+(temp.y*mtv.edge.v2.oldpos.y);
		
		double vdiff=vdotaxis-udotaxis;
		double v1diff=v1dotaxis-u1dotaxis;
		double v2diff=v2dotaxis-u2dotaxis;
		
		if(vdiff>0){
			v.oldpos.x += fric*mtv.magnitude*temp.x;
			v.oldpos.y += fric*mtv.magnitude*temp.y;
		}
		else{
			v.oldpos.x -= fric*mtv.magnitude*temp.x;
			v.oldpos.y -= fric*mtv.magnitude*temp.y;
		}
		if(v1diff>0){
			mtv.edge.v1.oldpos.x += fric*mtv.magnitude*temp.x;
			mtv.edge.v1.oldpos.y += fric*mtv.magnitude*temp.y;
		}
		else{
			mtv.edge.v1.oldpos.x -= fric*mtv.magnitude*temp.x;
			mtv.edge.v1.oldpos.y -= fric*mtv.magnitude*temp.y;
		}
		if(v2diff>0){
			mtv.edge.v2.oldpos.x += fric*mtv.magnitude*temp.x;
			mtv.edge.v2.oldpos.y += fric*mtv.magnitude*temp.y;
		}
		else{
			mtv.edge.v2.oldpos.x -= fric*mtv.magnitude*temp.x;
			mtv.edge.v2.oldpos.y -= fric*mtv.magnitude*temp.y;
		}
		
	}
	
	
	public void updateVerlet(double t){
		for(int i=0;i<vertex.length;i++){
			vertex[i].updateVerlet(t);
		}
		
		box.transformAABB(vertex);
		calcCenter();
	}	
	
	public void updateConstraint() {
		if(edge!=null){
			for(int i=0;i<edge.length;i++){
				if(edge[i]!=null)
					edge[i].constrain();
			}
		}
		
		if(stick!=null){
			for(int i=0;i<stick.length;i++){
				if(stick[i]!=null)
					stick[i].constrain();
			}
		}
		
		calcCenter();
	}
	
	public void draw(){
		if(edge!=null){
			for(int i=0;i<edge.length;i++){
				if(edge[i]!=null)
					edge[i].draw(polyCollision);
			}
		}
		
		if(stick!=null){
			for(int i=0;i<stick.length;i++){
				if(stick[i]!=null)
					stick[i].draw(polyCollision);
			}
		}
		
		for(int i=0;i<vertex.length;i++)
			if(vertex[i]!=null)
				vertex[i].draw();
		
		//box.draw(aabbCollision);
	}
	
	public void calcCenter(){
		//CALCULATES CENTER
		double tempX=0, tempY=0;
		
		for(int i=0;i<vertex.length;i++){
			tempX+=vertex[i].pos.x;
			tempY+=vertex[i].pos.y;
		}
		tempX = tempX/vertex.length;
		tempY = tempY/vertex.length;
		center = new Point2D.Double(tempX, tempY);
	}
}
