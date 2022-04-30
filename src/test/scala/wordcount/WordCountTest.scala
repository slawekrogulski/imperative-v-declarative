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
        WordCount.imperative(wordList) shouldBe WordCount.declarative(wordList)
    }
  }
}
object WordCountTest {
  import org.scalacheck.Gen

  val words: Gen[Word] = Gen.alphaUpperChar.map(_.toString)
  val wordLists: Gen[Words] = Gen.listOf[Word](words)
}
