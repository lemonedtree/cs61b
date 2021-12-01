/**
 * @author XD
 * @create 2021-12-01 18:36
 * A class for off-by-1 comparators.
 */
public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == 1 || x - y == -1) {
            return true;
        }
        return false;
    }
}
