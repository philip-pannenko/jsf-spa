This monitors the Sessions

C:\servers\wildfly-8.1.0.Final\bin>jboss-cli.bat

/deployment=jsfspa.war/subsystem=undertow :read-attribute(name=active-sessions)


================


updated loggign docs in standalone.xml


================



use limited primefaces. (only datatable for now)

try to have real links perform the ajax update

dont use any weird hacky linking to navigate.
currently it's something like this?

    <h:form>
      <h:inputText value="#{helloBean.name}"></h:inputText>
      <p:commandButton onclick="History.pushState(null, null, 'welcome')" value="Welcome" actionListener="#{SPASessionBean.setPage('/fragment/welcome.xhtml')}" update="testMe"/>
    </h:form>
    
    ideally,
    
    <h:link outcome="/home.xhtml">Welcome</h:link>
    
    1) see if HistoryJS push state auto listens to linking with a #! or #? or # appended to links.
           
    2) see if the url rewriter has a listener which can take the value after #!/#?/# and add it to the view scope
        #wont work because url rewriter never is hit due to ajax (which is what we're trying to do.)
    
    3) see if <f:ajax/> allows for targets to be updated of if primefaces needs to be there for it
    
    4) place a listener on the different phases to debug how much is actually being done / created. are the non ajaxy parts being recreated still?
    
       potential solution.
       
       on navigation
	       use a global ajax bind for <a> and add the History thing there. (potentially add a class to prevent othe <a> tags from breaking)
	       additionall define a remoteCommand and trigger the remote command from the global ajaxbind
	          have the remote command update the main content target.   
          
          
       on first load with value past the #!/#?/#.
       		find how ocpsoft -faces does it. i think there's a way to create and inject a viewscoped bean.
       		might have to be created/referenced using the context so that it gets reused by the actual page.
       		once we have it. give the viewscope the value so that when the phase render the page, it'll build.
       
       		
       		
   when ajax available
   use jquery to call GET urls.
   	append a tag to the header that says only fragment
   	return only a fragment of the page necessary
   	
   
   when a user decides to bookmark the page or use the url to navigate or clicks refresh
    the url rewriter will not find the tag in the header and will provide the whole page
    we load index and set the current page as the inteded page and the page will load with
      the fragment loaded.
   
=================================

http://stackoverflow.com/questions/2206911/performing-user-authentication-in-java-ee-jsf-using-j-security-check/2207147#2207147

get rid of shiro if not needed. just use annotations and JAAS.
   
   
   @ManagedBean
@ViewScoped
public class Auth {

    private String username;
    private String password;
    private String originalURL;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/home.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
    }

    @EJB
    private UserService userService;

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            request.login(username, password);
            User user = userService.find(username, password);
            externalContext.getSessionMap().put("user", user);
            externalContext.redirect(originalURL);
        } catch (ServletException e) {
            // Handle unknown username/password in request.login().
            context.addMessage(null, new FacesMessage("Unknown login"));
        }
    }

    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
    }

    // Getters/setters for username and password.
}

<h:form>
    <h:outputLabel for="username" value="Username" />
    <h:inputText id="username" value="#{auth.username}" required="true" />
    <h:message for="username" />
    <br />
    <h:outputLabel for="password" value="Password" />
    <h:inputSecret id="password" value="#{auth.password}" required="true" />
    <h:message for="password" />
    <br />
    <h:commandButton value="Login" action="#{auth.login}" />
    <h:messages globalOnly="true" />
</h:form>

=============

here's how to has a password

http://www.javacodegeeks.com/2012/05/secure-password-storage-donts-dos-and.html
https://crackstation.net/hashing-security.htm#javasourcecode


==============

The container 'Maven Dependencies' references non existing library 'C:\Users\user.m2\repository\sun\jdk\jconsole\jdk\jconsole-jdk.jar'

http://stackoverflow.com/questions/25362127/build-path-issue-with-maven-dependencies-jconsole-jdk-jar

-vm 
C:\Program Files\Java\jdk1.7.0_60\bin\javaw.exe


   