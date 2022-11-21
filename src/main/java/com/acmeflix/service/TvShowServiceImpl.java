package com.acmeflix.service;

import com.acmeflix.domain.Episode;
import com.acmeflix.domain.Season;
import com.acmeflix.domain.TvShow;
import com.acmeflix.repository.TvShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"tvshows"}, keyGenerator = "customCacheKeyGenerator")
public class TvShowServiceImpl extends BaseServiceImpl<TvShow> implements TvShowService {
	private final TvShowRepository tvShowRepository;

	@Override
	public JpaRepository<TvShow, Long> getRepository() {
		return tvShowRepository;
	}

	@Override
	public void addSeasons(final TvShow tvShow, final Season... seasons) {
		Arrays.stream(seasons).forEach(s -> {
			s.setTvShow(tvShow);
			tvShow.getSeasons().add(s);
		});
	}

	@Override
	public void addEpisodes(final Season season, final Episode... episodes) {
		Arrays.stream(episodes).forEach(e -> {
			e.setSeason(season);
			season.getEpisodes().add(e);
		});
	}

	@Override
	@Cacheable
	public List<TvShow> findByTitle(final String title) {
		return tvShowRepository.findByTitleIgnoreCase(title);
	}

	@Override
	@Cacheable(cacheNames = "seasons", key = "#ordering")
	public Season getSeason(final TvShow tvShow, Integer ordering) {
		return tvShow.getSeasons().stream().filter(s -> s.getOrdering() == ordering).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No season found for tvshow: "+ tvShow));
	}

	@Override
	@Cacheable
	public List<TvShow> findAll() {
		return getFullContent();
	}

	@Override
	@Cacheable(cacheNames = "tvshows", key = "#id")
	public List<TvShow> getFullContent() {
		return tvShowRepository.getFullContent();
	}

	@Override
	public TvShow getFullContent(final Long id) {
		return tvShowRepository.getFullContent(id);
	}

	@Override
	@Cacheable(cacheNames = "ratings")
	public List<TvShow> getRatings() {
		return tvShowRepository.getRatings();
	}

	@Caching(evict = {@CacheEvict(value = "tvshows", allEntries = true), @CacheEvict(value = "seasons", allEntries = true), @CacheEvict(value = "ratings", allEntries = true)})
	@Scheduled(cron = "0/10 * * * * ?")
	public void clearAllCaches() {
		logger.info("Clearing tvshows cache.");
	}
}
