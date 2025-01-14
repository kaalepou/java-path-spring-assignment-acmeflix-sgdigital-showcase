package com.acmeflix.mapper;

import com.acmeflix.base.BaseMapper;
import com.acmeflix.domain.Profile;
import com.acmeflix.transfer.resource.ProfileResource;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ContentRatingMapper.class)
public interface ProfileMapper extends BaseMapper<Profile, ProfileResource> {

  ProfileResource toResource(Profile profile);

  @InheritInverseConfiguration
  Profile toDomain(ProfileResource profileResource);
}
