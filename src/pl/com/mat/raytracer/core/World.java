package pl.com.mat.raytracer.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.com.mat.raytracer.core.geom.GeometricObject;
import pl.com.mat.raytracer.core.light.Light;
import pl.com.mat.raytracer.core.utils.ColorRGB;
import pl.com.mat.raytracer.core.utils.Vector3;


public class World implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ColorRGB backgroundColor;
	private ArrayList<GeometricObject> objects;
	private ArrayList<Light> lights;

	public World(ColorRGB background) {
		this.backgroundColor = background;
		this.objects = new ArrayList<GeometricObject>();
		this.lights = new ArrayList<Light>();
	}

	public void add(GeometricObject obj) {
		objects.add(obj);
	}
	
	public void setObjects(ArrayList<GeometricObject> objects) {
		this.objects.clear();
		this.objects.addAll(objects);
	}
	
	public void addLight(Light light) {
		lights.add(light);
	}
	
	public List<Light> getLights() {
		return lights;
	}

	public ColorRGB getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(ColorRGB backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public HitInfo traceRay(Ray ray) {
		HitInfo result = new HitInfo();
		//Vector3 normal = null;
		double minimalDistance = Ray.Huge; 			// najbli¿sze trafienie
		double lastDistance = 0; 					// zmienna pomocnicza, ostatnia odleg³oœæ
		
		for (GeometricObject obj : objects) {
			if (obj.hitTest(ray)) { 						// jeœli najbli¿sze trafienie
				lastDistance = obj.getMinDistance();
				if (lastDistance < minimalDistance) {
					minimalDistance = lastDistance; 				// nowa najmniejsza odleg³oœæ
					result.setHitObject(obj); 					// trafiono obiekt
					result.setNormal(obj.getOutNormal());
				}
			}
		}
		
		if (result.getHitObject() != null) // jeœli trafiliœmy cokolwiek
		{
			result.setHitPoint(ray.getDirection().mult(minimalDistance).plus(ray.getOrigin()));
			result.setRay(ray);
			result.setWorld(this);
		}
		
		return result;
	}
	
	public boolean anyObstacleBetween(Vector3 pointA, Vector3 pointB) {
		// odleg³oœæ od cieniowanego punktu do œwiat³a
		Vector3 vectorAB = pointB.minus(pointA);
		double distAB = vectorAB.length();
		double currDistance = Ray.Huge;
		// promieñ (pó³prosta) z cieniowanego punktu w kierunku œwiat³a
		Ray ray = new Ray(pointA, vectorAB);
		
		for (GeometricObject obj: objects) {
			if (obj.hitTest(ray)) { 
				currDistance = obj.getMinDistance();
				if (currDistance < distAB)
					return true;
			}
		}
		// obiekt nie jest w cieniu
		return false;
	}

}
