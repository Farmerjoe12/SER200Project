/**
 * This program will be used to simulate a railroad yard
 * Train cars will be entered by the user in the order that 
 *   they enter the station
 * The user must also enter the desired order that they wish 
 *   for the train cars to exit the station
 *  
 *  @author Adam Farmelo
 */


package unit12Project;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class FarmeloRailroad {
	
	private Stack<String> inputStack;
	private LinkedList<String> outputList;
	
	public FarmeloRailroad() {
		inputStack = getInput();
		outputList = getOutput();
	}
	
	public void run() {
		if (checkOrder(inputStack, outputList)) {
			System.out.println("The input was successfully matched to the output!");
		} else {
			System.out.println("The input could not be matched to the output.");
		}
	}
	
	private Scanner in = new Scanner(System.in);


	/** 
	 * getInput handles the user interaction and will process the incoming train cars
	 * this method will not allow the user to enter less than one car or a blank line/string
	 *   it will stop taking input when the user enters "DONE"
	 * 
	 * @param input = a stack of incoming train cars
	 * @param car = the next string entered by the user
	 * @return
	 */
	public Stack<String> getInput() {
		Stack<String> input = new Stack<String>();
		System.out.println("Enter the order of incoming cars. Type DONE to stop.");
		String car = in.nextLine();
		if (car.equals("DONE") || car.equals("") || car.equals(" ")) {
			while (car.equals("DONE") || car.equals("") || car.equals(" ")) {
				System.out.println("You must enter at least one car.\nPlease enter a car:");
				car = in.nextLine();
			}
		}
		while (!car.equals("DONE")) {
			input.push(car);
			car = in.nextLine();
		}
		return input;
	}
	
	/**
	 * getOutput handles the user interaction and will desired output of trian cars 
	 * This method will not allow the user to enter less values than were entered in getInput
	 * 
	 * @param output = all values entered from user
	 * @param car = the next string entered by the user
	 * @return
	 */
	public LinkedList<String> getOutput() {
		LinkedList<String> output = new LinkedList<String>();
		System.out.println("Enter the desired output order. You must provide as many" 
				+ " outgoing as you did incoming.");
		while (output.size() < inputStack.size()) {
			String car = in.nextLine();
			if (car.equals("DONE") || car.equals("") || car.equals(" ")) {
				while (car.equals("DONE") || car.equals("") || car.equals(" ")) {
					System.out.println("Nice try, you need to add more trains.");
					car = in.nextLine();
				}
			}
			output.add(car);
		}
		return output;
	}
	
	

	/**
	 * checkOrder will check the trains in the incoming stack vs the desired
	 * 	outgoing order. The trains will be added and checked into the waitingLine as needed
	 * No lists will be reordered in this method
	 * 
	 * @param input = return from getInput()
	 * @param output = return from getOutput()
	 * @param origInput = original size of input to control while loop
	 * @param desiredTrain = next train value in the output list
	 * @return
	 */
	
	private boolean checkOrder(Stack<String> input, LinkedList<String> output) {
		Stack<String> waitingLine = new Stack<String>();
		Queue<String> outgoing = new LinkedList<String>();
		int origInput = input.size();
		
		while (outgoing.size() != origInput) {
			String desiredTrain = output.get((output.size()-1) - outgoing.size());
			
			if (input.size() > 0) {
				if (input.peek().equals(desiredTrain)) {
					outgoing.add(input.pop());
				} else {
					if (waitingLine.size() > 0) {
						if (waitingLine.peek().equals(desiredTrain)) {
							outgoing.add(waitingLine.pop());
						} else waitingLine.push(input.pop());
					} else waitingLine.push(input.pop());
				}
			} else if (waitingLine.size() > 0) {
				if (waitingLine.peek().equals(desiredTrain)) {
					outgoing.add(waitingLine.pop());
				} else return false;
			}
		}
		return true;
	}
	
}

