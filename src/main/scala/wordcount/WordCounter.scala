package wordcount

object WordCounter {
  type Word = String
  type Words = List[Word]
  type Count = Int
  type WordCounts = Map[Word, Count]

  trait WordCounter {
    def apply(words: Words): WordCounts
  }
  object imperative extends WordCounter {
    def apply(words: Words): WordCounts =
      import scala.collection.mutable
      val wordCounts = mutable.Map.empty[Word, Count]
      val it = words.iterator
      while (it.hasNext)
        val word = it.next()
        val count = wordCounts.getOrElse(word, 0) + 1
        wordCounts.update(word, count)
      wordCounts.toMap
  }

  object declarative_groupBy extends WordCounter {
    def apply(words: Words): WordCounts =
      words
        .groupBy(identity) // map
        .view.mapValues(_.size) // reduce
        .toMap
  }

  object declarative_foldLeft extends WordCounter {
    private val empty:WordCounts = Map.empty

    def apply(words: Words): WordCounts =
      words.foldLeft(empty){
        (wcs, w) =>
          wcs + (w -> (wcs.getOrElse(w, 0) + 1))
      }
  }
}
