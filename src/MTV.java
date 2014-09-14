import java.awt.geom.Point2D;


public class MTV {
	protected double magnitude;
	protected Point2D.Double axis;
	protected Edge edge;
	protected boolean other; 
	
	public MTV(double magnitude, Point2D.Double axis, Edge edge, boolean other){
		this.magnitude=magnitude;
		this.axis=axis;
		this.edge=edge;
		this.other=other;
	}
}
