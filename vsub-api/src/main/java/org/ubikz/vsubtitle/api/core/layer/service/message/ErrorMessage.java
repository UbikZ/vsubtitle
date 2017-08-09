package org.ubikz.vsubtitle.api.core.layer.service.message;

import java.util.Arrays;

public class ErrorMessage {
    private String title;
    private String detail;
    private StackTraceElement[] stackTraceElements;

    public ErrorMessage() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public StackTraceElement[] getStackTraceElements() {
        return stackTraceElements;
    }

    public void setStackTraceElements(StackTraceElement[] stackTraceElements) {
        this.stackTraceElements = stackTraceElements;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", stackTraceElements=" + Arrays.toString(stackTraceElements) +
                '}';
    }
}