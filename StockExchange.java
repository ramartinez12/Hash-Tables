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
public class StockExchange {
	//the global variables that will be manipulated and then showed to the user, the load factor, hast table doubled, number of elements, the length of the longest chain, empty places, max, min and most transactions
	StockNode[] Table;
	double loadFactor;
	int elements;
	int Chain;
	int Empty;
	int Rehash;
	StockNode maxNode;
	StockNode minNode;
	StockNode mostTrans;

	public StockExchange(){ 
		//constructor of the StockExchange
		Table = new StockNode[5]; 
		//starts with a table of size 5 and everything starts at 0
		loadFactor=0;
		elements = 0;
		Chain = 0;
		Empty = 0;
		Rehash = 0;
	}

	void insert(String name, double transaction){
		//the insert method which inserts the stock into the table if it was transacted
		int key = toInt(name);
		//changes the name to int for key 
		int index = key%Table.length;

		for(StockNode t=Table[index];t!=null;t=t.next){
			//looks if the stock is in the Table and if it is it updates the value of the Stock
			if(sameName(name,t.stockName)){
				t.transactions++;
				t.stockValue+=updateValue(t.stockValue,transaction);
				updateMax(t);
				updateMin(t);
				if(mostTrans.transactions<t.transactions){
					//changes the value of the most transated stock
					mostTrans=t;
				}
				return;
			}
		}
		//if the stock is not in the table it adds it to the table
		Table[index] = new StockNode(name, updateValue(100, transaction), Table[index], 1);
		elements++;
		//adds to the number of the elements
		if(mostTrans==null){
			//if the mostTrans variable is null it declares the first stock to that variable
			mostTrans=Table[index];
		}
		//updates the max, min and the load factor of the table
		updateMax(Table[index]);
		updateMin(Table[index]);
		updateLoad();
	}

	int toInt(String a){
		//method that changes the String into integer for the key
		int name=0;
		for(int i=0;i<a.length();i++){
			name+=(int)a.charAt(i);
		}
		return name;
	}

	double updateValue(double value, double percentage){
		//method which updates the stock value if it is part of the transaction
		return value+=(value*(.01*percentage));
	}

	boolean sameName(String a, String b){
		//method to check if the stock has the same name as another name of a stock in the table
		if(a.length()!=b.length()){

			return false;
		}
		for(int i=0;i<a.length();i++){
			if(a.charAt(i)!=b.charAt(i)){
				return false;
			}
		}
		return true;
	}

	void updateMax(StockNode a){
		//method which changes the variable maxValue if a stock is greater than the value that is stored at the time 
		//if the variable is null then declares a stock to the variable
		if(maxNode==null){
			maxNode=a;
		} else { 
			if(a.stockValue>maxNode.stockValue){
				maxNode=a;
			}
		}
	}

	void updateMin(StockNode a){
		//method which changes the variable minValue if a stock is less than the value that is stored at the time
		//if the variable is null then declares a stock to the variable
		if(minNode==null){
			minNode=a;
		} else {
			if(a.stockValue<minNode.stockValue){
				minNode=a;
			}
		}
	}

	void updateLoad(){
		//method to update the load factor after each addition to the hash table 
		double aloadFactor = elements/Table.length;
		if(aloadFactor==1){
			Rehash();
		}
		loadFactor = ((double)elements)/Table.length;
	}

	void Rehash(){
		//if the load factor equals to 1, then the hash table is then resized and then the nodes added to their proper slot in the array 
		Rehash++;
		//updates if the hash table changed size
		StockNode[] temp=new StockNode[(2*Table.length)+1];
		//creates a temporary table with the new size 
		for(int i=Table.length-1;i>=0;i--){
			//goes through each array linked list 
			StockNode t=Table[i];
			while(t!=null){
				//the change of node to its new proper place is done here 
				int key = toInt(t.stockName);
				int index = key%temp.length;
				if(temp[index]!=null){
					StockNode next=temp[index].next;
					while(next!=null){
						temp[index]=next;
						next=temp[index].next;
					}
					temp[index].next=t;
				} else{
					temp[index]=t;
				}
				StockNode next=t.next;
				t.next=null;
				t=next;
			}
		}
		Table=temp;
	}

	void countChains(){
		//checks what chain of linked list nodes is the greatest one
		int count=0;
		for(int i=0;i<Table.length;i++){
			if(Table[i]==null){
				Empty++;
			}
			for(StockNode t=Table[i];t!=null;t=t.next){
				if(t.next!=null)
					count++;
			}
			if(Chain<count){
				Chain=count;
			}
		}
	}

	void stockRetrieval(){
		//method to check the value of a stock in the list
		boolean repeat = true;
		Scanner r = new Scanner(System.in);
		System.out.println("Enter the name of the stock you want to retrieve, -1 to end price retrievals.");
		//asks the user for the name of the stock
		while(repeat){
			//while loop to keep asking until the user wants to stop looking for a stock
			System.out.print("Stock name:");
			String input = r.nextLine();
			if(input.equals("-1")){
				return;
			}else{
				boolean valid = true;
				//the valid variable is used for keeping track if the users input is valid
				for(int i=0;i<input.length();i++)
					//verifies if the entry was valid by the user
					if(valid)
						if(!Character.isLetter(input.charAt(i))&&!Character.isLowerCase(input.charAt(i))){
							System.out.println("Invalid entry");
							valid = false;
					}
				if(valid){
				StockNode temp = search(input);
				//looks for the entry of the user in the hash table
				if(temp==null){
					System.out.println("The value of stock " + input + " is $" + 100);
				} else{
					System.out.printf("The value of stock " + temp.stockName + " is $%.2f%n",temp.stockValue);
				}
				}
			}
		}
	}

	StockNode search(String Stock){
		//method to look for a certain stock in the hash table and return the stock if found
		int index = toInt(Stock)%Table.length;
		StockNode temp = null;
		for(StockNode t=Table[index];t!=null;t=t.next){
			if(t.stockName.length()==Stock.length()){
				for(int i=0;i<t.stockName.length();i++){
					if(t.stockName.charAt(i)!=Stock.charAt(i)){
						continue;
					}
				}
				return t;
			}
		}
		return null;
	}
	//all of these methods are just to print the information to the user
	void printMax(){
		System.out.printf("\nThe stock with the highest value is " + maxNode.stockName + " with a value of $%.2f%n",maxNode.stockValue);
	}

	void printMin(){
		System.out.printf("The stock with the lowest value is " + minNode.stockName + " with a value of $%.2f%n",minNode.stockValue);
	}

	void printTrans(){
		System.out.println("The stock with the most transactions is " + mostTrans.stockName + " having made " + mostTrans.transactions + " transactions");
	}

	void printInfo(){
		System.out.print("The size of the hashtable is "+ Table.length + ". There are " + elements + " items in the hashtable, \nthe longest list has length ");
	}

	void printChain(){
		System.out.printf(Chain + ", the load factor is %.2f,%nand there are " + Empty + " empty slots in the table. The table size was doubled " + Rehash +" times.\n",loadFactor);
	}

	void printNodes(){
		//method to print every node in the has table
		for(int i=0;i<Table.length;i++){
			for(StockNode t=Table[i];t!=null;t=t.next){
				System.out.println(t.stockName+":"+t.stockValue+" "+t.transactions);	
			}
		}
	}
}
