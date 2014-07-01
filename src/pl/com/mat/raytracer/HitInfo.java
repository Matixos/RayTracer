package pl.com.mat.raytracer;

import pl.com.mat.raytracer.geom.GeometricObject;
import pl.com.mat.raytracer.utils.Vector3;

public class HitInfo {

	private GeometricObject hitObject;
	private World world;
	private Vector3 normal;
	private Vector3 hitPoint;
	private Ray ray;
	private int depth;
	
	public GeometricObject getHitObject() {
		return hitObject;
	}

	public void setHitObject(GeometricObject hitObject) {
		this.hitObject = hitObject;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Vector3 getNormal() {
		return normal;
	}

	public void setNormal(Vector3 normal) {
		this.normal = normal;
	}

	public Vector3 getHitPoint() {
		return hitPoint;
	}

	public void setHitPoint(Vector3 hitPoint) {
		this.hitPoint = hitPoint;
	}

	public Ray getRay() {
		return ray;
	}

	public void setRay(Ray ray) {
		this.ray = ray;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

}