# API tests

This module will test the Sirocco OpenStack API using external OpenStack clients in order to validate the implementation.
Each test first creates required data using EJBs then Openstack API calls are launched from various clients (jclouds, jaxrs, ...)

## Howto

### Variables

- test.timeout: The time to wait for sirocco operations to achieve (start, stop, ...). Default value is 60.

## Notes

- When running tests with @RunAsClient annotation, it means that JUnit test is launched on the client side ie beans are not injected
- @Before and @After are called on each method, so be careful on what is defined inside...
- Will be nice to use junit-ext or textng for better test initialization.
