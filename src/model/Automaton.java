package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Automaton {
	
	private String type;
	private ArrayList<State> states;
	private char[] stimuli;
	private char[] outputs;
	public final static String MEALY = "Mealy";
	public final static String MOORE = "Moore";

	private HashMap<State, Integer> index;
	
	public Automaton(String type, ArrayList<State> states, char[] stimuli, char[] outputs) {
		
		this.type = type;
		this.states = states;
		this.stimuli = stimuli;
		this.outputs = outputs;
		index = new HashMap<>();
		initialize_index();
		
	}

	private void initialize_index(){
		for (int i = 0; i < states.size(); i++) {
			index.put(states.get(i), i);
		}
	}

	public String getType(){
		return type;
	}

	public ArrayList<State> getStates(){
		return states;
	}

	public char[] getStimuli(){
		return stimuli;
	}

	public char[] getOutputs(){
		return outputs;
	}

	public ArrayList<State> getConnected(){
		dfs();
		ArrayList<State> connectedStates = new ArrayList<>();
		for (State s : states) {
			if (s.getVisited()) {
				connectedStates.add(s);
			}
		}

		return connectedStates;
	}

	private void dfs(){

		for (State s : states) {
			s.setVisited(false);
		}

		Stack<State> stack = new Stack<State>();
		boolean[] visited = new boolean[states.size()];
		State start = states.get(0);
		stack.push(start);

		while (!stack.isEmpty()){
			State current = stack.pop();
			int ind = index.get(current);
			visited[ind] = true;
			states.get(ind).setVisited(true);

			for (State s : current.getSuccessorStates()) {
				if (!visited[index.get(s)]) {
					stack.push(s);
				}
			}
			
		}
	}
	
	private ArrayList<ArrayList<State>> getFirstPartition(){ 

		ArrayList<State> cState = getConnected();

		for (State state : cState) {
			state.setVisited(false);
		}

		ArrayList<ArrayList<State>> list = new ArrayList<>();
		ArrayList<State> block;
		String c1;
		String c2;
		int count = 0;

		for (int i = 0; i < cState.size()-1; i++) {
			
			if (!cState.get(i).getVisited()) {

				cState.get(i).setVisited(true);
				cState.get(i).setBlock(count);
				cState.get(i).changeBlocks();
				block = new ArrayList<>();
				block.add(cState.get(i));
				

				for (int j = i+1; j < cState.size(); j++) {
					if (!cState.get(j).getVisited()) {
						
						c1 = String.valueOf(cState.get(i).getResponses());
						c2 = String.valueOf(cState.get(j).getResponses());

						if (c1.equals(c2)) {
							cState.get(j).setVisited(true);
							cState.get(j).setBlock(count);
							cState.get(j).changeBlocks();
							block.add(cState.get(j));
						}

					}
				}

				list.add(block);
				count++;
			}


		}

		return list;
	}

	private ArrayList<ArrayList<State>> getFinalPartition(){

		ArrayList<ArrayList<State>> list1 = getFirstPartition();
		ArrayList<ArrayList<State>> list2 = new ArrayList<>();

		ArrayList<State> newBlocks;
		boolean exit1 = true;

		while (exit1) {
			
			int count = 0;

			for (ArrayList<State> arrayList : list1) {
				for (State s : arrayList){
					s.setVisited(false);
					s.changeBlocks();
				}
			}

			for (ArrayList<State> block : list1) {
				for (int i = 0; i < block.size(); i++) {			
					if (block.get(i).getVisited() == false) {
						
						block.get(i).setVisited(true);
						block.get(i).setBlock(count);
						newBlocks = new ArrayList<>();
						newBlocks.add(block.get(i));

						for (int j = i+1; j < block.size(); j++) {
							
							if (block.get(j).getVisited() == false) { 
								
								if (belongSameBlock(block.get(i), block.get(j))) {
									
									block.get(j).setBlock(count);
									block.get(j).setVisited(true);
									newBlocks.add(block.get(j));

								}
							}

						}
		
						list2.add(newBlocks);
						count++;
					}
				}
			}
			
			if (!list1.equals(list2)) {
				
				list1 = new ArrayList<>(list2);
				list2 = new ArrayList<>();

			}else{
				exit1 = false;
			}
		}
		return list2;
	} 

	private boolean belongSameBlock(State s1, State s2){

		boolean ans = true;
		int n1, n2;

		for (int i = 0; i < s1.getSuccessorStates().size() && ans; i++) {
			
			n1 = s1.getSuccessorStates().get(i).getPrevBlock();
			n2 = s2.getSuccessorStates().get(i).getPrevBlock();

			if (n1 != n2) {
				ans = false;
			}
		}

		return ans;

	}

	public ArrayList<State> getReducedAutomaton(){

		ArrayList<ArrayList<State>> list = getFinalPartition();
		ArrayList<State> newAutomaton = new ArrayList<>();	
		State s;
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			String name = "Q" + index;
			index++;
			char[] array = list.get(i).get(0).getResponses();
			s = new State(name, array);
			newAutomaton.add(s);
		}

		for (int i = 0; i < newAutomaton.size(); i++) {
			
			for (int j = 0; j < stimuli.length; j++) {
				
				int n = list.get(i).get(0).getSuccessorStates().get(j).getBlock();
				newAutomaton.get(i).addSuccessor(newAutomaton.get(n));

			}
			
		}

		return newAutomaton;

	}

	
}
