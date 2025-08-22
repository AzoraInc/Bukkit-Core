package com.zerosio.utility;

import java.util.ArrayList;
import java.util.List;

public class ScoreAnimation {

	public static List<String> createScoreAnimation(String input) {
		List<String> frames = new ArrayList<>();

		for (int i = 1; i <= input.length(); i++) {
			String prefix = input.substring(0, i);
			String suffix = input.substring(i);

			String frame = "§f§l" + prefix + "§6§l" + (suffix.isEmpty() ? "" : suffix.charAt(0))
						   + "§e§l" + (suffix.length() > 1 ? suffix.substring(1) : "");
			frames.add(frame);
		}

		String staticFrame = "§e§l" + input;
		for (int i = 0; i < 20; i++) {
			frames.add(staticFrame);
		}

		return frames;
	}
}
