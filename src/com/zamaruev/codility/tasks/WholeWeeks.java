package com.zamaruev.codility.tasks;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * You are given a year Y and the names of two months A and B.
 * Calculate how many whole weeks are contained in the period of time between months A and B (inclusive) in year Y.
 * A whole week consists of 7 consecutive days from Monday to Sunday (in that order).
 * For convenience, you are also given W, the day of the week for January 1st of year Y.
 *
 * The names of the days of the week are Monday, Tuesday, Wednesday, Thursday, Friday, Saturday and Sunday.
 * The names of the months are January, February, March, April, May, June, July, August, September, October, November and December.
 * The lengths of the corresponding months are 31, 28 (or 29 in a leap year), 31, 30, 31, 30, 31, 31, 30, 31, 30 and 31 days.
 * Assume that a year is a leap year if it is divisible by 4.
 *
 * Write a function:
 *     class Solution { public int solution(int Y, String A, String B, String W); }
 * that, given an integer Y and three non-empty strings A, B and W,
 * returns the number of whole weeks in the period of time described above.
 *
 * For example, given Y = 2014, A = "April", B = "May" and W = "Wednesday", the function should return 7,
 * since there are seven whole weeks between April and May in the year 2014, starting respectively on
 * April 7th, April 14th, April 21st, April 28th, May 5th, May 12th and May 19th.
 *
 * Assume that:
 * - Y is an integer within the range [2,001..2,099];
 * - A and B are valid month names;
 * - month B does not preceed month A;
 * - W is a valid name of a day of the week;
 * - W is the correct day of the week for January 1st of year Y.
 *
 * In your solution, focus on correctness.
 * The performance of your solution will not be the focus of the assessment.
 *
 */
public class WholeWeeks
{
    public int solution(int Y, String A, String B, String W) {
        String day = W;
        int startWeeks = 0;
        int endWeeks = 0;
        for (int month = 0; month <= monthNum(B); month++) {
            for (int i = 1; i <= daysInMonth(Y, month); i++) {
                if(month >= monthNum(A) && "Monday".equals(day)) {
                    startWeeks++;
                } else if (month >= monthNum(A) && "Sunday".equals(day) && startWeeks > 0) {
                    endWeeks++;
                }
                day = nextDay(day);
            }
        }

        return endWeeks;
    }

    private int monthNum(String month) {
        String[] months = new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        for(int i = 0; i <= 11; i++) {
            if (month.equals(months[i])){
                return i;
            }
        }
        return -1;
    }

    private String nextDay(String day) {
        String[] days = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
        for(int i = 0; i <= 5; i++) {
            if (day.equals(days[i])) {
                return days[i+1];
            }
        }
        return days[0];
    }

    private int daysInMonth(int year, int num) {
        int[] days = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (year % 4 == 0) {
            days[1]++;
        }
        return days[num];
    }

    @Test
    public void testMonthNum() {
        assertEquals(0, monthNum("January"));
        assertEquals(1, monthNum("February"));
        assertEquals(7, monthNum("August"));
        assertEquals(11, monthNum("December"));
    }


    @Test
    public void testDaysInMonth() {
        assertEquals(31, daysInMonth(2015, 11));
        assertEquals(31, daysInMonth(2014, 0));
        assertEquals(28, daysInMonth(2014, 1));
        assertEquals(29, daysInMonth(2012, 1));
    }

    @Test
    public void testNextDay() {
        assertEquals("Tuesday", nextDay("Monday"));
        assertEquals("Sunday", nextDay("Saturday"));
        assertEquals("Monday", nextDay("Sunday"));
    }

    @Test
    public void test() {
        assertEquals(7, solution(2014, "April", "May", "Wednesday"));
        assertEquals(3, solution(2014, "January", "January", "Wednesday"));
        assertEquals(4, solution(2014, "December", "December", "Wednesday"));
        assertEquals(4, solution(2015, "August", "August", "Thursday"));
    }

}
