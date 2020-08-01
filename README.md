# oaas_client PoC
A multiplatform http client for connecting to OAAS from JVM languages and Javascript

# Notes
If we were to use this in production I think we would use a structure like this:

```
oaas-rest
    |_ maas-portal-v1
        |_ maas-portal-entities-v1 (MP project in pure common Kotlinas a library, can be used by the backend)
        |_ maas-portal-client-v1 (MP project in Kotlin with JVM/JS/Native impls, can be used by frontend/e2e tests)
```
