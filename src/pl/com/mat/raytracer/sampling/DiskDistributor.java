package pl.com.mat.raytracer.sampling;

import pl.com.mat.raytracer.utils.Vector2;

public class DiskDistributor implements ISampleDistributor {

	@Override
	public Vector2 mapSample(Vector2 sample) {
		sample.setX(sample.getX() * 2 - 1);
		sample.setY(sample.getY() * 2 - 1);
		double r;
		double phi;
		if (sample.getX() > -sample.getY())
		if (sample.getX() > sample.getY()) { r = sample.getX(); phi = sample.getY() / sample.getX(); }
		else { r = sample.getY(); phi = 2 - sample.getX() / sample.getY(); }
		else
		if (sample.getX() < sample.getY()) { r = -sample.getX(); phi = 4 + sample.getY() / sample.getX(); }
		else { r = -sample.getY(); phi = 6 - sample.getX() / sample.getY(); }
		if (sample.getX() == 0 && sample.getY() == 0) { phi = 0; }
		phi *= Math.PI / 4;
		return new Vector2(
		r * Math.cos(phi),
		r * Math.sin(phi));
	}
	
}