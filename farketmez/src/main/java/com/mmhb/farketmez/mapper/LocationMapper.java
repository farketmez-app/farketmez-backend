package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.LocationDTO;
import com.mmhb.farketmez.model.Location;

public class LocationMapper {
    private LocationMapper() {

    }

    public static LocationDTO toLocationDto(Location location){
        return new LocationDTO(location.getId(), location.getLongitude(), location.getLatitude());
    }

    public static Location fromLocationDto(LocationDTO locationDto){
        return new Location(locationDto.getId(), locationDto.getLongitude(), locationDto.getLatitude());
    }

}
