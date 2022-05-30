package fr.aureliancnx.betterbungee.rabbit;

import com.rabbitmq.client.ConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author aureliancnx
 */
@Getter
@AllArgsConstructor
public class RabbitCredentials {

    private final String hostname;
    private final int    port;
    private final String username;
    private final String password;
    private final String virtualHost;
    private final int    workers;

    public RabbitCredentials(String hostname, String username, String password, String virtualHost, int workers) {
        this(hostname, 5672, username, password, virtualHost, workers);
    }

    public ConnectionFactory toConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.hostname);
        factory.setPort(this.port);
        factory.setUsername(this.username);
        factory.setPassword(this.password);
        factory.setVirtualHost(this.virtualHost);
        factory.setRequestedHeartbeat(60);
        factory.setConnectionTimeout(30000);
        factory.setHandshakeTimeout(30000);
        factory.setWorkPoolTimeout(30000);
        return factory;
    }

}
