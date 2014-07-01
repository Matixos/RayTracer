package pl.com.mat.raytracer.sampling;

import java.util.Random;

import pl.com.mat.raytracer.utils.Vector2;

public class Jittered implements ISampleGenerator {
	
	private Random r;
	
	public Jittered(int sampleCt, int seed) {
		this.r = new Random(seed);
	}
	
	@Override
	public Vector2[] sample(int count) {
		int sampleRow = (int)Math.sqrt(count);
		Vector2[] result = new Vector2[sampleRow * sampleRow];
		for (int x = 0; x < sampleRow; x++)
			for (int y = 0; y < sampleRow; y++) {
				double fracX = (x + r.nextDouble()) / sampleRow;
				double fracY = (y + r.nextDouble()) / sampleRow;
				result[x * sampleRow + y] = new Vector2(fracX, fracY);
			}
		
		return result;
	}

}