package com.acmeflix.bootstrap;

import com.acmeflix.base.BaseComponent;
import com.acmeflix.domain.Account;
import com.acmeflix.domain.CastMember;
import com.acmeflix.domain.CreditCard;
import com.acmeflix.domain.Episode;
import com.acmeflix.domain.Movie;
import com.acmeflix.domain.Person;
import com.acmeflix.domain.Season;
import com.acmeflix.domain.TvShow;
import com.acmeflix.domain.enumeration.Genre;
import com.acmeflix.domain.enumeration.Language;
import com.acmeflix.domain.enumeration.Role;
import com.acmeflix.domain.enumeration.SubscriptionPlans;
import com.acmeflix.domain.enumeration.ViewingRestriction;
import com.acmeflix.service.AccountService;
import com.acmeflix.service.CastMemberService;
import com.acmeflix.service.MovieService;
import com.acmeflix.service.PersonService;
import com.acmeflix.service.SubscriptionPlanService;
import com.acmeflix.service.TvShowService;
import java.util.Arrays;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
//@Profile("generate-sample-data")
@AllArgsConstructor
public class SampleContentCreator extends BaseComponent implements CommandLineRunner {
	private final AccountService accountService;
	private final CastMemberService castMemberService;
	private final MovieService movieService;
	private final PersonService personService;
	private final SubscriptionPlanService subscriptionPlanService;
	private final TvShowService tvShowService;

