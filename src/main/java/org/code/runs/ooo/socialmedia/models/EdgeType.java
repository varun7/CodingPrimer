package org.code.runs.ooo.socialmedia.models;

public enum EdgeType {
  // From Post to User
  AUTHORED_BY,
  // From User to Post
  AUTHOR,
  COMMENTED_BY,
  COMMENTS,
  LIKED_BY,
  LIKES;
}
