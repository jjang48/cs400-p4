import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
///////////////////////////////// FILE HEADER //////////////////////////////////
//
// Assignment name: DictionaryGraph
//
// Files submitted: Graph.java, GraphTest.java, WordProcessor.java,
// GraphProcessor.java, GraphProcessorTest.java
//
// Course: CS 400 Spring 2018
//
// Authors: Bryan Jin, Joon Jang, Aanjanaye Kajaria
//
// Emails: bjin23@wisc.edu, jjang48@wisc.edu, kajaria@wisc.edu
//
// Lecturer's Name: Deb Deppeler
//
// Outside sources: NONE
//
// Known bugs: NONE
//
// Due date: 4/16/2018
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 * This class contains some utility helper methods
 * 
 * @author sapan (sapan@cs.wisc.edu), Bryan Jin (bjin23@wisc.edu), Joon Jang (jjang48@wisc.edu),
 *         Aanjanaye Kajaria (kajaria@wisc.edu)
 */
public class WordProcessor {

    /**
     * Gets a Stream of words from the filepath.
     * 
     * The Stream should only contain trimmed, non-empty and UPPERCASE words.
     * 
     * @see <a href=
     *      "http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8
     *      stream blog</a>
     * 
     * @param filepath file path to the dictionary file
     * @return Stream<String> stream of words read from the filepath
     * @throws IOException exception resulting from accessing the filepath
     */
    public static Stream<String> getWordStream(String filepath) throws IOException {
        /**
         * @see <a href=
         *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
         * @see <a href=
         *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
         * @see <a href=
         *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
         * @see <a href=
         *      "https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
         * 
         *      class Files has a method lines() which accepts an interface Path object and produces
         *      a Stream<String> object via which one can read all the lines from a file as a
         *      Stream.
         * 
         *      class Paths has a method get() which accepts one or more strings (filepath), joins
         *      them if required and produces a interface Path object
         * 
         *      Combining these two methods: Files.lines(Paths.get(<string filepath>)) produces a
         *      Stream of lines read from the filepath
         * 
         *      Once this Stream of lines is available, you can use the powerful operations
         *      available for Stream objects to combine multiple pre-processing operations of each
         *      line in a single statement.
         * 
         *      Few of these features: 1. map( ) [changes a line to the result of the applied
         *      function. Mathematically, line = operation(line)] - trim all the lines - convert all
         *      the lines to UpperCase - example takes each of the lines one by one and apply the
         *      function toString on them as line.toString() and returns the Stream: streamOfLines =
         *      streamOfLines.map(String::toString)
         * 
         *      2. filter( ) [keeps only lines which satisfy the provided condition] - can be used
         *      to only keep non-empty lines and drop empty lines - example below removes all the
         *      lines from the Stream which do not equal the string "apple" and returns the Stream:
         *      streamOfLines = streamOfLines.filter(x -> x != "apple");
         * 
         *      3. collect( ) [collects all the lines into a java.util.List object] - can be used in
         *      the function which will invoke this method to convert Stream<String> of lines to
         *      List<String> of lines - example below collects all the elements of the Stream into a
         *      List and returns the List: List<String> listOfLines =
         *      streamOfLines.collect(Collectors::toList);
         * 
         *      Note: since map and filter return the updated Stream objects, they can chained
         *      together as: streamOfLines.map(...).filter(a -> ...).map(...) and so on
         */

        Stream<String> wordStream = Files.lines(Paths.get(filepath)); // gets a Stream of lines
        wordStream = wordStream.map(String::trim); // trims each line
        wordStream = wordStream.filter(x -> x != null && !x.equals("")); // filters out empty lines
        wordStream = wordStream.map(String::toUpperCase); // each line is set to UPPERCASE
        return wordStream;
    }

    /**
     * Adjacency between word1 and word2 is defined by: if the difference between word1 and word2 is
     * of 1 char replacement 1 char addition 1 char deletion then word1 and word2 are adjacent else
     * word1 and word2 are not adjacent
     * 
     * Note: if word1 is equal to word2, they are not adjacent
     * 
     * @param word1 first word
     * @param word2 second word
     * @return true if word1 and word2 are adjacent else false
     */
    public static boolean isAdjacent(String word1, String word2) {
        // if lengths of words are equal, check for 1 char replacement
        if (word1.length() == word2.length()) {
            int diff = 0; // number of corresponding character differences between words
            for (int i = 0; i < word1.length(); i++) {
                if (word1.charAt(i) != word2.charAt(i)) {
                    diff++;
                    if (diff > 1) { // more than one char different
                        return false;
                    }
                }
            }
            // if code reaches here, diff == 0 or diff == 1
            // diff == 0 iff words are equal
            return diff == 1;
        }

        // if lengths of words differ by 1, check for whether deleting a char from longer
        // word makes two words equal (deleting is symmetric to inserting)
        if (Math.abs(word1.length() - word2.length()) == 1) {
            // figure out which word is longer:
            String longer;
            String shorter;
            if (word1.length() > word2.length()) {
                longer = word1;
                shorter = word2;
            } else {
                longer = word2;
                shorter = word1;
            }

            // special case: see if deleting last char from longer works
            if (longer.substring(0, longer.length() - 1).equals(shorter)) {
                return true;
            }

            // if code reaches here, there exists an i, 0 <= i <= shorter.length() - 1, such that
            // longer.charAt(i) != shorter.charAt(i)
            // two words are adjacent iff they are equal after removing longer.charAt(i) from longer
            // (I think I have a proof of the above fact?)
            for (int i = 0; i < shorter.length(); i++) {
                if (longer.charAt(i) != shorter.charAt(i)) {
                    return shorter.equals(longer.substring(0, i) + longer.substring(i + 1));
                }
            }
        }

        // final case: if lengths of words differ by more than 1, they cannot be adjacent
        return false;
    }

}
