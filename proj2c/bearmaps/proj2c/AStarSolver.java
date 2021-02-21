package bearmaps.proj2c;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private int numStates;
    private Vertex start, end;
    Map<Vertex, Double> distTo = new HashMap<>();
    Map<Vertex, Vertex> edgeTo = new HashMap<>();

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        solution = new ArrayList<>();
        this.start = start;
        this.end = end;

        ArrayHeapMinPQ<Vertex> pq = new ArrayHeapMinPQ<>();
        distTo.put(start, 0.0);
        pq.add(start, distTo.get(start) + input.estimatedDistanceToGoal(start, end));

        while (pq.size() > 0) {
            if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                timeSpent = sw.elapsedTime();
                return;
            }
            Vertex x = pq.removeSmallest();
            //solution.add(x);
            numStates++;
            if (x.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                solutionWeight = distTo.get(end);
                return;
            }
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(x);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                Vertex q = e.to();
                double w = e.weight();
                if (!distTo.containsKey(q) || distTo.get(x) + w < distTo.get(q)) {
                    distTo.put(q, distTo.get(x) + w);
                    double newPriority = distTo.get(q) + input.estimatedDistanceToGoal(q, end);
                    edgeTo.put(q, x);
                    if (pq.contains(q))
                        pq.changePriority(q, newPriority);
                    else
                        pq.add(q, newPriority);
                }
            }
        }

        outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        List<Vertex> path = new ArrayList<>();
        if (outcome == SolverOutcome.SOLVED) {
            Vertex v = end;
            path.add(0, v);
            while (!v.equals(start)) {
                v = edgeTo.get(v);
                path.add(0, v);
            }
        }
        return path;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStates;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
