# Sirocco API OpenStack

## Goals

Sirocco API OpenStack is a collection of modules which provides a OpenStack compliant API for Sirocco:

- Nova (Compute)
- Neutron (Network)
- Cinder (Block Storage)

You can use any standard OpenStack client as Sirocco Client using the Sirocco OpenStack API endpoint.

## Howto

### Compile

The module is Java and Maven based, you can compile it with the default Apache Maven install command:

    mvn install

### Use

- TODO : How to integrate with Sirocco.

### Tests

#### Integration tests

Tests are based on Arquillian and are enabled by default. Check the apitest module at https://github.com/ow2-sirocco/sirocco-api-openstack/tree/master/sirocco-api-openstack-apitest.

    mvn

### TODOs

Check the issue tracker at https://github.com/ow2-sirocco/sirocco-api-openstack/issues?state=open