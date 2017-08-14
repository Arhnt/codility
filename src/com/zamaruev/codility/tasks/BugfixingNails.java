package com.zamaruev.codility.tasks;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * BugfixingNails
 * Consider N nails embedded in the same wooden block.
 * They extend to different heights.
 * You can choose K nails and hammer them down to any height between their original heights and 0.
 * Nails cannot be pulled up.
 * The goal is to have as many nails of the same height as possible.
 *
 * You are given an implementation of a function:
 *    class Solution { public int solution(int[] A, int K); }
 * for solving this problem.
 * The heights of the nails are given as elements of a non-empty zero-indexed array A consisting of N integers,
 * sorted in a non-decreasing order. This function, when given the array A and integer K,
 * returns the maximal number of nails that can be positioned at the same height.
 *
 * For example, given K = 2 and array A consisting of ten elements such that:
 * - A[0] = 1
 * - A[1] = 1
 * - A[2] = 3
 * - A[3] = 3
 * - A[4] = 3
 * - A[5] = 4
 * - A[6] = 5
 * - A[7] = 5
 * - A[8] = 5
 * - A[9] = 5
 * the function should return 5.
 * One of the possibilities is to hammer the nails represented by A[8] and A[9] down to height 3.
 *
 * Unfortunately, despite the fact that the function may return expected result for the example input,
 * there is a bug in the implementation, which may produce incorrect results for other inputs.
 * Find the bug and correct it. You should modify at most four lines of code.
 *
 * Assume that:
 * - N is an integer within the range [1..10,000];
 * - K is an integer within the range [0..N];
 * - each element of array A is an integer within the range [1..1,000,000,000];
 * - array A is sorted in non-decreasing order.
 *
 * Complexity:
 * - expected worst-case time complexity is O(N);
 * - expected worst-case space complexity is O(1), beyond input storage (not counting the storage required for input arguments).
 *
 * Elements of input arrays can be modified.
 *
 * Tests:
 * - new int[]{1, 1, 3, 3, 3, 4, 5, 5, 5, 5}
 * - single element
 * - double element
 * - simple tests
 * - small test, K = N
 * - small test, K = N - 1 / K = N - 2
 * - large random sequence, length = ~10,000
 * - medium range sequence, length = ~2,000
 * - extreme large test K = N, length = ~10,000
 */
public class BugfixingNails {

    int solution(int[] A, int K) {
        int n = A.length;
        int best = 1;
        int count = 1;
        for (int i = 0; i < n - K - 1; i++) {
            if (A[i] == A[i + 1])
                count = count + 1;
            else
                count = 1;
            if (count > best)
                best = count;
        }
        int result = best + K;

        return (result > n) ? n : result;
    }

    @Test
    public void test() {
        assertEquals(5, solution(new int[]{1, 1, 3, 3, 3, 4, 5, 5, 5, 5}, 2));
        assertEquals(4, solution(new int[]{1, 1, 3, 3, 3, 4, 5, 5, 5, 5}, 0));
        assertEquals(5, solution(new int[]{5, 5, 5, 5, 5}, 5));
        assertEquals(5, solution(new int[]{5, 5, 5, 5, 5}, 2));
        assertEquals(5, solution(new int[]{5, 5, 5, 5, 5}, 0));
        assertEquals(1, solution(new int[]{1, 2, 3, 4, 5}, 0));
        assertEquals(2, solution(new int[]{1, 2, 3, 4, 5}, 1));
        assertEquals(3, solution(new int[]{1, 2, 3, 4, 5}, 2));
        assertEquals(4, solution(new int[]{1, 2, 3, 4, 5}, 3));
        assertEquals(5, solution(new int[]{1, 2, 3, 4, 5}, 4));
        assertEquals(5, solution(new int[]{1, 2, 3, 4, 5}, 5));
        assertEquals(4, solution(new int[]{5, 5, 5, 5, 6}, 0));
        assertEquals(5, solution(new int[]{5, 5, 5, 5, 6}, 1));
        assertEquals(3, solution(new int[]{1, 2, 5, 5, 5}, 1));
        assertEquals(3, solution(new int[]{1, 2, 5, 5, 5}, 0));
        assertEquals(4, solution(new int[]{1, 2, 5, 5, 5}, 3));
    }
}
