package com.zamaruev.codility.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A file contains a sequence of integers, stored one per line.
 * Implement a class that facilitates iteration over these integers.
 *
 * A valid integer is a sequence of one or more digits (without leading zeros),
 * optionally preceded by a plus or minus sign,
 * representing a number within the range [−1,000,000,000..1,000,000,000].
 * We allow spaces to appear in a line before and/or after a valid integer.
 * Lines are separated with the line-feed character (ASCII code 10).
 *
 * There might be lines that do not represent valid integers, e.g. 2u1, 23.9, #12, 00, ++1, 2000000000.
 * Such lines are considered to be comments, and should be discarded.
 *
 * Define a class SolutionIter that implements Iterable<Integer> and iterates
 * over integers from a Reader object compliant with the above format.
 *
 * For an input file containing the following lines:
 * 137
 * -104
 * 2
 * 58
 * +0
 * ++3
 * +1
 * 23.9
 * 2000000000
 * -0
 * five
 * -1
 * your iterator should return the following sequence of integers:
 * [137, -104, 0, 1, 0, -1]
 *
 * Assume that:
 * - the file can contain only line-feed characters (ASCII code 10) and printable ASCII characters (ASCII codes from 32 to 126);
 * - for every file your iterator will be used only once.
 */
public class SolutionIter implements Iterable<Integer> {

    private BufferedReader inp;
    private static final int MIN = -1000000000;
    private static final int MAX = 1000000000;

    public SolutionIter(Reader inp){
        this.inp = new BufferedReader(inp);
    }

    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private Integer next;

            @Override
            public boolean hasNext() {
                if(next != null) {
                    return true;
                }
                try {
                    next = next();
                } catch (Exception ex) {
                    return false;
                }
                return (next != null);
            }

            @Override
            public Integer next() {
                if(next != null) {
                    int temp = next;
                    next = null;
                    return temp;
                }

                Integer value = null;

                try {
                    String line;
                    while ((value == null) && ((line = inp.readLine()) != null)) {
                        value = parseLine(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if(value == null) {
                    throw new NoSuchElementException();
                }

                return value;
            }

            private Integer parseLine(String line) {
                if(line == null || line.isEmpty()) {
                    return null;
                }
                String val = line.trim();
                if (val.matches("^[+-]*0.+$")) { // check for trail zeroes
                    return null;
                }
                try {
                    Integer value = Integer.valueOf(val);
                    if ((value < MIN) || (value > MAX)) {
                        return null;
                    }
                    return  value;
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
        };
    }


}
