package net.pannenko.jsfspa.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import net.pannenko.jsfspa.domain.HibernateUtil;
import net.pannenko.jsfspa.domain.Todo;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class TodoCrudBean implements Serializable {

  private static final Logger log = LoggerFactory.getLogger(TodoCrudBean.class);

  private Todo todo = new Todo();

  private Long fooId;

  public TodoCrudBean() {
    log.info("TodoCrudBean constructor() " + toString());
  }

  public void init() {
    log.info("TodoCrudBean init fooId(" + fooId + ") " + toString());

    if (fooId != null) {
      Session s = HibernateUtil.getSessionFactory().openSession();
      todo = (Todo) s.get(Todo.class, fooId);
      s.close();
    } else {
      todo = new Todo();
    }
  }

  public Todo getTodo() {
    return todo;
  }

  public void setTodo(Todo todo) {
    this.todo = todo;
  }

  public Long getFooId() {
    return fooId;
  }

  public void setFooId(Long fooId) {
    this.fooId = fooId;
  }

  public void save() throws IOException {
    Session s = HibernateUtil.getSessionFactory().openSession();
    try {
      s.saveOrUpdate(todo);
      s.flush();
    } finally {
      s.close();
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Successful", null));
      ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
      externalContext.redirect(externalContext.getRequestContextPath() + "/todolist.xhtml");
    }

  }

}
