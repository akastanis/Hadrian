/*
 * Copyright 2014 Richard Thurston.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.northernwall.hadrian.service.dao;

import com.northernwall.hadrian.domain.GitMode;
import com.northernwall.hadrian.domain.Service;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GetServiceData {

    public String serviceId;
    public String serviceAbbr;
    public String serviceName;
    public String teamId;
    public String description;
    public String serviceType;
    public GitMode gitMode;
    public String gitProject;
    public Date creationDate;
    public List<GetModuleData> modules;
    public List<GetDataStoreData> dataStores;
    public List<GetServiceRefData> uses;
    public List<GetServiceRefData> usedBy;
    public List<GetPairData> links;
    public boolean canModify;

    public static GetServiceData create(Service service) {
        GetServiceData temp = new GetServiceData();
        temp.serviceId = service.getServiceId();
        temp.serviceAbbr = service.getServiceAbbr();
        temp.serviceName = service.getServiceName();
        temp.teamId = service.getTeamId();
        temp.description = service.getDescription();
        temp.serviceType = service.getServiceType();
        temp.gitMode = service.getGitMode();
        temp.gitProject = service.getGitProject();
        temp.creationDate = service.getCreationDate();
        temp.modules = new LinkedList<>();
        temp.dataStores = new LinkedList<>();
        temp.uses = new LinkedList<>();
        temp.usedBy = new LinkedList<>();
        temp.links = new LinkedList<>();
        if (service.getLinks() != null && !service.getLinks().isEmpty()) {
            for (Map.Entry<String, String> entry : service.getLinks().entrySet()) {
                temp.links.add(new GetPairData(entry.getKey(), entry.getValue()));
            }
        }
        return temp;
    }

}
