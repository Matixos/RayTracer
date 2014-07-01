package pl.com.mat.raytracer.utils;

public class Vector2 {

	private double x;
	private double y;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
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
	
	public Vector2 mult(double val) {
		return new Vector2(this.x * val, this.y * val);
	}
	
	public Vector2 div(double val) {
		return new Vector2(this.x / val, this.y / val);
	}

}