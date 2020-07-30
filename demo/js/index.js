// Import the local JS artifact for the OAAS client and dereference the package structure
const oc = require('oaas_client/packages/oaas_client/kotlin/oaas_client').com.opennms.cloud.maas.client;

// Build a ReST client
const asyncClient = new oc.MaasPortalClientBuilder()
    // Use an already defined JWT token for convenience
    .withAuthenticationMethod(new oc.auth.TokenAuthenticationMethod(process.env.BEARER_TOKEN))
    .withOrganization("matt")
    // Send requests to the DEV instance of OAAS
    .withEnvironment(oc.Environment.DEV)
    .build();

asyncClient.read.onmsInstances().then((res, ex) => {
    console.log(res.pagedRecords);
});
