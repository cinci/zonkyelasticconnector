package com.jctechcloud.mapper;

import org.springframework.stereotype.Service;

/**
 * Class which maps region ID to region name
 * <p>
 * Created by jcincera on 18/06/16.
 */
@Service
public class RegionMapper {
    private static final String[] mapping = new String[]{
            "Hlavní město Praha",
            "Středočeský kraj",
            "Jihočeský kraj",
            "Plzeňský kraj",
            "Karlovarský kraj",
            "Ústecký kraj",
            "Liberecký kraj",
            "Královéhradecký kraj",
            "Pardubický kraj",
            "Kraj Vysočina",
            "Jihomoravský kraj",
            "Olomoucký kraj",
            "Moravskoslezský kraj",
            "Zlínský kraj"
    };

    /**
     * Map region ID to region name
     *
     * @param regionId region ID
     * @return region name
     */
    public String mapRegionIdToRegionName(String regionId) {
        Integer regionIndex;

        try {
            regionIndex = Integer.valueOf(regionId) - 1;
            if (regionIndex < 0 || regionIndex > 13) {
                throw new IllegalArgumentException("Unsupported region ID");
            }

            return mapping[regionIndex];
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Unsupported region ID");
        }
    }
}
