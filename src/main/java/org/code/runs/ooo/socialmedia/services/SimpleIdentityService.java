package org.code.runs.ooo.socialmedia.services;


import org.code.runs.ooo.socialmedia.models.SocialMediaUser;

public final class SimpleIdentityService implements IdentityService {
  private final GraphService graphService;

  public SimpleIdentityService(GraphService graphService) {
    this.graphService = graphService;
  }

  @Override
  public void createUser(SocialMediaUser socialMediaUser) {
    graphService.createEntity(socialMediaUser);
  }
}
