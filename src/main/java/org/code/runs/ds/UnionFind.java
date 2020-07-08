package org.code.runs.ds;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public interface UnionFind<V> {
    /**
     * Returns the name of the group input node is part of.
     */
    V find(V node);

    /**
     * Fuse groups which x and y are part of.
     */
    void union(V x, V y);

    class Leader<V> {
        public V leader;
        public int rank;
        Leader(V leader, int rank) { this.leader = leader; this.rank = rank; }
    }

    class RankedUnion<V> implements UnionFind<V> {

        private final Map<V, Leader<V>> groupMap;

        public RankedUnion(Collection<V> items) {
            // Every element points to itself as leader.
            groupMap = items.stream().collect(Collectors.toMap(v -> v, v -> new Leader<>(v, 0)));
        }

        @Override
        public V find(V node) {
    //            return _find(node);
            return _pathCompressionFind(node).leader;
        }

        // This performs path compression. Rank remains unchanged during path compression.
        private Leader<V> _pathCompressionFind(V node) {
            Leader<V> nodeLeader = groupMap.get(node);
            if (node == nodeLeader.leader) {
                return nodeLeader;
            }
            Leader<V> actualLeader = _pathCompressionFind(nodeLeader.leader);
            groupMap.put(node, actualLeader);
            return actualLeader;
        }

        // This is naive implementation of find.
        private Leader<V> _find(V node) {
            Leader<V> nodeLeader = groupMap.get(node);
            if (node == nodeLeader.leader) {
                return nodeLeader;
            }
            return _find(nodeLeader.leader);
        }

        @Override
        public void union(V x, V y) {
            Leader<V> xLeader = _pathCompressionFind(x);
            Leader<V> yLeader = _pathCompressionFind(y);
            if (xLeader == yLeader) {
                System.out.println(x + " and " + y + " are already in same group");
                return;
            }

            if (xLeader.rank == yLeader.rank) {
                xLeader.rank++;
                groupMap.put(yLeader.leader, xLeader);
            } else if (xLeader.rank > yLeader.rank) {
                groupMap.put(yLeader.leader, xLeader);
            } else {
                groupMap.put(xLeader.leader, yLeader);
            }
        }
    }
}
