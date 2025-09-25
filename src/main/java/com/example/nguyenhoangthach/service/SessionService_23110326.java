package com.example.nguyenhoangthach.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService_23110326 {
    
    public void setAttribute(HttpSession session, String name, Object value) {
        session.setAttribute(name, value);
    }
    
    public Object getAttribute(HttpSession session, String name) {
        return session.getAttribute(name);
    }
    
    public void removeAttribute(HttpSession session, String name) {
        session.removeAttribute(name);
    }
    
    public void invalidate(HttpSession session) {
        session.invalidate();
    }
}
