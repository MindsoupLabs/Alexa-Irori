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
		assertEquals("shocking grasp", textService.getClosestMatch("shopping grass"));
		assertEquals("shocking grasp", textService.getClosestMatch("shocking grass"));
		assertEquals("summon monster", textService.getClosestMatch("salmon monster"));
	}
}
