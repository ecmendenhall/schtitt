package com.cmendenhall.tests;

import com.cmendenhall.handlers.HelloPageHandler;
import com.cmendenhall.handlers.PageHandler;
import com.cmendenhall.RequestRouter;
import com.cmendenhall.webresources.WebResource;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class RequestRouterTest {
    private RequestRouter requestRouter;
    private PageHandler hello;

    @Before
    public void setUp() {
        requestRouter = new RequestRouter();
        hello = new HelloPageHandler();
        requestRouter.createRoute("GET", "/mygreathello", hello);
    }

    @Test
    public void requestRouterStoresRoutes() {
        assertTrue(requestRouter.routeRegistered("GET", "/mygreathello"));
    }

    @Test
    public void requestRouterStoresDefaultRoutes() {
        assertTrue(requestRouter.routeRegistered("GET", "/"));
        assertTrue(requestRouter.routeRegistered("GET", "/hello"));
        assertTrue(requestRouter.routeRegistered("GET", "/time"));
        assertTrue(requestRouter.routeRegistered("GET", "/form"));
        assertTrue(requestRouter.routeRegistered("POST", "/form"));
    }

    @Test
    public void requestRouterFetchesSpecialPages() {
        WebResource helloPage = requestRouter.getResource("GET", "/mygreathello");
        assertTrue(helloPage.stringData().contains("Schtitt 0.9a"));
    }

    @Test
    public void requestRouterFetchesNotFoundPages() {
        WebResource notFound = requestRouter.getResource("GET", "/hovercraft/eels");
        assertTrue(notFound.stringData().contains("404: File not found"));
    }

    @Test
    public void requestRouterFetchesDirectoryPages() {
        WebResource directoryPage = requestRouter.getResource("GET", "/test/sampledirectory");
        assertTrue(directoryPage.stringData().contains("slip.gif"));
    }

    @Test
    public void requestRouterFetchesFilePages() {
        WebResource filePage = requestRouter.getResource("GET", "/test/sampledirectory/text.txt");
        assertTrue(filePage.stringData().contains("Cantorian continuum of infinities"));
    }

}
