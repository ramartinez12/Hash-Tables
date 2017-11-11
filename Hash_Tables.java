package lab5;
import java.util.*;
/*
 * Course: Data Structure CS2302
 * Author: Roman Martinez
 * Objective: to implement Hash Tables using an array to store a linked list in each place in the array and the position is determined by the remainder of the value in the Node and complete 
 * various objectives
 * Instrutor: Olac Fuentes
 * T.A: Saiful Abu
 * Date Last Modified: October 30, 2015
 * Purpose: The purpose of the program is to represent a fictitious stock market with transactions using Hash Tables to store the stocks that were involved in a transaction   
 */
public class Hash_Tables {

	public static void main(String[] args) {
		Random r = new Random(); //generates the random value for the transaction
		String Stock; //the random generated string/name of the stock
		double v; //variable of the rate change of the transaction
		boolean repeat = true; //variable to see if the user wants to repeat the representation
		while(repeat!=false){ //the while loop is to ask the user if it wants to repeat the simulation
			StockExchange a = new StockExchange();	//calls the object StockExchange
			Scanner read = new Scanner(System.in); //Scanner 
			//asks the user for the number of transactions and it wants to display the transactions
			System.out.println("Enter the number of transactions:");
			int n = read.nextInt();
			System.out.println("Initial value of all stocks: 100");
			System.out.println("Do you want to display the transaction? (y/n)");
			char show = read.next().charAt(0);
			//generates the rate of change or percentages for the stock value and the name of the stock that will change
			for(int i=0;i<n;i++) {
				Stock = "";
				for(int k=0; k<=r.nextInt(4);k++)
					Stock=Stock+(char)(65 + r.nextInt(26));
				v=r.nextGaussian()*3.+.1;
				a.insert(Stock,v);
				if(show=='y'){
					System.out.print(Stock);
					System.out.format(" %.2f%n", v);
				}
			}
			//the generates the operations 
			a.printMax();
			a.printMin();
			a.printTrans();
			System.out.println();
			a.countChains();
			a.printInfo();
			a.printChain();
			a.stockRetrieval();
			//asks the user if it wants to run the simulation again
			System.out.println("Do you want to run the simulation again? (y/n)");
			char answer = read.next().charAt(0);
			if(answer!='y')
				repeat = false;
		}
	}

}
