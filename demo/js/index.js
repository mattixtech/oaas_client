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

    async function createInstance(instance) {
        return asyncClient.create.onmsInstance(instance);
    }

    async function listInstances() {
        return asyncClient.read.onmsInstances();
    }

    async function updateInstance(id, updatedEntity) {
        return asyncClient.update.onmsInstance(id, updatedEntity);
    }

    async function deleteInstance(id) {
        return asyncClient.delete.onmsInstance(id);
    }

    async function printInstances() {
        const result = await listInstances();
        console.log(result.pagedRecords)
    }

    // Create an instance with name 'devjam'
    const instanceCreatedId = await createInstance(new mpc.model.OnmsInstanceRequestEntity("devjam"));

    await printInstances();

    // Update the existing instance's name to 'kiwi'
    const updatedInstance = new mpc.model.OnmsInstanceEntity.Builder()
        .withName("kiwi")
        .withId(instanceCreatedId)
        .withOrganization(organization)
        .build();
    await updateInstance(instanceCreatedId, updatedInstance);

    await printInstances();

    // Delete the instance we created
    await deleteInstance(instanceCreatedId);

    await printInstances()


})();

