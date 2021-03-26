package md.maib.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;


/**
 * @author - Alexandru.Grubneac
 */
@Service
public class GitHubLookupService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        LOGGER.info("Looking for " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User result = null;
        try {
            result = restTemplate.getForObject(url, User.class);
        } catch (RestClientException e) {
//            e.printStackTrace();
        }
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(result);
    }
}
