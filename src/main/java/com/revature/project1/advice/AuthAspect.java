package com.revature.project1.advice;

import com.revature.project1.annotations.Authenticate;
import com.revature.project1.annotations.Author;
import com.revature.project1.exceptions.NotLoggedInException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Author(authorName = "Leo Schaffner",
        description = "Will be called whenever a method with @Authoized executes")
@Aspect
@Component
public class AuthAspect {
    @Autowired
    private HttpServletRequest req;

    @Around("@annotation(authenticate)")
    public Object authenticate(ProceedingJoinPoint pjp, Authenticate authenticate) throws Throwable {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("currentUser") == null) throw new NotLoggedInException("Must be logged in to perform this action");

        return pjp.proceed(pjp.getArgs());
    }
}
