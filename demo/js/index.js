// Import the local JS artifact for the OAAS client and dereference the package structure
const mpc = require('oaas_client/packages/oaas_client/kotlin/oaas_client').com.opennms.cloud.maas.client;

(async function () {

    const organization = "matt";
    // Build a ReST client
    const asyncClient = new mpc.MaasPortalClientBuilder()
    // Use an already defined JWT token for convenience
        .withAuthenticationMethod(new mpc.auth.TokenAuthenticationMethod(process.env.BEARER_TOKEN))
        .withOrganization(organization)
        // Send requests to the DEV instance of OAAS
        .withEnvironment(mpc.Environment.DEV)
        .build();

    async function listInstances(prefix) {
        const options = new mpc.model.ListQueryOptions.Builder()
            .withSearchField("name")
            .withSearchPrefix(prefix)
            .build();
        return asyncClient.read.onmsInstances(options);
    }

    async function printInstances(prefix) {
        const result = await listInstances(prefix);
        console.log(result.pagedRecords)
    }

    // Test getting a specific instance
    // asyncClient.read.onmsInstance("5f7481e2-6dfe-4aa4-b7ae-6303e3fd4620").then((res) => {
    //     console.log(res);
    // });

    // Create an instance with name 'devjam'
    const instanceCreatedId = await asyncClient.create.onmsInstance(new mpc.model.OnmsInstanceRequestEntity("devjam"));
    await printInstances("devjam");

    // Update the existing instance's name to 'kiwi'
    const updatedInstance = new mpc.model.OnmsInstanceEntity.Builder()
        .withName("kiwi")
        .withId(instanceCreatedId)
        .withOrganization(organization)
        .build();
    await asyncClient.update.onmsInstance(instanceCreatedId, updatedInstance);
    await printInstances("kiwi");

    // Delete the instance we created
    await asyncClient.delete.onmsInstance(instanceCreatedId);
    await printInstances("kiwi")

})();

