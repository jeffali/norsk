package com.norsk3.model;

import com.norsk3.entities.Permutation;
import com.norsk3.entities.Player;
import com.norsk3.entities.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A game engine instances is composed of the following :
 * - a set of (question, answer) couples. Every such couple is linked to a category.
 * - a set of totally random suggestions to be proposed to the player.
 *   These suggestions are linked to the current chosen category.
 * - one or two players (currently only one player is supported).
 *
 * - Operations conducted by a game engine instance can be :
 *   1. Loading the question/answer data 
 *   2. Shuffling of data given a category
 *
 * @author jeffali
 */
public interface GameEngineInterface {
    /* The data model interface */
    void addEntry(String cat, Response q);
    void addEntry(Response q);
    String getCurrentCategory();
    List<Response> getEntries(String cat);
    List<Permutation> getPerms();
    void setCurrentCategory(String category);
    void setEntries(String cat, ArrayList<Response> responses);
    void setPerms(List<Permutation> perms);

    /* Player management (will be useful for multiplayer mode) */
    void addPlayer(Player p);
    List<Player> getPlayers();
    void setPlayers(List<Player> players);
    
    /* Checking answers */
    Boolean checkAnswer(int qid, int aid);
    Integer getRightAnswer(int qid);
    Boolean reportAnswer(int uid, int qid, int answer);
    
    /* Generating suggestions for the end user */
    void loadDemo(Map<String, Permutation> suggestions);
    void shuffle();
}
