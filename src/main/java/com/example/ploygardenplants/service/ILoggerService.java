package com.example.ploygardenplants.service;

import org.springframework.boot.logging.LogLevel;

public interface ILoggerService {

    void system(LogLevel level, String refID, String message, Object param);

    void printStackTrace(String refID, String exceptionClassName, Throwable e);

    void track(LogLevel level, String name, Object param, String status);

    void flow(String refID, String flowName, Object uniqueFlowId);

    void event(LogLevel level, String message, Object param);

}
