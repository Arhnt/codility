package com.zamaruev.codility.heroes;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

/**
 * Solve dynamic programming task using branches and limits
 * Answer:
 * Creature set: [10, 20, 30, 15, 25, 110, 500]
 * Power: 12875, Flight: 4000, Remote: 5600, Cost: 177000, Gems: 115, Hg: 80
 */
public class Heroes {

    private static final int MAX_COST = 200000;
    private static final int MAX_GEM = 115;
    private static final int MAX_HG = 80;
    private static final int MIN_REMOTE = 4000;
    private static final int MIN_FLIGHT = 2000;
    private static final int GEM_PRICE = 500;
    private static final List<Creature> CREATURES = createCreatures();
    private static final int SIZE = CREATURES.size();

    private static List<Creature> createCreatures() {
        List<Creature> creatures = new ArrayList<>(7);
        //                         name        j  c     p    f  r  h  g  max
        creatures.add(new Creature("Титан", 1, 5000, 300, 0, 1, 1, 3, 10));
        creatures.add(new Creature("Нага", 2, 1500, 120, 0, 0, 0, 2, 20));
        creatures.add(new Creature("Джинн", 3, 750, 60, 1, 0, 1, 1, 30));
        creatures.add(new Creature("Маг", 4, 500, 40, 0, 1, 1, 1, 55));
        creatures.add(new Creature("Голем", 5, 400, 35, 0, 0, 1, 0, 60));
        creatures.add(new Creature("Горгулья", 6, 200, 20, 1, 0, 0, 0, 110));
        creatures.add(new Creature("Гремлин", 7, 70, 4, 0, 1, 0, 0, 500));

        return creatures;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log(recursion(new int[]{0, 0, 0, 0, 0, 0, 0}, 0));
    }

    public static int[] recursion(final int[] creatures, int creature) {
        // logging
        if(creature == 1) {
            System.out.println(System.currentTimeMillis() + ": Starting branch " + Arrays.toString(creatures));
        }

        int maxPower = power(creatures);
        int[] max = Arrays.copyOf(creatures, SIZE);

        // check lower branch - if flight & remote power for all possible lower creatures are lower then required
        if (!lower(Arrays.copyOf(creatures, SIZE), creature)) {
            return max;
        }

        // main loop
        int[] current = Arrays.copyOf(creatures, SIZE);
        for (int i = 0; i <= CREATURES.get(creature).max; i++) {
            current[creature] = i;

            // set all zeroes to right
            for (int j = creature + 1; j < SIZE; j++) {
                current[j] = 0;
            }

            // check upper branch - if cost for selected creatures already higher then limits
            if (!upper(current)) {
                return max;
            }

            if (creature < SIZE - 1) {
                current = canBuyAllLower(Arrays.copyOf(current, SIZE), creature)
                    ? allLower(Arrays.copyOf(current, SIZE), creature)
                    : recursion(current, creature + 1);
            }

            if ((maxPower < power(current)) && valid(current)) {
                maxPower = power(current);
                max = Arrays.copyOf(current, SIZE);
            }

        }

        return max;
    }

    private static boolean valid(int[] creatures) {
        int flight = sum(c -> c.flight * c.power * creatures[c.index - 1]);
        if (flight < MIN_FLIGHT) return false;

        int remote = sum(c -> c.remote * c.power * creatures[c.index - 1]);
        if (remote < MIN_REMOTE) return false;

        int hg = sum(c -> c.hg * creatures[c.index - 1]);
        if (hg > MAX_HG) return false;

        int gem = sum(c -> c.gem * creatures[c.index - 1]);
        if (gem > MAX_GEM) return false;

        int cost = sum(c -> c.cost * creatures[c.index - 1]);
        return (cost <= MAX_COST + GEM_PRICE * (MAX_GEM - gem));
    }

    private static boolean upper(int[] creatures) {
        int hg = sum(c -> c.hg * creatures[c.index - 1]);
        if (hg > MAX_HG) return false;

        int gem = sum(c -> c.gem * creatures[c.index - 1]);
        if (gem > MAX_GEM) return false;

        int cost = sum(c -> c.cost * creatures[c.index - 1]);
        return (cost <= MAX_COST + GEM_PRICE * (MAX_GEM - gem));
    }

    private static boolean lower(int[] creatures, int creature) {
        for (int i = creature; i < CREATURES.size(); i++) {
            creatures[i] = CREATURES.get(i).max;
        }

        int flight = sum(c -> c.flight * c.power * creatures[c.index - 1]);
        if (flight < MIN_FLIGHT) return false;

        int remote = sum(c -> c.remote * c.power * creatures[c.index - 1]);
        if (remote < MIN_REMOTE) return false;

        return true;
    }

    private static boolean canBuyAllLower(int[] creatures, int creature) {
        creatures = allLower(creatures, creature);
        return upper(creatures);
    }

    private static int[] allLower(int[] creatures, int creature) {
        for (int i = creature + 1; i < CREATURES.size(); i++) {
            creatures[i] = CREATURES.get(i).max;
        }
        return creatures;
    }

    private static int sum(ToIntFunction<Creature> creature) {
        return CREATURES.stream().mapToInt(creature::applyAsInt).sum();
    }

    private static int power(int[] creatures) {
        return IntStream.range(0, SIZE)
                .map(i -> creatures[i] * CREATURES.get(i).power)
                .sum();
    }

    private static void log(final int[] creatures) {
        System.out.println(System.currentTimeMillis() + ": Creature set: " + Arrays.toString(creatures));
        System.out.println(String.format(
                "Power: %d, Flight: %d, Remote: %d, Cost: %d, Gems: %d, Hg: %d",
                power(creatures),
                sum(c -> c.flight * c.power * creatures[c.index - 1]),
                sum(c -> c.remote * c.power * creatures[c.index - 1]),
                sum(c -> c.cost * creatures[c.index - 1]),
                sum(c -> c.gem * creatures[c.index - 1]),
                sum(c -> c.hg * creatures[c.index - 1])
        ));
        System.out.println("---");

    }

    public static class Creature {
        String name;
        int index;
        int cost;
        int power;
        int flight;
        int remote;
        int hg;
        int gem;
        int max;

        public Creature(String name, int j, int c, int p, int f, int r, int hg, int g, int max) {
            this.name = name;
            this.index = j;
            this.cost = c;
            this.power = p;
            this.flight = f;
            this.remote = r;
            this.hg = hg;
            this.gem = g;
            this.max = max;
        }

        public String toString() {
            return this.name;
        }
    }

}
