package wordcountstream

object WordCounterStream {
  type Count = Int
  type Word = String
  type Words = Iterator[Word]
  case class WordCount(word:Word, count:Count) {
    def tupled: (Word, Count) = word -> count
  }
  type WordCounts = Iterator[WordCount]
  trait WordCounterStream {
    def apply(words: Words): WordCounts
  }

  object Imperative extends WordCounterStream {
    override def apply(words: Words): WordCounts = new Iterator[WordCount] {
      import scala.collection.mutable
      private val wordCounts = mutable.Map.empty[Word, Count]

      override def next(): WordCount =
        val word = words.next()
        val wordCount = WordCount(word, wordCounts.getOrElse(word, 0) + 1)
        wordCounts.update.tupled(wordCount.tupled)
        wordCount

      override def hasNext: Boolean = words.hasNext
    }
  }

  object Declarative extends WordCounterStream {
    private val empty = Map.empty[Word, Count] -> Option.empty[WordCount]

    override def apply(words: Words): WordCounts =
      words.scanLeft(empty) {
        case ((wordCounts, _), word) =>
          val wordCount = WordCount(word, wordCounts.getOrElse(word, 0) + 1)
          (wordCounts + wordCount.tupled, Option(wordCount))
      }.collect {
        case (_, Some(wc)) =>
          wc
      }
  }

}
