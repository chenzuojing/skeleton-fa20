package bearmaps.proj2ab;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> items;
    private int size;
    HashMap<T, Integer> map = new HashMap<>();

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(new PriorityNode(null, 0));
    }

    @Override
    public void add(T item, double priority) {
        if (map.containsKey(item))
            throw new IllegalArgumentException();
        size++;
        map.put(item, size);
        items.add(new PriorityNode(item, priority));
        swim(size);
    }

    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }


    @Override
    public T getSmallest() {
        if (size == 0)
            throw new NoSuchElementException();
        return items.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        if (size == 0)
            throw new NoSuchElementException();
        T min = getSmallest();
        map.remove(min);
        swap(1, size);
        items.remove(size);
        size--;
        sink(1);
        return min;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!map.containsKey(item))
            throw new NoSuchElementException();
        int index = map.get(item);
        items.get(index).setPriority(priority);
        swim(index);
        sink(index);
    }

    private void swim(int k) {
        if (k == 1) return;
        int parent = k / 2;
        if (greater(parent, k)) {
            swap(k, parent);
            swim(parent);
        }
    }

    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j + 1 <= size && greater(j, j + 1)) j++;
            if (!greater(k, j)) break;
            swap(k, j);
            k = j;
        }
    }

    private void swap(int p, int q) {
        PriorityNode tmp = items.get(p);
        items.set(p, items.get(q));
        map.put(items.get(q).getItem(), p);
        items.set(q, tmp);
        map.put(tmp.getItem(), q);
    }

    private boolean greater(int p, int q) {
        return items.get(p).compareTo(items.get(q)) > 0;
    }

    ////////////////

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }


    public static void main(String[] args) {
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        //Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 250000; i++) {
            double rand = StdRandom.uniform() * 10;
            pq.add(i, rand);
        }
        //System.out.println("Total time elapsed: " + sw.elapsedTime() + " seconds.");

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 250000; i++) {
//            int j = StdRandom.uniform(0, 100);
//            double rand = StdRandom.uniform() * 10;
//            pq.changePriority(j, rand);
            pq.removeSmallest();
        }
        System.out.println("Total time elapsed: " + sw.elapsedTime() + " seconds.");

    }
}
