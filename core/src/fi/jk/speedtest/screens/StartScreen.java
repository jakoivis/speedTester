package fi.jk.speedtest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import fi.jk.speedtest.SpeedTestGame;

public class StartScreen implements Screen {

    private SpeedTestGame game;

    public StartScreen(SpeedTestGame game) {

        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
        }
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

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
