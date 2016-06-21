package com.jctechcloud.connector;

import com.jctechcloud.entity.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Connector which handles all requests to Zonky server
 * <p>
 * Created by jcincera on 15/06/16.
 */
@Service
public class ZonkyConnector {
    private static final Logger log = LoggerFactory.getLogger(ZonkyConnector.class);

    private static final String REQUEST_USER_AGENT = "User-Agent";
    private static final String REQUEST_HEADER_PAGE = "X-Page";
    private static final String REQUEST_HEADER_SIZE = "X-Size";
    private static final String REQUEST_HEADER_ORDER_BY = "X-Order";
    private static final String RESPONSE_HEADER_TOTAL_SIZE = "X-Total";

    @Value("${custom.zonky.userAgent}}")
    private String userAgent;

    @Value("${custom.zonky.defaultOrderByField}")
    private String defaultOrderByField;

    @Value("${custom.zonky.loansPerRequest}")
    private Integer loansPerRequest;

    @Value("${custom.zonky.logFullResponse}")
    private Boolean logFullResponse;

    @Value("${custom.zonky.url.marketplace}")
    private String loanMarketplaceUrl;

    @Value("${custom.zonky.url.detail}")
    private String loanDetailUrl;

    /**
     * Load all loans from marketplace
     *
     * @return list of all loans
     */
    public List<Loan> loadAllLoansWithDefaultOrdering() {
        Integer totalSize = getMarketplaceTotalSize();

        List<Loan> result = new ArrayList<>();
        Integer requests = (totalSize / loansPerRequest) + 1;

        for (int i = 0; i < requests; i++) {
            result.addAll(loadLoans(i, loansPerRequest));
        }

        log.info("Total loan count: " + result.size());
        return result;
    }

    /**
     * Load loans from marketplace
     *
     * @param page page number
     * @param size expected result size
     * @return list of loans
     */
    public List<Loan> loadLoans(Integer page, Integer size) {
        return loadLoans(page, size, defaultOrderByField);
    }

    /**
     * Load loans from marketplace
     *
     * @param page    page number
     * @param size    expected result size
     * @param orderBy order by field
     * @return list of loans
     */
    public List<Loan> loadLoans(Integer page, Integer size, String orderBy) {
        MultiValueMap<String, String> headers = getDefaultHeaders();

        String requestOrderBy = orderBy == null ? defaultOrderByField : orderBy;
        headers.add(REQUEST_HEADER_ORDER_BY, requestOrderBy);
        headers.add(REQUEST_HEADER_PAGE, page.toString());
        headers.add(REQUEST_HEADER_SIZE, size.toString());
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        log.info("Requesting marketplace page: " + page + " with size: " + size + " order by: " + requestOrderBy);
        ResponseEntity<Loan[]> response = new RestTemplate()
                .exchange(loanMarketplaceUrl, HttpMethod.GET, httpEntity, Loan[].class);

        if (!isCorrectResponseWithBody(response) || response.getBody().length == 0) {
            return new ArrayList<>();
        }

        return Arrays.asList(response.getBody());
    }

    /**
     * Load specific loan detail
     *
     * @param id loan id
     * @return loan detail or null
     */
    public Loan loadLoan(Long id) {
        log.info("Requesting loan detail with id: " + id);
        MultiValueMap<String, String> headers = getDefaultHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<Loan> response = new RestTemplate()
                .exchange(loanDetailUrl + "/" + id.toString(), HttpMethod.GET, httpEntity, Loan.class);

        if (!isCorrectResponseWithBody(response)) {
            return null;
        }

        return response.getBody();
    }

    /**
     * Get marketplace total size
     *
     * @return marketplace size
     */
    private Integer getMarketplaceTotalSize() {
        MultiValueMap<String, String> headers = getDefaultHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<Loan[]> response = new RestTemplate()
                .exchange(loanMarketplaceUrl, HttpMethod.GET, httpEntity, Loan[].class);

        if (!isCorrectResponseWithBody(response)) {
            return 0;
        }

        List<String> totalSizeHeaderValues = response.getHeaders().get(RESPONSE_HEADER_TOTAL_SIZE);
        if (totalSizeHeaderValues == null || totalSizeHeaderValues.size() < 1) {
            throw new RestClientException("Missing " + RESPONSE_HEADER_TOTAL_SIZE + " header!");
        }

        Integer marketplaceTotalSize = Integer.valueOf(totalSizeHeaderValues.get(0));
        log.info("Marketplace total size: " + marketplaceTotalSize);

        return marketplaceTotalSize;
    }

    /**
     * Get default headers
     *
     * @return headers
     */
    private MultiValueMap<String, String> getDefaultHeaders() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(REQUEST_USER_AGENT, userAgent);
        return headers;
    }

    /**
     * Check if response from server is OK and body is not empty
     *
     * @param response response from server
     * @return true if response has status 200 and body is not null
     */
    private boolean isCorrectResponseWithBody(ResponseEntity<?> response) {
        logResponse(response);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            log.error("Invalid status code: " + response.getStatusCode());
            return false;
        }

        if (!response.hasBody()) {
            log.error("Response body is null!");
            return false;
        }

        return true;
    }

    /**
     * Log response
     *
     * @param response response from server
     */
    private void logResponse(ResponseEntity<?> response) {
        if (!logFullResponse) {
            return;
        }

        log.info("Response status code: " + response.getStatusCode().toString());

        for (Map.Entry<String, List<String>> header : response.getHeaders().entrySet()) {
            log.info(header.getKey() + ": " + header.getValue());
        }

        log.info("Response body: " + (response.hasBody() ? response.getBody().toString() : "null"));
    }
}
