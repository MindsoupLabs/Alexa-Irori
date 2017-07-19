import net.mindsoup.irori.models.IroriStat;
import net.mindsoup.irori.utils.NethysParser;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Valentijn on 16-7-2017.
 */
public class ImportServiceTests {

	private final String spellBless = "<span><h1 class=\"title\"><img src=\"images\\PathfinderSocietySymbol.gif\" title=\"PFS Legal\" style=\"margin:3px 3px 0px 3px;\"> Bless</h1><b>Source</b> <a href=\"http://paizo.com/pathfinderRPG/v5748btpy88yj\" target=\"_blank\" class=\"external-link\"><i>PRPG Core Rulebook pg. 249</a> (<a href=\"http://www.amazon.com/gp/product/1601251505/ref=as_li_qf_sp_asin_il_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1601251505&linkCode=as2&tag=archofneth0e-20\" target=\"_blank\" class=\"external-link\">Amazon</a>)</i><br /><b>School</b> enchantment (compulsion) [mind-affecting]; <b>Level</b> adept 1, cleric/oracle 1, inquisitor 1, paladin 1, shaman 1, warpriest 1<h3 class=\"framing\">Casting</h3><b>Casting Time</b> 1 standard action<br /><b>Components</b> V, S, DF<h3 class=\"framing\">Effect</h3><b>Range</b> 50 ft.<br /><b>Area</b> The caster and all allies within a 50-ft. burst, centered on the caster<br /><b>Duration</b> 1 min./level<br /><b>Saving Throw</b> none; <b>Spell Resistance</b> yes (harmless)<h3 class=\"framing\">Description</h3><i>Bless</i> fills your allies with courage. Each ally gains a +1 morale bonus on attack rolls and on saving throws against fear effects.<br /><br /><i>Bless</i> counters and dispels <i>bane</i>.<h2 class=\"title\">Mythic Bless</h2><b>Source</b> <a href=\"http://paizo.com/products/btpy8ywe?Pathfinder-Roleplaying-Game-Mythic-Adventures\" target=\"_blank\" class=\"external-link\"><i>Mythic Adventures pg. 85</i></a><br />The +1 morale bonus applies on attack rolls, weapon damage rolls, and all saving throws. Once during the spell’s duration, an affected creature can roll an attack roll or saving throw twice and take the higher result. The target must decide to use this ability before the first roll is attempted.<span>";

	@Test
	public void test_nethys_spell_parser() {
		List<IroriStat> iroriStats = NethysParser.fromString(spellBless);

		assertEquals(10, iroriStats.size());

		for(IroriStat stat : iroriStats) {
			switch (stat.getStatName()) {
				case "source":
					assertEquals("PRPG Core Rulebook pg. 249", stat.getStatValue());
					break;
				case "school":
					assertEquals("enchantment (compulsion) [mind-affecting];", stat.getStatValue());
					break;
				case "level":
					assertEquals("adept 1, cleric/oracle 1, inquisitor 1, paladin 1, shaman 1, warpriest 1", stat.getStatValue());
					break;
				case "casting Time":
					assertEquals("1 standard action", stat.getStatValue());
					break;
				case "components":
					assertEquals("V, S, DF", stat.getStatValue());
					break;
				case "range":
					assertEquals("50 ft.", stat.getStatValue());
					break;
				case "area":
					assertEquals("The caster and all allies within a 50-ft. burst, centered on the caster", stat.getStatValue());
					break;
				case "duration":
					assertEquals("1 min. per level", stat.getStatValue());
					break;
				case "saving throw":
					assertEquals("none;", stat.getStatValue());
					break;
				case "spell resistance":
					assertEquals("yes (harmless)", stat.getStatValue());
					break;
				case "description":
					assertEquals("Bless fills your allies with courage. Each ally gains a +1 morale bonus on attack rolls and on saving throws against fear effects. Bless counters and dispels bane.", stat.getStatValue());
				default:
					break;
			}

		}
	}
}
