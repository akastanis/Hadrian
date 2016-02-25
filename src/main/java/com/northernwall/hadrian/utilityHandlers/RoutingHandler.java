/*
 * Copyright 2014 Richard Thurston.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.northernwall.hadrian.utilityHandlers;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoutingHandler extends AbstractHandler {

    private final static Logger logger = LoggerFactory.getLogger(RoutingHandler.class);
    
    private final Map<Route, Handler> routes;

    public RoutingHandler() {
        routes = new ConcurrentHashMap<>();
    }
    
    public void addRoute(String target, String method, Handler handler) {
        Route route = new Route(target, method);
        routes.put(route, handler);
    }

    @Override
    public void handle(String target, Request request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException, ServletException {
        Route route = new Route(target, request.getMethod());
        Handler handler = routes.get(route);
        if (handler == null) {
            return;
        }
        handler.handle(target, request, httpRequest, response);
    }
    
}
