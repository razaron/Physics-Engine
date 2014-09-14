
public class Projection {
	protected double min;
	protected double max;
	
	protected int type=0;
	
	public Projection(double min, double max){
		this.min=min;
		this.max=max;
	}
	
	public boolean overlap(Projection p2){
		boolean temp=true;
		
		if(min>=p2.max || max<=p2.min)
			temp=false;
		
		return temp;
	}
	
	public double magnitude(Projection p2){
		double m=0;
		
		if(min>=p2.min){
			m=p2.max-min;
			type=0;
		}
		
		else{
			m=max-p2.min;
			type=1;
		}
		
		return m;
	}
}
