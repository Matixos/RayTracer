package pl.com.mat.raytracer.core.geom;

import java.io.Serializable;

import pl.com.mat.raytracer.core.Ray;
import pl.com.mat.raytracer.core.material.IMaterial;
import pl.com.mat.raytracer.core.utils.Vector3;


public abstract class GeometricObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected IMaterial material;
	protected double minDistance;
	protected Vector3 outNormal;
	
	public abstract boolean hitTest(Ray ray);

	public double getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(double minDistance) {
		this.minDistance = minDistance;
	}

	public IMaterial getMaterial() {
		return material;
	}

	public void setMaterial(IMaterial material) {
		this.material = material;
	}

	public Vector3 getOutNormal() {
		return outNormal;
	}

	public void setOutNormal(Vector3 normal) {
		this.outNormal = normal;
	}

}