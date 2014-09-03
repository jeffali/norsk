package com.norsk3.model;

import com.norsk3.entities.Permutation;
import com.norsk3.entities.Player;
import com.norsk3.entities.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation of the game engine characteristics' interface
 * Refer to the interface for documentation
 *
 * @author jeffali
 */
public class GameEngine implements GameEngineInterface {
    public static Log LOG = LogFactory.getLog(GameEngine.class);

    private HashMap<String,ArrayList<Response>> entries = new HashMap<String,ArrayList<Response>>();
    private List<Permutation> perms = new ArrayList<Permutation>();
        
    private String curcat; //current category
    
    private List<Player> players = new ArrayList<Player>();
    
    private List<Integer>[] shuffled = (ArrayList<Integer>[]) new ArrayList[3];
    private List<ArrayList<Integer>> precalc = new ArrayList<ArrayList<Integer>>();

    @Override
    public List<Response> getEntries(String cat) {
        return entries.get(cat);
    }

    @Override
    public void setEntries(String cat, ArrayList<Response> responses) {
        if (this.entries.containsKey(cat)) {
            this.entries.put(cat, responses);            
        } else {
            this.entries.put(cat, responses);
        }
    }

    @Override
    public List<Permutation> getPerms() {
        return perms;
    }

    @Override
    public void setPerms(List<Permutation> perms) {
        this.perms = perms;
    }

    @Override
    public String getCurrentCategory() {
        return curcat;
    }

