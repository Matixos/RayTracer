package pl.com.mat.raytracer.geom;

import pl.com.mat.raytracer.Ray;
import pl.com.mat.raytracer.material.IMaterial;
import pl.com.mat.raytracer.utils.Vector3;

public class Plane extends GeometricObject {

	private Vector3 point; 		// Punkt przez który p³aszczyzna przechodzi
	private Vector3 normal; 		// Normalna do p³aszczyzny

	public Plane(Vector3 point, Vector3 normal, IMaterial material) {
		this.point = point;
		this.normal = normal;
		this.material = material;
	}

	@Override
	public boolean hitTest(Ray ray) {
		double t = (point.minus(ray.getOrigin())).dot(normal)
				/ ray.getDirection().dot(normal);
		if (t > Ray.Epsilon) {
			minDistance = t;
			outNormal = normal;
			return true;
		}
		return false;
	}

}