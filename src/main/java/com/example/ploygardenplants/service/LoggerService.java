package com.example.ploygardenplants.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;
import com.example.ploygardenplants.constant.CommonConstant;
import com.example.ploygardenplants.enums.LogSubject;

@Service
public class LoggerService implements ILoggerService {

    private final Logger accessLogger = LoggerFactory.getLogger(CommonConstant.ACCESS_LOG);
    private final Logger externalLogger = LoggerFactory.getLogger(CommonConstant.EXTERNAL_LOG);
    private final Logger systemLogger = LoggerFactory.getLogger(CommonConstant.SYSTEM_LOG);
    private final Logger errorLogger = LoggerFactory.getLogger(CommonConstant.ERROR_LOG);
    private final Logger cacheLogger = LoggerFactory.getLogger(CommonConstant.CACHE_LOG);
    private final Logger eventLogger = LoggerFactory.getLogger(CommonConstant.EVENT_LOG);
    private final Logger metaLogger = LoggerFactory.getLogger(CommonConstant.META_LOG);

    @Override
    public void flow(String refID, String flowName, Object uniqueFlowId) {
        StringBuilder sb = new StringBuilder();
        // Reference
        sb.append(coverBracketValue(LogSubject.REF_ID, refID));

        String flowId = uniqueFlowId == null ? "null" : uniqueFlowId.toString();
        String legends = String.format(" [%s {ID=%s}] ", flowName, flowId);

//        sb.append(StringUtils.center(legends, AUSPICIOUS_NUMBER, "-"));
        writeLog(systemLogger, LogLevel.INFO, sb.toString());
    }

    @Override
    public void system(LogLevel level, String refID, String message, Object param) {
        StringBuilder sb = new StringBuilder();
        // ReferenceID
        sb.append(coverBracketValue(LogSubject.REF_ID, refID));

        // Result
        sb.append(coverBracketValue(LogSubject.MESSAGE, message));

        // Object
        if (param != null) {
            sb.append(coverBracketValue(LogSubject.PARAMETER, param));
        }
        writeLog(systemLogger, level, sb.toString());
    }

    @Override
    public void track(LogLevel level, String name, Object param, String status) {
        StringBuilder sb = new StringBuilder();
        // ReferenceID
        sb.append(coverBracketValue(LogSubject.NAME, name));

        // Result
        sb.append(coverBracketValue(LogSubject.PARAMETER, param));

        // Object
        sb.append(coverBracketValue(LogSubject.STATUS, status));

        writeLog(cacheLogger, level, sb.toString());
    }

    @Override
    public void event(LogLevel level, String message, Object param) {
        StringBuilder sb = new StringBuilder();
        // ReferenceID
        sb.append(coverBracketValue(LogSubject.REF_ID, CommonConstant.SCHEDULE_USER));

        // Result
        sb.append(coverBracketValue(LogSubject.MESSAGE, message));

        // Object
        if (param != null) {
            sb.append(coverBracketValue(LogSubject.PARAMETER, param));
        }
        writeLog(eventLogger, level, sb.toString());
    }

    @Override
    public void printStackTrace(String refID, String exceptionClassName, Throwable e) {
        StringBuilder str = new StringBuilder();
        str.append(coverBracketValue(LogSubject.REF_ID, refID));
        str.append(coverBracketValue(LogSubject.TYPE, exceptionClassName));
        str.append(coverBracketValue(LogSubject.MESSAGE_CODE, e.getMessage()));
        StackTraceElement[] elements = e.getStackTrace();
        if (elements != null && elements.length > 0) {
            str.append(e.toString());
            for (int i = 0, n = elements.length; i < n; i++) {
                str.append(" at ");
                str.append(elements[i].getClassName()).append(" (").append(elements[i].getMethodName())
                        .append(":").append(elements[i].getLineNumber()).append(")\n");
            }

        } else {
            str.append(e.toString());
        }

        if (e.getCause() != null) {
            StackTraceElement[] elements2 = e.getCause().getStackTrace();
            if (elements2 != null && elements2.length != 0) {
                str.append(" Caused by : ");
                str.append(e.getCause().toString());
                str.append(" at ");
                for (StackTraceElement elements21 : elements2) {
                    str
                            .append(" at ")
                            .append(elements21.getClassName())
                            .append("(").append(elements21.getMethodName())
                            .append(":").append(elements21.getLineNumber()).append(")\n");
                }
            } else {
                str.append(e.getCause().toString());
            }
        }
        writeLog(errorLogger, LogLevel.ERROR, str.toString());
    }

    private String coverBracketValue(LogSubject logSubject, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append(logSubject.getDesc());
        sb.append("=");
        sb.append(CommonConstant.START_SQUARE_BRACKETS);
        sb.append(value == null ? "" : value.toString());
        sb.append(CommonConstant.END_SQUARE_BRACKETS);
        sb.append(" ");
        return sb.toString();
    }

    private void writeLog(Logger logger, LogLevel level, String log) {
        switch (level) {
            case DEBUG:
                logger.debug(log);
                break;
            case INFO:
                logger.info(log);
                break;
            case WARN:
                logger.warn(log);
                break;
            case ERROR:
                logger.error(log);
                break;
            default:
                logger.trace(log);
        }
    }
}
