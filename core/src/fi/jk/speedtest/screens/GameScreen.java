package fi.jk.speedtest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;

import fi.jk.speedtest.game.GameAnalyzer;
import fi.jk.speedtest.game.GameButtons;
import fi.jk.speedtest.game.GameSequenceGenerator;
import fi.jk.speedtest.game.GameSequenceItem;
import fi.jk.speedtest.game.IGameAnalyzerCallback;
import fi.jk.speedtest.game.IGameButtonCallback;
import fi.jk.speedtest.game.IGameSequenceGeneratorCallback;
import fi.jk.speedtest.SpeedTestGame;
import fi.jk.speedtest.game.ResetButton;
import fi.jk.speedtest.game.ScoreScreen;
import fi.jk.speedtest.game.TopBg;


public class GameScreen implements Screen {

    private SpeedTestGame game;

    private GameButtons buttons;
    private GameSequenceGenerator generator;
    private GameAnalyzer analyzer;
    private ResetButton resetButton;
    private ScoreScreen scoreScreen;
    private TopBg topbg;

    private Vector3 touchPosition;

    private Sound startSound;
    private Sound endSound;
    private Texture topBgTexture;

    private float graphicsScale;


    public GameScreen(SpeedTestGame game) {

        this.game = game;

        startSound = Gdx.audio.newSound(Gdx.files.internal("start1.wav"));
        endSound = Gdx.audio.newSound(Gdx.files.internal("end.wav"));
        topBgTexture = new Texture(Gdx.files.internal("topbg.png"));

        analyzer = new GameAnalyzer(new IGameAnalyzerCallback() {
            @Override
            public void onGameStart() {
                startSound.play();
                generator.startGame();
            }

            @Override
            public void onGameEnd() {
                endSound.play();
                generator.startDemo();
            }

            @Override
            public void onScoreChange(int score) {
                scoreScreen.setScore(score);
            }
        });

        buttons = new GameButtons(game.batch, new IGameButtonCallback() {
            @Override
            public void onClick(int buttonIndex) {
                analyzer.analyzeUserInput(buttonIndex);
            }
        });

        graphicsScale = buttons.getScale();

        generator = new GameSequenceGenerator(4, new IGameSequenceGeneratorCallback() {
            @Override
            public void onTrigger(GameSequenceItem item) {
                buttons.triggerButton(item);
                analyzer.addToQueue(item);
            }
        });

        resetButton = new ResetButton(game.batch, new IGameButtonCallback() {
            @Override
            public void onClick(int buttonIndex) {
                analyzer.start();
            }
        }, graphicsScale);

        scoreScreen = new ScoreScreen(game.batch, graphicsScale);
        topbg = new TopBg(game.batch, graphicsScale);

        generator.startDemo();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        updateInput();

        topbg.update();
        generator.update();
        buttons.update(touchPosition);
        resetButton.update(touchPosition);
        scoreScreen.update();

        game.batch.begin();
        topbg.render();
        buttons.render();
        resetButton.render();
        scoreScreen.render();
        game.batch.end();
    }

    private void updateInput() {

        if (Gdx.input.isTouched()) {
            touchPosition = game.getUnprojectedTouchPos();

        } else {
            touchPosition = null;
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
        buttons.dispose();
        resetButton.dispose();
        scoreScreen.dispose();
        topbg.dispose();
    }
}
