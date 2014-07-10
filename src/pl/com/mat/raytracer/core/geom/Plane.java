package pl.com.mat.raytracer.core.geom;

import pl.com.mat.raytracer.core.Ray;
import pl.com.mat.raytracer.core.material.IMaterial;
import pl.com.mat.raytracer.core.utils.Vector3;

public class Plane extends GeometricObject {

	private static final long serialVersionUID = 1L;
	
	private Vector3 point; 			// Punkt przez kt�ry p�aszczyzna przechodzi
	private Vector3 normal; 		// Normalna do p�aszczyzny

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