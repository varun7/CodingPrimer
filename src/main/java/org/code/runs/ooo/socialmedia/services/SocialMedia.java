package org.code.runs.ooo.socialmedia.services;

import java.util.Set;
import java.util.UUID;
import org.code.runs.ooo.socialmedia.models.EntityType;
import org.code.runs.ooo.socialmedia.models.Post;
import org.code.runs.ooo.socialmedia.models.SocialMediaUser;

public final class SocialMedia {
  private static PostService postService;
  private static IdentityService identityService;

  static {
    GraphService graphService = new SimpleGraphService();
    postService = new SimplePostService(graphService);
    identityService = new SimpleIdentityService(graphService);
  }

  public static void main(String[] args) {
    SocialMediaUser socialMediaUserA = SocialMediaUser.newBuilder()
        .setEntityId(UUID.randomUUID().toString())
        .setFirstName("Varun")
        .setEntityType(EntityType.USER)
        .build();

    SocialMediaUser socialMediaUserB = SocialMediaUser.newBuilder()
        .setEntityId(UUID.randomUUID().toString())
        .setEntityType(EntityType.USER)
        .setFirstName("Megha")
        .build();

    Post post = Post.newBuilder()
        .setEntityId(UUID.randomUUID().toString())
        .setEntityType(EntityType.POST)
        .setText("This is first post on my facebook")
        .build();

    createUser(socialMediaUserA);
    createUser(socialMediaUserB);
    createPost(socialMediaUserA.entityId(), post);
    likes(socialMediaUserA.entityId(), post.entityId());

    System.out.println("Post is liked by users: \n\n");
    likedBy(post.entityId()).forEach(System.out::println);

    likes(socialMediaUserB.entityId(), post.entityId());
    System.out.println("Post is liked by users: \n\n");
    likedBy(post.entityId()).forEach(System.out::println);
  }

  public static void createUser(SocialMediaUser socialMediaUser) {
    identityService.createUser(socialMediaUser);
  }

  public static void createPost(String userId, Post post) {
    postService.createPost(userId, post);
  }

  public static void likes(String userId, String postId) {
    postService.like(userId, postId);
  }

  public static Set<SocialMediaUser> likedBy(String postId) {
    return postService.likedBy(postId);
  }
}
