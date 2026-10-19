package com.stayhaven.web;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdminDataErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(AdminDataErrorHandler.class);

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String databaseUnavailable(DataAccessException exception, HttpServletRequest request, Model model) {
        String path = request.getRequestURI();
        log.error("Database request failed for {}", path, exception);
        model.addAttribute("retryUrl", path);
        model.addAttribute("adminRequest", path.startsWith("/admin/"));
        return "error/data-unavailable";
    }
}
