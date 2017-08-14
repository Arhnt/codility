package com.zamaruev.codility.tasks;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

/**
 * Merging a sorted list consisting of K elements with a sorted list consisting of L elements takes (K+L) milliseconds (ms).
 * The time required to merge more than two lists into one final list depends on the order in which the merges are performed.
 *
 * For example, consider the following three lists:
 * - list P consisting of 100 elements,
 * - list Q consisting of 250 elements,
 * - list R consisting of 1000 elements.
 *
 * They can be merged into one final sorted list in three different ways:
 * 1. first merge P with Q, then merge the result with R; or
 * 2. first merge P with R, then merge the result with Q; or
 * 3. first merge R with Q, then merge the result with P.
 *
 * The times needed to perform the above merges are respectively:
 * - merge P with Q: 350ms; result with R: 1350ms; total: 1700ms;
 * - merge P with R: 1100ms; result with Q: 1350ms; total: 2450ms;
 * - merge Q with R: 1250ms; result with P: 1350ms; total: 2600ms.
 *
 * The first schema is the fastest (1700ms).
 *
 * If there are more than three lists to merge, there are even more merge strategies to consider.
 * When the number of lists to merge is fewer than two, no merges are performed and the total merge time is assumed to be 0.
 *
 * Write a function:
 *   class Solution { public int solution(int[] A); }
 * that, given a zero-indexed array A of length N describing the lengths of N lists,
 * returns the shortest time (measured in milliseconds) required to merge these lists into one.
 *
 * For example, given array A consisting of three elements such that:
 * A[0] = 100 A[1] = 250 A[2] = 1000
 * the function should return 1700, as explained above.
 *
 * Assume that:
 * - N is an integer within the range [0..10,000];
 * - each element of array A is an integer within the range [1..1,000].
 *
 * Complexity:
 * - expected worst-case time complexity is O(N*log(N));
 * - expected worst-case space complexity is O(N), beyond input storage (not counting the storage required for input arguments).
 *
 * Elements of input arrays can be modified.
 */
public class MinMergeTime {

    public int solution(int[] A) {
        if ((A == null) || A.length == 0) {
            return 0;
        }
        if(A.length == 1) {
            return A[0];
        }

        PriorityQueue<Integer> q = new PriorityQueue();
        for(int i = 0; i < A.length; i++) {
            q.add(A[i]);
        }

        int total = 0;
        while (q.size() > 1) {
            int el1 = q.poll();
            int el2 = q.poll();
            total = total + el1 + el2;
            q.add(el1 + el2);
        }

        return total;
    }

    @Test
    public void test() {
        assertEquals(0, solution(new int[] {}));
        assertEquals(0, solution(new int[] {0}));
        assertEquals(1, solution(new int[] {1}));
        assertEquals(350, solution(new int[] {100, 250}));
        assertEquals(1700, solution(new int[] {100, 250, 1000}));
        assertEquals(20, solution(new int[] {2, 3, 2, 3}));
    }

}
