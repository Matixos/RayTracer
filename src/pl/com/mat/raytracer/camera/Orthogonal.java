package pl.com.mat.raytracer.camera;

import pl.com.mat.raytracer.Ray;
import pl.com.mat.raytracer.utils.Vector2;
import pl.com.mat.raytracer.utils.Vector3;

public class Orthogonal implements ICamera {

	private Vector3 eyePosition;
	private double angle;
	private Vector2 cameraSize;

	public Orthogonal(Vector3 eye, double angle, Vector2 size) {
		this.eyePosition = eye;
		this.angle = angle;
		this.cameraSize = size;
	}

	public Vector3 getEyePosition() {
		return eyePosition;
	}

	public void setEyePosition(Vector3 eyePosition) {
		this.eyePosition = eyePosition;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Vector2 getCameraSize() {
		return cameraSize;
	}

	public void setCameraSize(Vector2 cameraSize) {
		this.cameraSize = cameraSize;
	}

	@Override
	public Ray GetRayTo(Vector2 pictureLocation) {
		// Kierunek w którym skierowane s¹ wszystkie promienie wychodz¹ce z kamery.
		// Otrzymany prostymi funkcjami trygonometrycznymi.
		Vector3 direction = new Vector3(Math.sin(angle), 0, Math.cos(angle));
		// Kierunek promienia zawsze musi byæ znormalizowany.
		direction = direction.normalized();
		// Jak bardzo pocz¹tek promienia jest oddalony od po³o¿enia kamery
		Vector2 offsetFromCenter = new Vector2(pictureLocation.getX()
				* cameraSize.getX(), pictureLocation.getY() * cameraSize.getY());
		// Obliczenie finalnego po³o¿enia kamery, rówie¿ proste funkcje trygonometryczne.
		Vector3 position = new Vector3(eyePosition.getX()
				+ offsetFromCenter.getX() * Math.cos(angle), eyePosition.getY()
				+ offsetFromCenter.getY(), eyePosition.getZ()
				+ offsetFromCenter.getX() * Math.sin(angle));
		return new Ray(position, direction);
	}

}