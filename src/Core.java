import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class Core {
	
	PhysicsSystem world;
	
	int lastFrame, width=2000, height=600;

	public void Start(){
		
		try {
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		initGL();
		System.out.println(GL11.glGetString(GL11.GL_VERSION));
		boolean Close = Display.isCloseRequested();
		
		world = new PhysicsSystem(width, height, 10, 5);
		
		while(!Close){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Display.isCloseRequested())
				Close = true;
			
			
			int delta=getDelta();
			
			world.physicsHandler(delta);
			world.renderer();
			
			
			
			Display.update();
			//Display.sync(60);
		}
		
	}
	
	public int getDelta(){
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = (int) time;
		
		return delta;
	}
	
	public long getTime(){
		return (Sys.getTime()*1000)/Sys.getTimerResolution();
	}
	
	public void initGL(){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public static void main(String args[]){
		Core m = new Core();
		m.Start();
	}

	
}
