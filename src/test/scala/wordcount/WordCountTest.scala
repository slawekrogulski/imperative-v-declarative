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
    forAll(words) {
      ws =>
        WordCount.imperative(ws) shouldBe WordCount.declarative(ws)
    }
  }
}
object WordCountTest {
  import org.scalacheck.Gen

  val word: Gen[Word] = Gen.alphaUpperChar.map(_.toString)
  val words: Gen[Words] = Gen.listOf[Word](word)
}
