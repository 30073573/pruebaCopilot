package adeo.lmes.pruebaCopilot.infraestructura.configuracion;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.util.concurrent.TimeUnit;

/**
 * Configuración Reactiva
 * Define beans para WebClient y configuración de Reactor Netty
 */
@Configuration
public class ReactiveConfig {

    @Bean
    public WebClient webClient() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("custom")
                .maxConnections(500)
                .maxIdleTime(java.time.Duration.ofSeconds(20))
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .responseTimeout(java.time.Duration.ofSeconds(5))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5, TimeUnit.SECONDS))
                );

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}