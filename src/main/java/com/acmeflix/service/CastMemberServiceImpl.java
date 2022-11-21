package com.acmeflix.service;

import com.acmeflix.domain.CastMember;
import com.acmeflix.domain.Movie;
import com.acmeflix.repository.CastMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"cast"}, keyGenerator = "customCacheKeyGenerator")
public class CastMemberServiceImpl extends BaseServiceImpl<CastMember> implements CastMemberService {
	private final CastMemberRepository castMemberRepository;

	@Override
	public JpaRepository<CastMember, Long> getRepository() {
		return castMemberRepository;
	}

	@Override
	@Cacheable
	public List<CastMember> findAll() {
		return castMemberRepository.findAll();
	}

	@Caching(evict = {@CacheEvict(value = "cast", allEntries = true)})
	@Scheduled(cron = "0/10 * * * * ?")
	public void clearAllCaches() {
		logger.info("Clearing cast member cache.");

	}
}
