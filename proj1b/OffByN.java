/**
 * A class for off-by-N comparators.
 * @author XD
 * @create 2021-12-01 18:37
 */
public class OffByN implements CharacterComparator {
    //as well as a single argument constructor which takes an integer.
    private int N;
    public OffByN (int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == N || y - x == N) {
            return true;
        }
        return false;
    }
}
