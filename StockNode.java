package lab5;
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

public class StockNode { //this class represents the each Stock that was involved in a transaction
	public String stockName;
	public int transactions;
	public double stockValue;
	public StockNode next;

	public StockNode(String name,double value, StockNode link,int numTransaction){
		//the node stores the name of the stock
		stockName = name;
		//the node stores the value of the stock
		stockValue = value;
		//the node stores the reference 
		next=link;
		//the node stores the number of transactions that has experienced
		transactions = numTransaction;
	}

}
