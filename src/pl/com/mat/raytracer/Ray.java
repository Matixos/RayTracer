package pl.com.mat.raytracer;

import pl.com.mat.raytracer.utils.Vector3;

public class Ray {

	public static final double Epsilon = 0.00001;
	public static final double Huge = Double.MAX_VALUE;
	
	private Vector3 origin;
	private Vector3 direction;
	
	public Ray(Vector3 origin, Vector3 direction) {
		this.origin = origin;
		this.direction = direction.normalized();
	}
	
	public Vector3 getOrigin() { 
		return origin;  
	}
	
	public Vector3 getDirection() { 
		return direction;  
	}
	
}