package com.example.nextword


import android.util.Log

import java.util.*

import kotlin.collections.ArrayList

/**
 * Class Trie implements the Trie data structure for auto complete.
 * Words are stored in a tree like data structure.
 * Each character is represented as a TrieNode which contains a map of other TrieNode to represent subsequent characters
 * EX:
 *                  *
 *                  /
 *                  h
 *                  /\
 *                  e i
 *                  / /\
 *                  l t g
 *                  /    \
 *                  l     h
 *                  /
 *                  0
 */

class Trie {
    private val t = "Trie"

    /**
     * TrieNode represents a character in the Trie
     */
    private class TrieNode{
        var endOfWord: Boolean = false //set to "true" when the end of a word is reached
        var charMap = hashMapOf<Char, TrieNode>() //map of characters to other nodes to continue a word
        var charFreq = hashMapOf<Char, Int>() //map of character frequency to determine which character should come next in autocomplete
        var charList: TreeSet<Char> = TreeSet { a, b -> charFreq[a]!! - charFreq[b]!!}
    }
    private var root = TrieNode()
    //high
    //hit           <--- Example words
    //hisham
    /**
     * insertWord function to populate trie with word
     */
    fun insertWord(word: String){
        var tempNode = root
        for(c in word){
            if(!tempNode.charMap.containsKey(c)){
                tempNode.charMap[c] = TrieNode()
                tempNode.charFreq[c] = 0
            }
            tempNode.charFreq[c] = tempNode.charFreq[c]!! + 1
            if(!tempNode.charList.contains(c))tempNode.charList.add(c)
            //tempNode.charList.sortBy {tempNode.charFreq[it]}
            tempNode = tempNode.charMap[c]!!
        }
        tempNode.endOfWord = true
    }



    /**
     * get rest of word returns top 3 most frequent words based on last character input
     */
    fun getRestOfWord(s: String): List<String>{
        Log.d(t, "Entered getRestOfWord function...")
        Log.d(t, s)
        var startNode = root
        val wordList = ArrayList<String>()
        wordList.add(String())
        wordList.add(String())
        wordList.add(String())
        val charArrays = ArrayList<ArrayList<Char>>(3)
        charArrays.add(ArrayList())
        charArrays.add(ArrayList())
        charArrays.add(ArrayList())
        for(c in s.indices) {
            if (startNode.charMap[s[c]] == null){
                Log.d(t, "loop broken")
                return wordList
            }
            Log.d(t, s[c].toString())
            startNode = startNode.charMap[s[c]]!!
            charArrays[0].add(s[c])
            charArrays[1].add(s[c])
            charArrays[2].add(s[c])

        }
        nextWord(startNode,  1, charArrays[0])
        nextWord(startNode,  2, charArrays[1])
        nextWord(startNode,  3, charArrays[2])
        wordList[0] = list2String(charArrays[0])
        wordList[1] = list2String(charArrays[1])
        wordList[2] = list2String(charArrays[2])

        return wordList
    }

    private fun list2String(arrayList: ArrayList<Char>): String {
        var s = ""
        for(c in arrayList)s += c
        return s
    }
    //hisham
    /**
     * continues the word based on the last character passed in as currentNode
     * charNumber: k-th most frequent character. ex charNumber = 2 means second most frequent character
     * word: the list to populate with the next character
     */
    private fun nextWord(currentNode: TrieNode, charNumber: Int, word: ArrayList<Char>){
        if(currentNode.charList.isEmpty())return
        val iterator = currentNode.charList.descendingIterator()
        var nextChar = iterator.next()
        var i = 1
        while(iterator.hasNext() && i < charNumber){
            nextChar = iterator.next()
            i++
        }
        word.add(nextChar)
        //Log.d(t, nextChar.toString())
        val nextNode = currentNode.charMap[nextChar]
        if (nextNode != null) nextWord(nextNode, 1, word) // recursive call since this a tree-traversal system

    }
}