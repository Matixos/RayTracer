package pl.com.mat.raytracer.core.sampling;

import pl.com.mat.raytracer.core.utils.Vector2;

public interface ISampleDistributor {

	Vector2 mapSample(Vector2 sample);
	
}