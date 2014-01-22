# Collection of utils for keystone

## Filters

JAXRS2 filters to be used as Sirocco API filter.

### org.ow2.sirocco.cloudmanager.api.openstack.keystone.jclouds.filter.KeystoneDelegateFilter

This filter is in charge of validating the incoming request by getting the X-Auth-Token HTTP header and sending it to a keystone instance.

- If the token is valid, IdentityContext is filled with keystone response based on the incoming header.
- If not valid, the filter chain is aborted and the client receive an error (400 or 500 if it is a server side problem).