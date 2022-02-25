package FileManager

import Models.Movie

import java.io.FileNotFoundException
import com.github.tototoshi.csv.CSVReader

object FileReader {

  def readCsvFile(fileName:String):  Option[List[Map[String, String]]] ={
    try{
      val reader:CSVReader = com.github.tototoshi.csv.CSVReader.open(fileName)
      val it :  Iterator[Seq[String]] = reader.iterator
      val mapList  : List[Map[String, String]] = reader.allWithHeaders()
      reader.close()
      Some(mapList)
    }
    catch {
      case f : FileNotFoundException => {
        println("CSV File not found")
        None
      }
      case _ =>None
    }
  }

  // getMovieDetailsFromList will provide a List of first 10k movies from csv
  def getMovieDetailsFromList(fileName:String): List[Movie] ={
    val listFromCsv :Option[List[Map[String, String]]] = readCsvFile(fileName)
    val list:List[Map[String, String]] = listFromCsv.getOrElse(List())
    if(list.isEmpty){
      return List()
    }
    val moviesList:List[Movie] = list.map(m => {
      var imdb_title_id =""
      var title =""
      var year =""
      var budget =""
      var reviews_from_users =""
      var country =""
      var genre =""
      var duration =""
      var director =""
      var writer =""
      var production_company =""
      var actors =""
      var description =""
      var avg_vote =""
      var language =""
      var votes =""
      var usa_gross_income =""
      var worldwide_gross_income =""
      var metaScore =""
      var reviews_from_critics =""
      m.map(x => {
        if(x._1 == "imdb_title_id") imdb_title_id = x._2
        else if(x._1 == "title") title = x._2
        else if(x._1 == "year") year = x._2
        else if(x._1 == "budget") budget = x._2
        else if(x._1 == "reviews_from_users") reviews_from_users = x._2
        else if(x._1 == "country") country = x._2
        else if(x._1 == "genre") genre = x._2
        else if(x._1 == "duration") duration = x._2
        else if(x._1 == "director") director = x._2
        else if(x._1 == "writer") writer = x._2
        else if(x._1 == "production_company") production_company = x._2
        else if(x._1 == "actors") actors = x._2
        else if(x._1 == "description") description = x._2
        else if(x._1 == "avg_vote") avg_vote = x._2
        else if(x._1 == "language") language = x._2
        else if(x._1 == "votes") votes = x._2
        else if(x._1 == "usa_gross_income") usa_gross_income = x._2
        else if(x._1 == "worldwide_gross_income") worldwide_gross_income = x._2
        else if(x._1 == "metaScore") metaScore = x._2
        else if(x._1 == "reviews_from_critics") reviews_from_critics = x._2
      })
      val  movie:Movie = Movie(imdb_title_id,title,year,budget,reviews_from_users,country,genre,duration,
        director,writer,production_company,actors,description,avg_vote,language,votes,usa_gross_income,worldwide_gross_income,metaScore,reviews_from_critics)
      movie
    })
    val movies10K = moviesList.take(10000)  //only top 10000 records to be selected
    movies10K
  }
}
