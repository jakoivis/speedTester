package fi.jk.speedtest.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

public class GameButton {

    public Texture textureDark;
    public Texture textureLight;
    public Sound triggerSound;
    public Sound pressSound;
    public Rectangle rectangle;
    public long triggerStartTime;
    public long triggerDuration;
    public boolean pressed;

    private boolean isTriggered;
    private boolean justTriggered;
    private boolean justTriggeredEnd;
    private boolean previousTriggerState;
    private boolean justPressed;
    private boolean justReleased;
    private boolean previousPressedState;

    public GameButton(Texture textureDark,
                      Texture textureLight,
                      Sound triggerSound,
                      Sound pressSound,
                      Rectangle rectangle) {

        this.textureDark = textureDark;
        this.textureLight = textureLight;
        this.triggerSound = triggerSound;
        this.pressSound = pressSound;
        this.rectangle = rectangle;
    }

    public boolean isTouched(Vector3 touchPosition) {

        return rectangle.contains(touchPosition.x, touchPosition.y);
    }

    public void trigger(long milliseconds, boolean playSound) {

        triggerStartTime = TimeUtils.millis();
        triggerDuration = milliseconds;

        if (playSound) {
            triggerSound.play();
        }
    }

    public void update() {
        isTriggered = TimeUtils.millis() - triggerStartTime < triggerDuration;
        justTriggered = previousTriggerState != isTriggered && isTriggered == true;
        justTriggeredEnd = previousTriggerState != isTriggered && isTriggered == false;
        previousTriggerState = isTriggered;

        justPressed = previousPressedState != pressed && pressed == true;
        justReleased = previousPressedState != pressed && pressed == false;
        previousPressedState = pressed;

        if (justTriggeredEnd) {
            triggerSound.stop();
        }

        if (justPressed) {
            pressSound.play();
        }
    }

    public Texture getTexture() {

        if (isTriggered) {

            return textureLight;

        } else {

            return textureDark;
        }
    }

    public void dispose() {
        textureDark.dispose();
        textureLight.dispose();
        triggerSound.dispose();
        pressSound.dispose();
    }
}
