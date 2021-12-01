/**
 * A class for palindrome operations.
 * @author XD
 * @create 2021-12-01 18:36
 */
public class Palindrome {


    /**
     * Given a String, wordToDeque should return a Deque
     * where the characters appear in the same order as in the String.
     * @example if the word is “persiflage”, then the returned Deque
     * should have ‘p’ at the front, followed by ‘e’, and so forth.
     * @param word
     * @return Deque<Character>
     */
    public Deque<Character> wordToDeque(String word) {
        char[] wordCharArray = word.toCharArray();

        Deque<Character> result = new LinkedListDeque<Character>();
        //必须用它的实现？那么我为什么要返回一个Deque<Character>,我用LinkedListDeque<Character>不好吗？
        //我什么时候需要把<Charcter>指定成char？哦，原来Character已经是具体的了

        for (int i = 0; i < word.length(); i++) {
            result.addLast(wordCharArray[i]);
        }
        return result;
    }

    /**
     * The isPalindrome method should return true
     * if the given word is a palindrome回文
     * @notice Any word of length 1 or 0 is a palindrome.
     * @notice ‘A’ and ‘a’ should not be considered equal
     * @param word
     * @return
     */
    public boolean isPalindrome(String word) {
        LinkedListDeque wordLL = (LinkedListDeque) wordToDeque(word);

        while (wordLL.size() != 0 && wordLL.size() != 1) {
            //removeFirst compares with removeLast
            char firstOne = (char) wordLL.removeFirst();
            char lastOne = (char) wordLL.removeLast();

            if (firstOne != lastOne) {
                //if the two ones are not equal, break and return false;
                return false;
            }

            //if the two ones are always equal, then it will out of the circulation
            // and the return true
        }
        return true;
    }

    /**
     * 0.逐一回文
     * 1. 中间那个不管
     * To allow for odd length palindromes,
     * we do not check the middle character for equality with itself.
     * So “flake” is an off-by-1 palindrome,
     * even though ‘a’ is not one character off from itself.
     *2.  0-1 都可以
     * As with our earlier isPalindrome method, any zero or 1 character word is considered a palindrome.
     * @param word
     * @param cc
     * @return
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        LinkedListDeque wordLL = (LinkedListDeque) wordToDeque(word);

        while (wordLL.size() != 0 && wordLL.size() != 1) {
            //removeFirst compares with removeLast
            char firstOne = (char) wordLL.removeFirst();
            char lastOne = (char) wordLL.removeLast();

            if (!cc.equalChars(firstOne, lastOne)) {
                //if the two ones are not equal, break and return false;
                return false;
            }

            //if the two ones are always equal, then it will out of the circulation
            // and the return true
        }
        return true;
    }
}
