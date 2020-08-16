/*
 * #%L
 * Advanced Java LIVE course-2020
 * %%
 * Copyright (C) 2020 MasterDevSkills.com
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package com.masterdevskills.cha2.ext2.service;

import com.masterdevskills.cha1.ext3.Person;
import com.masterdevskills.cha2.ext2.model.Movie;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author A N M Bazlur Rahman @bazlur_rahman
 * @since 07 August 2020
 */
public class RealMovieService {


	/**
	 * TODO: count the Movie object stored in InMemoryMovieService
	 *
	 * @return number of movies stored in the InMemoryMovieService
	 * @see Stream#count()
	 */
	public long countMovies() {
		var allMovies = InMemoryMovieService.getInstance().findAllMovies();
		return allMovies.stream()
						.count();
	}

	/**
	 * TODO: find all the movies released in a particular year
	 *
	 * @param year given year
	 * @return list of Movies in a particular year
	 * @see java.util.stream.Stream#filter(Predicate)
	 * @see java.util.stream.Stream#collect(Collector)
	 */
	public List<Movie> findAllMoviesInYear(int year) {

		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.filter(movie -> movie.getYear().equals(String.valueOf(year)))
				.collect(Collectors.toList());

	}

	/**
	 * TODO: given a rating, return the list of the movies of that rating
	 *
	 * @param rated given rating
	 * @return list of movies of that rating
	 * @see java.util.stream.Stream#filter(Predicate)
	 * @see java.util.stream.Stream#collect(Collector)
	 */

	public List<Movie> findAllMovieRated(String rated) {

		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.filter(movie -> movie.getRated().equals(String.valueOf(rated)))
				.collect(Collectors.toList());
	}

	/**
	 * TODO: given a rating, return the count of the movies of that rating
	 *
	 * @param rated given rating
	 * @return count of movies of that rating
	 * @see java.util.stream.Stream#filter(Predicate)
	 * @see java.util.stream.Stream#collect(Collector)
	 */

	public long countMoviesWithRated(String rated) {

		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.filter(movie -> movie.getRated().equals(String.valueOf(rated)))
				.count();
	}

	/**
	 * TODO: given a rating, return list of movies whose ratings are equal or greater than given rating
	 *
	 * @param rating given rating
	 * @return list of movies whose ratings are equal or greater than given rating
	 * @see java.util.stream.Stream#filter(Predicate)
	 * @see java.util.stream.Stream#collect(Collector)
	 */
	public List<Movie> findMoviesWithImdbRatingEqualAndGreaterThan(double rating) {

		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.filter(movie -> movie.getImdbRating() >= rating )
				.collect(Collectors.toList());
	}

	/**
	 * TODO: return list of movies that are directed by given director name
	 *
	 * @param director name of director
	 * @return list of movies that are directed by given director name
	 * @see java.util.stream.Stream#filter(Predicate)
	 * @see java.util.stream.Stream#collect(Collector)
	 */
	public List<Movie> findMoviesOfDirector(String director) {

		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.filter(movie -> movie.getDirector().equals(director))
				.collect(Collectors.toList());
	}

	/**
	 * TODO: return list of Movie Title of those movies whose rating is equal to given rating
	 *
	 * @param rated given rating
	 * @return list of Movie Title of those movies whose rating is equal to given rating
	 */
	public List<String> listMovieTitleRated(String rated) {

		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.filter(movie -> movie.getRated().equals(rated))
				.map(Movie::getTitle)
				.collect(Collectors.toList());
	}

	/**
	 * TODO: return list of distinct movie title of that movie, whose rating is equal to given rating
	 *
	 * @param rated given rating
	 * @return list of distinct movie title of that movie, whose rating is equal to given rating
	 * @see java.util.stream.Stream#filter(Predicate)
	 * @see java.util.stream.Stream#collect(Collector)
	 * @see Stream#distinct()
	 */
	public List<String> listUniqueMovieTitleRated(String rated) {

		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.filter(movie -> movie.getRated().equals(rated))
				.map(Movie::getTitle)
				.distinct()
				.collect(Collectors.toList());
	}

