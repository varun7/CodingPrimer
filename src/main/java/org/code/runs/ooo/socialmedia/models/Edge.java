package org.code.runs.ooo.socialmedia.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Edge {
  public abstract  String edgeId();
  public abstract  EdgeType edgeType();
  public abstract  String from();
  public abstract  String to();

  public static Builder newBuilder() {
    return new AutoValue_Edge.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setEdgeId(String value);

    public abstract Builder setEdgeType(EdgeType value);

    public abstract Builder setFrom(String value);

    public abstract Builder setTo(String value);

    public abstract Edge build();
  }
}
