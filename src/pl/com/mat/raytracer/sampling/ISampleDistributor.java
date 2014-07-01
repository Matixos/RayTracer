package pl.com.mat.raytracer.sampling;

import pl.com.mat.raytracer.utils.Vector2;

public interface ISampleDistributor {

	Vector2 mapSample(Vector2 sample);
	
}