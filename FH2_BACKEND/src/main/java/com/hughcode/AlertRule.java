package com.hughcode;

public class AlertRule {

    public int ruleId;
    public String ruleName;
    public String ruleDescription;
    public String ruleData;
    public boolean enabled;

    public AlertRule(int ruleId, String ruleName, String ruleDescription, String ruleData, boolean enabled) {
        this.ruleId = ruleId;
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.ruleData = ruleData;
        this.enabled = enabled;
    }
}