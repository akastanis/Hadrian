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
package com.northernwall.hadrian.access.simple;

import com.northernwall.hadrian.Const;
import com.northernwall.hadrian.access.AccessHelper;
import com.northernwall.hadrian.domain.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleAccessHandler extends AbstractHandler {
    private final static Logger logger = LoggerFactory.getLogger(SimpleAccessHandler.class);

    private final AccessHelper accessHelper;

    public SimpleAccessHandler(AccessHelper accessHelper) {
        this.accessHelper = accessHelper;
    }

    @Override
    public void handle(String target, Request request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException, ServletException {
        if (request.getMethod().equals(Const.HTTP_POST) && target.equals("/login")) {
            if (checkAndStartSession(request, response)) {
                redirect("/ui/", response);
                request.setHandled(true);
                return;
            } else {
                logger.warn("login failed!");
            }
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                //for (Cookie cookie : request.getCookies()) {
                //    logger.info("cookie name={} value={} domain={} path={}", cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath());
                //}
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(Const.COOKIE_SESSION)) {
                        User user = accessHelper.getUserForSession(cookie.getValue());
                        if (user != null) {
                            request.setAttribute(Const.ATTR_SESSION, cookie.getValue());
                            request.setAttribute(Const.ATTR_USER, user);
                            //logger.info("found, allowing to continue, user={}", username);
                            request.setHandled(false);
                            return;
                        }
                    }
                }
            }
        }

        logger.info("No session found, redirecting to login");
        redirect("/ui/login.html", response);
        request.setHandled(true);
    }

    private void redirect(String url, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getOutputStream().print("<html><head><meta http-equiv=\"refresh\" content=\"1;url="+url+"\"></head><body></body></html>");
    }

    private boolean checkAndStartSession(Request request, HttpServletResponse response) {
        String username = null;
        String password = null;
        MultiMap<String> mm = new MultiMap<>();
        request.extractFormParameters(mm);
        for (String key : mm.keySet()) {
            switch (key) {
                case "username":
                    username = mm.getValue(key, 0);
                    break;
                case "password":
                    password = mm.getValue(key, 0);
                    break;
            }
        }
        
        //check username and password
        if (!checkCreds(username, password)) {
            return false;
        }
        
        String sessionId = accessHelper.createUserSession(username);
        Cookie cookie = new Cookie(Const.COOKIE_SESSION, sessionId);
        cookie.setMaxAge(Const.COOKIE_EXPRIY);
        response.addCookie(cookie);
        return true;
    }

    private boolean checkCreds(String username, String password) {
        return username != null && !username.isEmpty();
    }

}