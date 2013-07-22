package com.cmendenhall;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.cmendenhall.Utils.split;

public class ResponseBuilder {
    private DateFormat dateFormat;
    private RequestRouter requestRouter;

    public ResponseBuilder() {
        dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        requestRouter = new RequestRouter();
    }

    private String getCurrentDate() {
        Date now = new Date();
        return dateFormat.format(now);
    }

    private void addDefaultHeaders(Response response) {
        response.httpVersion("1.0");
        response.setHeader("Server", "Schtitt/0.9a");
        response.setHeader("Date", getCurrentDate());
        response.setHeader("Pragma", "no-cache");
    }

    private void addFileHeaders(Response response, WebResource file) {
        response.setHeader("Content-Type", file.mimeType());
        response.setHeader("Content-Length", file.contentLength());
        response.setHeader("Content-MD5", file.checkSum());
    }

    private void addCustomHeaders(Response response, WebResource resource) {
        HashMap<String, String> customHeaders = resource.customHeaders();
        for (String header : customHeaders.keySet()) {
            response.setHeader(header, customHeaders.get(header));
        }
    }

    public Response constructResponse(String method, String path) {
        return constructResponse(method, path, new HashMap<String, String>());
    }

    public Response constructResponse(String method, String path, HashMap<String, String> queryParameters) {

        Response response = new Response();

        if (method.equals("HEAD")) {
            response = constructResponse("GET", path, queryParameters);
            response.body(new byte[0]);
            return response;
        }

        addDefaultHeaders(response);

        if (method.equals("POST") || method.equals("PUT")) {
            if(requestRouter.routeRegistered(method, path)) {
                response.statusCode("200");
                WebResource requestedResource = requestRouter.getResource(method, path, queryParameters);
                response.setHeader("Location", requestedResource.url());
                addCustomHeaders(response, requestedResource);
            } else {
                response.statusCode("405");
            }
            response.body(new byte[0]);
            return response;
        }

        if (method.equals("GET")) {
            if (requestRouter.pathExists(method, path) ||
                requestRouter.routeRegistered(method, path)) {
                response.statusCode("200");
            } else {
                response.statusCode("404");
            }
            WebResource requestedResource = requestRouter.getResource(method, path, queryParameters);
            addFileHeaders(response, requestedResource);

            String range = queryParameters.get("Range");
            if (range != null) {
                response.statusCode("206");
                List<String> startEnd = split(split(range, "=").get(1), "-");

                Integer start = Integer.parseInt(startEnd.get(0).trim());
                Integer end = Integer.parseInt(startEnd.get(1).trim());

                byte[] fullResponseData = requestedResource.binaryData();
                byte[] partialData = Arrays.copyOfRange(fullResponseData, start, end);
                response.body(partialData);
                response.setHeader("Content-Length", "" + (end - start));
            }
            else {
                response.body(requestedResource.binaryData());
            }

            String redirect = requestedResource.customHeaders().get("Location");
            if (redirect != null) response.statusCode("301");

            String authenticate = requestedResource.customHeaders().get("WWW-Authenticate");
            if (authenticate != null) response.statusCode("401");

            addCustomHeaders(response, requestedResource);
            return response;
        }
        return response;
    }

}
