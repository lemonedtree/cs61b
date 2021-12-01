import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author XD
 * @create 2021-12-01 20:46
 */
public class TestOffByN {
    @Test
    public void testEqualChars() {
        OffByN offBy5 = new OffByN(5);

        assertTrue(offBy5.equalChars('a', 'f'));  // true
        assertTrue(offBy5.equalChars('f', 'a'));  // true
        assertFalse(offBy5.equalChars('f', 'h'));  // false



    }
}
