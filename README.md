persistent-cookies
==================

android library for persistent cookies


There are 2 ways to import this library.

1. Maven

Enter this... in POM

2. No Maven

Download the JAR and import into the project


Usage within the project
------------------------

To make your cookies persist across app restarts you use the PersistantCookieStore class that is contained in the library.

You can use this by setting it as an attribute for httpContext like so.

        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookiesStore);


You can then use httpcontext when sending a request.

        httpClient.execute(httpPost, httpContext);
