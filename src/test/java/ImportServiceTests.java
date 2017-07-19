import net.mindsoup.irori.models.IroriStat;
import net.mindsoup.irori.utils.NethysParser;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Valentijn on 16-7-2017.
 */
public class ImportServiceTests {

	private final String SPELL_BLESS = "<span><h1 class=\"title\"><img src=\"images\\PathfinderSocietySymbol.gif\" title=\"PFS Legal\" style=\"margin:3px 3px 0px 3px;\"> Bless</h1><b>Source</b> <a href=\"http://paizo.com/pathfinderRPG/v5748btpy88yj\" target=\"_blank\" class=\"external-link\"><i>PRPG Core Rulebook pg. 249</a> (<a href=\"http://www.amazon.com/gp/product/1601251505/ref=as_li_qf_sp_asin_il_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1601251505&linkCode=as2&tag=archofneth0e-20\" target=\"_blank\" class=\"external-link\">Amazon</a>)</i><br /><b>School</b> enchantment (compulsion) [mind-affecting]; <b>Level</b> adept 1, cleric/oracle 1, inquisitor 1, paladin 1, shaman 1, warpriest 1<h3 class=\"framing\">Casting</h3><b>Casting Time</b> 1 standard action<br /><b>Components</b> V, S, DF<h3 class=\"framing\">Effect</h3><b>Range</b> 50 ft.<br /><b>Area</b> The caster and all allies within a 50-ft. burst, centered on the caster<br /><b>Duration</b> 1 min./level<br /><b>Saving Throw</b> none; <b>Spell Resistance</b> yes (harmless)<h3 class=\"framing\">Description</h3><i>Bless</i> fills your allies with courage. Each ally gains a +1 morale bonus on attack rolls and on saving throws against fear effects.<br /><br /><i>Bless</i> counters and dispels <i>bane</i>.<h2 class=\"title\">Mythic Bless</h2><b>Source</b> <a href=\"http://paizo.com/products/btpy8ywe?Pathfinder-Roleplaying-Game-Mythic-Adventures\" target=\"_blank\" class=\"external-link\"><i>Mythic Adventures pg. 85</i></a><br />The +1 morale bonus applies on attack rolls, weapon damage rolls, and all saving throws. Once during the spell’s duration, an affected creature can roll an attack roll or saving throw twice and take the higher result. The target must decide to use this ability before the first roll is attempted.<span>";
	private final String MONSTER_DUERGAR = "<span id=\"ctl00_MainContent_DataListFeats_ctl00_Label1\"><h1 class=\"title\">Creatures in \"Duergar\" Category</h1><table class=\"inner\"><tbody><tr><td><b>Name</b></td><td><b>CR</b></td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar\">Duergar</a></td><td>1/3</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar Anvil\">Duergar Anvil</a></td><td>1</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar Bombardier\">Duergar Bombardier</a></td><td>1</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar Captain\">Duergar Captain</a></td><td>8</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar Hammer\">Duergar Hammer</a></td><td>11</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar High Priest\">Duergar High Priest</a></td><td>12</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar Lieutenant\">Duergar Lieutenant</a></td><td>5</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar Sergeant\">Duergar Sergeant</a></td><td>1</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar Sharpshooter\">Duergar Sharpshooter</a></td><td>1/2</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar Slaver\">Duergar Slaver</a></td><td>1/2</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar Taskmaster\">Duergar Taskmaster</a></td><td>9</td></tr><tr><td><a href=\"MonsterDisplay.aspx?ItemName=Duergar Tyrant\">Duergar Tyrant</a></td><td>3</td></tr></tbody></table><h1 class=\"title\">Duergar, Duergar Sharpshooter</h1><h2 class=\"title\">Duergar Sharpshooter CR 1/2</h2><b>Source</b> <a href=\"http://paizo.com/products/btpy9926?Pathfinder-Roleplaying-Game-Monster-Codex&#10;\" target=\"_blank\" class=\"external-link\"><i>Monster Codex pg. 46</i></a><i> (<a href=\"http://www.amazon.com/gp/product/1601256868/ref=as_li_tl?ie=UTF8&amp;camp=1789&amp;creative=9325&amp;creativeASIN=1601256868&amp;linkCode=as2&amp;tag=archofneth0e-20&amp;linkId=7N2DAMINDXP6U2ZO\" target=\"_blank\" class=\"external-link\">Amazon</a>)</i><br><b>XP</b> 200<br>Duergar ranger 1<br>LE Medium humanoid (dwarf)<br><b>Init</b> +2; <b>Senses</b> darkvision 120 ft.; Perception +6<h3 class=\"framing\">Defense</h3><b>AC</b> 15, touch 12, flat-footed 13 (+3 armor, +2 Dex)<br><b>hp</b> 13 (1d10+3)<br><b>Fort</b> +4, <b>Ref</b> +4, <b>Will</b> +2; +2 vs. spells<br><b>Immune</b> paralysis, phantasms, poison<br><b>Weaknesses</b> light sensitivity<h3 class=\"framing\">Offense</h3><b>Speed</b> 20 ft.<br><b>Melee</b> warhammer +3 (1d8+2/×3)<br><b>Ranged</b> mwk light crossbow +4 (1d8/19–20)<br><b>Special Attacks</b> favored enemy (dwarves +2)<br><b>Spell-Like Abilities</b> (CL 1st; concentration –2)<br>1/day—<i>invisibility</i> (self only), <i>ironskin</i><h3 class=\"framing\">Statistics</h3><b>Str</b> 14, <b>Dex</b> 15, <b>Con</b> 14, <b>Int</b> 10, <b>Wis</b> 15, <b>Cha</b> 4<br><b>Base Atk</b> +1; <b>CMB</b> +3; <b>CMD</b> 15 (19 vs. bull rush or trip)<br><b>Feats</b> Rapid Reload (light crossbow)<br><b>Skills</b> Climb +5, Handle Animal +1, Knowledge (dungeoneering) +4, Perception +6, Stealth +5, Survival +6<br><b>Languages</b> Common, Dwarven, Undercommon<br><b>SQ</b> ironskinned, slow and steady, stability, track +1, wild empathy –2<br><b>Gear</b> studded leather, mwk light crossbow with 20 bolts, warhammer, 16 gp<h3 class=\"framing\">Ecology</h3><b>Environment</b> any underground<br><br>Spending much time scouting and exploring alone, rangers prove their toughness to their kin.</span>";

