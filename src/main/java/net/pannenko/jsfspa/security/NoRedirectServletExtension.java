package net.pannenko.jsfspa.security;

import javax.servlet.ServletContext;

import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;

public class NoRedirectServletExtension implements ServletExtension {
  @Override
  public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext servletContext) {
    deploymentInfo.addAuthenticationMechanism("NOREDIRFORM", new NoRedirectFormBasedAuthenticationMechanism.Factory());
  }
}
