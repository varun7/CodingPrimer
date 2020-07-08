package org.code.runs.ds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class AdjacencyListGraphTest {

    @Test
    public void testDijkstra() {
        Graph.AdjacencyListGraph<Integer> graph = new Graph.AdjacencyListGraph<>(true);
        graph.insert(1);
        graph.insert(2);
        graph.insert(3);
        graph.insert(4);
        graph.connect(1,2, 1.0);
        graph.connect(1,3, 4.0);
        graph.connect(2,3, 2.0);
        graph.connect(2,4, 5.0);
        graph.connect(3,4, 4.0);

        Map<Integer,Double> result =  graph.dijkstra(1);
        Assert.assertEquals(result.get(1), Double.valueOf(0));
        Assert.assertEquals(result.get(2), Double.valueOf(1));
        Assert.assertEquals(result.get(3), Double.valueOf(3));
        Assert.assertEquals(result.get(4), Double.valueOf(6));
    }

    @Test
    public void testCyclic() {
        Graph<Integer> directedGraph = new Graph.AdjacencyListGraph<>(true);
        directedGraph.insert(1);
        directedGraph.insert(2);
        directedGraph.insert(3);
        directedGraph.insert(4);
        directedGraph.insert(5);
        directedGraph.insert(6);
        directedGraph.insert(7);
        directedGraph.insert(8);
        directedGraph.connect(1,3);
        directedGraph.connect(2,3);
        directedGraph.connect(3,4);
        directedGraph.connect(3,5);
        directedGraph.connect(4,6);
        directedGraph.connect(4,7);
        directedGraph.connect(5,7);
        directedGraph.connect(7,8);
        Assert.assertFalse(directedGraph.isCyclic());

        directedGraph.connect(7,3);
        Assert.assertTrue(directedGraph.isCyclic());
    }

    @Test
    public void testBellmanFord() {
        Graph.AdjacencyListGraph<Integer> graph = new Graph.AdjacencyListGraph<>(true);
        graph.insert(1);
        graph.insert(2);
        graph.insert(3);
        graph.insert(4);
        graph.connect(1,2, 1.0);
        graph.connect(1,3, 4.0);
        graph.connect(2,3, 2.0);
        graph.connect(2,4, 5.0);
        graph.connect(3,4, 4.0);

        Map<Integer,Double> result =  graph.bellManFord(1);
        Assert.assertEquals(result.get(1), Double.valueOf(0));
        Assert.assertEquals(result.get(2), Double.valueOf(1));
        Assert.assertEquals(result.get(3), Double.valueOf(3));
        Assert.assertEquals(result.get(4), Double.valueOf(6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBellmanFordForNegativeWeightCycle() {
        Graph.AdjacencyListGraph<Integer> graph = new Graph.AdjacencyListGraph<>(true);
        graph.insert(1);
        graph.insert(2);
        graph.insert(3);
        graph.insert(4);
        graph.connect(1,2, 1.0);
        graph.connect(2,3, -2.0);
        graph.connect(3,4, 3.0);
        graph.connect(4,1, -4.0);

        graph.bellManFord(1);
    }

    @Test
    public void testPrims() {
        Graph.AdjacencyListGraph<Integer> graph = new Graph.AdjacencyListGraph<>(true);
        graph.insert(1);
        graph.insert(2);
        graph.insert(3);
        graph.insert(4);
        graph.connect(1,2, 1.0);
        graph.connect(1,3, 4.0);
        graph.connect(2,3, 2.0);
        graph.connect(2,4, 5.0);
        graph.connect(3,4, 4.0);

        Assert.assertEquals(7.0, graph.mstPrims(), 0.0);
        Assert.assertEquals(7.0, graph.prims(), 0.0);
    }

    @Test
    public void testKruskal() {
        Graph.AdjacencyListGraph<Integer> graph = new Graph.AdjacencyListGraph<>(true);
        graph.insert(1);
        graph.insert(2);
        graph.insert(3);
        graph.insert(4);
        graph.connect(1,2, 1.0);
        graph.connect(1,3, 4.0);
        graph.connect(2,3, 2.0);
        graph.connect(2,4, 5.0);
        graph.connect(3,4, 4.0);

        Assert.assertEquals(7d, graph.kruskal(), 0.0);
    }
}
