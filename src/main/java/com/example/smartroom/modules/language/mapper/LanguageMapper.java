package com.example.smartroom.modules.language.mapper;

import com.example.smartroom.modules.language.dto.CreateLanguageDto;
import com.example.smartroom.modules.language.dto.LanguageDto;
import com.example.smartroom.modules.language.dto.UpdateLanguageDto;
import com.example.smartroom.modules.language.entity.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    LanguageMapper INSTANCE = Mappers.getMapper(LanguageMapper.class);

    LanguageDto toDto(Language entity);

    @Mapping(target = "id", ignore = true)
    Language toEntity(CreateLanguageDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromDto(@MappingTarget Language entity, UpdateLanguageDto dto);
}
