package com.jctechcloud.mapper

import com.jctechcloud.ZonkyElasticConnectorApplication
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

/**
 * Region name mapper test
 *
 * Created by jcincera on 18/06/16.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@SpringApplicationConfiguration(classes = arrayOf(ZonkyElasticConnectorApplication::class))
@ActiveProfiles("test")
@WebAppConfiguration
class RegionMapperTest {

    @Autowired
    lateinit var regionMapper: RegionMapper

    @Test
    fun verifyFirstRegionIndex() {
        val region = regionMapper.mapRegionIdToRegionName("1")

        Assert.assertEquals("Hlavní město Praha", region.name)
    }

    @Test
    fun verifyMiddleRegionIndex() {
        val region = regionMapper.mapRegionIdToRegionName("8")

        Assert.assertEquals("Královéhradecký kraj", region.name)
    }

    @Test
    fun verifyLastRegionIndex() {
        val region = regionMapper.mapRegionIdToRegionName("14")

        Assert.assertEquals("Zlínský kraj", region.name)
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
