package pl.com.mat.raytracer.camera;

import pl.com.mat.raytracer.utils.Vector3;

public class OrthonormalBase {

	private Vector3 u;
	private Vector3 v;
	private Vector3 w;
	
	public OrthonormalBase(Vector3 front, Vector3 up) {
		w = front;
		w = w.normalized();
		u = up.cross(w);
		u = u.normalized();
		v = w.cross(u);
	}
	
	public Vector3 mult(Vector3 v)
	{
		Vector3 xes = this.u.mult(v.getX());
		Vector3 yes = this.v.mult(v.getY());
		Vector3 zes = this.w.mult(v.getZ());
		return (xes.plus(yes).plus(zes));
	}

}