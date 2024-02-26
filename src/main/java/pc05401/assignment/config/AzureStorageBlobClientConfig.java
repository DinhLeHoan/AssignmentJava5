package pc05401.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobClientBuilder;

@Configuration
public class AzureStorageBlobClientConfig {

	@Value("${blob.connection-string}")
	String connectionString;

	@Value("${blob.container-name}")
	String containerName;

	@Bean
	public BlobClientBuilder getClient() {
		BlobClientBuilder client = new BlobClientBuilder();
		client.connectionString(connectionString);
		client.containerName(containerName);
		return client;
	}
}