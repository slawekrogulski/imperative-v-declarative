package wordcount

object WordCount {
  type Word = String
  type Words = List[Word]
  type Count = Int
  type WordCounts = Map[Word, Count]

  trait WordCount {
    def apply(words: Words): WordCounts
  }
  object imperative extends WordCount {
    def apply(words: Words): WordCounts =
      import scala.collection.mutable
      val wordCounts = mutable.Map.empty[Word, Count]
      val it = words.iterator
      while (it.hasNext)
        val word = it.next()
        val count = wordCounts.getOrElse(word, 0)
        wordCounts.update(word, count + 1)
      wordCounts.toMap
  }
  
  object declarative extends WordCount {
    def apply(words: Words): WordCounts =
      words
        .groupBy(identity)
        .view.mapValues(_.size)
        .toMap
  }
}
