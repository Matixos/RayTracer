package pl.com.mat.raytracer.core.utils;

import java.io.Serializable;

public class Vector3 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public double x;
	public double y;
	public double z;

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public double lengthSq() {
		return x * x + y * y + z * z;
	}
	
	public Vector3 normalized() {  
		return this.div(this.length());  
	}
	
	public Vector3 plus(Vector3 vec) {
		return new Vector3(this.x + vec.x, this.y + vec.y, this.z + vec.z);
	}

	public Vector3 minus(Vector3 center) {
		return new Vector3(this.x - center.x, this.y - center.y, this.z - center.z);
	}
	
	public Vector3 mult(double val) {
		return new Vector3(this.x * val, this.y * val, this.z * val);
	}

	public Vector3 div(double val) {
		return new Vector3(this.x / val, this.y / val, this.z / val);
	}
	
	public double dot(Vector3 vec) {
		return (this.x * vec.x + this.y * vec.y + this.z * vec.z);
	}
	
	public Vector3 cross(Vector3 vec) {
		return new Vector3(this.y * vec.z - this.z * vec.y, this.z * vec.x
				- this.x * vec.z, this.x * vec.y - this.y * vec.x);
	}
	
	public Vector3 reflect(Vector3 normal) {
		double dot = normal.dot(this);
		return normal.mult(dot).mult(2).minus(this);
	}
	
	public Vector3 reverse() {
		return new Vector3(-this.x, -this.y, -this.z);
	}
	
}