	@Override
	public void run(final String... args) throws Exception {
		subscriptionPlanService.createAll(SubscriptionPlans.BASIC.getSubscriptionPlan(),
				SubscriptionPlans.STANDARD.getSubscriptionPlan(),
				SubscriptionPlans.PREMIUM.getSubscriptionPlan());

		//@formatter:off
		Account firstAccount = Account.builder()
				.email("c.giannacoulis@codehub.gr")
				.password("test123")
				.subscriptionPlan(subscriptionPlanService.getByTitle("STANDARD")).build();

		CreditCard firstCreditCard = CreditCard.builder()
				.cardNumber("1234432156788765")
				.cardHolder("XXX")
				.expirationDate("01/27")
				.securityCode(123).build();

		accountService.addCreditCard(firstAccount, firstCreditCard);
		firstAccount.setProfiles(new HashSet<>(Arrays.asList(com.acmeflix.domain.Profile.builder()
						.name("adult1")
						.language(Language.ENGLISH)
						.viewingRestriction(ViewingRestriction.ALL)
						.account(firstAccount)
						.build(),
				com.acmeflix.domain.Profile.builder()
						.name("adult2")
						.language(Language.ENGLISH)
						.viewingRestriction(ViewingRestriction.EIGHTEEN)
						.account(firstAccount)
						.build(),
				com.acmeflix.domain.Profile.builder()
						.name("kiddo")
						.language(Language.ENGLISH)
						.viewingRestriction(ViewingRestriction.SEVEN)
						.account(firstAccount)
						.build())));

		Account firstAccountSaved = accountService.create(firstAccount);
		logger.info("Account saved: {}.", firstAccountSaved);

		Account firstAccountRetrieved = accountService.get(firstAccountSaved.getId());
		logger.info("Account retrieved: {}.", firstAccountRetrieved);

//		Account firstAccountFullyRetrieved = accountService.getFullContent(firstAccountSaved.getId());
//		logger.info("Account retrieved: {}.", firstAccountFullyRetrieved);

		// Create first movie
		Movie firstMovie = Movie.builder()
				.title("Man From Toronto")
				.plot("The world's deadliest assassin and New York's biggest screw-up are mistaken for each other at an Airbnb rental.")
				.releaseYear(2022)
				.durationInMinutes(112)
				.genres(new HashSet<>(Arrays.asList(Genre.ACTION, Genre.COMEDY)))
				.viewingRestriction(ViewingRestriction.THIRTEEN)
				.audioLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.subtitleLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.build();
		// Create second movie
		Movie secondMovie = Movie.builder()
				.title("Red Notice")
				.plot("An Interpol agent successfully tracks down the world's most wanted art thief, with help from a rival thief. But nothing is as it seems, as a series of double-crosses ensue.")
				.releaseYear(2022)
				.durationInMinutes(118)
				.genres(new HashSet<>(Arrays.asList(Genre.ACTION, Genre.COMEDY)))
				.viewingRestriction(ViewingRestriction.THIRTEEN)
				.audioLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.subtitleLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.build();
		// Create fellowship of the ring movie
		Movie fellowshipOfTheRing = Movie.builder()
				.title("The Lord of the Rings: The Fellowship of the Ring")
				.plot("A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.")
				.releaseYear(2001)
				.durationInMinutes(178)
				.genres(new HashSet<>(Arrays.asList(Genre.ADVENTURE, Genre.ACTION, Genre.COMEDY, Genre.SCIFI)))
				.viewingRestriction(ViewingRestriction.THIRTEEN)
				.audioLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.subtitleLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.build();
		// Create two towers
		Movie theTwoTowers = Movie.builder()
				.title("The Lord of the Rings: The Two Towers")
				.plot("While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.")
				.releaseYear(2002)
				.durationInMinutes(179)
				.genres(new HashSet<>(Arrays.asList(Genre.ADVENTURE, Genre.ACTION, Genre.COMEDY, Genre.SCIFI)))
				.viewingRestriction(ViewingRestriction.THIRTEEN)
				.audioLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.subtitleLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.build();

		// Set second movie as a recommendation to the first one
		firstMovie.setRecommendations(new HashSet<>(Arrays.asList(secondMovie)));
		// Set the Two Towers as a recommendation to fellowship of the ring
		fellowshipOfTheRing.setRecommendations(new HashSet<>(Arrays.asList(theTwoTowers)));

		movieService.createAll(firstMovie, secondMovie, fellowshipOfTheRing, theTwoTowers);

		// First TV Show
		TvShow firstTvShow = TvShow.builder()
				.title("Stranger Things")
				.plot("When a young boy disappears, his mother, a police chief and his friends must confront terrifying supernatural forces in order to get him back.")
				.releaseYear(2016)
				.genres(new HashSet<>(Arrays.asList(Genre.ACTION, Genre.COMEDY)))
				.viewingRestriction(ViewingRestriction.THIRTEEN)
				.audioLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.subtitleLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.seasons(new HashSet<>(Arrays.asList(
						Season.builder()
								.title("Season 1")
								.ordering(1)
								.releaseYear(2016)
								.episodes(new HashSet<>(Arrays.asList(
										Episode.builder().title("Chapter One: The Vanishing of Will Byers").durationInMinutes(49).build(),
										Episode.builder().title("Chapter Two: The Weirdo on Maple Street").durationInMinutes(56).build(),
										Episode.builder().title("Chapter Three: Holly, Jolly").durationInMinutes(52).build(),
										Episode.builder().title("Chapter Four: The Body").durationInMinutes(51).build())))
								.build(),
						Season.builder()
								.title("Season 2")
								.ordering(2)
								.releaseYear(2017)
								.episodes(new HashSet<>(Arrays.asList(
										Episode.builder().title("Chapter One: MADMAX").durationInMinutes(48).build(),
										Episode.builder().title("Chapter Two: Trick or Treat, Freak").durationInMinutes(56).build(),
										Episode.builder().title("Chapter Three: The Pollywog").durationInMinutes(51).build(),
										Episode.builder().title("Chapter Four: Will the Wise").durationInMinutes(46).build())))
								.build(),
						Season.builder()
								.title("Season 3")
								.ordering(3)
								.releaseYear(2019)
								.episodes(new HashSet<>(Arrays.asList(
										Episode.builder().title("Chapter One: Suzie, Do You Copy?").durationInMinutes(51).build(),
										Episode.builder().title("Chapter Two: The Mall Rats").durationInMinutes(50).build(),
										Episode.builder().title("Chapter Three: The Case of the Missing Lifeguard").durationInMinutes(50).build(),
										Episode.builder().title("Chapter Four: The Sauna Test").durationInMinutes(53).build())))
								.build(),
						Season.builder()
								.title("Season 4")
								.ordering(4)
								.releaseYear(2022)
								.episodes(new HashSet<>(Arrays.asList(
										Episode.builder().title("Chapter One: The Hellfire Club").durationInMinutes(78).build(),
										Episode.builder().title("Chapter Two: Vecna's Curse").durationInMinutes(77).build(),
										Episode.builder().title("Chapter Three: The Monster and the Superhero").durationInMinutes(64).build(),
										Episode.builder().title("Chapter Four: Dear Billy").durationInMinutes(79).build())))
								.build())))
				.build();
		// setting TV Show for each season
		firstTvShow.getSeasons().forEach(season -> season.setTvShow(firstTvShow));

		// setting season for each episode
		firstTvShow.getSeasons().forEach(season -> season.getEpisodes().forEach(episode -> episode.setSeason(season)));

		tvShowService.addSeasons(firstTvShow,
				Season.builder().title("Season 1").ordering(1).releaseYear(2016).build(),
				Season.builder().title("Season 2").ordering(2).releaseYear(2017).build(),
				Season.builder().title("Season 3").ordering(3).releaseYear(2018).build(),
				Season.builder().title("Season 4").ordering(4).releaseYear(2022).build());

		tvShowService.create(firstTvShow);

		// Using services now
		TvShow secondTvShow = TvShow.builder()
				.title("Peaky Blinders")
				.plot("A gangster family epic set in 1900s England, centering on a gang who sew razor blades in the peaks of their caps, and their fierce boss Tommy Shelby.")
				.releaseYear(2013)
				.genres(new HashSet<>(Arrays.asList(Genre.ACTION, Genre.DRAMA)))
				.viewingRestriction(ViewingRestriction.THIRTEEN)
				.audioLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.subtitleLanguages(new HashSet<>(Arrays.asList(Language.ENGLISH, Language.GREEK)))
				.build();

		tvShowService.addSeasons(secondTvShow,
				Season.builder().title("Season 1").ordering(1).releaseYear(2013).build(),
				Season.builder().title("Season 2").ordering(2).releaseYear(2014).build(),
				Season.builder().title("Season 3").ordering(3).releaseYear(2016).build(),
				Season.builder().title("Season 4").ordering(4).releaseYear(2017).build(),
				Season.builder().title("Season 5").ordering(5).releaseYear(2019).build(),
				Season.builder().title("Season 6").ordering(6).releaseYear(2022).build());

		tvShowService.create(secondTvShow);

		TvShow secondTvShowRetrieved = tvShowService.getFullContent(5L);

		tvShowService.addEpisodes(tvShowService.getSeason(secondTvShowRetrieved, 4),
				Episode.builder().title("Black Day").durationInMinutes(55).build(),
				Episode.builder().title("Black Shirt").durationInMinutes(59).build(),
				Episode.builder().title("Gold").durationInMinutes(56).build(),
				Episode.builder().title("Sapphire").durationInMinutes(59).build(),
				Episode.builder().title("The Road to Hell").durationInMinutes(59).build(),
				Episode.builder().title("Lock and Key").durationInMinutes(82).build());

		tvShowService.update(secondTvShowRetrieved);


		personService.createAll(Person.builder().firstName("Cillian").lastName("Murphy").build(),
				Person.builder().firstName("Paul").lastName("Anderson").build());

		Person cillianMurphy = personService.findByLastNameAndFirstName("MURPHY", "Cillian");
		Person paulAnderson = personService.findByLastNameAndFirstName("ANDERSON", "Paul");

		castMemberService.create(CastMember.builder()
				.person(cillianMurphy)
				.content(secondTvShowRetrieved).role(Role.ACTOR).build());
		castMemberService.create(CastMember.builder()
				.person(paulAnderson)
				.content(secondTvShowRetrieved)
				.role(Role.ACTOR)
				.build());

		logger.info("Submitting ratings.");
		Account retrievedFirstAccount = accountService.getFullContent(1L);
		accountService.rate(retrievedFirstAccount.getProfile("adult1"), movieService.getFullContent(1L), 4.5d);
		accountService.rate(retrievedFirstAccount.getProfile("adult1"), movieService.getFullContent(2L), 4d);

		// Enable the following line to check whether validation works
		// accountService.rate(retrievedFirstAccount.getProfile("kiddo"), movieService.get(2L), 4d);
		accountService.rate(retrievedFirstAccount.getProfile("adult1"), movieService.get(1L), 4.5d);
		accountService.rate(retrievedFirstAccount.getProfile("adult1"), movieService.get(2L), 4d);
		accountService.rate(retrievedFirstAccount.getProfile("adult2"), movieService.get(1L), 4d);
		accountService.rate(retrievedFirstAccount.getProfile("adult2"), movieService.get(2L), 4d);
		accountService.rate(retrievedFirstAccount.getProfile("adult2"), movieService.get(3L), 4d);
		accountService.rate(retrievedFirstAccount.getProfile("adult2"), movieService.get(4L), 4d);


		logger.info("Retrieving ratings.");
//
//		tvShowService.getRatings().forEach(tvShow -> {
//			logger.debug("TV Show '{}' was rated {}.", tvShow.getTitle(),
//					String.format("%3.2f", tvShow.getRatings().stream().mapToDouble(Rating::getRate).average()
//							.getAsDouble()));
//		});
//		movieService.getRatings().forEach(movie -> {
//			logger.debug("Movie '{}' was rated {}.", movie.getTitle(),
//					String.format("%3.2f", movie.getRatings().stream().mapToDouble(Rating::getRate).average()
//							.getAsDouble()));
//		});
	}
}