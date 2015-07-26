package net.pannenko.jsfspa.bean;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
@Named
public class LoginBean implements Serializable {

  private static final Logger log = LoggerFactory.getLogger(LoginBean.class);

  private String username;
  private String password;
  private String originalURL;

  public LoginBean() {
    log.debug("LoginBean Construct " + toString());
  }

  @PostConstruct
  private void init() {
    log.debug("LoginBean Post Construct " + toString());
  }

  public void login() throws IOException {
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = context.getExternalContext();
    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

    try {
      request.login(username, password);
      Principal user = request.getUserPrincipal();
      externalContext.getSessionMap().put("user", user);
      externalContext.redirect(request.getContextPath());
    } catch (ServletException e) {
      context.addMessage(null, new FacesMessage("Unknown login"));
    }
  }

  public void logout() throws IOException {
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    externalContext.invalidateSession();
    externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getOriginalURL() {
    return originalURL;
  }

  public void setOriginalURL(String originalURL) {
    this.originalURL = originalURL;
  }

}
