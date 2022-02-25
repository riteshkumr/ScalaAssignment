import Actions.QueriesActions
import FileManager.FileReader
import Models.{Movie, MoviesResult}

import scala.util.Try

object Main extends App {
  val fileName: String = "imdb_movies.csv";
  //println(FileReader.getMovieDetailsFromList(fileName))
  val movies: List[Movie] = FileReader.getMovieDetailsFromList(fileName)

  /** ************************************************************************************************
   *  1. Titles directed by  given director in the given year range e.g :
   *     generate titles report for director D.W. Griffith and year range 2010 to 2020
   *     ************************************************************************************************ */

  print("Please provide the director name : ")
  val directorName: String = scala.io.StdIn.readLine
  println("Please provide the range for start year :")
  val startYear: Int = scala.io.StdIn.readLine.toInt
  println("Please provide the range for end year :")
  val endYear: Int = scala.io.StdIn.readLine.toInt
  val listOfMovie: Try[List[MoviesResult]] = QueriesActions.getMovieTitleByDirectorNameAndYearRange(movies, directorName, startYear, endYear)
  if (listOfMovie.isSuccess)
    listOfMovie.map(_.foreach(println))
  else
    throw new RuntimeException

  /** ************************************************************************************************
   * 2. Generate report of English titles which have user reviews more than given user review filter
   * and sort the report with user reviews by descending
   * ********************************************************************************************** */

  print("Please provide the no. of reviews : ")
  val reviewsCount: Int = scala.io.StdIn.readLine.toInt
  try {
    val listOfMovie = QueriesActions.getMovieTitleFilterByReviewsCount(movies, reviewsCount)
    listOfMovie.foreach(m => println(m))
  }
  catch {
    case r: RuntimeException => println(r.getMessage)
    case _ => println("Error!! Please try again")
  }

  /** ************************************************************************************************
   * 4. Generate report of longest duration title for the given country filter, no of minimum
   * votes filter and sort by duration in descending order
   * ********************************************************************************************** */

  print("Please provide the country name : ")
  val newCountry: String = scala.io.StdIn.readLine()
  print("Please provide the minimum votes : ")
  val minVotes: Int = scala.io.StdIn.readLine().toInt
  try {
    val listOfMovie = QueriesActions.getMovieForLongestDurationFilterByCountryAndVotes(movies, newCountry, minVotes)
    listOfMovie.foreach(m => println(m))
  }
  catch {
    case r: RuntimeException => println(r.getMessage)
    case _ => println("Error!! Please try again")
  }
}

