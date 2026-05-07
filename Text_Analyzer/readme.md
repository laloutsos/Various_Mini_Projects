# Text Analyzer (Under Development)

Text Analyzer is a Java-based project that uses a Trie (prefix tree) data structure to efficiently store, query, and analyze words from a large text file. It supports word frequency tracking, prefix-based searches, pattern matching with wildcards, and more.

## Features

- Insert and store words from a text file using a Trie
- Check if a word exists and how many times it appears
- Find the number of words with a specific prefix
- List all words with a given prefix along with their frequencies
- Search for words that match a pattern using wildcards (e.g. `sc????e`)
- Find the longest prefix of a given word found in the text
- Identify the most frequent word in the text
- Delete words and update the structure accordingly
- User-based logging:
  - Program asks for a username at startup
  - Saves all search logs in a personal log file named `<username>_log.txt`
- Efficient memory usage and fast lookup operations

## Example Use Case

The program reads from a file (e.g. `DickensB.txt`) and performs various operations such as:

```text
- Total word count and memory usage
- Check if "astonished" or "carol" exists
- Search all words with prefix "caro"
- Search words matching pattern "sc????e"
- Find the most frequent word
- Log every search query and operation in a username-specific log file
```

## How It Works

The core of the project is the `StringTrie` class, which:

- Inserts words character-by-character into a tree structure
- Tracks how many times each word appears
- Supports retrieval based on exact match, prefix, and pattern
- Offers frequency statistics for every word in the text
- Supports deletion of words
- Logs user queries and results into a log file specific to each username

## Project Structure

```text
Main.java        // Loads a text file, handles user interaction,
                 // asks for username, and writes to user-specific log files

StringTrie.java  // Core Trie implementation

Item.java        // Simple data class holding word and its count

Node.java        // Represents a single node in the Trie structure

Q.java           // Array-based circular queue implementation
                 // with generic support
```

## Example Usage

```bash
Open the project folder and run:

javac *.java
java Main
```

Follow the on-screen instructions to interact with the Trie and analyze the text data.
