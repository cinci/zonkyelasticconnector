package com.jctechcloud.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jctechcloud.entity.Loan;
import com.jctechcloud.mapper.RegionMapper;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

/**
 * Connector which handles all requests to ElasticSearch
 * <p>
 * Created by jcincera on 15/06/16.
 */
@Service
public class ElasticSearchConnector {
    private static final Logger log = LoggerFactory.getLogger(ElasticSearchConnector.class);

    @Value("${custom.elasticsearch.host}")
    private String elasticSearchHost;

    @Value("${custom.elasticsearch.port}")
    private Integer elasticSearchPort;

    @Value("${custom.elasticsearch.index.name}")
    private String indexName;

    @Value("${custom.elasticsearch.type.name}")
    private String typeName;

    private RegionMapper regionMapper;

    @Autowired
    public ElasticSearchConnector(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    /**
     * Store list of loans into ElasticSearch
     *
     * @param loans list of loans
     */
    public void storeLoans(List<Loan> loans) {
        Client client = null;

        try {
            client = getTransportClient();
            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

            // Prepare bulk operation
            final Long internalBulkId = System.currentTimeMillis();
            final Date internalDateCreated = new Date();
            for (Loan loan : loans) {
                loan.setRegionName(regionMapper.mapRegionIdToRegionName(loan.getRegion()));
                loan.setInternalDateCreated(internalDateCreated);
                loan.setInternalBulkId(internalBulkId);

                bulkRequestBuilder.add(client.prepareIndex(indexName, typeName)
                        .setSource(new ObjectMapper().writeValueAsString(loan)));
            }

            // Execute
            BulkResponse bulkResponse = bulkRequestBuilder.get();

            // Check result status
            if (bulkResponse.hasFailures()) {
                for (BulkItemResponse i : bulkResponse.getItems()) {
                    if (i.isFailed()) {
                        log.error("Failed to store item to ElasticSearch: " + i.getFailureMessage());
                    }
                }
            } else {
                log.info("BulkRequest stored with id: " + internalBulkId + " - total items count: " + loans.size());
            }
        } catch (Exception e) {
            log.error("Failed to store loan into ElasticSearch", e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * Initialize ElasticSearch client
     *
     * @return client
     * @throws UnknownHostException
     */
    private TransportClient getTransportClient() throws UnknownHostException {
        return TransportClient.builder().build().addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName(elasticSearchHost), elasticSearchPort));
    }
}
