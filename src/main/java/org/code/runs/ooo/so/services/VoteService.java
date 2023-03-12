package org.code.runs.ooo.so.services;

import org.code.runs.ooo.so.models.CommentType;

public interface VoteService {
  void upvote(String id, CommentType type);

  void downVote(String id, CommentType type);
}
