


import Actions.QueriesActions
import FileManager.FileReader
//import Main.{country, minVotes, movies, reviewsCount}
import Models.{Movie, MoviesResult}
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.util.{Failure, Success, Try}

class MovieTest extends FunSuite with BeforeAndAfter{

  var movies: List[Movie] = _

  before {
    movies = FileReader.getMovieDetailsFromList("imdb_movies.csv")
  }

  test("Reading csv file : Success Test") {
    val testCSVReaderMovie = FileReader.readCsvFile("imdb_movies.csv")
    assert(testCSVReaderMovie.isEmpty==false)
  }

  test("Reading CSV File : Failure Test - Reading wrong file ") {
    val testMovieList=FileReader.readCsvFile("wrongCsvFile.csv")
    assert(testMovieList.isEmpty)
  }


  test("Testing Query no. 1 : Failure Test Case"){
    val listOfMovie:Try[List[MoviesResult]] = QueriesActions.getMovieTitleByDirectorNameAndYearRange(movies, "abc-Director-Name", 1907, 1907)
    assert(listOfMovie.isFailure)
  }

  test("Testing Query no. 1 : Success Test Case"){
    val listOfMovie:Try[List[MoviesResult]] = QueriesActions.getMovieTitleByDirectorNameAndYearRange(movies, "Charles Tait", 1906, 1906)
    assert(listOfMovie.isSuccess)
    assert(!listOfMovie.get.isEmpty)
  }

  test("Testing Query no. 2 : Failure Test Case"){
    val listOfMovies:Try[List[MoviesResult]] = Try{QueriesActions.getMovieTitleFilterByReviewsCount(movies, 20000)}
    assert(listOfMovies.isFailure)
  }

  test("Testing Query no. 2 : Success Test Case"){
    val listOfMovies:Try[List[MoviesResult]] = Try{QueriesActions.getMovieTitleFilterByReviewsCount(movies, 1300)}
    assert(listOfMovies.isSuccess)
    assert(!listOfMovies.get.isEmpty)
    val listOfTitles = listOfMovies.map(x=>x.map(y=>y.title))
    assert(listOfTitles == Success(List("Citizen Kane", "12 Angry Men")))
  }

  test("Testing no. 3 : Failure Test Case"){
    val listOfMovies:Try[List[MoviesResult]] = Try{QueriesActions.getMovieForLongestDurationFilterByCountryAndVotes(movies, "Ind", 5000)}
    assert(listOfMovies.isFailure)
  }

  test("Testing Query no. 3 : Success Test Case"){
    val listOfMovies:Try[List[MoviesResult]] = Try{QueriesActions.getMovieForLongestDurationFilterByCountryAndVotes(movies, "Denmark", 5000)}
    assert(listOfMovies.isSuccess)
    assert(!listOfMovies.get.isEmpty)
    val listOfTitles = listOfMovies.map(x=>x.map(y=>y.title))
    assert(listOfTitles == Success(List("Ordet", "Vredens dag")))
  }


}
