package org.code.runs.ooo.so.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Comment {
  public abstract String commentId();
  public abstract String content();
  public abstract String parentId();
  public abstract String commenterId();
  public abstract CommentType type();

  public static Builder newBuilder() {
    return new AutoValue_Comment.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setCommentId(String value);

    public abstract Builder setContent(String value);

    public abstract Builder setParentId(String value);

    public abstract Builder setCommenterId(String value);

    public abstract Builder setType(CommentType value);

    public abstract Comment build();
  }
}
