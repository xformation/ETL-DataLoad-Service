package com.brc.etl.service.mapper;


import com.brc.etl.domain.*;
import com.brc.etl.service.dto.ETLDataLoadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ETLDataLoad} and its DTO {@link ETLDataLoadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ETLDataLoadMapper extends EntityMapper<ETLDataLoadDTO, ETLDataLoad> {



    default ETLDataLoad fromId(Long id) {
        if (id == null) {
            return null;
        }
        ETLDataLoad eTLDataLoad = new ETLDataLoad();
        eTLDataLoad.setRecordId(id);
        return eTLDataLoad;
    }
}
