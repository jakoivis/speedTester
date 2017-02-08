package fi.jk.speedtest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import fi.jk.speedtest.*;

public class GameButtons {

    // To add new buttons or modify the order, change only this array
    private final Array<String> buttonTexturePaths = new Array<String>() {{
        add("button_dark_blue.png"); add("button_light_blue.png");
        add("button_dark_orange.png"); add("button_light_orange.png");
        add("button_dark_yellow.png"); add("button_light_yellow.png");
        add("button_dark_red.png"); add("button_light_red.png");
    }};

    private final Array<String> buttonSoundPaths = new Array<String>() {{
        add("button1.wav"); add("button2.wav"); add("button3.wav"); add("button4.wav");
    }};

    private final String buttonPressSoundPath = "press1.wav";

    private final String backgroundTexturePath = "button_background.png";

    private final int paddingBetween = 10;
    private final int paddingSides = 20;
    private final int numButtonStates = 2;

    private final int buttonCount;
    private final int buttonSize;
    private final float buttonScale;
    private final int onePixelSize;


    private SpriteBatch batch;
    private IGameButtonCallback callback;
    private Array<GameButton> buttons;
    private Texture backgroundTexture;
    private int pressedButtonIndex = -1;

    public float getScale() {
        return buttonScale;
    }

    public GameButtons(SpriteBatch batch, IGameButtonCallback callback) {

        this.batch = batch;
        this.callback = callback;

        buttonCount = buttonTexturePaths.size / numButtonStates;

        backgroundTexture = new Texture(Gdx.files.internal(backgroundTexturePath));

        Array<Texture> textures = new Array<Texture>();
        for (int i = 0; i < buttonCount; i++) {
            textures.add(new Texture(Gdx.files.internal(buttonTexturePaths.get(i * numButtonStates))));
            textures.add(new Texture(Gdx.files.internal(buttonTexturePaths.get(i * numButtonStates +1))));
        }

        buttonSize = (SpeedTestGame.VIEWPORT_WIDTH - (paddingSides * 2 + (buttonCount-1) * paddingBetween)) / buttonCount;
        buttonScale = buttonSize / backgroundTexture.getWidth();
        onePixelSize = - Math.round(buttonScale);

        buttons = new Array<GameButton>();
        for (int i = 0; i < buttonCount; i++) {
            buttons.add(new GameButton(
                    textures.get(i*numButtonStates),
                    textures.get(i*numButtonStates+1),
                    Gdx.audio.newSound(Gdx.files.internal(buttonSoundPaths.get(i))),
                    Gdx.audio.newSound(Gdx.files.internal(buttonPressSoundPath)),
                    new Rectangle(calculateButtonBgX(i), 10, buttonSize, buttonSize)
            ));
        }
    }

    private int calculateButtonBgX(int buttonIndex) {

        return paddingSides + (paddingBetween + buttonSize) * buttonIndex;
    }

    public void triggerButton(GameSequenceItem item) {

        if(item.index == -1) {
            return;
        }

        GameButton button = buttons.get(item.index);
        button.trigger(item.duration, item.isGame);
    }

    public void render() {

        GameButton button;
        Texture texture;
        Rectangle rectangle;

        float y;

        for (int i = 0; i < buttonCount; i++) {

            button = buttons.get(i);
            texture = button.getTexture();
            rectangle = button.rectangle;

            y = i == pressedButtonIndex ? rectangle.y + onePixelSize : rectangle.y;

            batch.draw(backgroundTexture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            batch.draw(texture, rectangle.x, y, rectangle.width, rectangle.height);
        }
    }

    public void update(Vector3 touchPosition) {

        GameButton button;

        for (int i = 0; i < buttons.size; i++) {

            buttons.get(i).update();
        }

//        handle pressed states

        if (touchPosition == null) {

            // reset press states if not being touched

            for (int i = 0; i < buttons.size; i++) {
                buttons.get(i).pressed = false;
            }

            pressedButtonIndex = -1;
            return;
        }

        for (int i = 0; i < buttons.size; i++) {

            button = buttons.get(i);

            if (button.isTouched(touchPosition)) {

                if(pressedButtonIndex != i) {

                    pressedButtonIndex = i;
                    button.pressed = true;
                    callback.onClick(i);
                }
            }
        }
    }

    public void dispose() {

        backgroundTexture.dispose();

        Iterator<GameButton> iterator = buttons.iterator();
        GameButton button;

        while (iterator.hasNext()) {
            button = iterator.next();
            button.dispose();
        }
    }


}
