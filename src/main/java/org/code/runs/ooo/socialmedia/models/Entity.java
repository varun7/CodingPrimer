package org.code.runs.ooo.socialmedia.models;

import javax.annotation.Nonnull;

public interface Entity {
  @Nonnull
  String entityId();

  @Nonnull
  EntityType entityType();
}
