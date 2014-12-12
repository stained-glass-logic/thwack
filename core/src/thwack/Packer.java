package thwack;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class Packer {
	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.pot = true;
		settings.stripWhitespaceX = true;
		settings.stripWhitespaceY = true;
		settings.combineSubdirectories = true;
		TexturePacker.process(settings, "C:/Users/super radish/git/thwack/desktop/assets/Saw Rat", "C:/Users/super radish/git/thwack/desktop/assets/Saw Rat-Packed", "Saw Rat");
	}
}
