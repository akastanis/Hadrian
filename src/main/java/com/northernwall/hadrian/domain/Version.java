package com.northernwall.hadrian.domain;

import java.util.LinkedList;
import java.util.List;

public class Version {
    public String api;
    public String status = "Live";
    public List<Link> links = new LinkedList<>();
    public List<Link> operations = new LinkedList<>();
    public List<ServiceRef> uses = new LinkedList<>();
    public List<ServiceRef> usedby = new LinkedList<>();

    public ServiceRef findUses(String service, String version) {
        if (uses == null || uses.isEmpty()) {
            return null;
        }
        for (ServiceRef ref : uses) {
            if (ref.service.equals(service) && ref.version.equals(version)) {
                return ref;
            }
        }
        return null;
    }
    
    public ServiceRef findUsedBy(String service, String version) {
        if (usedby == null || usedby.isEmpty()) {
            return null;
        }
        for (ServiceRef ref : usedby) {
            if (ref.service.equals(service) && ref.version.equals(version)) {
                return ref;
            }
        }
        return null;
    }
    
    public void addUsedBy(ServiceRef ref) {
        if (usedby == null) {
            usedby = new LinkedList<>();
        }
        usedby.add(ref);
    }

}
