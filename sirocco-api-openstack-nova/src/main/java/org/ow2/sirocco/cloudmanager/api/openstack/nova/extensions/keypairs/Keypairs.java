/**
 * SIROCCO
 * Copyright (C) 2014 France Telecom
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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs.model.KeypairForCreate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Keypair extension. Doc at http://api.openstack.org/api-ref-compute.html#os-keypairs
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.Nova.TENANT_PATH_TEMPLATE + "/os-keypairs")
public interface Keypairs {

    /**
     * Lists keypairs associated with the account.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Generates or import a keypair. If the public key is not set, sirocco will generate one.
     *
     * @param keypair
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response create(KeypairForCreate keypair);

    /**
     * Deletes a keypair
     *
     * @param name
     * @return
     */
    @DELETE
    @Path("/{keypair_name}")
    Response delete(@PathParam("keypair_name") String name);

    /**
     * Shows a keypair associated with the account.
     *
     * @param name
     * @return
     */
    @GET
    @Path("/{keypair_name}")
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("keypair_name") String name);
}
