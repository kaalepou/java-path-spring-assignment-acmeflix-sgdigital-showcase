package com.acmeflix.controller;

import com.acmeflix.base.BaseMapper;
import com.acmeflix.domain.TvShow;
import com.acmeflix.mapper.TvShowMapper;
import com.acmeflix.service.BaseService;
import com.acmeflix.service.TvShowService;
import com.acmeflix.transfer.resource.TvShowResource;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tvshows")
public class TvShowController extends AbstractController<TvShow, TvShowResource> {
	private final TvShowService tvShowService;
	private final TvShowMapper tvShowMapper;

	@Override
	public BaseService<TvShow, Long> getBaseService() {
		return tvShowService;
	}

	@Override
	public BaseMapper<TvShow, TvShowResource> getMapper() {
		return tvShowMapper;
	}

	@GetMapping(value = "/all")
	public List<TvShow> getAllShows() {
		return tvShowService.findAll();
	}

	@GetMapping(value = "all/{title}/info")
	public List<TvShow> getShowInfo(@PathVariable("title") String title) {
		return tvShowService.findByTitle(title);
	}
	@GetMapping(value = "/all/review")
	public List<TvShow> addMovieReview() {
		return tvShowService.getRatings();
	}

	@GetMapping(value = "/all/first-ten-popular")
	public List<TvShow> getFirstTenPopularShows() {
		List<TvShow> tvShows = tvShowService.findAll();
		return tvShows.stream().limit(10).filter(show -> null != show.getRatings())
				.collect(Collectors.toList());
	}
}
