/*
 *  JFLAP - Formal Languages and Automata Package
 * 
 * 
 *  Susan H. Rodger
 *  Computer Science Department
 *  Duke University
 *  August 27, 2009

 *  Copyright (c) 2002-2009
 *  All rights reserved.

 *  JFLAP is open source software. Please see the LICENSE for terms.
 *
 */





package model.pumping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




/**
 * A <code>PumpingLemma</code> contains the information needed
 * to guide the user through a pumping lemma proof.
 * 
 * @author Jinghui Lim & Chris Morgan
 *
 */
public abstract class PumpingLemma implements Serializable
{
	
	public static final String COMPUTER = "Computer";
	public static final String HUMAN = "Human";
    protected static final String AB_STAR = "{<i>a</i>, <i>b</i>}*";
    protected static final String NOT_EQUAL = "&#8800;";
    protected static final String ELEMENT_OF = "&#8712;";
    protected static final String GREATER_OR_EQ = "&#8805;";
    protected static final String GREATER_THAN = "&#62;";
    protected static final String LESS_THAN = "&#60;";
    protected static final String LESS_OR_EQ = "&#8804;";
    protected boolean partitionIsValid;
    protected String explanation;
    protected String firstPlayer;
    protected int m;
    protected String w;
    protected int i;
    protected ArrayList<Case> myAllCases;
    protected ArrayList<Case> myDoneCases;
    protected ArrayList<String> myAttempts;
    protected int[] myRange;
    protected int[] myDecomposition;
    
    
    public PumpingLemma()
    {
        myDoneCases = new ArrayList<Case>();
        myAllCases = new ArrayList<Case>();
        myAttempts = new ArrayList<String>();
        setRange();
        setDescription();
        reset();
        addCases();
    }
    
    /**
     * Returns a string repeated i times, <i>s<sup>i</sup></i>.
     * 
     * @param s the string to repeat
     * @param i the number of times to repeat it
     * @return the string <i>s<sup>i</sup></i>
     */
    protected static String pumpString(String s, int i)
    {
        StringBuffer sb = new StringBuffer();
        for(int n = i; n > 0; n--)
            sb.append(s);
        return sb.toString();
    }        
    
    /**
     * Returns who is the first player, the human or the computer.
     * 
     * @return the first player
     */
    public String getFirstPlayer() 
    {
    	return firstPlayer;
    }
    
    /**
     * Returns the current <i>m</i> value.
     * 
     * @return the current <i>m</i> value
     */
    public int getM()
    {
        return m;
    }
    
    /**
     * Returns the current <i>w</i> value.
     * 
     * @return the current <i>w</i> value
     */
    public String getW()
    {
        return w;
    }
    
    /**
     * Returns the current decomposition as an int[].
     * 
     * @return the current decomposition
     */
    public int[] getDecomposition() 
    {
    	return myDecomposition;
    }
    
    /**
     * Returns the decomposition as a string with explicit labeling of which variables 
     * in the decomposition are assigned to what substrings.
     */
    public abstract String getDecompositionAsString();
    
    /**
     * Returns the number of times to pump the string, <i>i</i>.
     * 
     * @return the current <i>i</i> value
     */
    public int getI()
    {
        return i;
    }
    
    /**
     * Returns the list of attempts already made.
     * 
     * @return the current list of attempts
     */
    public List<String> getAttempts()
    {
    	return myAttempts;
    }
    
    /**
     * Returns whether a valid partition can be applied to this function, meaning there is a 
     * valid repeatable decomposition.
     * 
     * @return the partition's validity
     */
    public boolean getPartitionValidity()
    {
    	return partitionIsValid; 
    }
    
    /**
     * Returns the explanation of this language.  The explanation has html tags, so the explanation should
     * only be shown in an html text editor.
     * 
     *  @return the explanation
     */
    public String getExplanation()
    {
    	return explanation;
    }
    
