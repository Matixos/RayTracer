package pl.com.mat.raytracer.trace;

import java.io.Serializable;

import pl.com.mat.raytracer.core.World;
import pl.com.mat.raytracer.core.camera.ICamera;


public class InputPack implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private World world;
	private ICamera camera;
	
	private int dims;
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public ICamera getCamera() {
		return camera;
	}
	
	public void setCamera(ICamera camera) {
		this.camera = camera;
	}

	public int getDims() {
		return dims;
	}

	public void setDims(int dims) {
		this.dims = dims;
	}
	
}