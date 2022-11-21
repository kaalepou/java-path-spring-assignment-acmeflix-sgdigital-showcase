package com.acmeflix.mapper;

import com.acmeflix.base.BaseMapper;
import com.acmeflix.domain.Episode;
import com.acmeflix.transfer.resource.EpisodeResource;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EpisodeMapper extends BaseMapper<Episode, EpisodeResource> {

  EpisodeResource toResource(Episode episode);

  @InheritInverseConfiguration
  Episode toDomain(EpisodeResource episodeResource);

}
