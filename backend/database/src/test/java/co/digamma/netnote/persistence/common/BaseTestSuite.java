package co.digamma.netnote.persistence.common;

import co.digamma.netnote.navigation.RatedDomain;
import co.digamma.netnote.navigation.RatedDomainRepository;
import co.digamma.netnote.navigation.RatedLink;
import co.digamma.netnote.navigation.RatedLinkRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class BaseTestSuite {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    protected RatedLinkRepository linkRepository;

    @Autowired
    protected RatedDomainRepository domainRepository;

    protected RatedDomain givenDomain() {
        String url = "https://www.%s.com".formatted(
                UUID.randomUUID()
        );
        RatedDomain domain = domainRepository.create(url);
        return domainRepository.write(domain);
    }

    protected RatedLink givenLinkForDomain(RatedDomain domain) {
        String url = "%s/%s".formatted(
                domain.getUrl(),
                UUID.randomUUID()
        );
        RatedLink link = linkRepository.create(url, domain);
        return linkRepository.write(link);
    }

    protected RatedLink givenLink() {
        return givenLinkForDomain(givenDomain());
    }
}
