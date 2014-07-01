package pl.com.mat.raytracer.utils;


public class ColorRGB {

	public static final ColorRGB WHITE = new ColorRGB(1, 1, 1);
	public static final ColorRGB BLACK = new ColorRGB(0, 0, 0);
	public static final ColorRGB RED = new ColorRGB(1, 0, 0);
	public static final ColorRGB GREEN = new ColorRGB(0, 1, 0);
	public static final ColorRGB BLUE = new ColorRGB(0, 0, 1);
	public static final ColorRGB CYAN = new ColorRGB(0, 1, 1);
	public static final ColorRGB GRAY = new ColorRGB(0.5, 0.5, 0.5);

	private double r;
	private double g;
	private double b;

	public ColorRGB(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public ColorRGB plus(ColorRGB col) {
		return new ColorRGB(this.r + col.r, this.g + col.g, this.b + col.b);
	}

	public ColorRGB mult(double val) {
		return new ColorRGB(this.r * val, this.g * val, this.b * val);
	}

	public ColorRGB mult(ColorRGB col) {
		return new ColorRGB(this.r * col.r, this.g * col.g, this.b * col.b);
	}

	public ColorRGB div(double val) {
		return this.mult(1 / val);
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

}