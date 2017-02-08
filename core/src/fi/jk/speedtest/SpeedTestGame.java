package fi.jk.speedtest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fi.jk.speedtest.screens.StartScreen;

public class SpeedTestGame extends Game {

    public static int VIEWPORT_WIDTH = 800;
    public static int VIEWPORT_HEIGHT = 480;

	public SpriteBatch batch;
	public OrthographicCamera camera;
    private Viewport viewport;

	@Override
	public void create () {

		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
//        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);

		setScreen(new StartScreen(this));
	}

//    public void onResize(int width, int height) {
//        viewport.update(width, height);
//    }

	public void render () {

		Gdx.gl.glClearColor(0.231f, 0.203f, 0.215f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		super.render();
	}

	public void dispose () {

        getScreen().dispose();

		batch.dispose();
	}

    public Vector3 getUnprojectedTouchPos() {

        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);

        return touchPos;
    }
}
