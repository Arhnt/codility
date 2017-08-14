package com.zamaruev.codility.tasks;

import org.junit.Assert;
import org.junit.Test;

/**
 * Pow11
 *
 * Write a function:
 *   class Solution { public int solution(int N); }
 * that, given a non-negative integer N,
 * returns the number of digits equal to 1 in the decimal representation of the number 11N.
 *
 * For example, given N=3 the function should return 2, because 113=1331 and two digits of the number 1331 are equal to 1.
 *
 * Assume that:
 * - N is an integer within the range [0..1,000].
 *
 * Complexity:
 * - expected worst-case time complexity is O(N2);
 * - expected worst-case space complexity is O(N).
 *
 * Tests:
 * - n=3
 * - n=5
 * - n=12
 * - n=33
 * - n=77
 * - n=0
 * - n=1
 * - n=490
 * - n=500
 */
public class Pow11 {

    public int solution(int N) {
        String result = "1";
        for(int i = 1; i <= N; i++) {
            result = add(result + "0", result);
        }

        System.out.println(N + ": " + result);
        int occurs = 0;
        for(int i = 0; i < result.length(); i++) {
            if(result.charAt(i) == '1') {
                occurs++;
            }
        }
        return occurs;
    }

    private String add(String left, String right) {
        // assume left.length > right.length
        int high = 0;

        int rightIndex = right.length() - 1;
        int leftIndex = left.length() - right.length() + rightIndex;
        while (rightIndex >= 0 || (high > 0 && leftIndex >= 0)) {
            char[] digits = left.toCharArray();
            int toAdd = high;
            if(rightIndex >= 0) {
                toAdd = toAdd + Character.getNumericValue(right.charAt(rightIndex));
            }


            int low = Character.getNumericValue(digits[leftIndex]) + toAdd;
            if(low > 9) {
                low = low - 10;
                high = 1;
            } else {
                high = 0;
            }
            digits[leftIndex] = Character.forDigit(low, 10);
            left = String.valueOf(digits);

            leftIndex--;
            rightIndex--;
        }

        if(high > 0) {
            left = "1" + left;
        }
        return left;
    }


    @Test
    public void testAdd() {
        Assert.assertEquals("0", add("0", "0"));
        Assert.assertEquals("1", add("1", "0"));
        Assert.assertEquals("9", add("4", "5"));
        Assert.assertEquals("10", add("9", "1"));
        Assert.assertEquals("18", add("9", "9"));
        Assert.assertEquals("28", add("19", "9"));
        Assert.assertEquals("1000", add("999", "1"));
        Assert.assertEquals("1331", add("1210", "121"));
        Assert.assertEquals("214358881", add("194871710", "19487171"));

    }

    @Test
    public void test() {
        Assert.assertEquals(1, solution(0));
        Assert.assertEquals(2, solution(1));
        Assert.assertEquals(2, solution(2));
        Assert.assertEquals(2, solution(3));
        Assert.assertEquals(3, solution(5));
        Assert.assertEquals(3, solution(7));
        Assert.assertEquals(2, solution(8));
        Assert.assertEquals(1, solution(20));
        Assert.assertEquals(1, solution(23));
        Assert.assertEquals(105, solution(1000));
    }
}
