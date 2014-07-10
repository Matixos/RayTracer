package pl.com.mat.raytracer.core.camera;

import java.io.Serializable;

import pl.com.mat.raytracer.core.utils.Vector3;


public class OrthonormalBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
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