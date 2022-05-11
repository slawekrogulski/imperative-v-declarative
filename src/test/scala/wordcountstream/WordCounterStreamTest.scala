package wordcountstream

import org.scalatest.*
import flatspec.*
import matchers.*
import org.scalatestplus.scalacheck.*
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks.forAll

class WordCounterStreamTest extends AnyFlatSpec with should.Matchers {
  import WordCounterStream._
  import WordCounterStreamTest._

  "Imperative and Declarative" should "be equivalent" in {
    forAll(wordLists) {
      case wl =>
        val (wl1, wl2) = wl.duplicate
        val wc1 = Imperative(wl1)
        val wc2 = Declarative(wl2)
        while (wc1.hasNext && wc2.hasNext) {
          wc1.next() shouldBe wc2.next()
        }
        wc1 shouldBe empty
        wc2 shouldBe empty
    }
  }

}

object WordCounterStreamTest {
  import org.scalacheck.Gen
  import wordcountstream.WordCounterStream._

  val word: Gen[Word] = Gen.alphaUpperChar.map(_.toString)
  val wordLists: Gen[Words] = Gen.listOf[Word](word).map(_.iterator)



}