package pl.com.mat.raytracer;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pl.com.mat.raytracer.camera.ICamera;
import pl.com.mat.raytracer.camera.ThinLens;
import pl.com.mat.raytracer.geom.Plane;
import pl.com.mat.raytracer.geom.Sphere;
import pl.com.mat.raytracer.light.Light;
import pl.com.mat.raytracer.material.IMaterial;
import pl.com.mat.raytracer.material.Reflective;
import pl.com.mat.raytracer.sampling.Jittered;
import pl.com.mat.raytracer.sampling.Regular;
import pl.com.mat.raytracer.sampling.Sampler;
import pl.com.mat.raytracer.sampling.SquareDistributor;
import pl.com.mat.raytracer.utils.ColorRGB;
import pl.com.mat.raytracer.utils.Vector2;
import pl.com.mat.raytracer.utils.Vector3;

public class MainProgramm {

	public static void main(String[] args) {
		int sampleCt = 64;   // przy perspective ok jest 9
		
		World world = new World(ColorRGB.WHITE);
		
		IMaterial redMat = new Reflective(ColorRGB.RED, 0.4, 1, 300, 0.6);
		IMaterial greenMat = new Reflective(ColorRGB.GREEN, 0.4, 1, 300, 0.6);
		IMaterial blueMat = new Reflective(ColorRGB.BLUE, 0.4, 1, 300, 0.6);
		IMaterial grayMat = new Reflective(ColorRGB.GRAY, 0.4, 1, 300, 0.6);
		
		world.add(new Sphere(new Vector3(-4, 0, 0), 2, redMat));
		world.add(new Sphere(new Vector3(4, 2, 6), 3, greenMat));
		world.add(new Sphere(new Vector3(0, 0, 3), 2, blueMat));
		world.add(new Plane(new Vector3(0, -2, 0), new Vector3(0, 1, 0), grayMat));
		
		world.addLight(new Light(new Vector3(0, 5, -5), ColorRGB.WHITE));    // punktowe proste
		
		//Sampler areaLightSampler = new Sampler(new Jittered(sampleCt, 0), new SquareDistributor(), sampleCt, 97);
		//world.addLight(new Light(new Vector3(6, 2, 0), ColorRGB.WHITE, areaLightSampler, 2)); // promieñ ost arg
		
		//ICamera camera = new Orthogonal(new Vector(0, 0, -5), 0, new Vector2(5, 5));
		/*ICamera camera = new Perspective(new Vector3(0, 1, -8), new Vector3(0, 0, 0),
				new Vector3(0, -1, 0), new Vector2(1, 1), 1);*/      // pamiêtaj o skali - V2
		
		ICamera camera = new ThinLens(new Vector3(0, 1, -8), new Vector3(0, 0, 0),
				new Vector3(0, -1, 0), new Vector2(1, 1), 1, new Sampler(new Jittered(sampleCt, 8), new SquareDistributor(), 
						sampleCt, 1), 0.65, 7);
		
		Raytracer tracer = new Raytracer(5);

		long time = System.currentTimeMillis();
		// Raytracing!
		Sampler antiAlias = new Sampler(new Regular(), new SquareDistributor(), sampleCt, 1); // na razie wystarczy jeden set
		BufferedImage image = tracer.raytrace(world, camera, new Dimension(1200, 1200), antiAlias);

		File outputFile = new File("raytraced.png");
		try {
			ImageIO.write(image, "PNG", outputFile);
		} catch (IOException e) {}
		
		System.out.println("Czas: " + (System.currentTimeMillis() - time));
		
		System.out.println("Done");
	}
}