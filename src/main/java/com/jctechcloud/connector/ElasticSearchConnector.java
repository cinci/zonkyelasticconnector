package com.jctechcloud.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jctechcloud.entity.Loan;
import com.jctechcloud.entity.Region;
import com.jctechcloud.entity.RegionGeo;
import com.jctechcloud.mapper.RegionMapper;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
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

    @Value("${elasticsearch.host}")
    private String elasticSearchHost;

    @Value("${elasticsearch.port}")
    private Integer elasticSearchPort;

    @Value("${elasticsearch.index.name}")
    private String indexName;

    @Value("${elasticsearch.type.name}")
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
        try (Client client = getTransportClient()) {
            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

            // Prepare bulk operation
            final Long internalBulkId = System.currentTimeMillis();
            final Date internalDateCreated = new Date();
            for (Loan loan : loans) {

                // Region data
                Region region = regionMapper.mapRegionIdToRegionName(loan.getRegion());
                loan.setRegionName(region.getName());
                loan.setRegionGeo(new RegionGeo(region.getGeoLat(), region.getGeoLon()));

                // Internal data
                loan.setInternalDateCreated(internalDateCreated);
                loan.setInternalBulkId(internalBulkId);

                bulkRequestBuilder.add(client.prepareIndex(indexName, typeName)
                        .setSource(new ObjectMapper().writeValueAsString(loan)));
            }

            // Execute
            BulkResponse bulkRequestResponse = bulkRequestBuilder.get();
            checkBulkRequestResult(loans.size(), internalBulkId, bulkRequestResponse);
        }
        catch (Exception e) {
            log.error("Failed to store loans into ElasticSearch", e);
        }
    }

    /**
     * Check bulk request result
     *
     * @param requestSize    bulk request size
     * @param internalBulkId internal bulk id
     * @param bulkResponse   bulk response
     */
    private void checkBulkRequestResult(Integer requestSize, Long internalBulkId, BulkResponse bulkResponse) {
        // Check result status
        if (bulkResponse.hasFailures()) {
            for (BulkItemResponse i : bulkResponse.getItems()) {
                if (i.isFailed()) {
                    log.error("Failed to store item from Bulk ID:" + internalBulkId + " to ElasticSearch: " + i.getFailureMessage());
                }
            }
        }
        else {
            log.info("BulkRequest stored with id: " + internalBulkId + " - total items count: " + requestSize);
        }
    }

    /**
     * Initialize ElasticSearch client
     *
     * @return client
     * @throws UnknownHostException unknown host exception
     */
    private TransportClient getTransportClient() throws UnknownHostException {

        return new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(
                        InetAddress.getByName(elasticSearchHost), elasticSearchPort));
    }
}
