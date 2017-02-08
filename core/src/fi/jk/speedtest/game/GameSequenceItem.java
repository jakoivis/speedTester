package fi.jk.speedtest.game;

public class GameSequenceItem {

    public long triggerTime;
    public int index;
    public int duration;
    public boolean isGame;

    /**
     * @param triggerTime   Time in milliseconds when this is triggered
     * @param index         Index if the button that is triggered
     * @param duration      Duration in milliseconds on how long the trigger is
     * @param isGame        Whether this trigger item is a trigger for a game of a demo
     */
    public GameSequenceItem(long triggerTime, int index, int duration, boolean isGame) {

        this.triggerTime = triggerTime;
        this.index = index;
        this.duration = duration;
        this.isGame = isGame;
    }
}