package org.example;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/time")
public class GetTimezone extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(getServletContext().getRealPath("/WEB-INF/classes/org/example/templates/"));
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String timezoneParam = req.getParameter("timezone");

        Instant instant = Instant.now();
        ZoneId customTimeZone;
        try {
            customTimeZone = ZoneId.of(timezoneParam);
            resp.addCookie(new Cookie("timezone", timezoneParam));
        } catch (NullPointerException e) {
            if (!getCookie(req).isEmpty()) {
                customTimeZone = ZoneId.of(getCookie(req));
            } else {
                customTimeZone = ZoneId.of("UTC");
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
                .withZone(customTimeZone);

        String formattedTime = formatter.format(instant);

        Map<String, Object> mapData = new HashMap<>();
        mapData.put("currentTime", formattedTime);

        Context context = new Context(req.getLocale(), mapData);

        engine.process("index", context, resp.getWriter());
        resp.getWriter().close();
    }

    private String getCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("timezone")) {
                    String timezoneValue = cookie.getValue();
                    return timezoneValue;
                }
            }
        }
        return "";
    }
}
