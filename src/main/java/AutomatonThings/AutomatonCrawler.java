package AutomatonThings;

import rationals.Automaton;
import rationals.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by loren on 26/09/2016.
 */
public class AutomatonCrawler {
	Automaton automaton;
	public AutomatonCrawler(Automaton automaton){ this.automaton = automaton;}
	public void start(){
		System.out.println("Menu di scelta:\n");
		System.out.println("\t1: \tShow Terminals");
		System.out.println("\t2: \tShow Initials");
		System.out.println("\t3: \tShow Alphabet");
		System.out.println("\t4: \tShow States");
		System.out.println("\t5: \tShow Transitions");
		System.out.println("\t6: \tShow Transitions from State");
		System.out.println("\t7: \tShow Path between Two States");
		System.out.println("\tEverything else: Exit AutomatonCrawler");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			String read = br.readLine();
			chosePath(read);
		}catch (IOException ex){
			ex.printStackTrace();
		}
	}
	void chosePath(String path){
		int p = getInt(path);
		boolean exit = false;
		switch (p){
			case 1: terminals();break;
			case 2: initials();break;
			case 3: alphabet();break;
			case 4: states();break;
			case 5: transitions();break;
			case 6: pathFrom();break;
			case 7: pathFromTo();break;
			default: exit = true;break;
		}
		if (!exit) start();
		else System.out.println("\n-------GOODBYE--------\n");
	}
	void pathFrom(){
		int result = -1;
		try {
		System.out.print("Starting state: ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String read = reader.readLine();
			result = getInt(read);
		}catch (IOException ex){

		}
		if (result != -1 && result<automaton.states().toArray().length) {
			System.out.println("Transitions from :" + result);
			for (Object t : automaton.delta((State) automaton.states().toArray()[result])) System.out.println(t);
		} else System.out.println("Error");
	}
	void pathFromTo(){
		int result = -1;
		int result2 = -1;
		try {
			System.out.print("Starting state: ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String read = reader.readLine();
			result = getInt(read);
			System.out.print("Ending state: ");
			read = reader.readLine();
			result2 = getInt(read);
		}catch (IOException ex){

		}
		if (result != -1 && result2 != -1 &&
			result<automaton.states().toArray().length &&
			result2<automaton.states().toArray().length) {

			System.out.println("Transition from " + result + " to " + result2);
			for (Object t : automaton.deltaFrom(
					(State) automaton.states().toArray()[result],
					(State) automaton.states().toArray()[result2]
			))
				System.out.println(t);
		}else System.out.println("Error");
	}
	void terminals(){
		System.out.println("TERMINALS: ");
		System.out.println("\t"+automaton.terminals());
	}
	void initials(){
		System.out.println("INITIALS: ");
		System.out.println("\t"+automaton.initials());
	}
	void alphabet(){
		System.out.println("ALPHABET: ");
		for (Object o:automaton.alphabet()) System.out.println("\t"+o);
	}
	void states(){
		System.out.println("STATES: ");
		System.out.println("\t"+automaton.states());
	}
	void transitions(){
		System.out.println("TRANSITIONS: ");
		for (Object o:automaton.delta())System.out.println("\t"+o);
	}
	public int getInt(String path){
		int result = -1;
		try {
			result = Integer.parseInt(path);
		}catch (Exception ex){return -1;}
		return result;
	}
}
