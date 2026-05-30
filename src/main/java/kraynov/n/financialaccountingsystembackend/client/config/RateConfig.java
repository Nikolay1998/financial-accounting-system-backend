package kraynov.n.financialaccountingsystembackend.client.config;

import kraynov.n.financialaccountingsystembackend.client.RateClient;
import kraynov.n.financialaccountingsystembackend.client.impl.RateHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RateConfig {

    @Bean
    public RestTemplate rateRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${rate-service.url}") String rateServiceUrl
    ) {
        return restTemplateBuilder.rootUri(rateServiceUrl).build();
    }

    @Bean
    public RateClient rateClient(RestTemplate rateRestTemplate) {
        return new RateHttpClient(rateRestTemplate);
    }
}
