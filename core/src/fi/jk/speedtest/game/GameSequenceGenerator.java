package fi.jk.speedtest.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameSequenceGenerator {

    private int numberOfButtons;
    private IGameSequenceGeneratorCallback callback;

    private Array<GameSequenceItem> buffer;
    private int bufferPosition = 0;

    // triggeringInterval and triggeringDuration are multiplied with game speed
    // gameSpeed is multiplied with gameSpeedFactor after each trigger
    private final int triggeringInterval = 1000;
    private final int triggeringDuration = 500;
    private final float initialGameSpeed = 1.0f;
    private final float gameSpeedFactor = 0.995f;
    private float gameSpeed;

    private boolean useSpeedDispersion = true;
    private int speedDispersionMin = -100;
    private int speedDispersionMax = 100;

    public GameSequenceGenerator(int numberOfButtons, IGameSequenceGeneratorCallback callback) {

        this.numberOfButtons = numberOfButtons;
        this.callback = callback;
    }

    public void update() {

        if (buffer == null || bufferPosition >= buffer.size-1) {
            return;
        }

        GameSequenceItem item = buffer.get(bufferPosition);

        if (TimeUtils.millis() > item.triggerTime) {

            bufferPosition ++;
            callback.onTrigger(item);

            if (bufferPosition >= buffer.size -1) {

                if (item.isGame){
                    // if the last buffer item is a game item, let's create ne new
                    // buffer to continue the game.
                    continueGame();

                } else {
                    startDemo();
                }

            }
        }
    }

    public void startGame() {

        gameSpeed = initialGameSpeed;
        bufferPosition = 0;
        buffer = new Array<GameSequenceItem>();
        buffer.addAll(createStartDemoSequence());
        buffer.addAll(createGameBuffer(buffer.peek().triggerTime));
    }

    public void continueGame() {

        bufferPosition = 0;
        buffer = new Array<GameSequenceItem>();
        buffer.addAll(createGameBuffer(TimeUtils.millis()));
    }

    public void startDemo() {

        bufferPosition = 0;
        buffer = new Array<GameSequenceItem>();
        buffer.addAll(createGameOverDemoSequence());
    }

    public Array<GameSequenceItem> createGameBuffer(long startTime) {

        long lastTriggerTime = startTime;
        Array<GameSequenceItem> sequence = new Array<GameSequenceItem>();

        for (int i = 0; i < 10; i++) {

            sequence.add(createRandomGameSequenceItem(lastTriggerTime, gameSpeed));

            gameSpeed *= gameSpeedFactor;
            lastTriggerTime = sequence.peek().triggerTime;
        }

        return sequence;
    }

    private GameSequenceItem createRandomGameSequenceItem(long lastTriggerTime, float gameSpeed) {

        long triggerTime = lastTriggerTime + Math.round(triggeringInterval * gameSpeed);
        int buttonIndex = MathUtils.random(numberOfButtons -1);
        int duration = Math.round(triggeringDuration * gameSpeed);

        if (useSpeedDispersion) {
            int dispersion = Math.round(MathUtils.random(speedDispersionMin, speedDispersionMax) * gameSpeed);
            triggerTime += dispersion;
        }

        return new GameSequenceItem(triggerTime, buttonIndex, duration, true);
    }

//    private Array<GameSequenceItem> createTriggerAllSequenceItemsInRow(long lastTriggerTime, float gameSpeed) {
//        long triggerTime = lastTriggerTime + Math.round(triggeringInterval * gameSpeed);
//        int buttonIndex = MathUtils.random(numberOfButtons -1);
//        int duration = Math.round(triggeringDuration * gameSpeed);
//        Array<GameSequenceItem> sequence = new Array<GameSequenceItem>();
//        return new GameSequenceItem(triggerTime, buttonIndex, duration, true);
//    }

    private Array<GameSequenceItem> createStartDemoSequence() {

        // wait 0.5seconds and trigger all the buttons for 1 second

        long triggerTime = TimeUtils.millis() + 500;
        int duration = 1000;
        Array<GameSequenceItem> sequence = new Array<GameSequenceItem>();

        for (int i = 0; i < numberOfButtons; i++) {
            sequence.add(new GameSequenceItem(triggerTime, i, duration, false));
        }

        // add 1 second pause
        sequence.add(new GameSequenceItem(triggerTime + 1000, -1, 0, false));

        return sequence;
    }

    private Array<GameSequenceItem> createGameOverDemoSequence() {

        long triggerTime = TimeUtils.millis() + 500;
        int duration = 500;
        Array<GameSequenceItem> sequence = new Array<GameSequenceItem>();

        for (int j = 0; j < 3; j++) {

            for (int i = 0; i < numberOfButtons; i++) {
                sequence.add(new GameSequenceItem(triggerTime, i, duration, false));
            }

            triggerTime += 1000;
        }

        for (int j = 0; j < 3; j++) {

            for (int i = 0; i < numberOfButtons; i++) {
                sequence.add(new GameSequenceItem(triggerTime, i, duration, false));
                triggerTime += 500;
            }
        }

        for (int j = 0; j < 3; j++) {

            for (int i = 0; i < numberOfButtons; i++) {
                sequence.add(new GameSequenceItem(triggerTime, i, 200, false));
                triggerTime += 200;
            }
        }

        for (int j = 0; j < 3; j++) {

            for (int i = 0; i < numberOfButtons; i++) {
                sequence.add(new GameSequenceItem(triggerTime, i, 100, false));
                triggerTime += 100;
            }
        }

        for (int j = 0; j < 8; j++) {

            for (int i = 0; i < numberOfButtons; i++) {
                sequence.add(new GameSequenceItem(triggerTime, i, 50, false));
            }

            triggerTime += 100;
        }

        sequence.add(new GameSequenceItem(triggerTime + 1000, -1, 0, false));

        return sequence;
    }
}