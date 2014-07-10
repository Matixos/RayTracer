package pl.com.mat.raytracer.core.sampling;

import pl.com.mat.raytracer.core.utils.Vector2;

public class Regular implements ISampleGenerator {
	
	@Override
	public Vector2[] sample(int sampleCt) {
		int sampleRow = (int)Math.sqrt(sampleCt);
		Vector2[] result = new Vector2[sampleRow * sampleRow];
		for (int x = 0; x < sampleRow; x++)
			for (int y = 0; y < sampleRow; y++) {
				double fracX = (x + 0.5) / sampleRow;
				double fracY = (y + 0.5) / sampleRow;
				result[x * sampleRow + y] = new Vector2(fracX, fracY);
			}
		
		return result;
	}

}