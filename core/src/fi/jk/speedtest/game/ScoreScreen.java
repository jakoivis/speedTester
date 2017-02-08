package fi.jk.speedtest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.StringBuilder;

import fi.jk.speedtest.SpeedTestGame;

public class ScoreScreen {

    private SpriteBatch batch;

    private BitmapFont font;

    private StringBuilder score = new StringBuilder("0000");

    private Texture texture;
    private Rectangle rect;

    public ScoreScreen(SpriteBatch batch, float scale) {

        this.batch = batch;

        font = new BitmapFont(Gdx.files.internal("Untitled-1-export.fnt"));

        texture = new Texture(Gdx.files.internal("screen.png"));

        float width = scale * texture.getWidth();
        float height = scale * texture.getHeight();
        float x = (SpeedTestGame.VIEWPORT_WIDTH - width) - 10;
        float y = (SpeedTestGame.VIEWPORT_HEIGHT - height) - 10;

        rect = new Rectangle(x, y, width, height);
    }

    public void setScore(int score) {

        this.score = new StringBuilder();

        if (score < 10) {
            this.score.append("000").append(score);

        } else if (score < 100) {
            this.score.append("00").append(score);

        } else if (score < 1000) {
            this.score.append("0").append(score);
        }
    }

    public void update() {

    }

    public void render() {

        StringBuilder str = new StringBuilder().append(score);

        batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
        font.draw(batch, str, rect.x + 35, rect.y + 190);
    }

    public void dispose() {

        font.dispose();
    }
}
