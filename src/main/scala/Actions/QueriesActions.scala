package Actions

import Models.{Movie, MoviesResult}

import scala.util.{Failure, Success, Try}

object QueriesActions {

  /** ************************************************************************************************
   *  1.  Titles directed by  given director in the given year range e.g :
   *  generate titles report for director D.W. Griffith and year range 2010 to 2020
   * ************************************************************************************************ */

  def getMovieTitleByDirectorNameAndYearRange(moviesList: List[Movie], directorName: String, startRange: Int, endRange: Int): Try[List[MoviesResult]] = {
    val movieTitles: List[MoviesResult] = for {
      movieRow <- moviesList if (movieRow.director == directorName && movieRow.publishedYear.toInt >= startRange && movieRow.publishedYear.toInt <= endRange)
    } yield convertToMovie(movieRow)
    if (movieTitles.isEmpty) {
      Failure(new RuntimeException("OOPS !got an problem..."))
    } else Success(movieTitles)
  }

  /** ************************************************************************************************
   *  2. Generate report of English titles which have user reviews more than given user review filter
   *  and sort the report with user reviews by descending
   * ********************************************************************************************** */

  def getMovieTitleFilterByReviewsCount(moviesList: List[Movie], userReview: Int): List[MoviesResult] = {
    val movieList = for {
      movieRow <- moviesList if (movieRow.userReviews != "" && movieRow.language != "" && movieRow.userReviews.toInt > userReview && movieRow.language.contains("English"))
    } yield convertToMovie(movieRow)
    if (movieList.isEmpty)
      throw new RuntimeException("No Records found")
    implicit val sortListBasedOnUserReviews:Ordering[MoviesResult] = Ordering.fromLessThan((a,b)=>a.userReviews.toInt > b.userReviews.toInt)
    movieList.sorted
  }

  /** ************************************************************************************************
   *  4. Generate report of longest duration title for the given country filter, no of minimum
   *  votes filter and sort by duration in descending order
   * ********************************************************************************************** */

  def getMovieForLongestDurationFilterByCountryAndVotes(moviesList: List[Movie], country: String, noOfVotes: Int): List[MoviesResult] = {
    val movieList = for {
      movieRow <- moviesList if (movieRow.country != "" && movieRow.votes != "" && movieRow.country == country && movieRow.votes.toInt >= noOfVotes)
    } yield convertToMovie(movieRow)
    if (movieList.isEmpty)
      throw new RuntimeException("No Records found")
    implicit val sortListBasedOnDuration:Ordering[MoviesResult] = Ordering.fromLessThan((a,b)=>a.duration.toInt > b.duration.toInt)
    movieList.sorted
  }


  /*************
  Convert Movie Model class to MovieResults to get the desired output which contains
  Output: Print output of params: Title, published year, budget, user reviews,country,genre,duration
  ***********/

  def convertToMovie (movie: Movie): MoviesResult = {
    new MoviesResult(movie.title,movie.publishedYear, movie.budget, movie.userReviews, movie.country,movie.genre, movie.duration)
  }
}
