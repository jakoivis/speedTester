package fi.jk.speedtest.game;

public interface IGameAnalyzerCallback {

    void onGameStart();

    void onGameEnd();

    void onScoreChange(int score);
}
