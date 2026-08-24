package com.hughcode;

import java.time.LocalDateTime;

public class Alerts {
    private int alert_id;
    private int rule_id;
    private String transaction_id;
    private String severity;
    private String reason;
    private String alert_status
    private LocalDateTime created_at;
    private LocalDateTime solved_At;

    public Alerts(int alert_id, int rule_id, String transaction_id, String severity, String reason, String alert_status, LocalDateTime created_at, LocalDateTime solved_At) {
        this.alert_id = alert_id;
        this.rule_id = rule_id;
        this.transaction_id = transaction_id;
        this.severity = severity;
        this.reason = reason;
        this.alert_status = alert_status;
        this.created_at = created_at;
        this.solved_At = solved_At;
    }
}
