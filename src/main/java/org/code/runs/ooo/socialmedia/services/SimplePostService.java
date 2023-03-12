package org.code.runs.ooo.socialmedia.services;

import org.code.runs.ooo.socialmedia.models.Comment;
import org.code.runs.ooo.socialmedia.models.Edge;
import org.code.runs.ooo.socialmedia.models.EdgeType;
import org.code.runs.ooo.socialmedia.models.Entity;
import org.code.runs.ooo.socialmedia.models.GraphQuery;
import org.code.runs.ooo.socialmedia.models.Post;
import org.code.runs.ooo.socialmedia.models.SocialMediaUser;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class SimplePostService implements PostService {

  private final GraphService graphService;

  public SimplePostService(GraphService graphService) {
    this.graphService = graphService;
  }

  @Override
  public void createPost(String userId, Post post) {
    graphService.createEntity(post);
    Edge authoredBy = Edge.newBuilder()
        .setEdgeId(UUID.randomUUID().toString())
        .setEdgeType(EdgeType.AUTHORED_BY)
        .setFrom(post.entityId())
        .setTo(userId)
        .build();

    Edge authors = Edge.newBuilder()
        .setEdgeId(UUID.randomUUID().toString())
        .setFrom(userId)
        .setTo(post.entityId())
        .setEdgeType(EdgeType.AUTHOR)
        .build();
    graphService.addEdge(authors);
    graphService.addEdge(authoredBy);
  }

  @Override
  public void updatePost(Post post) {
    graphService.updateEntity(post);
  }

  @Override
  public void deletePost(String postId) {
    graphService.deleteEntity(postId);
  }

  @Override
  public void comment(String userId, String parentId, Comment comment) {
    Edge commentedBy = Edge.newBuilder()
        .setEdgeId(UUID.randomUUID().toString())
        .setEdgeType(EdgeType.COMMENTED_BY)
        .setFrom(comment.entityId())
        .setTo(userId)
        .build();
    Edge userComments = Edge.newBuilder()
        .setEdgeId(UUID.randomUUID().toString())
        .setEdgeType(EdgeType.COMMENTS)
        .setFrom(userId)
        .setTo(comment.entityId())
        .build();
    graphService.createEntity(comment);
    graphService.addEdge(commentedBy);
    graphService.addEdge(userComments);
  }

  @Override
  public void like(String userId, String postId) {
    Edge likes = Edge.newBuilder()
        .setEdgeId(UUID.randomUUID().toString())
        .setEdgeType(EdgeType.LIKES)
        .setFrom(userId)
        .setTo(postId)
        .build();
    Edge likedBy = Edge.newBuilder()
        .setEdgeId(UUID.randomUUID().toString())
        .setEdgeType(EdgeType.LIKED_BY)
        .setFrom(postId)
        .setTo(userId)
        .build();
    graphService.addEdge(likedBy);
    graphService.addEdge(likes);
  }

  @Override
  public Set<SocialMediaUser> likedBy(String postId) {
    return graphService.query(GraphQuery.newBuilder()
        .setEntityId(postId)
        .setRelationsShip(EdgeType.LIKED_BY)
        .build())
        .stream()
        .map(SimplePostService::validateUserAndCast)
        .collect(Collectors.toSet());
  }

  private static SocialMediaUser validateUserAndCast(Entity entity) {
    if (entity instanceof SocialMediaUser) {
      return (SocialMediaUser) entity;
    }
    throw new IllegalArgumentException("LIKED_BY pointing to entity that is not user");
  }
}