    /**
     * Returns a string title of the pumping lemma.
     * 
     * @return the title of the lemma
     */
    public abstract String getName();    
    
    /**
     * Returns a title with HTML tags that will allow for a better
     * representation of the language of the lemma.
     * 
     * @return a title with HTML tags
     */
    public abstract String getHTMLTitle();        
    
    /**
     * Returns the recommended range for<i>m</i>.
     * 
     * @return the recommended range for<i>m</i>
     */
    public int[] getRange()
    {
        return myRange;
    }
    
    /**
     * Sets a recommended range for <i>m</i>.
     *
     */
    protected abstract void setRange();
   
    /**
     * Sets who the first player is.
     */
    public void setFirstPlayer(String s) 
    {
    	firstPlayer = s; 
    }
    
    /**
     * Sets the <code>isInClassification</code> and <code>explanation</code> values.
     */
    protected abstract void setDescription();
    
    /**
     * Sets <i>m</i> to the number given.
     * 
     * @param n the number <i>m</i> will be set to
     */
    public void setM(int n)
    {
        reset();
        m = n;
        chooseW();
    }
    
    /**
     * /**
     * Sets <i>w</i> to the number given.
     * 
     * @param s the string <i>w</i> will be set to
     */
    public void setW(String s){
    	w = s;
    }
    
    /**
     * Sets <i>i</i> and sets the decomposition given.
     * 
     * @param decomposition the decomposition to set for this lemma
     * @param num the number to set <i>i</i> to
     * @return <code>true</code> if this deocmposition is legal, <code>false</code> otherwise
     */
    public abstract boolean setDecomposition(int[] decomposition, int num);
    
    /**
     * Sets the decomposition, with the length of each part of the 
     * decomposition in the corresponding space of the input array, 
     * then chooses an acceptable <i>i</i>.
     * 
     * @see #setDecomposition(int[], int)
     * @param decomposition the array that contains the length of each part of the decomposition
     * @return <code>true</code> if this decomposition is legal, <code>false</code> otherwise
     */
    public abstract boolean setDecomposition(int[] decomposition);
    
    /**
     * Sets the <i>i</i> this instance of the pumping lemma uses.
     * 
     * @param num the value <i>i</i> will be set to
     */
    public void setI(int num)
    {
        i = num;
    }
    
    /**
     * Adds the given attempt to the list of attempts.
     * 
     * @param attempt the attempt to be added.
     */
    public void addAttempt(String attempt)
    {
    	myAttempts.add(attempt);
    }
    
    /**
     * Clears all existing attempts from the list of attempts.
     */
    public void clearAttempts()
    {
    	myAttempts.clear();
    }
    
    /**
     * Clears all information the user has entered and other fields, including
     * <i>m</i>, <i>w</i>, and its decomposition.
     *
     */
    public abstract void reset();          
    
    /**
     * The computer chooses a sutiable value for m.  Called when the computer goes first
     */    
    public void chooseM() 
    {
    	m = LemmaMath.fetchRandInt(myRange[0], myRange[1]);
    }
    
    /**
     * The computer chooses a suitable string <i>w</i>.  Called when the user goes first.
     */
    protected abstract void chooseW();
    
    /**
     * The computer chooses an acceptable decomposition for the string when given an 
     * acceptable 'w' value.  'w' is known to be in the lemma before this method is 
     * called.    Regular lemmas will have two values (x-y, y-z) dividers, while context-free 
     * lemmas will have four.  Called when the computer goes first.
     */
    public abstract void chooseDecomposition();
    
    /**
     * The computer chooses and returns a suitable number of times to pump the string, <i>i</i>.
     * Called when the user goes first. 
     */
    public abstract void chooseI();
    
    /**
     * Returns the pumped string according the the decomposition and choice of
     * <i>i</i>.
     * 
     * @return the pumped string
     */
    public abstract String createPumpedString();

    /**
     * Returns the total number of cases.
     * 
     * @return the total number of cases
     */
    public int numCasesTotal()
    {
        return myAllCases.size();
    }
    
