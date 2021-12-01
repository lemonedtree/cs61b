import org.junit.Test;
import static org.junit.Assert.*;


public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        // boolean isPalindrome(String word)

        //0.test 0 word,should be ture
        String input = "";
        assertTrue(palindrome.isPalindrome(input));

        //1.test 1 word,should be ture
        input = "a";
        assertTrue(palindrome.isPalindrome(input));

        //2.test cat,should be false
        input = "cat";
        assertFalse(palindrome.isPalindrome(input));

        //3.test Tit should be false
        input = "Tit";
        assertFalse(palindrome.isPalindrome(input));

        //4.test tit,should be ture
        input = "tit";
        assertTrue(palindrome.isPalindrome(input));

        //5.more long one and is an even number,such as testtset,ture
        input = "testtset";
        assertTrue(palindrome.isPalindrome(input));

        //6.more long one and is an odd number,such as ttstt,true
        input = "ttstt";
        assertTrue(palindrome.isPalindrome(input));

        //7.more long one and is an odd number,such as wsost,false
        input = "wsost";
        assertFalse(palindrome.isPalindrome(input));

        //8.more long one and is an even number,such as wsbt,false
        input = "wsbt";
        assertFalse(palindrome.isPalindrome(input));
    }

    @Test
    public void testIsPalindromeLoaded() {
        // public boolean isPalindrome(String word, CharacterComparator cc)
        CharacterComparator offByOne = new OffByOne();

        //1. 1 item ,true
        assertTrue(palindrome.isPalindrome("t",offByOne));

        //2. o item, true
        assertTrue(palindrome.isPalindrome(" ",offByOne));

        //3. many characters, odd, flake, return true
        assertTrue(palindrome.isPalindrome("flake",offByOne));

        //3. many characters, mean, flke, return true
        assertTrue(palindrome.isPalindrome("flke",offByOne));

        //4.many characters, daflhfjwq, return false
        assertFalse(palindrome.isPalindrome("daflhfjwq",offByOne));

    }
}
