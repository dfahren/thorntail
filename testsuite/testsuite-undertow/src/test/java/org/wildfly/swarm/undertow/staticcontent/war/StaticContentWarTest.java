/**
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.swarm.undertow.staticcontent.war;

import java.util.Collections;
import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.arquillian.ArtifactDependencies;
import org.wildfly.swarm.undertow.WARArchive;
import org.wildfly.swarm.undertow.staticcontent.StaticContentCommonTests;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 */
@RunWith(Arquillian.class)
public class StaticContentWarTest implements StaticContentCommonTests {

    @Deployment
    public static Archive createDeployment() throws Exception {
        WARArchive deployment = ShrinkWrap.create(WARArchive.class);
        deployment.staticContent();
        return deployment;
    }

    @ArtifactDependencies
    public static List<String> appDependencies() {
        return Collections.singletonList(
                "org.wildfly.swarm:undertow"
        );
    }

    @RunAsClient
    @Test
    public void testStaticContent() throws Exception {
        assertBasicStaticContentWorks("");
    }

    @Override
    public void assertContains(String path, String content) throws Exception {
        browser.navigate().to("http://localhost:8080/" + path);
        assertThat(browser.getPageSource()).contains(content);
    }

    @Override
    public void assertNotFound(String path) throws Exception {
        assertThat(browser.getPageSource().contains("Not Found"));
    }

    @Drone
    WebDriver browser;
}
