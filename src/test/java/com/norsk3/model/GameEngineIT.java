package com.norsk3.model;

import com.norsk3.entities.Permutation;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Basic testing of the mono-user game engine
 * @author jeffali
 */
public class GameEngineIT {
    public static Log LOG = LogFactory.getLog(GameEngineIT.class);
    
    public GameEngineIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testLoadDemo() {

        Map<String, Permutation> suggestions = new HashMap<String, Permutation>();
        GameEngine instance = new GameEngine();
        instance.loadDemo(suggestions);

        assertEquals(suggestions.size(), instance.getEntries(instance.getCurrentCategory()).size());
        
        instance.setCurrentCategory("drink");
        
        instance.shuffle();

        assertEquals(instance.getEntries(instance.getCurrentCategory()).size(), 7);        
    }
}
