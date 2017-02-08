package fi.jk.speedtest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import fi.jk.speedtest.SpeedTestGame;

public class TopBg {

    private SpriteBatch batch;

    Texture texture;

    Rectangle rectangle;

    public TopBg(SpriteBatch batch, float scale) {

        this.batch = batch;

        texture = new Texture(Gdx.files.internal("topbg.png"));

        float width = scale * texture.getWidth();
        float height = scale * texture.getHeight();
        float y = SpeedTestGame.VIEWPORT_HEIGHT - (height - 125);

        rectangle = new Rectangle(-180, y, width, height);
    }

    public void update() {

    }

    public void render() {

        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void dispose() {

        texture.dispose();
    }
}
