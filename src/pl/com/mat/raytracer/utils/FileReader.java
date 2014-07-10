package pl.com.mat.raytracer.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import pl.com.mat.raytracer.core.Raytracer;
import pl.com.mat.raytracer.core.World;
import pl.com.mat.raytracer.core.camera.ICamera;
import pl.com.mat.raytracer.core.camera.ThinLens;
import pl.com.mat.raytracer.core.geom.GeometricObject;
import pl.com.mat.raytracer.core.geom.Plane;
import pl.com.mat.raytracer.core.geom.Sphere;
import pl.com.mat.raytracer.core.material.Reflective;
import pl.com.mat.raytracer.core.sampling.Jittered;
import pl.com.mat.raytracer.core.sampling.Sampler;
import pl.com.mat.raytracer.core.sampling.SquareDistributor;
import pl.com.mat.raytracer.core.utils.ColorRGB;
import pl.com.mat.raytracer.core.utils.Vector2;
import pl.com.mat.raytracer.core.utils.Vector3;
import pl.com.mat.raytracer.trace.InputPack;


public class FileReader {
	
	public static InputPack readFile(String fileName) throws Exception {
		Scanner scan = new Scanner(new File(fileName));

		InputPack result = new InputPack();
		ArrayList<GeometricObject> geomList = new ArrayList<GeometricObject>();
		ICamera camera = null;
		ColorRGB bgColor = null;
		
		result.setDims(scan.nextInt());   // wymiary obrazu

		if (scan.next().equals("objects")) {
			String object = scan.next();
			while (!object.equals("camera")) {
				if (object.equals("Sphere")) {
					geomList.add(new Sphere(
							new Vector3(scan.nextInt(), scan.nextInt(), scan
									.nextInt()),
							scan.nextInt(),
							new Reflective((ColorRGB) ColorRGB.class.getField(
									scan.next()).get(bgColor), 0.4, 1, 300, 0.6)));
				} else {
					geomList.add(new Plane(
							new Vector3(scan.nextInt(), scan.nextInt(), scan
									.nextInt()),
							new Vector3(scan.nextInt(), scan.nextInt(), scan
									.nextInt()),
							new Reflective((ColorRGB) ColorRGB.class.getField(
									scan.next()).get(bgColor), 0.4, 1, 300, 0.6)));
				}

				object = scan.next();
			}

			camera = new ThinLens(new Vector3(scan.nextInt(), scan.nextInt(),
					scan.nextInt()), new Vector3(scan.nextInt(),
					scan.nextInt(), scan.nextInt()), new Vector3(0, -1, 0),
					new Vector2(1, 1), 1, new Sampler(new Jittered(
							Raytracer.sampleCt, 8), new SquareDistributor(),
							Raytracer.sampleCt, 1), 0.65, 7);
			bgColor = (ColorRGB) ColorRGB.class.getField(scan.next()).get(
					bgColor);
		}

		scan.close();
		
		World world = new World(bgColor);
        world.setObjects(geomList);
        result.setWorld(world);
        result.setCamera(camera);

		return result;
	}

}