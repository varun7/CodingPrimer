package org.code.runs.ds;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;

public interface Graph<V> {

    void insert(V vertex);

    void delete(V vertex);

    Set<V> vertices();

    Set<V> successors(V vertex);

    Set<V> predecessors(V vertex);

    void connect(V v1, V v2);

    void connect(V v1, V v2, double weight);

    boolean isPresent(V vertex);

    double edgeWeight(V v1, V v2);

    boolean bfs(V startingVertex, V element);

    boolean bfs(V element);

    boolean dfs(V startingVertex, V element);

    boolean dfs(V element);

    Map<V, Double> singleSourceShortestPathDijkstra(V source);

    Map<V, Double> dijkstra(V source);

    double mstPrims();

    double kruskal();

    /**
     * Only for directed graph.
     */
    void topologicalSort();

    boolean isCyclic();

    void stronglyConnectedComponents();

    class AdjacencyListGraph<V> implements Graph<V> {

        class Edge {
            private final double weight;
            private final V to;
            private final V from;

            public Edge(V from, V to, double weight) {
                this.from = from;
                this.weight = weight;
                this.to = to;
            }


            public Edge(V from, V to) {
                this.from = from;
                this.weight = 1;
                this.to = to;
            }
        }

        private Map<V, Set<Edge>> vertexMap;
        private boolean isDirected = false;

        public AdjacencyListGraph(boolean isDirected) {
            vertexMap = new HashMap<>();
            this.isDirected = isDirected;
        }

        public AdjacencyListGraph() {
            vertexMap = new HashMap<>();
        }

        @Override
        public void insert(V vertex) {
            if (isPresent(vertex)) {
                throw new IllegalArgumentException("Tried to add duplicate node.");
            }
            vertexMap.put(vertex, new HashSet<>());
        }

        @Override
        public void delete(V vertex) {
            if (!isPresent(vertex)) {
                throw new IllegalArgumentException("Vertex is not present in the graph.");
            }
            throw new UnsupportedOperationException("Not yet implemented.");
        }

        @Override
        public Set<V> vertices() {
            return vertexMap.keySet();
        }

        protected Set<Edge> edges() {
            Set<Edge> edgeSet = new HashSet<>();
            for (Map.Entry<V, Set<Edge>> entry: vertexMap.entrySet()) {
                edgeSet.addAll(entry.getValue());
            }
            return edgeSet;
        }

        @Override
        public Set<V> successors(V vertex) {
            if (isPresent(vertex)) {
                return vertexMap.get(vertex).stream().map(e -> e.to).collect(Collectors.toSet());
            }
            throw new IllegalArgumentException("node is not present in graph");
        }

        @Override
        public Set<V> predecessors(V vertex) {
            if (!isPresent(vertex)) {
                throw new IllegalArgumentException("Node is not present in graph.");
            }
            final Set<V> predecessors = new HashSet<>();
            vertexMap.entrySet()
                    .forEach(entry ->entry.getValue()
                            .forEach(edge -> {
                                if (edge.to == vertex) {
                                    predecessors.add(entry.getKey());
                                }
                            })
                    );
            return predecessors;
        }

        @Override
        public void connect(V v1, V v2) {
            if (!isPresent(v1) || !isPresent(v2)) {
                throw new IllegalArgumentException("One or both node not present in graph.");
            }
            vertexMap.get(v1).add(new Edge(v1, v2));

            if (!isDirected) {
                vertexMap.get(v2).add(new Edge(v2, v1));
            }
        }

        @Override
        public void connect(V v1, V v2, double weight) {
            if (!isPresent(v1) || !isPresent(v2)) {
                throw new IllegalArgumentException("One or both node not present in graph.");
            }
            vertexMap.get(v1).add(new Edge(v1, v2, weight));

            if (!isDirected) {
                vertexMap.get(v2).add(new Edge(v2, v1, weight));
            }
        }

        @Override
        public boolean isPresent(V vertex) {
            return vertexMap.containsKey(vertex);
        }

