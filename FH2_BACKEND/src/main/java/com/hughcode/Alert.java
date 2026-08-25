package com.hughcode;

import java.time.LocalDateTime;

public class Alert {
    private int ruleId;
    private String transactionId;
    private String severity;
    private String reason;
    private String alertStatus;
    private LocalDateTime createdAt;
    private LocalDateTime solvedAt;

    public Alert(int alert_id, int ruleId, String transactionId, String severity, String reason, String alertStatus, LocalDateTime createdAt, LocalDateTime solvedAt) {
        this.ruleId = ruleId;
        this.transactionId = transactionId;
        this.severity = severity;
        this.reason = reason;
        this.alertStatus = alertStatus;
        this.createdAt = createdAt;
        this.solvedAt = solvedAt;
    }



    public void setAlertId(int alertId) {

    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(String alertStatus) {
        this.alertStatus = alertStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSolvedAt() {
        return solvedAt;
    }

    public void setSolvedAt(LocalDateTime solvedAt) {
        this.solvedAt = solvedAt;
    }
}
