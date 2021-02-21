package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> npq = new NaiveMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            double rand = StdRandom.uniform() * 10;
            pq.add(i, rand);
            npq.add(i, rand);
        }
        for (int i = 0; i < 5000; i++) {
            int j = StdRandom.uniform(0, 100);
            double rand = StdRandom.uniform() * 100;
            pq.changePriority(j, rand);
            npq.changePriority(j, rand);
        }

        for (int i = 0; i < 1000; i++) {
            assertEquals(pq.removeSmallest(), npq.removeSmallest());
        }
    }

}
