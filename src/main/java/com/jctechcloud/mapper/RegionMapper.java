package com.jctechcloud.mapper;

import com.jctechcloud.entity.Region;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class which maps region ID to region name
 * <p>
 * Created by jcincera on 18/06/16.
 */
@Service
public class RegionMapper {
    private static final List<Region> mapping = new ArrayList<>();

    static {
        mapping.add(new Region("1", "Hlavní město Praha", 50.075538, 14.437800));
        mapping.add(new Region("2", "Středočeský kraj", 50.075538, 14.437800));
        mapping.add(new Region("3", "Jihočeský kraj", 48.975658, 14.480255));
        mapping.add(new Region("4", "Plzeňský kraj", 49.738431, 13.373637));
        mapping.add(new Region("5", "Karlovarský kraj", 50.231852, 12.871962));
        mapping.add(new Region("6", "Ústecký kraj", 50.661116, 14.053146));
        mapping.add(new Region("7", "Liberecký kraj", 50.766280, 15.054339));
        mapping.add(new Region("8", "Královéhradecký kraj", 50.210361, 15.825211));
        mapping.add(new Region("9", "Pardubický kraj", 50.034309, 15.781199));
        mapping.add(new Region("10", "Kraj Vysočina", 49.398378, 15.587041));
        mapping.add(new Region("11", "Jihomoravský kraj", 49.195060, 16.606837));
        mapping.add(new Region("12", "Olomoucký kraj", 49.593778, 17.250879));
        mapping.add(new Region("13", "Moravskoslezský kraj", 49.820923, 18.262524));
        mapping.add(new Region("14", "Zlínský kraj", 49.224437, 17.662763));
    }

    /**
     * Map region ID to region name
     *
     * @param regionId region ID
     * @return region
     */
    public Region mapRegionIdToRegionName(final String regionId) {
        List<Region> result = mapping.stream().filter(r -> r.getId().equals(regionId)).collect(Collectors.toList());

        if (result.size() != 1) {
            throw new IllegalArgumentException("Invalid region ID: " + regionId);
        }

        return result.get(0);
    }
}
