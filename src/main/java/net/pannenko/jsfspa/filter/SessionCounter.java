package net.pannenko.jsfspa.filter;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class SessionCounter implements HttpSessionListener {
  private static final Logger logger = LoggerFactory.getLogger(SessionCounter.class);
  private static int count;

  @Override
  public void sessionCreated(HttpSessionEvent event) {
    logger.info("session created: " + event.getSession().getId());
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent event) {
    logger.info("session destroyed: " + event.getSession().getId());
  }

  public static int getCount() {
    return count;
  }

}