        @Override
        public double edgeWeight(V v1, V v2) {
            Optional<Edge> edge = vertexMap.get(v1).stream().filter(e -> e.to == v2).findFirst();
            if (edge.isPresent()) {
                return edge.get().weight;
            }
            throw new IllegalArgumentException("Passed nodes are not connected.");
        }

        @Override
        public boolean bfs(V startingVertex, V element) {
            Set<V> explored = new HashSet<>();
            while(startingVertex != null) {
                if (_bfs(startingVertex, element, explored)) {
                    return true;
                }
                startingVertex = nextUnexploredNode(explored);
            }
            return false;
        }

        private boolean _bfs(V startingVertex, V element, Set<V> explored) {
            Queue<V> queue = new LinkedBlockingQueue<>();
            queue.add(startingVertex);

            while (!queue.isEmpty()) {
                V front = queue.remove();
                if (!explored.contains(front)) {
                    explored.add(front);
                    if (front == element) {
                        return true;
                    }
                    queue.addAll(this.successors(front));
                }
            }
            return false;
        }

        @Override
        public boolean bfs(V element) {
            Set<V> unexplored = new HashSet<>(this.vertices());
            while (!unexplored.isEmpty()) {
                V start = unexplored.stream().findFirst().get();
                if (_bfsUnexplored(start, element, unexplored)) {
                    return true;
                }
            }
            return false;
        }

        private boolean _bfsUnexplored(V start, V element, Set<V> unexplored) {
            Queue<V> queue = new LinkedBlockingQueue<>();
            queue.add(start);

            while (!queue.isEmpty()) {
                V vertex = queue.remove();
                unexplored.remove(vertex);

                if (vertex.equals(element)) {
                    return true;
                }

                queue.addAll(this.successors(vertex)
                            .stream()
                            .filter(v -> unexplored.contains(v))
                            .collect(Collectors.toList()));
            }
            return false;
        }

        @Override
        public boolean dfs(V startingVertex, V element) {
            Set<V> explored = new HashSet<>();
            while(startingVertex != null) {
                if (_dfs(startingVertex, element, explored)) {
                    return true;
                }
                startingVertex = nextUnexploredNode(explored);
            }
            return false;
        }

        private boolean _dfs(V startingVertex, V element, Set<V> explored) {
            if (startingVertex == element) {
                return true;
            }
            if (explored.contains(startingVertex)) {
                return false;
            }

            explored.add(startingVertex);
            for (V vertex: this.successors(startingVertex)) {
                if (!explored.contains(vertex) && _dfs(vertex, element, explored)) {
                    return true;
                }
            }
            return false;
        }

        public boolean dfs(V element) {
            Set<V> unexplored = new HashSet<>(this.vertices());
            while (!unexplored.isEmpty()) {
                V start = unexplored.stream().findFirst().get();
                if (_dfsUnexplored(start, element, unexplored)) {
                    return true;
                }
            }
            return false;
        }

