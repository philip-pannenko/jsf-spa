# JSF-SPA

Work to demonstrate the feasibility of making a secure semi-stateless JSF SPA using minimal to no external frameworks.
Focus was to hold onto the benefits and convenience of jsf/bean development without introducing any hacky code.

###Key features
* No session is created until after a user logs in
* GET Requests along with Ajax to navigate without browser needing to changes pages
* History.js to manage the browser back/forward functionality without refreshing whole page
* Pages can be refreshed and bookmarked

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

Implemented a custom AuthenticationMechanism to prevent creation of a session object. What ended up happeneing is with the default FORM AuthenticationMechanism, if the servley detected that a secure page was accessed, a Session object was created to save the URL into. Then the login page would appear and upon a successful long, the saved URL would be accessed. I implemented a custom AuthenticationMechanism and overrode any creation of session object. 
'''
  public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext servletContext) {
    deploymentInfo.addAuthenticationMechanism("NOREDIRFORM", new NoRedirectFormBasedAuthenticationMechanism.Factory());
  }
'''

## Upcomming changes

Currently a proof of concept that worked. Future changelist that may be worked on if I want to experiment with something else.
* Hash password
* Replace with JPA
* Organize java packages
* Defer a GET init data load until after the document is fully loaded in the browser
* Replace code and resources with additional resource Inject

## Notes

This monitors the Sessions
'''
<path>\wildfly-8.1.0.Final\bin\jboss-cli.bat
connect
/deployment=jsfspa.war/subsystem=undertow :read-attribute(name=active-sessions)
'''
   