import net.mindsoup.irori.MatchType;
import net.mindsoup.irori.services.TextService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Valentijn on 18-7-2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = net.mindsoup.irori.Application.class)
public class TextServiceTests {

	@Autowired
	TextService textService;

	@Test
	public void test_name_matching() {
		assertEquals("shocking grasp", textService.getClosestMatch("shopping grass", MatchType.OBJECT));
		assertEquals("shocking grasp", textService.getClosestMatch("shopping grass", MatchType.OBJECT));
		assertEquals("shocking grasp", textService.getClosestMatch("shocking grass", MatchType.OBJECT));
		assertEquals("summon monster", textService.getClosestMatch("salmon monster", MatchType.OBJECT));
	}

	@Test
	public void test_stat_matching() {
		assertEquals("armor class", textService.getClosestMatch("armor glass", MatchType.STAT));
		assertEquals("armor class", textService.getClosestMatch("armor glass", MatchType.STAT));
		assertEquals("combat maneuver defense", textService.getClosestMatch("comment meneuver defense ", MatchType.STAT));
	}

	@Test
	public void test_synonyms() {
		Map<String, String> synonyms = new HashMap<>();
		synonyms.put("xp", "experience");
		synonyms.put("init", "initiative");
		synonyms.put("ac", "armor class");
		synonyms.put("fort", "fortitude");
		synonyms.put("ref", "reflex");
		synonyms.put("will", "willpower");
		synonyms.put("str", "strength");
		synonyms.put("dex", "dexterity");
		synonyms.put("con", "constitution");
		synonyms.put("int", "intelligence");
		synonyms.put("wis", "wisdom");
		synonyms.put("cha", "charisma");
		synonyms.put("base atk", "base attack bonus");
		synonyms.put("cmb", "combat maneuver bonus");
		synonyms.put("cmd", "combat maneuver defense");
		synonyms.put("sq", "special qualities");

		for(String key : synonyms.keySet()) {
			assertEquals(synonyms.get(key), textService.getSynonym(key, "monster"));
		}
	}
}