	@Test
	public void test_nethys_spell_parser() {
		List<IroriStat> iroriStats = NethysParser.fromString(SPELL_BLESS, "Bless");

		assertEquals(10, iroriStats.size());

		Map<String, String> expectedValues = new HashMap<>();
		expectedValues.put("school", "enchantment (compulsion) [mind-affecting];");
		expectedValues.put("level", "adept 1, cleric/oracle 1, inquisitor 1, paladin 1, shaman 1, warpriest 1");
		expectedValues.put("casting time", "1 standard action");
		expectedValues.put("components", "V, S, DF");
		expectedValues.put("range", "50 ft.");
		expectedValues.put("area", "The caster and all allies within a 50-ft. burst, centered on the caster");
		expectedValues.put("duration", "1 min. per level");
		expectedValues.put("saving throw", "none;");
		expectedValues.put("spell resistance", "yes (harmless)");
		expectedValues.put("description", "Bless fills your allies with courage. Each ally gains a +1 morale bonus on attack rolls and on saving throws against fear effects. Bless counters and dispels bane.");

		for(IroriStat stat : iroriStats) {
			assertEquals(expectedValues.get(stat.getStatName()), stat.getStatValue());
		}
	}

	@Test
	public void test_nethys_monster_parser() {
		List<IroriStat> iroriStats = NethysParser.fromString(MONSTER_DUERGAR, "Duergar, Duergar Sharpshooter");

		assertEquals(30, iroriStats.size());

		Map<String, String> expectedValues = new HashMap<>();
		expectedValues.put("xp", "200");
		expectedValues.put("init", "+2;");
		expectedValues.put("senses", "darkvision 120 ft.; Perception +6");
		expectedValues.put("ac", "15, touch 12, flat-footed 13 (+3 armor, +2 Dex)");
		expectedValues.put("hp", "13 (1d10+3)");
		expectedValues.put("fort", "+4,");
		expectedValues.put("ref", "+4,");
		expectedValues.put("will", "+2; +2 vs. spells");
		expectedValues.put("immune", "paralysis, phantasms, poison");
		expectedValues.put("weaknesses", "light sensitivity");
		expectedValues.put("speed", "20 ft.");
		expectedValues.put("melee", "warhammer +3 (1d8+2/×3)");
		expectedValues.put("ranged", "mwk light crossbow +4 (1d8/19–20)");
		expectedValues.put("special attacks", "favored enemy (dwarves +2)");
		expectedValues.put("spell-like abilities", "(CL 1st; concentration –2)");
		expectedValues.put("str", "14,");
		expectedValues.put("dex", "15,");
		expectedValues.put("con", "14,");
		expectedValues.put("int", "10,");
		expectedValues.put("wis", "15,");
		expectedValues.put("cha", "4");
		expectedValues.put("base atk", "+1;");
		expectedValues.put("cmb", "+3;");
		expectedValues.put("cmd", "15 (19 vs. bull rush or trip)");
		expectedValues.put("feats", "Rapid Reload (light crossbow)");
		expectedValues.put("skills", "Climb +5, Handle Animal +1, Knowledge (dungeoneering) +4, Perception +6, Stealth +5, Survival +6");
		expectedValues.put("languages", "Common, Dwarven, Undercommon");
		expectedValues.put("sq", "ironskinned, slow and steady, stability, track +1, wild empathy –2");
		expectedValues.put("gear", "studded leather, mwk light crossbow with 20 bolts, warhammer, 16 gp");
		expectedValues.put("environment", "any underground");
		expectedValues.put("source", "Monster Codex pg. 46");
		//expectedValues.put("description", "Spending much time scouting and exploring alone, rangers prove their toughness to their kin.");
		// parsing description from monsters is not supported atm

		for(IroriStat stat : iroriStats) {
			assertEquals(expectedValues.get(stat.getStatName()), stat.getStatValue());
		}
	}
}
