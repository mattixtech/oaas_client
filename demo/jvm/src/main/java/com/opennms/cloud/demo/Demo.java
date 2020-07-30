/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2020 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2020 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package com.opennms.cloud.demo;

import com.opennms.cloud.maas.client.AsyncMaasPortalClient;
import com.opennms.cloud.maas.client.Environment;
import com.opennms.cloud.maas.client.MaasPortalClientBuilder;
import com.opennms.cloud.maas.client.auth.TokenAuthenticationMethod;
import com.opennms.cloud.maas.client.model.OnmsInstanceEntity;
import com.opennms.cloud.maas.client.model.OnmsInstanceRequestEntity;

public class Demo {

    private static AsyncMaasPortalClient client = null;
    private static final String ORGANIZATION_NAME = "matt";

    public static void main(String[] args) {
        client = new MaasPortalClientBuilder()
                .withAuthenticationMethod(new TokenAuthenticationMethod(System.getenv("BEARER_TOKEN")))
                .withEnvironment(Environment.DEV)
                .withOrganization(ORGANIZATION_NAME)
                .build();

        // Create an instance with name 'devjam'
        String createdInstanceId = createInstance();
        listInstances();

        // Update the existing instance's name to 'kiwi'
        updateInstance(createdInstanceId);
        listInstances();

        // Delete the instance we created
        deleteInstance(createdInstanceId);
        listInstances();
    }

    private static String createInstance() {
        return client.create().onmsInstance(new OnmsInstanceRequestEntity("devjam")).join();
    }

    private static void updateInstance(String instanceId) {
        client.update().onmsInstance(instanceId, new OnmsInstanceEntity.Builder()
                .withId(instanceId)
                .withName("kiwi")
                .withOrganization(ORGANIZATION_NAME)
                .build()).join();
    }

    private static void deleteInstance(String instanceId) {
        client.delete().onmsInstance(instanceId).join();
    }

    private static void listInstances() {
        client.read().onmsInstances().thenAccept(res -> System.out.println(res.getPagedRecords())).join();
    }

}
