package net.pannenko.jsfspa.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pannenko.jsfspa.domain.HibernateUtil;
import net.pannenko.jsfspa.domain.Todo;

@ViewScoped
@Named
public class TodoBean implements Serializable{

  private static final Logger log = LoggerFactory.getLogger(TodoBean.class);
  private List<Todo> todos;
  private transient DataModel<Todo> model;

  public TodoBean() {
    log.debug("TodoBean constructor() " + toString());
  }
  
  @SuppressWarnings("unchecked")
  @PostConstruct
  private void init() {
    log.debug("TodoBean init() " + toString());
    Session s = HibernateUtil.getSessionFactory().openSession();
    todos = s.createQuery("from Todo").list();
    s.close();
  }

  public DataModel<Todo> getModel() {
    if (model == null) {
      model = new ListDataModel<Todo>(todos);
    }

    return model;
  }

  public List<Todo> getTodos() {
    return todos;
  }

  public void setTodos(List<Todo> todos) {
    this.todos = todos;
  }

  public void setModel(DataModel<Todo> model) {
    this.model = model;
  }

}
