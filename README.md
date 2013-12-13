# Sirocco API OpenStack

## Goals

Sirocco API OpenStack is a collection of modules which provides a OpenStack compliant API for Sirocco :

You can use any standard OpenStack client as Sirocco Client using the Sirocco OpenStack API endpoint.

## Howto

### Compile

The module is Java and Maven based, you can compile it with the default Apache Maven install command:

    mvn install

### Use

- TODO : How to integrate with Sirocco.

### Tests

#### Integration tests

Tests are based on Arquillian and are not enabled by default. To run the tests:

    mvn -Parquillian