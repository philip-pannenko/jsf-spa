package net.pannenko.jsfspa;

import java.util.Arrays;

import org.hibernate.Session;

import net.pannenko.jsfspa.domain.AppRole;
import net.pannenko.jsfspa.domain.AppUser;
import net.pannenko.jsfspa.domain.HibernateUtil;
import net.pannenko.jsfspa.domain.Todo;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
  /**
   * Create the test case
   *
   * @param testName
   *          name of the test case
   */
  public AppTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(AppTest.class);
  }

  /**
   * Rigourous Test :-)
   */
  public void testApp() {
    assertTrue(true);
  }

  public void search() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    AppUser a = (AppUser) session.get(AppUser.class, 0L);
    if (a != null) {
      System.out.println(a.getLogin());
    }

    else {
      System.out.println("not found");

    }

    session.close();
  }

  public void createTodoEntities() {
    Session session = null;
    try {
      session = HibernateUtil.getSessionFactory().openSession();
      session.beginTransaction();

      session.save(new Todo("a", "a2"));
      session.save(new Todo("b", "b2"));
      session.save(new Todo("c", "c2"));
      session.save(new Todo("d", "d2"));
      session.save(new Todo("e", "e2"));

      session.getTransaction().commit();

    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  public void testHibernate() {
    Session session = null;
    try {
      session = HibernateUtil.getSessionFactory().openSession();
      session.beginTransaction();

      String rawPassword = "test";

      AppRole roleAdmin = new AppRole("admin");
      session.save(roleAdmin);

      AppRole roleUser = new AppRole("user");
      session.save(roleUser);

      AppUser user = new AppUser("firstuser", rawPassword);
      user.setRoles(Arrays.asList(roleAdmin, roleUser));
      session.save(user);

      AppUser user2 = new AppUser("seconduser", rawPassword);
      user2.setRoles(Arrays.asList(roleUser));
      session.save(user2);

      session.getTransaction().commit();
      session.close();
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }
}
