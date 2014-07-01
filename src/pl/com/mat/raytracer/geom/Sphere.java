package pl.com.mat.raytracer.geom;

import pl.com.mat.raytracer.Ray;
import pl.com.mat.raytracer.material.IMaterial;
import pl.com.mat.raytracer.utils.Vector3;

public class Sphere extends GeometricObject {

	private Vector3 center;
	private double radius;
	
	public Sphere(Vector3 center, double radius, IMaterial matrial) {
		this.center = center;
		this.radius = radius;
		this.material = matrial;
	}

	@Override
	public boolean hitTest(Ray ray) {
		double t;
		Vector3 distance = ray.getOrigin().minus(center);

		double a = ray.getDirection().lengthSq();
		double b = (distance.mult(2)).dot(ray.getDirection());
		double c = distance.lengthSq() - radius * radius;
		double disc = b * b - 4 * a * c;
		if (disc < 0) {
			return false;
		}
		double discSq = Math.sqrt(disc);
		double denom = 2 * a;
		
		t = (-b - discSq) / denom;
		if (t < Ray.Epsilon) {
			t = (-b + discSq) / denom;
		}
		if (t < Ray.Epsilon) {
			return false;
		}
		
		Vector3 hitPoint = (ray.getDirection().mult(t).plus(ray.getOrigin()));
		outNormal = hitPoint.minus(center).normalized();
		minDistance = t;
		return true;
	}

	public Vector3 getCenter() {
		return center;
	}

	public void setCenter(Vector3 center) {
		this.center = center;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

}
