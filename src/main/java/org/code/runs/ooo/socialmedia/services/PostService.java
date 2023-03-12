package org.code.runs.ooo.socialmedia.services;

import java.util.Set;
import org.code.runs.ooo.socialmedia.models.Comment;
import org.code.runs.ooo.socialmedia.models.Post;
import org.code.runs.ooo.socialmedia.models.SocialMediaUser;

public interface PostService {
  void createPost(String userId, Post post);

  void updatePost(Post post);

  void deletePost(String postId);

  // User(userId) ---Comment---> Post (postId)
  void comment(String userId, String parentId, Comment comment);

  // User(userId) ---Likes ---> Post (postId)
  void like(String userId, String postId);

  Set<SocialMediaUser> likedBy(String postId);
}
