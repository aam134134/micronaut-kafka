package micronaut.kafka.example

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.Network
import org.testcontainers.utility.DockerImageName

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/**
 * Extend this class for any tester that needs to test this application.
 *
 * This class sets up local Kafka using TestContainers.
 */
open class MicronautKafkaTester : TestPropertyProvider {

    companion object {
        private val network = Network.newNetwork()

        private val zookeeper = GenericContainer("confluentinc/cp-zookeeper:4.0.0")
            .withNetwork(network)
            .withNetworkAliases("zookeeper")
            .withEnv("ZOOKEEPER_CLIENT_PORT", "2181")

        private val kafka: KafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"))
            .withNetwork(network)
            .withExternalZookeeper("zookeeper:2181")
            .dependsOn(zookeeper)
    }

    override fun getProperties(): MutableMap<String, String> {
        // manually start the TestContainers as we need the Kafka container started before the Micronaut app starts-up
        // this allows us to determine the bootstrap server address and put it into the Micronaut config: "kafka.bootstrap.servers"
        zookeeper.start()
        kafka.start()
        createKafkaTopics(kafka.bootstrapServers)
        return mutableMapOf<String, String>(
            Pair("kafka.bootstrap.servers", kafka.bootstrapServers)
        )
    }

    private fun createKafkaTopics(bootstrapServers: String) {
        mutableMapOf<String, Any>(
            Pair(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        ).run {
            AdminClient.create(this)
        }.also {
            it.createTopics(listOf(NewTopic("service", 1, 1)))
        }
    }
}