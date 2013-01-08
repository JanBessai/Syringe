/*
 */
package edu.udo.cs.ls14.syringe.term.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Jan
 */
public class VariableSupplyTest extends TestCase {
    
    public VariableSupplyTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of hasNext method, of class VariableSupply.
     */
    public void testHasNext() {
        System.out.println("hasNext");
        VariableSupply supply = new VariableSupply(new HashSet());
        for (int i = 0; i < 100; ++i) {
            assertTrue(supply.hasNext());
            supply.next();
        }
    }

    /**
     * Test of next method, of class VariableSupply.
     */
    public void testNext() {
        System.out.println("next");
        int start = 12;
        VariableSupply supply = new VariableSupply(new HashSet(), start);
        List<String> expected = new ArrayList(100);
        int count = 0;
        for (int j = 0; j <= 10; ++j) {
            for (char i = 'a'; i <= 'z'; ++i) {
                if (count++ < start) {
                    continue;
                }
                String currentName = String.format("%c%d", i, j);
                if (i != 'c' && i != 'd') {
                    expected.add(currentName);
                } else {
                    supply.markUsed(currentName);
                }
            }
        }
        
        for (String name : expected) {
            String next = supply.next();
            assertEquals(name, next);
        }
    }

    /**
     * Test of markUsed method, of class VariableSupply.
     */
    public void testMarkUsed() {
        System.out.println("markUsed");
        VariableSupply supply = new VariableSupply(new HashSet(Collections.singleton("a0")), 0);
        supply.markUsed("a0");
        assertEquals("b0", supply.next());
        supply.markUsed("c0");
        assertEquals("d0", supply.next());
        supply.markUsed("f0");
        supply.markUsed("g0");
        assertEquals("e0", supply.next());
        assertEquals("h0", supply.next());
    }

    /**
     * Test of remove method, of class VariableSupply.
     */
    public void testRemove() {
        System.out.println("remove");
        VariableSupply supply = new VariableSupply(new HashSet(), 0);
        try {
            supply.remove();
            fail("Expected a NotImplementedException for remove");
        } catch (UnsupportedOperationException e) {
            
        }        
    }
}