        private boolean _dfsUnexplored(V start, V element, Set<V> unexplored) {
            if (!unexplored.contains(start)) {
                return false;
            }
            unexplored.remove(start);
            if (start.equals(element)) {
                return true;
            }

            for (V vertex: this.successors(start)) {
                if (unexplored.contains(vertex) && _dfsUnexplored(vertex, element, unexplored)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void topologicalSort() {
            System.out.println("Topological sort of the graph");
            Set<V> unexplored = new HashSet<>(this.vertices());
            Stack<V> topologicalOrder = new Stack<>();
            while(!unexplored.isEmpty()) {
                V start = unexplored.stream().findAny().get();
                _topologicalSort(start, unexplored, topologicalOrder);
            }

            while (!topologicalOrder.isEmpty()) {
                System.out.print(topologicalOrder.pop() + " ");
            }
        }

        private void _topologicalSort(V startingVertex, Set<V> unexplored, Stack<V> topologicalOrder) {
            unexplored.remove(startingVertex);
            for (V vertex: this.successors(startingVertex)) {
                if (unexplored.contains(vertex)) {
                    _topologicalSort(vertex, unexplored, topologicalOrder);
                }
            }
            topologicalOrder.push(startingVertex);
        }

        @Override
        public boolean isCyclic() {
            Set<V> unexplored = new HashSet<>(this.vertices());
            while (!unexplored.isEmpty()) {
                V startingVertex = unexplored.stream().findAny().get();
                if (_isCyclic(startingVertex, unexplored, new HashSet<>())) {
                    return true;
                }
            }
            return false;
        }

        private boolean _isCyclic(V startingVertex, Set<V> unexplored, Set<V> ancestor) {
            if (ancestor.contains(startingVertex)) {
                return true;
            }

            if (!unexplored.contains(startingVertex)) {
                return false;
            }
            unexplored.remove(startingVertex);
            ancestor.add(startingVertex);

            for(V vertex: this.successors(startingVertex)) {
                // check for self-loop and cycles.
                if (vertex == startingVertex || _isCyclic(vertex, unexplored, ancestor)) {
                    return true;
                }
            }
            // Now that all it's child nodes have been processed and none of them have had a back edge
            // Remove this vertex from the ancestor set.
            ancestor.remove(startingVertex);
            return false;
        }

        @Override
        public void stronglyConnectedComponents() {
            Set<V> explored = new HashSet<>();
            List<V> exploredOrder = new ArrayList<>();

            // Step-1: DFS on reversed graph and keep track of explored time of each node.
            V startingVertex = nextUnexploredNode(explored);
            while (startingVertex != null) {
                _reverseDFS(startingVertex, explored, exploredOrder);
                startingVertex = nextUnexploredNode(explored);
            }

            // Step-2: DFS on original graph in descending order of explored time.
            Collections.reverse(exploredOrder);
            explored.clear();
            for (V leaderVertex: exploredOrder) {
                if (!explored.contains(leaderVertex)) {
                    _printDFS(leaderVertex, explored);
                    System.out.println();
                }
            }
        }

        private void _reverseDFS(V startingVertex, Set<V> explored, List<V> exploredOrder) {
            explored.add(startingVertex);
            for (V vertex: this.predecessors(startingVertex)) { // this is the only change for reverse DFS
                if (!explored.contains(startingVertex)) {
                    _reverseDFS(vertex, explored, exploredOrder);
                }
            }
            exploredOrder.add(startingVertex);
        }

        private void _printDFS(V startingVertex, Set<V> explored) {
            explored.add(startingVertex);
            System.out.print(startingVertex + " ");
            for (V vertex: this.successors(startingVertex)) {
                if (!explored.contains(vertex)) {
                    _printDFS(vertex, explored);
                }
            }
        }

        @Override
        public Map<V, Double> singleSourceShortestPathDijkstra(V source) {
            // Initializations.
            final Double INF = Double.MAX_VALUE - 1;
            Set<V> explored = new HashSet<>();
            // Creates a copy of set of vertices.
            Set<V> unexplored = new HashSet<>(this.vertices());
            Map<V, Double> distances = this.vertices().stream()
                                            .filter(v ->  v != source)
                                            .collect(Collectors.toMap(v -> v, v -> INF));
            distances.put(source, 0d);

            // We will take one vertex from unexplored and move it to explored set.
            while (!unexplored.isEmpty()) {
                V candidate = minimumDistanceVertex(explored, distances);

                // Add candidate to explored set.
                explored.add(candidate);
                unexplored.remove(candidate);

                // Revisit distance of all neighbors of candidate.
                this.successors(candidate)
                        .forEach(v -> {
                            Double neighborDistance = distances.get(candidate) + this.edgeWeight(candidate, v);
                            if (distances.get(v) > neighborDistance) {
                                distances.put(v, neighborDistance);
                            }
                        });

            }
            return distances;
        }

        @EqualsAndHashCode
        private class Pair {
            public V node;
            public double distance;

            public Pair(V first, double second) {
                this.node = first;
                this.distance = second;
            }
        }

        @Override
        public Map<V, Double> dijkstra(V source) {
            final Double INF = Double.MAX_VALUE - 1;

            // Initializations.
            Set<V> explored = new HashSet<>();
            Map<V, Double> distances = this.vertices().stream().collect(Collectors.toMap(v -> v, v-> INF));
            distances.put(source, 0d);
            TreeSet<Pair> treeSet = createTreeSet(source);

            // We will take one vertex from unexplored and move it to explored set.
            while (!treeSet.isEmpty()) {

                // Remove minimum distance vertex and mark it as explored.
                Pair candidate = treeSet.pollFirst();
                explored.add(candidate.node);

                // Revisit distance of all neighbors of candidate.
                this.successors(candidate.node)
                        .stream()
                        .filter(v -> !explored.contains(v))
                        .forEach(v -> {
                            Double neighborDistance = distances.get(candidate.node) + this.edgeWeight(candidate.node, v);
                            if (distances.get(v) > neighborDistance) {
                                distances.put(v, neighborDistance);

                                // Update tree set.
                                Pair newPair = new Pair(v, neighborDistance);
                                treeSet.remove(newPair);
                                treeSet.add(newPair);
                            }
                        });

            }
            return distances;
        }

        protected Map<V, Double> bellManFord(V source) {
            // Step-1: Inititalization.
            Double INF = 1000d;
            Map<V, Double> distances = this.vertices().stream()
                    .filter(v -> !v.equals(source))
                    .collect(Collectors.toMap(v -> v, v -> INF));
            distances.put(source, 0d);

            // Step-2: Relax edges repeatedly.
            for (int i=0; i< this.vertices().size(); i++) {
                for (Edge e: edges()) {
                    double neighbourDistance = e.weight + distances.get(e.from);
                    if (neighbourDistance < distances.get(e.to)) {
                        distances.put(e.to, neighbourDistance);
                    }
                }
            }

            // Step:-3: Check for negative weight cycles.
            for (Edge e: edges()) {
                double neighbourDistance = e.weight + distances.get(e.from);
                if (neighbourDistance < distances.get(e.to)) {
                    System.out.println("Graph contains negative weight cycle.");
                    throw new IllegalArgumentException("Graph contains negative weight cycle.");
                }
            }
            return distances;
        }

        private V minimumDistanceVertex(Set<V> explored, final Map<V, Double> distances) {
            Comparator<V> comparator = (o1, o2) -> {
                if (distances.get(o1).doubleValue() == distances.get(o2).doubleValue()) {
                    return 0;
                } else if (distances.get(o1) > distances.get(o2)) {
                    return 1;
                } else {
                    return -1;
                }
            };
            return this.vertices().stream()
                    .filter(v -> !explored.contains(v)) // this is same as unexplored set, we can use that directly.
                    .min(comparator)
                    .get();
        }

        @Override
        public double mstPrims() {
            System.out.println("\n\nNaive implementation of prims:");

            // Step-1 initializations.
            V source = this.vertices().stream().findAny().get();
            Set<V> unexplored = this.vertices().stream()
                    .filter(v -> v != source)
                    .collect(Collectors.toSet());
            Set<V> explored = new HashSet<>();
            Map<V, Double> distances = unexplored.stream()
                    .collect(Collectors.toMap(v -> v, v -> Double.MAX_VALUE -1 ));
            distances.put(source, 0d);
            double mstCost = 0;

            // At every iteration we move a vertex from unexplored to explored set.
            while (!unexplored.isEmpty()) {
                // Step-2: Pick unexplored vertex at minimum distance from explored set of vertices.
                V candidate = minimumDistanceVertex(explored, distances);
                explored.add(candidate);
                unexplored.remove(candidate);

                mstCost += distances.get(candidate);
                System.out.println(candidate + " cost = " + distances.get(candidate));

                // Step-3: For the newly explored vertex re-compute the distances.
                this.successors(candidate).stream()
                        .filter(v -> !explored.contains(v))
                        .forEach(v -> {
                            double neighborDistance = this.edgeWeight(candidate, v);
                            if (neighborDistance < distances.get(v)) {
                                distances.put(v, neighborDistance);
                            }
                        });
            }
            System.out.println("mst cost = " + mstCost);
            return mstCost;
        }

        // Efficient implememtation of prims using TreeSet.
        // Returns cost of minimum spanning tree.
        protected double prims() {
            System.out.println("\n\nPrims using balanced binary search tree:");
            Double INF = 10000d;

            // Initialization.
            V source = this.vertices().stream().findAny().get();
            TreeSet<Pair> treeSet = createTreeSet(source);
            Set<V> explored = new HashSet<>();
            Map<V, Double> distances = this.vertices().stream().collect(Collectors.toMap(v -> v, v-> INF));
            distances.put(source, 0d);

            double mstCost = 0;

            while (!treeSet.isEmpty()) {
                // Step-2: Pick unexplored vertex at minimum distance from explored set of vertices.
                Pair candidate = treeSet.pollFirst();
                explored.add(candidate.node);

                mstCost += distances.get(candidate.node);
                System.out.println(candidate.node + " cost = " + distances.get(candidate.node));

                // Step-3: For the newly explored vertex re-compute the distances.
                this.successors(candidate.node).stream()
                        .filter(v -> !explored.contains(v))
                        .forEach(v -> {
                                double neighborDistance = this.edgeWeight(candidate.node, v);
                                double distance = distances.get(v);
                                if (neighborDistance < distance) {
                                    distances.put(v, neighborDistance);

                                    // Update tree set
                                    treeSet.remove(new Pair(v, distance));
                                    treeSet.add(new Pair(v, neighborDistance));
                                }
                        });
            }
            System.out.println("mst cost = " + mstCost);
            return mstCost;
        }

        private TreeSet<Pair> createTreeSet(V source) {
            Double INF = 10000d;

            // So if there are 2 nodes in graph with same distance
            // from origin then only one entry will be made, it can be fixed by updating
            // compare method to act as equals also.
            Comparator<Pair> comp = (p,q) -> {
                if (p.node == q.node) {
                    return 0;
                }
                if (p.distance == q.distance) {
                    return p.hashCode() - q.hashCode();
                }
                return Double.compare(p.distance, q.distance);
            };

            // Treeset and TreeMap both are implemented on balanced binary search tree.
            // We are using treeSet to optimally obtain lowest distance node.
            TreeSet<Pair> treeSet = new TreeSet<>(comp);
            this.vertices().stream().filter(v -> !v.equals(source)).forEach(v -> treeSet.add(new Pair(v, INF)));
            treeSet.add(new Pair(source, 0d));

            return treeSet;
        }

        // Returns cost of minimum spanning tree.
        @Override
        public double kruskal() {
            // Initialization.
            UnionFind<V> group = new UnionFind.RankedUnion<>(new HashSet<>(this.vertices()));
            PriorityQueue<Edge> queue = new PriorityQueue<>((a,b) -> Double.compare(a.weight, b.weight));
            queue.addAll(this.edges());
            double mstCost = 0;

            while (!queue.isEmpty()) {
                Edge edge = queue.poll();
                V fromGroup = group.find(edge.from);
                V toGroup = group.find(edge.to);

                if (fromGroup != toGroup) {
                    System.out.println("Edge from : " + edge.from + " to : " + edge.to + " added to Spanning tree with cost = " + edge.weight);
                    mstCost += edge.weight;
                    group.union(edge.from, edge.to);
                }
            }
            System.out.println("mst cost = " + mstCost);
            return mstCost;
        }

        private V nextUnexploredNode(Set<V> explored) {
            for (V v: vertices()) {
                if (!explored.contains(v)) {
                    return v;
                }
            }
            return null;
        }
    }
}
