package fi.jk.speedtest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import fi.jk.speedtest.SpeedTestGame;

public class ResetButton {

    private SpriteBatch batch;
    private IGameButtonCallback callback;

    private Texture texture;
    private Rectangle rectangle;

    public ResetButton(SpriteBatch batch, IGameButtonCallback callback, float scale) {

        this.batch = batch;
        this.callback = callback;

        texture = new Texture(Gdx.files.internal("resetbutton.png"));

        float height = texture.getHeight() * scale;
        float width = texture.getWidth() * scale;
        float y = (SpeedTestGame.VIEWPORT_HEIGHT - height) -50;
        rectangle = new Rectangle(10, y, width, height);
    }

    public void update(Vector3 touchPosition) {

        if (touchPosition != null && rectangle.contains(touchPosition.x, touchPosition.y)) {

            callback.onClick(-1);
        }
    }

    public void render() {

        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void dispose() {
        texture.dispose();
    }
}