    @Override
    public void setCurrentCategory(String category) {
        this.curcat = category;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public void addPlayer(Player p) {
        this.players.add(p);
    }
    
    @Override
    public void addEntry(String cat, Response q) {
        if (entries.containsKey(cat)) {
            entries.get(cat).add(q);            
        } else {
            entries.put(cat, new ArrayList<Response>());
            entries.get(cat).add(q);
        }
    }
    
    @Override
    public void addEntry(Response q) {
        addEntry(curcat, q);
    }
    
    @Override
    public Boolean checkAnswer(int qid, int aid) {
        return (precalc.get(qid).get(0) == aid);
    }
    
    @Override
    public Boolean reportAnswer(int uid, int qid, int answer) {
        if (checkAnswer(qid, answer)) {
            players.get(uid).incrSuccesses();
            return true;
        } else {
            players.get(uid).incrFailures();
            return false;
        }
    }
    
    @Override
    public Integer getRightAnswer(int qid) {
        return precalc.get(qid).get(0);
    }
    
    @Override
    public void shuffle() {
        /* Pass 1 : pure shuffling of array indices */
        System.out.println("--> First pass");
        precalc.clear();
        for (int j = 0; j < 1; j++) {
            this.shuffled[j] = new ArrayList<Integer>();
            this.shuffled[j].clear();
            for (int i=0; i<this.entries.get(curcat).size(); i++) {
                 this.shuffled[j].add(i);
            }
            Collections.shuffle(this.shuffled[j]);
            System.out.println(this.shuffled[j]);
        }
        /* Pass 2 : generate 3 different sub-hashes */
        System.out.println("--> Second pass");
        List<Integer> tmphashes = new ArrayList<Integer>();

        for (int i = 0; i <shuffled[0].size(); i++) {
            tmphashes.clear();
            /* 
            Important : set to "i" to make result immutable 
                        it's good for conducting tests !
            */
            int newhash = shuffled[0].get(i);
            int count = 3;
            while (count > 0 ) {
                int tmphash = Math.abs(((newhash << 6) + (newhash >> 16)) % shuffled[0].size());
                if (tmphash == newhash) {
                    newhash = (Math.abs(--tmphash) % shuffled[0].size());
                } else {
                    newhash = (Math.abs(tmphash) % shuffled[0].size());
                }
                while (newhash == i || tmphashes.contains((Integer)Math.abs(newhash))) {
                    newhash = (Math.abs(newhash) + 7) % (shuffled[0].size());
                }
                tmphashes.add(((Integer)Math.abs(newhash) % shuffled[0].size()));
                count--;
            }
            tmphashes.add(0, (tmphashes.get(0) + 2) %4);
            precalc.add(new ArrayList<Integer>(tmphashes));
        }
        /* Pass 3 : */        
        System.out.println("--> Third pass");
        
        for (int i = 0; i <shuffled[0].size(); i++) {
            System.out.println(precalc.get(i));
        }

        constructPerms();
    }
        
    private void constructPerms() {
        perms.clear();
        List<Integer> tmp = new ArrayList<Integer>();
        for (int i = 0; i < entries.get(curcat).size(); i++) {
            tmp.clear();
            tmp.addAll(precalc.get(i));
            tmp.add(precalc.get(i).get(0) + 1, i);
            tmp.remove(0);
            Permutation p = new Permutation(
                entries.get(curcat).get(i).getId(),
                entries.get(curcat).get(i).getQuestion(), 
                entries.get(curcat).get(tmp.get(0)).getAnswer(),
                entries.get(curcat).get(tmp.get(1)).getAnswer(),
                entries.get(curcat).get(tmp.get(2)).getAnswer(),
                entries.get(curcat).get(tmp.get(3)).getAnswer()
            );
            perms.add(p);
        }
    }
    
    @Override
    public void loadDemo(Map<String,Permutation> suggestions) {
//      device" : [
        setCurrentCategory("device");
        addEntry(new Response(0L,"camera", "fotoapparat"));
        addEntry(new Response(1L,"cellphone", "mobiltelefon"));
        addEntry(new Response(2L,"clock", "ur"));
        addEntry(new Response(3L,"computer", "datamaskin"));
        addEntry(new Response(4L,"elevator", "heis"));
        addEntry(new Response(5L,"engine", "motor"));
        addEntry(new Response(6L,"instrument", "instrument"));
        addEntry(new Response(7L,"machine", "maskin"));
        addEntry(new Response(8L,"oven", "ovn"));
        addEntry(new Response(9L,"watch", "ur"));

//(drink" : [
        setCurrentCategory("drink");
        addEntry(new Response(10L,"juice", "jus"));
        addEntry(new Response(11L,"coffee", "kaffe"));
        addEntry(new Response(12L,"drink", "drikle"));
        addEntry(new Response(13L,"milk", "melk"));
        addEntry(new Response(14L,"milkshake", "milkshake"));
        addEntry(new Response(15L,"tea", "te"));
        addEntry(new Response(16L,"water", "vann"));
        addEntry(new Response(17L,"wine", "vin"));

//family" : [
        setCurrentCategory("family");
        addEntry(new Response(18L,"baby", "spedbarn"));
        addEntry(new Response(19L,"boy", "gutt"));
        addEntry(new Response(20L,"brother", "bror"));
        addEntry(new Response(21L,"daughter", "datter"));
        addEntry(new Response(22L,"family", "serie"));
        addEntry(new Response(23L,"father", "far"));
        addEntry(new Response(24L,"friend", "venn"));
        addEntry(new Response(25L,"girl", "jente"));
        addEntry(new Response(26L,"grandfather", "bestefar"));
        addEntry(new Response(27L,"human", "menneske"));
        addEntry(new Response(28L,"husband", "ektefelle"));
        addEntry(new Response(29L,"man", "mann"));
        addEntry(new Response(30L,"marriage", "ekteskap"));
        addEntry(new Response(31L,"mother", "mor"));
        addEntry(new Response(32L,"person", "individ"));
        addEntry(new Response(33L,"relationship", "forhold"));
        addEntry(new Response(34L,"wedding", "bryllup"));
        addEntry(new Response(35L,"wife", "kone"));
        addEntry(new Response(36L,"woman", "kvinne"));

        shuffle();
        for (Permutation p : getPerms()) {
            suggestions.put(p.getId().toString(), p);
        }
    }
}
