# JSF-SPA

Work to demonstrate the feasibility of making a secure semi-stateless JSF SPA using minimal to no external frameworks.
Focus was to hold onto the benefits and convenience of jsf/bean development without introducing any hacky code.

###Key features
* No session is created until after a user logs in
* GET Requests along with Ajax to navigate without browser needing to changes pages
* History.js to manage the browser back/forward functionality without refreshing whole page
* Pages can be refreshed and bookmarked
* Application server handles authentication / authorization
* Backwards compatible to work without Javascript working

####Libraries
* Wildfly 8.1
* Hibernate 4.3.5
* JSF 2.2
* History.js
* JQuery 2.1.4

## Notes

Used a HttpSessionListener to track how many sessions were being made and where they were made.

Reused a template for all pages but tracked their usability depending on header information. When a page was loaded via an Ajax call the header requested for a fragment Layout. If the page was refreshed or hit from a bookmark, then no header information is present and a stand-alone layout is provided. Each jsf page had the following up top.
```
template="#{headerValues.Fragment[0] == 'true' ? '/WEB-INF/template/fragment-page.xhtml' : '/WEB-INF/template/standalone-page.xhtml'}" 
```
Each Ajax call to the server looks for or places a Fragment header piece. 
```
url : State.url,
  headers : {Fragment : "true"},
  success : function(data, a, b, c) {
    if(b.getResponseHeader('Fragement') === 'false') {
      window.location.href = '/jsfspa/login.xhtml';
    } else {
      $("#testMe").html(data);
    }
  }
```

Implemented a custom AuthenticationMechanism to prevent creation of a session object. What ended up happeneing is with the default FORM AuthenticationMechanism, if the servley detected that a secure page was accessed, a Session object was created to save the URL into. Then the login page would appear and upon a successful long, the saved URL would be accessed. I implemented a custom AuthenticationMechanism and overrode any creation of session object. 
```
  public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext servletContext) {
    deploymentInfo.addAuthenticationMechanism("NOREDIRFORM", new NoRedirectFormBasedAuthenticationMechanism.Factory());
  }
```

## Wildfly Modifcations

Data Source
```
<datasource jndi-name="java:/jsfspaDS" pool-name="PostgrePool" enabled="true">
  <connection-url>jdbc:postgresql://localhost/scafold</connection-url>
  <driver>postgres</driver>
  <security>
    <user-name>postgres</user-name>
    <password>password</password>
  </security>
</datasource>
```
Security Domain
```
<security-domain name="jsfspa-security-domain" cache-type="default">
  <authentication>
    <login-module code="Database" flag="required">
      <module-option name="dsJndiName" value="java:/jsfspaDS"/>
      <module-option name="principalsQuery" value="SELECT PASSWORD FROM APPUSER WHERE LOGIN  = ?"/>
      <module-option name="rolesQuery" value="SELECT R.NAME, 'Roles' FROM USER_ROLE UR INNER JOIN APPROLE R ON R.ID = UR.ROLE_ID INNER JOIN APPUSER U ON U.ID = UR.USER_ID WHERE U.LOGIN = ?"/>
    </login-module>
  </authentication>
</security-domain>
```
Logging
```
<logger category="net.pannenko">
  <level name="ALL"/>
</logger>
```

## Upcomming changes

Currently a proof of concept that worked. Future changelist that may be worked on if I want to experiment with something else.
* Hash password
* Upload demo project to Heroku / Openshift 
* Add rewrite to make URLs look more RESTy
* Replace with JPA and clean up DB related code (try/catch to close sessions, ...)
* Organize java packages
* Defer a GET init data load until after the document is fully loaded in the browser
* Replace code and resources with additional resource Inject
* Experiment more GET responses that are HTML document responses versus XML partial requests. Specifically, there's an issue where PARTIAL_STATE_SAVING had to be set to false because of JSF790 bug
* Login.xhtml currently gets double loaded. It'd be great if the HTML Document and URL could be replaced with a Login.xhtml data instead of doing a redirect to the page.

## Notes

This monitors the Sessions
```
<path>\wildfly-8.1.0.Final\bin\jboss-cli.bat
connect
/deployment=jsfspa.war/subsystem=undertow :read-attribute(name=active-sessions)
```
