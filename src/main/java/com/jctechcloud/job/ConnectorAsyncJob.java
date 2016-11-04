package com.jctechcloud.job;

import com.jctechcloud.connector.ElasticSearchConnector;
import com.jctechcloud.connector.ZonkyConnector;
import com.jctechcloud.entity.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Scheduled job which periodically uploads data from Zonky to ElasticSearch
 * <p>
 * Created by jcincera on 15/06/16.
 */
@Service
@Profile("production")
public class ConnectorAsyncJob {
    private static final Logger log = LoggerFactory.getLogger(ConnectorAsyncJob.class);

    private ZonkyConnector zonkyConnector;
    private ElasticSearchConnector elasticSearchConnector;

    @Autowired
    public ConnectorAsyncJob(ZonkyConnector zonkyConnector, ElasticSearchConnector elasticSearchConnector) {
        this.zonkyConnector = zonkyConnector;
        this.elasticSearchConnector = elasticSearchConnector;
    }

    /**
     * Scheduled method which periodically uploads data from Zonky to ElasticSearch.
     * Interval between uploads is 3 hours.
     */
    @Scheduled(fixedRate = 3 * 60 * 60 * 1000) // ms (3 hours)
    public void execute() {
        List<Loan> loans = zonkyConnector.loadAllLoansWithDefaultOrdering();
        if (loans.size() > 0) {
            elasticSearchConnector.storeLoans(loans);
        }
        else {
            log.warn("No loans available on marketplace!");
        }
    }
}
