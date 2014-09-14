import java.util.Vector;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;



public class PhysicsSystem {
	int width, height;
	
	int type=1, rot=1;
	
	boolean simulating;
	int timestep, iterations;
	int ticker;
	double t;
	
	
	
	Vector<Polygon> polygon = new Vector<Polygon>(0,1);
	Vector<Object> shape = new Vector<Object>(0,1);
	
	public PhysicsSystem(int width, int height, int timestep, int iterations){
		this.width=width;
		this.height=height;
		this.timestep=timestep;
		this.iterations=iterations;
		double temp=(double)timestep/1000; 
		t=temp*temp;
		
		for(int i=0;i<width/50; i++){
			//shape.add(new Rectangle(i*50, height-10, 1, this));
		}
	}
	
	public void physicsHandler(int delta){
		
		KeyboardHandler();
		MouseHandler();
		
		if(simulating)
			ticker+=delta;
		while(ticker>timestep){
			physics();
			ticker-=timestep;
		}
	}
	
	public void physics(){
		double dif=0;
		double rest=1;
		//VERLET UPDATER
		for(int i=0;i<polygon.capacity();i++)
			polygon.elementAt(i).updateVerlet(t);
		
		
		for(int i=0; i<iterations; i++){
			for(int j=0;j<polygon.capacity();j++){
				
				//BORDER COLLISION HANDLER
				for(int k=0; k<polygon.elementAt(j).vertex.length;k++){					
					if(polygon.elementAt(j).vertex[k].pos.y >= height){
						dif=polygon.elementAt(j).vertex[k].pos.y-polygon.elementAt(j).vertex[k].oldpos.y;
						//polygon.elementAt(j).vertex[k].oldpos.y = height + rest*dif;
						polygon.elementAt(j).vertex[k].pos.y=height;
						
					}
					
					if(polygon.elementAt(j).vertex[k].pos.x >= width){
						dif=polygon.elementAt(j).vertex[k].pos.x-polygon.elementAt(j).vertex[k].oldpos.x;
					
						//polygon.elementAt(j).vertex[k].oldpos.x = width + rest*dif;
						polygon.elementAt(j).vertex[k].pos.x = width;
					}
				
					if(polygon.elementAt(j).vertex[k].pos.x <= 0){
						dif=polygon.elementAt(j).vertex[k].pos.x-polygon.elementAt(j).vertex[k].oldpos.x;
						
						//polygon.elementAt(j).vertex[k].oldpos.x = 0 + rest*dif;
						polygon.elementAt(j).vertex[k].pos.x = 0;
					}
					
					if(polygon.elementAt(j).vertex[k].pos.y <= 0){
						dif=polygon.elementAt(j).vertex[k].pos.y-polygon.elementAt(j).vertex[k].oldpos.y;
						
						//polygon.elementAt(j).vertex[k].oldpos.y = 0 + rest*dif;
						polygon.elementAt(j).vertex[k].pos.y = 0;
					}
					
				}
				
				//COLLISION DETECTOR
				polygon.elementAt(j).aabbCollision=false;
				polygon.elementAt(j).polyCollision=false;
				for(int l=0;l<polygon.capacity();l++){
					if(l!=j){
						//CHECK AABB
						if(polygon.elementAt(j).box.compareAABB(polygon.elementAt(l).box)){
							polygon.elementAt(j).aabbCollision=true;
							//CHECK POLYGON
							if(polygon.elementAt(j).collides(polygon.elementAt(l))){
								if(polygon.elementAt(j).mtv.axis != null){
									polygon.elementAt(j).polyCollision=true;
									polygon.elementAt(j).translate(polygon.elementAt(j), polygon.elementAt(l));
								}
							}
						}
					}
				}
				
				//CONSTRAINT UPDATER
				polygon.elementAt(j).updateConstraint();	
			}
		}
	}
	
	public void renderer(){
		for(int i=0;i<polygon.capacity();i++){
			polygon.get(i).draw();
		}
		ghostHandler();
	}
	
	public void KeyboardHandler(){
		while(Keyboard.next()){
			if(Keyboard.getEventKey()==Keyboard.KEY_RETURN){
				if(Keyboard.getEventKeyState()){
					if(simulating)
						simulating=false;
					else
						simulating=true;
				}
			}
			
			if(Keyboard.getEventKey()==Keyboard.KEY_R){
				if(Keyboard.getEventKeyState()){
					polygon = new Vector<Polygon>(0,1);
					shape = new Vector<Object>(0,1);
					
					for(int i=0;i<width/50; i++){
						//shape.add(new Rectangle(i*50, height-10, 1, this));
					}
				}
			}
			
			if(Keyboard.getEventKey()==Keyboard.KEY_A){
				if(Keyboard.getEventKeyState()){
					if(rot==1)
						rot=4;
					else
						rot--;
				}
			}
			
			if(Keyboard.getEventKey()==Keyboard.KEY_D){
				if(Keyboard.getEventKeyState()){
					if(rot==4)
						rot=1;
					else
						rot++;
				}
			}
			
			if(Keyboard.getEventKey()==Keyboard.KEY_1){
				if(Keyboard.getEventKeyState()){
					type=1;
				}
			}
			
			if(Keyboard.getEventKey()==Keyboard.KEY_2){
				if(Keyboard.getEventKeyState()){
					type=2;
				}
			}
			
			if(Keyboard.getEventKey()==Keyboard.KEY_3){
				if(Keyboard.getEventKeyState()){
					type=3;
				}
			}
			
			if(Keyboard.getEventKey()==Keyboard.KEY_4){
				if(Keyboard.getEventKeyState()){
					type=4;
				}
			}
			
			if(Keyboard.getEventKey()==Keyboard.KEY_5){
				if(Keyboard.getEventKeyState()){
					type=5;
				}
			}
		}
		
		
	}
	
	public void MouseHandler(){
		while(Mouse.next()){
			if(Mouse.getEventButton()==0){
				if(Mouse.getEventButtonState()){
					double x=Mouse.getEventX();
					double y=height-Mouse.getEventY();
					
					switch(type){
					case(1):
						shape.add(new Square(x,y,rot,this));
						break;
					
					case(2):
						shape.add(new Triangle(x,y,rot,this));
						break;
					
					case(3):
						shape.add(new Rectangle(x,y,rot,this));
						break;
					}
					
					System.out.println("poly's "+ polygon.capacity());
				}
			}
		}
	}
	
	public void ghostHandler(){
		switch(type){
		case(1):
			Square.ghost(Mouse.getX(), height-Mouse.getY(), rot);
			break;
		
		case(2):
			Triangle.ghost(Mouse.getX(), height-Mouse.getY(), rot);
			break;
		
		case(3):
			Rectangle.ghost(Mouse.getX(), height-Mouse.getY(), rot);
			break;
		}
	}
}
