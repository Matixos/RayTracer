package pl.com.mat.raytracer.core.camera;

import java.io.Serializable;

import pl.com.mat.raytracer.core.Ray;
import pl.com.mat.raytracer.core.utils.Vector2;


public interface ICamera extends Serializable {

	Ray GetRayTo(Vector2 relativeLocation);
	
}