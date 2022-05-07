package wordcount

import org.scalatest.*
import flatspec.*
import matchers.*
import org.scalatestplus.scalacheck.*
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks.forAll
import wordcount.WordCount.WordCounts

class WordCountTest extends AnyFlatSpec with should.Matchers {
  import WordCountTest._
  import WordCount._

  "imperative and declarative word count implementations" should "be equivalent" in {
    forAll(wordLists) {
      wordList =>
        List(
          JavaWrapper,
          imperative,
          declarative_groupBy,
          declarative_foldLeft
          ).map(_(wordList))
          .sliding(2, 1)
          .collect{
            case e1::e2::Nil => e1 -> e2
          }.foreach {
            case (e1, e2) => e1 shouldBe e2
          }
    }
  }
}
object WordCountTest {
  import org.scalacheck.Gen
  import WordCount.{Word, Words}

  val words: Gen[Word] = Gen.alphaUpperChar.map(_.toString)
  val wordLists: Gen[Words] = Gen.listOf[Word](words)

   object JavaWrapper extends WordCount.WordCount {
     override def apply(words: Words): WordCounts =
       import scala.jdk.CollectionConverters._
       WordCountJava.count(words.asJava).asScala.collect {
         case (k:String, v:Int) => k -> v
       }.toMap
   }
}
