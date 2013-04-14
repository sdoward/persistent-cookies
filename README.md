persistent-cookies
==================

Android library for persistent cookies for use with HttpClient


Sample Usage
------------------------

To make your cookies persist across app restarts you use the PersistantCookieStore class that is contained in the library.

You can use this by setting it as an attribute for httpContext like so.

        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookiesStore);


You can then use httpcontext when sending a request.

        httpClient.execute(httpPost, httpContext);
