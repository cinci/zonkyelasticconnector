package com.jctechcloud.connector

import org.elasticsearch.test.ESIntegTestCase
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * ElasticSearch connector test
 *
 * Created by jcincera on 12/11/2016.
 */
class ElasticSearchConnectorTest : ESIntegTestCase() {

    @Autowired
    lateinit var elasticSearchConnector: ElasticSearchConnector

    @Test
    fun storeLoansTest() {
        // todo
        // https://github.com/elastic/elasticsearch/issues/14348
    }
}
