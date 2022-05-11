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
      case listOfWordLists =>
        val counterObjects = List(Imperative, Declarative, ImperativeJava)
        listOfWordLists.length shouldBe counterObjects.length
        val counters = {
          counterObjects.zip(listOfWordLists)
        }.map {
          case (counter, wordList) => counter(wordList)
        }

        while (counters.forall(_.hasNext)) {
          counters
            .map(_.next())
            .sliding(2)
            .foldLeft(succeed) {
              case (_, wc1::wc2::Nil) => wc1 shouldBe wc2
              case (_, window)        => fail(s"did not match sliding window: $window")
            }
        }
        counters.foreach(_ shouldBe empty)
    }
  }

}

object WordCounterStreamTest {
  import org.scalacheck.Gen
  import wordcountstream.WordCounterStream._

  val word: Gen[Word] = Gen.alphaUpperChar.map(_.toString)
  val wordLists: Gen[List[Words]] = {
    Gen.listOf[Word](word).map {
      wl =>
        List(wl.iterator, wl.iterator, wl.iterator)
    }
  }


  object ImperativeJava extends WordCounterStream {
    override def apply(words: Words): WordCounts =
      import scala.jdk.CollectionConverters._
      WordCounterStreamJava.count(words.asJava).asScala
  }

}