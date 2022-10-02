package com.huhoot.mapper;

import com.huhoot.dto.StudentInChallengeUpdateRequest;
import com.huhoot.model.Participant;
import com.huhoot.organize.StudentInChallengeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface StudentInChallengeMapper {
    //    @Mappings({
//            @Mapping(source = "primaryKey.customer.id", target = "studentId"),
//            @Mapping(source = "primaryKey.customer.username", target = "studentUsername"),
//            @Mapping(source = "primaryKey.customer.fullName", target = "studentFullName")
//    })
    StudentInChallengeResponse toDto(Participant entity);

//    @Mappings({
//            @Mapping(source = "isKicked", target = "kicked"),
//            @Mapping(source = "isLogin", target = "login"),
//    })
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(StudentInChallengeUpdateRequest dto, @MappingTarget Participant entity);
}
