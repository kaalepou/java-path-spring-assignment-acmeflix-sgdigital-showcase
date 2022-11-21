package com.acmeflix.service;

import com.acmeflix.domain.Movie;
import com.acmeflix.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"movies"}, keyGenerator = "customCacheKeyGenerator")
public class MovieServiceImpl extends BaseServiceImpl<Movie> implements MovieService {
	private final MovieRepository movieRepository;

	@Override
	public JpaRepository<Movie, Long> getRepository() {
		return movieRepository;
	}

	@Override
	@Cacheable
	public List<Movie> findByTitle(final String title) {
		return movieRepository.findByTitleIgnoreCase(title);
	}

	@Override
	@Cacheable
	public List<Movie> findAll() {
		return getFullContent();
	}

	@Override
	public List<Movie> getFullContent() {
		return movieRepository.getFullContent();
	}

	@Override
	@Cacheable(cacheNames = "movies", key = "#id")
	public Movie getFullContent(final Long id) {
		return movieRepository.getFullContent(id);
	}

	@Override
	@Cacheable(cacheNames = "ratings")
	public List<Movie> getRatings() {
		return movieRepository.getRatings();
	}

	@Caching(evict = {@CacheEvict(value = "movies", allEntries = true), @CacheEvict(value = "ratings", allEntries = true)})
	@Scheduled(cron = "0/10 * * * * ?")
	public void clearAllCaches() {
		logger.info("Clearing movies cache.");
	}
}
