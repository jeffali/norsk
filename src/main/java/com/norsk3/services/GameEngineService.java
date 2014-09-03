package com.norsk3.services;

import com.norsk3.entities.Permutation;
import com.norsk3.entities.Player;
import com.norsk3.model.GameEngine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The "LaereNorsk" game service
 * This is the main entry and defines a REST service that interfaces with any kind of graphical interfaces
 *  whether it is a web browser, a smartphone or another service.
 *
 * This micro-service supports now only one player but it is aimed to support simultaneous multi-player functions.
 *
 * Another micro-service will support analysis of the answers given and to pinpoint the areas/categories where
 *  progress should be made.
 *
 * @author jeffali
 */

@Controller
@RequestMapping("/0") // a version # for this edition of services
public class GameEngineService
{
    public static Log LOG = LogFactory.getLog(GameEngineService.class);

    // todo: demo only, don't use this Collection
    // For the demo we'll hard code a few questions/answers. However, for a real application this will
    // come from a db, most likely.
    private static Map<String, Permutation> suggestions = new HashMap<String, Permutation>();
    private static GameEngine engine = new GameEngine();
    static
    {
        engine = new GameEngine();
        engine.loadDemo(suggestions);
    }

    /**
     * return a list of questions and possible answers to pick from
     */
    @RequestMapping("/permutation/list")
    public Collection<Permutation> listPerms( HttpServletRequest request,
            HttpServletResponse response)
    {
        /* Detection of cookies as a mean to identify players
           Note that only one player is supported for the time being.
           Also, no authentication/security is needed since this is supposed to
            be a game :-)
        */
        Cookie[] cookies = request.getCookies();
        boolean founduser = false;
        if (cookies != null) {
        for (Cookie c : cookies) {
            if (c.getName().equals("uid")) {
                System.out.println("Detected cookie : " + c.getValue());
                founduser = true;
            }
        }
        }
        if (!founduser) {
            Cookie user = new Cookie("uid", "AnonymousBastard");
            user.setMaxAge(24*60*60);
            response.addCookie(user);
            engine.addPlayer(new Player("AnonymousBastard"));
        }
        /* The actual stuff to return */
        return engine.getPerms();
    }
    
    /* declares a new player/user */
    @RequestMapping("/permutation/join")
    public List<String> declareUser( HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam("uid") String uid)
    {
        engine.addPlayer(new Player(uid));
        return (ArrayList<String>) new ArrayList<String>(); //return uid (might be different than requested
    }
    
    /* switches categories and thus generated a new random set of questions/possible answers */
    @RequestMapping("/permutation/switch")
    public List<String> switchCategoty( HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam("cat") String cat)            
    {
        engine.setCurrentCategory(cat);
        engine.shuffle();
        return (ArrayList<String>) new ArrayList<String>(); //return uid
    }
    
    /* submit an answer to a specific question. The system will then decide whether it is the right
       answer or a wrong one and returns back the current failure/success counters

     * Note that only one player is supported currently !
     */
    @RequestMapping("/permutation/report")
    public List<String> reportAnswer( HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam("uid") String uid,
                                     @RequestParam("qid") int qid, @RequestParam("aid") int aid )
    {        
        engine.reportAnswer(0/*uid*/, qid, aid);
        List<String> l = new ArrayList<String>();
        l.add(engine.checkAnswer(qid, aid).toString()); //status (true or false)
        l.add(engine.getRightAnswer(qid).toString());   //the right answer (aid)
        l.add(((Integer)engine.getPlayers().get(0/*uid*/).getFailures()).toString());   //failures so far
        l.add(((Integer)engine.getPlayers().get(0/*uid*/).getSuccesses()).toString());  //right answers so fair
        return l;
    }
}