	/**
	 * TODO: return movie title of any movie whose rating is equal or greater than given rating
	 *
	 * @param rating given rating
	 * @return movie title of any movie whose rating is equal or greater than given rating
	 * @see Stream#findAny()
	 */
	public Optional<String> findAnyMovieTitleWithImdbRatingEqualOrGreater(double rating) {
		Comparator<Movie> sortByImdRating =
				Comparator.comparing(Movie::getImdbRating);
		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.filter(movie -> movie.getImdbRating() >= rating )
				.sorted(sortByImdRating)
				.map(Movie::getTitle)
				.findAny();
	}

	/**
	 * TODO: return movie title of the first movie whose rating is equal or greater than given rating
	 *
	 * @param rating given rating
	 * @return name of the first movie with given rating, empty if not
	 * @see Stream#findFirst()
	 * @see Stream#map(Function)
	 */
	public Optional<String> findFirstMovieTitleWithImdbRatingEqualOrGreater(double rating) {
		Comparator<Movie> sortByImdRating =
				Comparator.comparing(Movie::getImdbRating);
		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.filter(movie -> movie.getImdbRating() >= rating )
				.sorted(sortByImdRating)
				.map(Movie::getTitle)
				.findFirst();
	}

	/**
	 * TODO: sort all the movies by their title and return the sorted list
	 *
	 * @return the sorted list
	 * @see Stream#sorted(Comparator)
	 */
	public List<Movie> sortMovieByTitle() {
		var allMovies = InMemoryMovieService.getInstance()
				.findAllMovies();
		Comparator<Movie> sortByTitle = Comparator.comparing(Movie::getTitle);
		return allMovies.stream()
				.sorted(sortByTitle)
				.collect(Collectors.toList());
	}

	/**
	 * TODO: sort all the movies by their imdb rating, then by their title(if rating are equal) and return the sorted list
	 *
	 * @return the sorted list
	 * @see Stream#sorted(Comparator)
	 * @see Comparator#thenComparing(Function)
	 */
	public List<Movie> sortByImdbRatingAndThenTitle() {

		var allMovies = InMemoryMovieService.getInstance()
				.findAllMovies();
		Comparator<Movie> sortByImdRatingAndTitle =
				Comparator.comparing(Movie::getImdbRating)
						.thenComparing(Movie::getTitle);
		return allMovies.stream()
				.sorted(sortByImdRatingAndTitle)
				.collect(Collectors.toList());
	}

	/**
	 * TODO: find top rated movie from all the movies and return the Movie
	 *
	 * @return movie with maximum rating
	 * @see Stream#max(Comparator)
	 */

	public Optional<Movie> findTopRatedMovie() {

		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.max(Comparator.comparing(Movie::getImdbRating));
	}

	/**
	 * TODO: find min rated movie from all the movies and return the Movie
	 *
	 * @return movie with minimum rating
	 */

	public Optional<Movie> findMinRatedMovie() {

		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.min(Comparator.comparing(Movie::getImdbRating));
	}

	/**
	 * TODO: find number of Distinct Movies of each director
	 *
	 * @return list of Distinct movies
	 */

	public Map<String, Long> findNumberOfDistinctMoviesOfEachDirector() {
		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.distinct()
				.collect(Collectors.groupingBy(Movie::getDirector,
						Collectors.counting()));
	}

	/**
	 * TODO:  find number movies title by years comma separated
	 *
	 * @return map containing year and movie tiles comma separated
	 */
	public Map<String, String> getMoviesByYear() {
		return InMemoryMovieService.getInstance().findAllMovies().stream()
				.collect(Collectors.groupingBy(Movie::getYear,
						Collectors.mapping(Movie::getTitle,
								Collectors.joining(", "))));
	}
}


