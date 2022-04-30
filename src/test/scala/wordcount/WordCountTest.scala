package wordcount

import org.scalatest.*
import flatspec.*
import matchers.*
import org.scalatestplus.scalacheck.*
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks.forAll
import wordcount.WordCount.{Word, Words}

class WordCountTest extends AnyFlatSpec with should.Matchers {
  import WordCountTest._

  "imperative and declarative word count implementations" should "be equivalent" in {
    forAll(wordLists) {
      wordList =>
        val imperative = WordCount.imperative(wordList)
        val declarative_groupBy = WordCount.declarative_groupBy(wordList)
        val declarative_foldLeft = WordCount.declarative_foldLeft(wordList)

        imperative shouldBe declarative_groupBy
        imperative shouldBe declarative_foldLeft
    }
  }
}
object WordCountTest {
  import org.scalacheck.Gen

  val words: Gen[Word] = Gen.alphaUpperChar.map(_.toString)
  val wordLists: Gen[Words] = Gen.listOf[Word](words)
}
