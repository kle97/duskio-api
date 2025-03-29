package com.duskio.features.profile;

import com.duskio.common.mapper.ReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReferenceMapper.class})
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    ProfileResponse toProfileResponse(Profile profile);

    ProfileEntityResponse toProfileEntityResponse(Profile profile);

    Profile toProfile(ProfileResponse profileRequest);

    Profile toExistingProfile(ProfileRequest profileRequest, @MappingTarget Profile profile);
}
