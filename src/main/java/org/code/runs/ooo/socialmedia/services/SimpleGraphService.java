package org.code.runs.ooo.socialmedia.services;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.code.runs.ooo.socialmedia.models.Edge;
import org.code.runs.ooo.socialmedia.models.Entity;
import org.code.runs.ooo.socialmedia.models.GraphQuery;

public final class SimpleGraphService implements GraphService {
  private final Map<String, Set<Edge>> graph;
  private final Map<String, Entity> entityMap;

  public SimpleGraphService() {
    entityMap = new HashMap<>();
    graph = new HashMap<>();
  }

  @Override
  public void createEntity(Entity entity) {
    Preconditions.checkArgument(!entityMap.containsKey(entity.entityId()), "Entity is already present.");
    entityMap.put(entity.entityId(), entity);
    graph.put(entity.entityId(), new HashSet<>());
  }

  @Override
  public void deleteEntity(String entityId) {
    Preconditions.checkArgument(!entityMap.containsKey(entityId), "Entity is not present.");
    entityMap.remove(entityId);
    graph.remove(entityId);
  }

  @Override
  public void addEdge(Edge edge) {
    Preconditions.checkArgument(entityMap.containsKey(edge.from()) && entityMap.containsKey(edge.to()),
        "One or more entity is not present and cannot be connected.");
    Set<Edge> fromEdges = graph.get(edge.from());
    fromEdges.add(edge);
  }

  @Override
  public void updateEntity(Entity entity) {
    Preconditions.checkArgument(!entityMap.containsKey(entity.entityId()), "Entity is already present.");
    entityMap.put(entity.entityId(), entity);
  }

  @Override
  public Entity getEntity(String entityId) {
    Preconditions.checkArgument(!entityMap.containsKey(entityId), "Entity is not present");
    return entityMap.get(entityId);
  }

  @Override
  public Set<Entity> query(GraphQuery query) {
    return graph.get(query.entityId())
        .stream()
        .filter(e -> e.edgeType().equals(query.relationsShip()))
        .map(e -> entityMap.get(e.to()))
        .collect(Collectors.toSet());
  }
}
