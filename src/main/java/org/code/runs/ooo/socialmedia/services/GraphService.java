package org.code.runs.ooo.socialmedia.services;

import org.code.runs.ooo.socialmedia.models.Edge;
import org.code.runs.ooo.socialmedia.models.Entity;
import org.code.runs.ooo.socialmedia.models.GraphQuery;
import java.util.Set;

public interface GraphService {
  void createEntity(Entity entity);

  void deleteEntity(String entityId);

  void addEdge(Edge edge);

  void updateEntity(Entity entity);

  Entity getEntity(String entityId);

  Set<Entity> query(GraphQuery query);
}
