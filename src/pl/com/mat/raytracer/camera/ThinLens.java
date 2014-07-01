package pl.com.mat.raytracer.camera;

import pl.com.mat.raytracer.Ray;
import pl.com.mat.raytracer.sampling.Sampler;
import pl.com.mat.raytracer.utils.Vector2;
import pl.com.mat.raytracer.utils.Vector3;

public class ThinLens implements ICamera {
	
	private OrthonormalBase onb;
	private Vector3 origin;
	private Vector2 scale;
	private double distance;
	private Sampler distributor;
	private double lensRadius;
	private double focal;
	
	public ThinLens(Vector3 origin, Vector3 lookAt, Vector3 up, Vector2 scale, double distance, Sampler distributor,
			double lensRadius, double focal) {
		this.onb = new OrthonormalBase(origin.minus(lookAt), up);
		this.origin = origin;
		this.scale = scale;
		this.distance = distance;
		this.distributor = distributor;
		this.lensRadius = lensRadius;
		this.focal = focal;
	}
	
	@Override
	public Ray GetRayTo(Vector2 relativePosition) {
		Vector2 pixelPosition = new Vector2(relativePosition.getX() * scale.getX(), 
				relativePosition.getY() * scale.getY());
		Vector2 lensPoint = distributor.single().mult(lensRadius);
		Vector3 rayOrigin = origin.plus(onb.mult(new Vector3(lensPoint.getX(), lensPoint.getY(), 0)));
		
		return new Ray(rayOrigin, RayDirection(pixelPosition, lensPoint));
	}
	
	private Vector3 RayDirection(Vector2 pixelPosition, Vector2 lensPoint) {
		Vector2 p = pixelPosition.mult(focal).div(distance);
		return onb.mult(new Vector3(p.getX() - lensPoint.getX(), p.getY() - lensPoint.getY(), -focal));
	}
}