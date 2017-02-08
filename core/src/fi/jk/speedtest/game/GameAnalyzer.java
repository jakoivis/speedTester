package fi.jk.speedtest.game;

import com.badlogic.gdx.utils.Array;

public class GameAnalyzer {

    private int score;
    private Array<Integer> queue;
    private boolean gameRunning = false;
    private IGameAnalyzerCallback callback;

    public int getScore() {
        return score;
    }

    public GameAnalyzer(IGameAnalyzerCallback callback) {

        this.callback = callback;
    }

    public void start() {

        score = 0;
        queue = new Array<Integer>();
        gameRunning = true;
        callback.onScoreChange(score);
        callback.onGameStart();
    }

    public void addToQueue(GameSequenceItem item) {

        if (item.isGame) {

            queue.add(item.index);

            if (queue.size > 10) {
                callback.onGameEnd();
                gameRunning = false;
            }
        }
    }

    public void analyzeUserInput(int buttonIndex) {

        if (!gameRunning) {
            return;
        }

        if (isInvalidUserInput(buttonIndex)) {
            callback.onGameEnd();
            gameRunning = false;

        } else {
            queue.removeIndex(0);
            score++;
            callback.onScoreChange(score);
        }
    }

    private boolean isInvalidUserInput(int buttonIndex) {

        return queue.size == 0 || buttonIndex != queue.first();
    }
}
