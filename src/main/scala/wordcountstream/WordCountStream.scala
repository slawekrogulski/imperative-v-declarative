package wordcountstream

object WordCountStream {
  type Count = Int
  type Word = String
  type Words = Iterator[Word]
  type WordCount = (Word, Count)
  type WordCounts = Iterator[WordCount]
  trait WordCountStream {
    def apply(words: Words): WordCounts
  }

  object Imperative extends WordCountStream {
    override def apply(words: Words): WordCounts = new Iterator[WordCount] {
      import scala.collection.mutable
      private val wordCounts = mutable.Map.empty[Word, Count]

      override def next(): WordCount =
        val word = words.next()
        val count = wordCounts.getOrElse(word, 0) + 1
        wordCounts.update(word, count)
        word -> count

      override def hasNext: Boolean = words.hasNext
    }
  }

  object Declarative extends WordCountStream {
    private val empty = Map.empty[Word, Count] -> Option.empty[WordCount]

    override def apply(words: Words): WordCounts =
      words.scanLeft(empty) {
        case ((wordCounts, _), word) =>
          val wordCount = word -> (wordCounts.getOrElse(word, 0) + 1)
          (wordCounts + wordCount, Option(wordCount))
      }.collect {
        case (_, Some(wc)) =>
          wc
      }
  }

}
