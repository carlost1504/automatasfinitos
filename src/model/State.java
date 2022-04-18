//______________________________________________________PACKAGE___________________________________________________________

/**
 *This package contains all the classes required to an Automaton 
 */

package model;

//______________________________________________________IMPORTS___________________________________________________________

import java.util.ArrayList;

//______________________________________________________THE CLASS__________________________________________________________

/**
* This class defines the basic attributes and methods of a state 
* @author Luis Felipe Sanchez Vega
*/

public class State {
    
//______________________________________________________ATTRIBUTES___________________________________________________________
	
    private String name;
    private boolean visited;
    private int block;
    private int prevBlock;
    private char[] responses;
    private ArrayList<State> successorStates; 
    
//______________________________________________________METHODS___________________________________________________________
    
    /**
	 * The constructor of a state <br><br>
	 * @param name the tag that identifies the state
	 * @param responses every symbol that the state has as an answer  
	 */
    
    public State(String name, char[] responses){
        this.name = name;
        visited = false;
        block = 0;
        prevBlock = 0;
        this.responses = responses;
        this.successorStates = new ArrayList<>();
    }

    /**
	 * This method returns the name of the state <br><br> 
	 * <b>Pre: </b> The state must not be null <br><br>
	 * @return A String which represents the name of the state <br><br>
	 */
    public String getName(){
        return name;
    }
    
    /**
   	 * This method returns a boolean indicating if the state have been visited or not <br><br> 
   	 * <b>Pre: </b> The state must not be null <br><br>
   	 * @return A boolean that tells if the state have been visited or not <br><br>
   	 */
    public boolean getVisited(){
        return visited;
    }
    
    /**
   	 * This method returns an array that contains the responses of the state <br><br> 
   	 * <b>Pre: </b> The state must not be null <br><br>
   	 * @return A array with the responses of the state <br><br>
   	 */
    public char[] getResponses(){
        return responses;
    }
    
    /**
	 * This method modifies the attribute visited <br><br>
	 * <b>Pre: </b> The state must not be null <br><br>
	 * <b>Post: </b> The state has a different visited value <br><br>
	 * @param visited the new visited value <br><br>
	 */
    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public ArrayList<State> getSuccessorStates(){
        return successorStates;
    }

    public void addSuccessor(State s){
        successorStates.add(s);
    }

    public int getBlock(){
        return block;
    }

    public int getPrevBlock(){
        return prevBlock;
    }

    public void changeBlocks(){
        prevBlock = block;
    }
    
    /**
	 * This method modifies the attribute visited <br><br>
	 * <b>Pre: </b> The state must not be null <br><br>
	 * <b>Post: </b> The state has a different visited value <br><br>
	 * @param visited the new visited value <br><br>
	 */
    public void setBlock(int block){
        this.block = block;
    }

}
