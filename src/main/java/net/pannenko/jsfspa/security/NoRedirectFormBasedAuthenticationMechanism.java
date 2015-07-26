package net.pannenko.jsfspa.security;

import java.util.Map;

import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.api.AuthenticationMechanismFactory;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.servlet.handlers.security.ServletFormAuthenticationMechanism;

public class NoRedirectFormBasedAuthenticationMechanism extends ServletFormAuthenticationMechanism {

  public NoRedirectFormBasedAuthenticationMechanism(FormParserFactory formParserFactory, String name, String loginPage, String errorPage) {
    super(formParserFactory, name, loginPage, errorPage);
  }

  @Override
  protected void storeInitialLocation(final HttpServerExchange exchange) {

  }

  @Override
  protected void handleRedirectBack(final HttpServerExchange exchange) {
  }

  public static final class Factory implements AuthenticationMechanismFactory {
    @Override
    public AuthenticationMechanism create(String mechanismName, FormParserFactory formParserFactory, Map<String, String> properties) {
      return new NoRedirectFormBasedAuthenticationMechanism(formParserFactory, mechanismName, properties.get(LOGIN_PAGE), properties.get(ERROR_PAGE));
    }
  }
}
