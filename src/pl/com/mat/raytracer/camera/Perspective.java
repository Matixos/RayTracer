package pl.com.mat.raytracer.camera;

import pl.com.mat.raytracer.Ray;
import pl.com.mat.raytracer.utils.Vector2;
import pl.com.mat.raytracer.utils.Vector3;

public class Perspective implements ICamera {
	
	private OrthonormalBase onb;
	private Vector3 origin;
	private Vector2 scale;
	private double distance;

	public Perspective(Vector3 origin, Vector3 lookAt, Vector3 up, Vector2 scale, double distance) {
		this.onb = new OrthonormalBase(origin.minus(lookAt), up);
		this.origin = origin;
		this.scale = scale;
		this.distance = distance;
	}
	
	@Override
	public Ray GetRayTo(Vector2 relLoc) {
		Vector2 vpLoc = new Vector2(relLoc.getX() * scale.getX(), relLoc.getY() * scale.getY());
		return new Ray(origin, RayDirection(vpLoc));
	}

	private Vector3 RayDirection(Vector2 v) {
		return onb.mult(new Vector3(v.getX(), v.getY(), -distance));
	}

}