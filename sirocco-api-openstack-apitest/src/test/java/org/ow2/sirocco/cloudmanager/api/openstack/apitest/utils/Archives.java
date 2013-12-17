/**
 * SIROCCO
 * Copyright (C) 2013 France Telecom
 * Contact: sirocco@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.strategy.RejectDependenciesStrategy;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.DbManagerBean;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.apps.KeystoneApplication;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.interceptors.TestInterceptor;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.server.OpenStackApplication;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.ow2.sirocco.cloudmanager.model.utils.SiroccoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Archives {

    private static Logger LOG = LoggerFactory.getLogger(Archives.class);

    private static WebArchive getSiroccoBaseWAR(String name) {
        File[] libs = Maven.resolver()
                //.offline()
                .loadPomFromFile("pom.xml")
                .resolve("org.ow2.sirocco.cloudmanager:sirocco-cloudmanager-core-api").withoutTransitivity()
                .as(File.class);
        //.asFile();

        LOG.info("sirocco-cloudmanager-core-api " + Arrays.toString(libs));

        // reject dependency. if not, Arquillian will fail to inject dependencies and EJB due to context-ref errors...
        // Example : Could not resolve a persistence unit corresponding to the persistence-context-ref-name [org.ow2.sirocco.cloudmanager.core.impl.DatabaseManager/em]

        File[] libs2 = Maven.resolver()
                //.offline()
                .loadPomFromFile("pom.xml")
                .resolve("org.ow2.sirocco.cloudmanager:sirocco-cloudmanager-core-manager")
                        //.using(new RejectDependenciesStrategy(false, "org.ow2.sirocco.cloudmanager:sirocco-cloudmanager-model-cimi"))
                .using(new RejectDependenciesStrategy("org.ow2.sirocco.cloudmanager:sirocco-cloudmanager-model-cimi"))
                        //.asFile();
                .as(File.class);
        LOG.info("sirocco-cloudmanager-core-manager " + Arrays.toString(libs2));

        File[] libs3 = Maven.resolver()
                //.offline()
                .loadPomFromFile("pom.xml")
                .resolve("org.ow2.sirocco.cloudmanager:sirocco-cloudmanager-connector-mock")
                .withoutTransitivity()
                        //.asFile();
                .as(File.class);

        LOG.info("sirocco-cloudmanager-connector-mock " + Arrays.toString(libs3));

        WebArchive war = ShrinkWrap.create(WebArchive.class, name + ".war").addAsLibraries(libs).addAsLibraries(libs2).addAsLibraries(libs3)
                .addClass(DbManagerBean.class).addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addPackages(true, Machine.class.getPackage(), SiroccoConfiguration.class.getPackage());
        // FIXME : Arquillian 1.0.3.Final does not allow to set the value N times, so we do not set it now and
        // FIXME but in the openstackAPIAndMockProvider

        return war;

    }

    /**
     * Creates the default Web Archive with Sirocco Core, EJB, H2 persistance.
     *
     * @return
     */
    public static WebArchive defaultEJBWebAppMockProvider(String name) {
        return getSiroccoBaseWAR(name).setWebXML("web-context.xml").addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    public static WebArchive baseOpenStack(String name) {
        WebArchive base = getSiroccoBaseWAR(name);

        File[] libs = Maven.resolver()
                //.offline()
                .loadPomFromFile("pom.xml")
                .resolve("org.codehaus.jackson:jackson-jaxrs").withTransitivity()
                .as(File.class);
        System.out.println("LIBS " + libs);
        base.addAsLibraries(libs);

        /*
        base.addAsLibraries(Maven.resolver()
                //.offline()
                .loadPomFromFile("pom.xml")
                .resolve("org.ow2.sirocco.cloudmanager:sirocco-api-openstack-server")
                .withoutTransitivity()
                //.asFile());
                .as(File.class));
          */

        // add all commons classes
        base.addPackages(true, Constants.class.getPackage());

        // add

        /*
        base.addAsLibraries(Maven.resolver()
                //.offline()
                .loadPomFromFile("pom.xml")
                .resolve("org.ow2.sirocco.cloudmanager:sirocco-api-openstack-commons")
                .withoutTransitivity()
                //.asFile());
                .as(File.class));
        */

        // add REST services
        base.addPackages(true, org.ow2.sirocco.cloudmanager.api.openstack.nova.Constants.class.getPackage());

        /*
        base.addAsLibraries(Maven.resolver()
                //.offline()
                .loadPomFromFile("pom.xml")
                .resolve("org.ow2.sirocco.cloudmanager:sirocco-api-openstack-nova")
                .withoutTransitivity()
                //.asFile());
                .as(File.class));
        */

        base.addPackages(true, OpenStackApplication.class.getPackage());

        base.addClass(TestInterceptor.class);

        // glassfish CDI...
        base.addAsLibraries(Maven.resolver()
                //.offline()
                .loadPomFromFile("pom.xml")
                .resolve("org.glassfish.jersey.containers.glassfish:jersey-gf-cdi")
                .withoutTransitivity()
                        //.asFile());
                .as(File.class));

        return base;
    }

    /**
     * Create the webapp with EJB and OpenStack API.
     *
     * Commented lines are for arquillian and resolvers 1.1.X... Check the README for more details!
     *
     * @return
     */
    public static WebArchive openstackAPIAndMockProvider(String name) {
        WebArchive base = baseOpenStack(name);
        base.setWebXML("rest-web.xml");
        base.addAsWebInfResource("beans.xml");

        LOG.info(base.toString(true));
        return base;
    }

    /**
     * Web archive for tests using keystone compliant service. This changes the beans.xml file to handle incoming HTTP headers.
     *
     * @param name
     * @return
     */
    public static WebArchive openstackAPIWithKeystoneMock(String name) {
        WebArchive base = baseOpenStack(name);

        File[] libs = Maven.resolver()
                //.offline()
                .loadPomFromFile("pom.xml")
                .resolve("ch.qos.logback:logback-classic").withTransitivity()
                .as(File.class);
        base.addAsLibraries(libs);

        base.addAsResource("logback.xml");

        base.addPackages(true, "org.ow2.sirocco.cloudmanager.api.openstack.keystone.server");
        base.addClass(KeystoneApplication.class);

        base.setWebXML("keystone-web.xml");
        base.addAsWebInfResource("beans.xml");

        LOG.info(base.toString(true));
        return base;


    }

}
