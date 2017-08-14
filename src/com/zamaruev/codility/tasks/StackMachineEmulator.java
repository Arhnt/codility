package com.zamaruev.codility.tasks;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * A stack machine is a simple system that performs arithmetic operations on an input string of numbers and operators.
 * It contains a stack that can store an arbitrary number of 12-bit unsigned integers.
 * Initially the stack is empty.
 *
 * The machine processes a string of characters in the following way:
 * - the characters of the string are processed one by one;
 * - if the current character is a digit ('0'-'9'), the machine pushes the value of that digit onto its stack;
 * - if the current character is '+', the machine pops the two topmost values from its stack, adds them and pushes the result onto the stack;
 * - if the current character is '*', the machine pops the two topmost values from its stack, multiplies them and pushes the result onto the stack;
 * - after the machine has processed the whole string it returns the topmost value of its stack as the result;
 * - the machine reports an error if any operation it performs (addition or multiplication) results in an overflow;
 * - the machine reports an error if it tries to pop an element from its stack when the stack is empty, or if the stack is empty after the machine has processed the whole string.
 *
 * For example, given the string "13+62*7+*" the machine will perform the following operations:
 * character | comment | stack
 * -----------------------------------------------
 * | | [empty]
 * '1' | push 1 onto the stack | | | 1
 * '3' | push 3 onto the stack | | | 1, 3
 * '+' | perform addition | | | 4
 * '6' | push 6 onto the stack | | | 4, 6
 * '2' | push 2 onto the stack | | | 4, 6, 2
 * '*' | perform multiplication | | | 4, 12
 * '7' | push 7 onto the stack | | | 4, 12, 7
 * '+' | perform addition | | | 4, 19
 * '*' | perform multiplication | | | 76
 *
 * The machine will return 76 as the result as it is the topmost element of its stack.
 *
 * Write a function:
 *  class Solution { public int solution(String S); }
 * that, given a string S consisting of N characters containing input for the stack machine,
 * returns the result the machine would return if given this string.
 * The function should return −1 if the machine would report an error when processing the string.
 *
 * For example, given string S = "13+62*7+*" the function should return 76, as explained in the example above.
 * Given string S = "11++" the function should return −1.
 *
 * Assume that:
 * - the length of S is within the range [0..200,000];
 * - string S consists only of the following characters: "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+" and/or "*".
 *
 * In your solution, focus on correctness. The performance of your solution will not be the focus of the assessment.
 *
 * Tests:
 * - 13+62*7+*
 * - 11++
 * - 11+22+33*+*
 * - 43567810294**+1**++12++*
 * - tests for arithmetic overflow
 * - a sequence with no arithmetic operations
 * - corner cases: empty sequence, and 11+
 * - long sequences of the form 111...***... (multiplying 1s)
 * - long sequences of the form 000...+++... (adding 0s)
 * - short and long sequences of the form 222...***... (multiplying 2s)
 *
 */
public class StackMachineEmulator {

    private Deque<Integer> stack = new ArrayDeque<Integer>();

    private final static int MAX = 4096; //2^12

    public int solution(String S) {
        if ((S == null) || S.isEmpty()) {
            System.out.println("empty string");
            return -1;
        }

        for(String c : S.split("")) {
            System.out.println(c);
            boolean isDigit = c.matches("\\d");
            boolean isPlus = "+".equals(c);
            boolean isMultiply = "*".equals(c);
            boolean isOperation = isPlus || isMultiply;

            if(isDigit) {
                int value = Integer.valueOf(c);
                if(value < MAX) {
                    stack.push(value);
                } else {
                    System.out.println("input is greater than 12 bit int");
                    return -1;
                }
            } else if (isOperation) {
                if(!doOperation(isPlus, isMultiply)) {
                    System.out.println("operation failed");
                    return -1;
                }
            } else {
                System.out.println("invalid character");
                return -1;
            }
        }
        if(stack.isEmpty()) {
            System.out.println("stack is empty");
            return -1;
        }
        return stack.pop();

    }

    private boolean doOperation(boolean isPlus, boolean isMultiply) {
        boolean success = true;
        try {
            Integer n1 = stack.pop();
            Integer n2 = stack.pop();
            Integer result = (isPlus) ? (n1 + n2) : (n1 * n2);
            if (result < MAX) {
                stack.push(result);
            } else {
                System.out.println("operation overflow");
                success = false;
            }
        } catch (NoSuchElementException ex) {
            System.out.println("no such element");
            success = false;
        }
        return success;
    }

    @Test
    public void test() {
        Assert.assertEquals(76, solution("13+62*7+*"));
        Assert.assertEquals(-1, solution("a13+62*7+*"));
        Assert.assertEquals(-1, solution("!"));
        Assert.assertEquals(-1, solution("*"));
        Assert.assertEquals(-1, solution("99*9*9*9*9"));
    }

}
