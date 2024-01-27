package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String timezoneParam = request.getParameter("timezone");

        //
        try {
            ZoneId customTimeZone = ZoneId.of(timezoneParam);
                chain.doFilter(request, response);
        } catch (DateTimeException e) {
            request.setAttribute("errorStatus", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("errorMessage", "Invalid timezone parameter");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage.jsp");
            dispatcher.forward(request, response);
        }catch (NullPointerException n){
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}