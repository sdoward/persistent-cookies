package com.doward.persistantcookietest;

import com.doward.persistentcookies.PersistentCookieStore;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowPreferenceManager;
import junit.framework.Assert;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(RobolectricTestRunner.class)
public class PersistentCookieStoreTest {

    private CookieStore persistantCookieStore;

    private BasicClientCookie basicClientCookie = new BasicClientCookie("basicClientCookieName", "basicClientCookieValue");
    private BasicClientCookie2 basicClientCookie2 = new BasicClientCookie2("basicClientCookie2Name", "basicClientCookieValue2");
    private Date date = new Date(System.currentTimeMillis());
    private int[] ports =  {1,2,3};

    @Before
    public void setUp() {
        persistantCookieStore = new PersistentCookieStore(ShadowPreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext()));
        basicClientCookie.setComment("comment");
        basicClientCookie.setVersion(3);
        basicClientCookie.setPath("path");
        basicClientCookie.setDomain("domain");
        basicClientCookie.setExpiryDate(date);
        basicClientCookie.setSecure(true);
        basicClientCookie2.setComment("comment");
        basicClientCookie2.setVersion(3);
        basicClientCookie2.setPath("path");
        basicClientCookie2.setDomain("domain");
        basicClientCookie2.setExpiryDate(date);
        basicClientCookie2.setSecure(true);
        basicClientCookie2.setPorts(ports);
    }

    @Test
    public void shouldAddCookies() {
        persistantCookieStore.addCookie(basicClientCookie);
        persistantCookieStore.addCookie(basicClientCookie2);
        Cookie cookie = persistantCookieStore.getCookies().get(0);
        Assert.assertEquals("basicClientCookieName", cookie.getName());
        Assert.assertEquals("comment", cookie.getComment());
        Assert.assertEquals(3, cookie.getVersion());
        Assert.assertEquals("path", cookie.getPath());
        Assert.assertEquals("domain", cookie.getDomain());
        Assert.assertEquals(true, cookie.isSecure());
        Assert.assertEquals(date, cookie.getExpiryDate());
        Cookie cookie1 = persistantCookieStore.getCookies().get(1);
        Assert.assertEquals("basicClientCookie2Name", cookie1.getName());
        Assert.assertEquals("comment", cookie1.getComment());
        Assert.assertEquals(3, cookie1.getVersion());
        Assert.assertEquals("path", cookie1.getPath());
        Assert.assertEquals("domain", cookie1.getDomain());
        Assert.assertEquals(true, cookie1.isSecure());
        Assert.assertEquals(date, cookie1.getExpiryDate());
        Assert.assertEquals(ports.length, cookie1.getPorts().length);
    }

    @Test
    public void shouldClearAllCookies() {
        persistantCookieStore.clear();
        Assert.assertEquals(0, persistantCookieStore.getCookies().size());
    }

    @Test
    public void shouldClearExpired() {
        persistantCookieStore.addCookie(basicClientCookie);
        long time = System.currentTimeMillis();
        time = time + 10000;
        basicClientCookie2.setExpiryDate(new Date(time));
        persistantCookieStore.addCookie(basicClientCookie2);
        time = System.currentTimeMillis() + 1000;
        Assert.assertEquals(true, persistantCookieStore.clearExpired(new Date(time)));
        Assert.assertEquals(1, persistantCookieStore.getCookies().size());
    }

    @Test
    public void getWithNoCookies() {
        persistantCookieStore.clear();
        persistantCookieStore.getCookies();
    }


}