    /**
     * Does all the remaining undone cases.
     */
    public void doAll()
    {
        for(int i = 0; i < myAllCases.size(); i++)
        {
            if(!myDoneCases.contains(myAllCases.get(i)))
                myDoneCases.add(myAllCases.get(i));
        }
    }
    
    /**
     * Clears all cases that the user has done. The user set decompositions
     * of those cases are also cleared.
     */
    public void clearDoneCases()
    {
        myDoneCases.clear();
        for(int i = 0; i < myAllCases.size(); i++)
            ((Case)myAllCases.get(i)).reset();
    }
    
    /**
     * Removes a particular case from the "done" pile.
     * 
     * @param n the index of the case to be removed
     */
    public void clearCase(int n)
    {
        ((Case)myDoneCases.remove(n)).reset();
    }
    
    /**
     * Returns the case at <code>index</code> that was added with 
     * {@link #addCase(int[], int)}.
     * 
     * @param index the index of the decomposition to be retrieved
     * @return the case at <code>index</code>
     */
    public Case getCase(int index)
    {
        return (Case)myDoneCases.get(index);
    }
    
    /**
     * Adds the decomposition to the list that the user has done. It should only be
     * called after {@link #setDecomposition(int[])} to ensure the fields are set
     * up properly. If the case is not a legal decomposition, the it returns 
     * <code>-1</code>. If the case has already been done, it returns the index of 
     * the case without changing the case. If it hasn't been done, it moves the case 
     * to the "done" list and returns its position in the done list.
     * 
     * @see Case#isCase(String, String)
     * @see #replaceCase(int[], int, int)
     * @param decomposition the decomposition we wish to add
     * @param num the value of <i>i</i>
     * @return <code>-1</code> if it is an illegal decomposition, the index of the
     * decomposition if it has already been done, or a number bigger than or equal
     * to the total number of cases, which can be found by calling 
     * {@link #numCasesTotal()}.  
     */
    public abstract int addCase(int[] decomposition, int num);
    
    /**
     * Replaces the decomposition in the list of that the user has done. It should only be
     * called after {@link #setDecomposition(int[])} to ensure the fields are set
     * up properly. If the user has not done the case, it returns <code>false</code>.
     * 
     * @see Case#isCase(String, String)
     * @see #addCase(int[], int)
     * @param decomposition the decomposition we wish to add
     * @param num the value of <i>i</i>
     * @param index the place of the decomposition we wish to have replaced
     * @return <code>true</code> if the decomposition and case match, 
     * <code>false<code> otherwise
     */
    public abstract boolean replaceCase(int[] decomposition, int num, int index);
    
    /**
     * Returns an <code>ArrayList</code> of <code>String</code>s that describe
     * each case.
     * 
     * @return descriptions of each case
     */
    public ArrayList<String> getDoneDescriptions()
    {
        ArrayList<String> ret = new ArrayList<String>();
        for(int i = 0; i < myDoneCases.size(); i++)
            ret.add(myDoneCases.get(i).toString());
        return ret;
    }        
    
    /**
     * Initializes all the possible cases for this pumping lemma.
     *
     */
    protected abstract void addCases();
    
    
    /**
     * Returns the list of done {@link model.pumping.Case}s. 
     * 
     * @return the list of done <code>Case</code>s
     */
    public ArrayList<Case> getDoneCases()
    {
        return myDoneCases;
    }
        
    
    
    @Override
	public PumpingLemma clone() {
		try {
			return this.getClass().newInstance() ;
		} catch (Exception e) {
			e.printStackTrace();
			throw(new RuntimeException("Issue cloning pumping lemma."));
			
		}
	}

	/**
     * Tests if the given string is in the language represented by this <code> 
     * PumpingLemma</code>.
     * 
     * @return <code>true</code> if the pumped string is in the language, 
     * <code>false</code> otherwise
     */
    public abstract boolean isInLang(String s);
}
