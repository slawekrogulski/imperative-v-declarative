package wordcount

import org.scalatest.*
import flatspec.*
import matchers.*
import org.scalatestplus.scalacheck.*
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks.forAll
import wordcount.WordCounter.WordCounts

class WordCounterTest extends AnyFlatSpec with should.Matchers {
  import WordCounterTest._
  import WordCounter._

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
object WordCounterTest {
  import org.scalacheck.Gen
  import WordCounter.{Word, Words}

  val words: Gen[Word] = Gen.alphaUpperChar.map(_.toString)
  val wordLists: Gen[Words] = Gen.listOf[Word](words)

   object JavaWrapper extends WordCounter.WordCounter {
     override def apply(words: Words): WordCounts =
       import scala.jdk.CollectionConverters._
       WordCountJava.count(words.asJava).asScala.collect {
         case (k:String, v:Int) => k -> v
       }.toMap
   }
}
