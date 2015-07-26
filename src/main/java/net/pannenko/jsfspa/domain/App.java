package net.pannenko.jsfspa.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {

  private static final Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    logger.trace("this is finest");
    logger.debug("this is finer");
    logger.info("this is fine");
    logger.warn("this is config");
    logger.error("this is info");
  }

  public void foo() {
    logger.trace("this is finest");
    logger.debug("this is finer");
    logger.info("this is fine");
    logger.warn("this is config");
    logger.error("this is info");
  }
}
