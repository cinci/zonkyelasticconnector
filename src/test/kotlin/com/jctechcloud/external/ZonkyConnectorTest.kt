package com.jctechcloud.external

import com.jctechcloud.ZonkyElasticConnectorApplication
import com.jctechcloud.connector.ZonkyConnector
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

/**
 * Zonky connector test
 *
 * Created by jcincera on 17/06/16.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@SpringApplicationConfiguration(classes = arrayOf(ZonkyElasticConnectorApplication::class))
@ActiveProfiles("test")
@WebAppConfiguration
class ZonkyConnectorTest {

    @Autowired
    lateinit var zonkyConnector: ZonkyConnector

    @Value("\${zonky.defaultOrderByField}")
    lateinit var defaultOrdering: String

    @Test
    fun testLoadAllLoans() {
        val loans = zonkyConnector.loadAllLoansWithDefaultOrdering()

        Assert.assertNotNull(loans)
        Assert.assertTrue(loans.size > 0)
    }

    @Test
    fun testDefaultPagination() {
        val loans = zonkyConnector.loadLoans(0, 20)

        Assert.assertNotNull(loans)
        Assert.assertEquals(20, loans.size)
    }

    @Test
    fun testCustomPagination() {
        val loans = zonkyConnector.loadLoans(2, 5)

        Assert.assertNotNull(loans)
        Assert.assertEquals(5, loans.size)
    }

    @Test
    fun testLoanDetail() {
        val loans = zonkyConnector.loadLoans(0, 5)

        Assert.assertNotNull(loans)
        Assert.assertTrue(loans.size > 0)

        val loan = zonkyConnector.loadLoan(loans[0].id)
        Assert.assertNotNull(loan)
        Assert.assertEquals(loans[0].id, loan.id)
    }

    @Test
    fun testOrdering() {
        val loans1 = zonkyConnector.loadLoans(0, 3)
        val loans2 = zonkyConnector.loadLoans(0, 3, "-" + defaultOrdering)

        Assert.assertNotNull(loans1)
        Assert.assertNotNull(loans2)

        Assert.assertTrue(loans1.size == 3)
        Assert.assertTrue(loans2.size == 3)

        Assert.assertTrue(loans1[0].datePublished.before(loans2[0].datePublished))
    }
}
