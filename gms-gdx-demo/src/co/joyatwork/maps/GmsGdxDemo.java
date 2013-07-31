package co.joyatwork.maps;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class GmsGdxDemo implements ApplicationListener {
	private PerspectiveCamera cam;
	private Model model;
	private ModelInstance instance;
	private ModelBatch modelBatch;
	private Lights lights;
	private CameraInputController camController;
	private float time = 0;
	
	@Override
	public void create() {		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// looking through the top-right corner of a cube into its center
        cam.position.set(3f, 3f, 3f);
        cam.lookAt(0,0,0); // [0,0,0] is center of world
        //<<
        cam.near = 0.1f; //  distance to near clipping plane
        cam.far = 100f; // distance to far clipping plane
        cam.update();
        
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(1f, 1f, 1f, 
            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
            Usage.Position | Usage.Normal);
        
        instance = new ModelInstance(model, 0f, 0f, 0f);
        
        modelBatch = new ModelBatch();
        
        lights = new Lights();
        lights.ambientLight.set(0.4f, 0.4f, 0.4f, 1f);
        lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		model.dispose();
	}

	@Override
	public void render() {		
		camController.update();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
 
        modelBatch.begin(cam);
        modelBatch.render(instance, lights);
        modelBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
