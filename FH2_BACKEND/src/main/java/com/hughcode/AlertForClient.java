package com.hughcode;

import java.time.LocalDateTime;

public class AlertForClient {
    private int alert_id;
    private int ruleId;
    private String transactionId;
    private String severity;
    private String reason;
    private String alertStatus;
    private LocalDateTime createdAt;
    private LocalDateTime solvedAt;

    public AlertForClient(int alert_id, int ruleId, String transactionId, String severity, String reason, String alertStatus, LocalDateTime createdAt, LocalDateTime solvedAt) {
        this.alert_id = alert_id;
        this.ruleId = ruleId;
        this.transactionId = transactionId;
        this.severity = severity;
        this.reason = reason;
        this.alertStatus = alertStatus;
        this.createdAt = createdAt;
        this.solvedAt = solvedAt;
    }
}
