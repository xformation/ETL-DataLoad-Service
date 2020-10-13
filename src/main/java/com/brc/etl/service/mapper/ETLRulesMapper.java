package com.brc.etl.service.mapper;


import com.brc.etl.domain.*;
import com.brc.etl.service.dto.ETLRulesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ETLRules} and its DTO {@link ETLRulesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ETLRulesMapper extends EntityMapper<ETLRulesDTO, ETLRules> {



    default ETLRules fromId(Long id) {
        if (id == null) {
            return null;
        }
        ETLRules eTLRules = new ETLRules();
        eTLRules.setId(id);
        return eTLRules;
    }
}
