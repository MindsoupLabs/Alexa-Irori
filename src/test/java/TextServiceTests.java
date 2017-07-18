import net.mindsoup.irori.services.TextService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
		assertEquals("Shocking Grasp", textService.getClosestMatch("Shopping Grass"));
		assertEquals("Shocking Grasp", textService.getClosestMatch("Shocking Grass"));
		assertEquals("Summon Monster", textService.getClosestMatch("Salmon Monster"));
	}
}
