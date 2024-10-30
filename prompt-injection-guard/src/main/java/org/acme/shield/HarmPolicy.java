package org.acme.shield;

public enum HarmPolicy {

    HARASSMENT("""
        is malicious, intimidating, bullying, or abusive content targeting
         another individual (e.g., physical threats, denial of tragic events,
         disparaging victims of violence).
        """);

    private String description;

    HarmPolicy(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
