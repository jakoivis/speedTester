package fi.jk.speedtest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fi.jk.speedtest.SpeedTestGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SpeedTestGame.VIEWPORT_WIDTH;
		config.height = SpeedTestGame.VIEWPORT_HEIGHT;

		new LwjglApplication(new SpeedTestGame(), config);
	}
}
