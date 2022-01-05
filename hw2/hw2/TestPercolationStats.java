package hw2;

import org.junit.Test;

/**
 * @author XD
 * @create 2022-01-05 14:05
 */
public class TestPercolationStats {
    @Test
    public void test1() {
        PercolationStats ps = new PercolationStats(20, 10, new PercolationFactory());
        System.out.println(ps.mean());
    }
}
