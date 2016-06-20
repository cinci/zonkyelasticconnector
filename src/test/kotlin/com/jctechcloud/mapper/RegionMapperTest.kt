package com.jctechcloud.mapper

import com.jctechcloud.ZonkyElasticConnectorApplication
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

/**
 * Region name mapper test
 *
 * Created by jcincera on 18/06/16.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@SpringApplicationConfiguration(classes = arrayOf(ZonkyElasticConnectorApplication::class))
@TestPropertySource(locations = arrayOf("classpath:application.test.properties"))
@WebAppConfiguration
class RegionMapperTest {

    @Autowired
    lateinit var regionMapper: RegionMapper

    @Test
    fun verifyFirstRegionIndex() {
        val regionName = regionMapper.mapRegionIdToRegionName("1")

        Assert.assertEquals("Hlavní město Praha", regionName)
    }

    @Test
    fun verifyMiddleRegionIndex() {
        val regionName = regionMapper.mapRegionIdToRegionName("8")

        Assert.assertEquals("Královéhradecký kraj", regionName)
    }

    @Test
    fun verifyLastRegionIndex() {
        val regionName = regionMapper.mapRegionIdToRegionName("14")

        Assert.assertEquals("Zlínský kraj", regionName)
    }

    @Test(expected = IllegalArgumentException::class)
    fun checkSmallUnsupportedRegionIndex() {
        regionMapper.mapRegionIdToRegionName("-1")
    }

    @Test(expected = IllegalArgumentException::class)
    fun checkBigUnsupportedRegionIndex() {
        regionMapper.mapRegionIdToRegionName("55")
    }
}
