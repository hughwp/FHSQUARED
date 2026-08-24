package com.hughcode;

public class AlertRule {

    public int ruleId;
    public String ruleName;
    public String ruleDescription;
    public boolean enabled;

    public AlertRule(int ruleId, String ruleName, String ruleDescription, boolean enabled) {
        this.ruleId = ruleId;
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.enabled = enabled;
    }
}