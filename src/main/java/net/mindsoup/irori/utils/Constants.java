package net.mindsoup.irori.utils;

/**
 * Created by Valentijn on 15-7-2017.
 */
public abstract class Constants {

	public abstract static class Stats {
		private Stats() {}

		public static final String SOURCE = "source";
		public static final String TARGET = "target";
		public static final String HITPOINTS = "hit points";
		public static final String DESCRIPTION = "description";
		public static final String REQUIREMENTS = "requirements";
	}

	public abstract static class Keywords {
		private Keywords() {}

		public static final String MYTHIC = "Mythic";
		public static final String CREATING_A = "<h1 class=\"title\">Creating a";
	}
}
