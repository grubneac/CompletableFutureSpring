package md.maib.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
        List<CompletableFuture<User>> futureList = new ArrayList<>();
        futureList.add(gitHubLookupService.findUser("PivotalSoftware"));
        futureList.add(gitHubLookupService.findUser("CloudFoundry"));
        futureList.add(gitHubLookupService.findUser("Spring-Projects"));
        futureList.add(gitHubLookupService.findUser("mojombo"));
        futureList.add(gitHubLookupService.findUser("anotherjesse"));

        //Wait when all threads are done
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
        allOf.join();

        //Print result
        long end = System.currentTimeMillis();
        LOGGER.info("Elapsed time: " + (end-start));

        for (CompletableFuture future : futureList)
            LOGGER.info("--> " + future.get());
    }
}
