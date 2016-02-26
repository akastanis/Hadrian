package com.northernwall.hadrian.domain;

import java.util.UUID;

public class CustomFunction implements Comparable<CustomFunction> {
    private String customFunctionId;
    private String serviceId;
    private String moduleId;
    private String name;
    private String method;
    private String url;
    private String helpText;
    private boolean teamOnly;

    public CustomFunction() {
        this.customFunctionId = UUID.randomUUID().toString();
        this.serviceId = null;
        this.moduleId = null;
        this.name = null;
        this.method = null;
        this.url = null;
        this.helpText = null;
        this.teamOnly = true;
    }

    public CustomFunction(String serviceId, String moduleId, String name, String method, String url, String helpText, boolean teamOnly) {
        this.customFunctionId = UUID.randomUUID().toString();
        this.serviceId = serviceId;
        this.moduleId = moduleId;
        this.name = name;
        this.method = method;
        this.url = url;
        this.helpText = helpText;
        this.teamOnly = teamOnly;
    }

    public String getCustomFunctionId() {
        return customFunctionId;
    }

    public void setCustomFunctionId(String customFunctionId) {
        this.customFunctionId = customFunctionId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public boolean isTeamOnly() {
        return teamOnly;
    }

    public void setTeamOnly(boolean teamOnly) {
        this.teamOnly = teamOnly;
    }

    @Override
    public int compareTo(CustomFunction o) {
        return name.compareTo(o.name);
    }
    
}
