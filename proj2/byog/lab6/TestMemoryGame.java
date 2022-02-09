package byog.lab6;

import org.junit.Test;

/**
 * @author XD
 * @create 2022-01-08 21:13
 */
public class TestMemoryGame {
    @Test
    public void testGenerateRandomString() {
        MemoryGame game = new MemoryGame(60,30,300);
        System.out.println(game.generateRandomString(3));
    }
}
