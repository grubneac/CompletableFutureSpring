package md.maib.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


/**
 * @author - Alexandru.Grubneac
 */
@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppRunner.class);

    private final  GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Start time
        long start = System.currentTimeMillis();

        //Run three threads
        CompletableFuture<User> u1 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> u2 = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> u3 = gitHubLookupService.findUser("Spring-Projects");

        //Wait when all threads are done
        CompletableFuture.allOf(u1, u2, u3).join();

        //Print result
        long end = System.currentTimeMillis();
        LOGGER.info("Elapsed time: " + (end-start));
        LOGGER.info("--> " + u1.get());
        LOGGER.info("--> " + u2.get());
        LOGGER.info("--> " + u3.get());
    }
}
