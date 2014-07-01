package pl.com.mat.raytracer.camera;

import pl.com.mat.raytracer.Ray;
import pl.com.mat.raytracer.utils.Vector2;

public interface ICamera {

	Ray GetRayTo(Vector2 relativeLocation);
	
}