package com.acmeflix.controller;

import com.acmeflix.base.BaseMapper;
import com.acmeflix.domain.Movie;
import com.acmeflix.mapper.MovieMapper;
import com.acmeflix.service.BaseService;
import com.acmeflix.service.MovieService;
import com.acmeflix.transfer.resource.MovieResource;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController extends AbstractController<Movie, MovieResource> {
	private final MovieService movieService;
	private final MovieMapper movieMapper;

	@Override
	public BaseService<Movie, Long> getBaseService() {
		return movieService;
	}

	@Override
	public BaseMapper<Movie, MovieResource> getMapper() {
		return movieMapper;
	}

	@GetMapping(value = "/all")
	public List<Movie> getAllMovies() {
		return movieService.findAll();
	}

	@GetMapping(value = "/all/{title}/info")
	public List<Movie> getMovieInfo(@PathVariable("title") String title) {
		return movieService.findByTitle(title);
	}
	@GetMapping(value = "/all/review")
	public List<Movie> addMovieReview() {
		return movieService.getRatings();
	}

	@GetMapping(value = "/all/first-ten-popular")
	public List<Movie> getFirstTenPopularMovies() {
		List<Movie> movieList = movieService.findAll();
		return movieList.stream().limit(10).filter(movie -> null != movie.getRatings())
				.collect(Collectors.toList());
	}

}
