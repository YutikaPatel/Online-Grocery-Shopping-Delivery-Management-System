
/*                           
TOPIC                  : Online Grocery Shopping & Delivery Management System
Data Structures Used   : AVL Tree
                         Linked Hashmap
                         File Handling
                         ArrayList
*/
                                                                          // package
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;                                                            //importing  collections framework
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Product{
	String name;
	String type;                                                                //types 1,2,3 for different categories
	int quantity;
	int price;
	Product(String name,int quantity,int price){               //parameterized constructor
		this.name=name;
		this.quantity=quantity;
		this.price=price;
	}
	

}
class Product_order{
	String name;
	int quantity;
	int price;
	int bill;
	Product_order(String name,int quantity,int price){
		this.name=name;
		this.quantity=quantity;
		this.price=price;
		this.bill=quantity*price;
	}
	Product_order(){
		
	}
}
class Shop {                                                                    //shop class
                                                                                       //stores all existing products added by admin 
	Customer root;
	String password="admin_abc";                                //password for admin to login
	LinkedHashMap<String,Product> items1 =new LinkedHashMap<String,Product>();
	LinkedHashMap<String,Product> items2 =new LinkedHashMap<String,Product>();
	LinkedHashMap<String,Product> items3 =new LinkedHashMap<String,Product>();
       //3 hashmaps to store 3 different types of items
	String[][] name_pro={{"Toothpaste" , "Soap", "Detergent"},{"Lays" , "Chocolates" , "Cold_Drink"},{"Rice", "Wheat", "Dal"}};
	int[][] price={{34,59,54},{23,43,45},{12,45,32}};                 //initializing prices
	int[][] quantity={{10,6,2},{8,7,6},{7,12,15}};                        //available quantities
	LinkedHashMap<String,Customer> cus_data= new LinkedHashMap<String,Customer>();
	Scanner sc= new Scanner(System.in);
	Scanner scl= new Scanner(System.in);
	ArrayList<Integer> to_complete = new ArrayList<Integer>();
	int orders;
	
	Shop(){
		root=null;
		for(int i=0;i<3;i++) {
			   Product p1=new Product(name_pro[0][i],quantity[0][i],price[0][i]);
			   items1.put(name_pro[0][i],p1);
		   }
		   for(int i=0;i<3;i++) {
			   Product p1=new Product(name_pro[1][i],quantity[1][i],price[1][i]);
			   items2.put(name_pro[1][i],p1);
		   }
		   for(int i=0;i<3;i++) {
			   Product p1=new Product(name_pro[2][i],quantity[2][i],price[2][i]);
			   items3.put(name_pro[2][i],p1);
		   }
		orders=0;
	}
	
	
public Customer register() {                               //method to register if user isn’t already registered
		
	Scanner p=new Scanner(System.in);
		String name,password,username;
		Customer record;
		int sector,house;
		while(true) {
			System.out.println("Choose any user name");
			username=scl.nextLine();
			if(cus_data.get(username)==null)                     //if username is taken
				break;
			else
			  System.out.println("Username already exist choose a different one please");
		}
		System.out.println("Enter the name:");
		name=scl.nextLine();
		System.out.println("Enter the sector number:");
		sector=sc.nextInt();
		System.out.println("Enter the house number:");
		house=sc.nextInt();
		System.out.println("Make your password:");
		password=p.next();
		record=new Customer(name,username,sector,house,password);
		//System.out.println("Before putting");
		cus_data.put(username,record);
		//System.out.println("After putting");
		System.out.println("Account created successfully");
		return record;
		
	}

//}
	public Customer login() {                                 //method for already registered customer to login 
		
		Customer record=null;
		String name,password,username;
		System.out.println("Enter your user name");
		username=scl.nextLine();
		System.out.println("Enter your password");
		password=scl.nextLine();
		record=cus_data.get(username);
		if(record==null) {
			System.out.println("Wrong username");
			return record;
		}
		if((record.password).equals(password)==true) {
			System.out.println("Logged in successfully");
		}else {
			System.out.println("Invalid password");                           //wrong login credentials
			record=null;
		}
		return record;
	}
	
	public void decrement(int q,int type,String name) {                  //decrementing original quantity when some order is placed by user
		
		switch(type) {		
		case 1:
			items1.get(name).quantity-=q;
			break;
		case 2:
			items2.get(name).quantity-=q;
			break;
		case 3:
			items3.get(name).quantity-=q;
			break;
		}
		
	}
	void display_UserDatabase() {                           //to display records of all customers registered with store
		String stat,id_order;                               //status to check if order is pending or completed
		System.out.printf("\n%10s %10s %10s %10s %10s %10s\n","Username","Name","Sector No","House No","Order status","OrderId");
		System.out.println("-----------------------------------------------------------------------------------");
		Set<Map.Entry<String,Customer>> s = cus_data.entrySet();
		Iterator<Map.Entry<String,Customer>> itr =s.iterator();
		while (itr.hasNext()) {                        //displaying all customers
			
			Map.Entry<String,Customer> entry = (Map.Entry<String,Customer>) itr.next();
			String name = entry.getKey();
			Customer cus= entry.getValue();	
			if(cus.status==false) 
				stat="Pending";
			else 
				stat="Completed";
			
			if(cus.order_id==0) 
				id_order="No orders";
			else 
				id_order=""+cus.order_id;
			
			System.out.printf("%10s %10s %10s %10s %10s %10s\n",cus.username,cus.name,cus.sector,cus.house,stat,cus.order_id);
		}
		
	}
	void display_partiUser(String username) {       //displaying details of a specific customer
		String stat,id_order;
		if(root.status==false) 
			stat="Pending";
		else 
			stat="Completed";
		
		if(root.order_id==0) 
			id_order="No orders";
		else 
			id_order=""+root.order_id;
		Customer cus=cus_data.get(username);
		System.out.printf("\n%10s %10s %10s %10s %10s %10s\n","Username","Name","Sector No","House No","Order status","OrderId");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.printf("%10s %10s %10s %10s %10s %10s\n",cus.username,cus.name,cus.sector,cus.house,stat,cus.order_id);
		
	}
	
	void Display_Customer_header(Customer root) {           //to display all pending orders
		
		System.out.printf("\n%10s %10s %10s %10s %10s %10s\n","Username","Name","Sector No","House No","Order status","OrderId");
		System.out.println("-----------------------------------------------------------------------------------");
		Display_Customer(root);
	}
	
	 void Display_Customer(Customer root) {
			String status,id_order;
		    
			if(root.left!=null)					//inOrder recursive method
				Display_Customer(root.left);
	        
			if(root.status==false) 
				status="Pending";
			else 
				status="Completed";
			
			if(root.order_id==0) 
				id_order="No orders";
			else 
				id_order=""+root.order_id;
			
			System.out.printf("%10s %10s %10s %10s %10s %10s\n",root.username,root.name,root.sector,root.house,status,id_order);
	        //System.out.println(root.id + "\t" + root.name +" \t"+root.address + " \t" + root.cart.get(i).name +"  \t"+ root.cart.get(i).quantity+ "   \t\t"+root.cart.get(i).price+"  \t"+root.cart.get(i).bill);
			//System.out.println("\nCart");
			//root.display_cart();
			System.out.println("-------------------------------------------------------------------------");
	        if(root.right!=null){
	        	Display_Customer(root.right);
	        }
	          return;
	    }
	
	 public Product_order search(String name,int type,int quantity) {//to search for a product
			Product_order user_p=new Product_order();
			Product shop_p;
			
			switch(type) {
			case 1:                                  //product is of type 1
				shop_p=items1.get(name);
				
				if(quantity > shop_p.quantity)
				{  
					user_p = new Product_order(shop_p.name,shop_p.quantity,shop_p.price);
				 
				}
				else {
				user_p=new Product_order(shop_p.name,quantity,shop_p.price);
				}
				break;
			case 2:                           //product is of type 2
				
				shop_p=items2.get(name);
				
				if(quantity > shop_p.quantity)
				{   user_p = new Product_order(shop_p.name,shop_p.quantity,shop_p.price);
				 
				}
				else {
				user_p=new Product_order(shop_p.name,quantity,shop_p.price);
				
				}
				break;
			case 3:                             //product is of type 3				
				shop_p=items3.get(name);
				
				if(quantity > shop_p.quantity)
				{   user_p = new Product_order(shop_p.name,shop_p.quantity,shop_p.price);
				 
				}
				else {
				user_p=new Product_order(shop_p.name,quantity,shop_p.price);
				
				}
				break;
				}
		
			return user_p;
		}

	
	
	public void add_pro() {			//add new product in product list
		String name;
		int quantity,price,type;
		Scanner scanl= new Scanner(System.in);
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter Product details: ");
		System.out.print("Name of the product: ");
		name=scl.nextLine();
		System.out.print("Price of the product: ");	//accept product details
		price=sc.nextInt();
		System.out.print("Quantity of the product: ");
		quantity=sc.nextInt();
		System.out.print("Type of the product: ");
		type=sc.nextInt();
		Product item=new Product(name,quantity,price);
		switch(type)	//put product in linked hashmap acc. To its type
		{
		case 1: 
			items1.put(name,item);	
			break;
		 case 2: 
			 items2.put(name,item);
			 break;
		 case 3: 
			 items3.put(name,item);
			 break;
		 default:
			 System.out.println("Enter a valid input");
		 }
	}
	public void remove_pro() {			//remove product from list of products
		String name;
		int quantity,price,type;
		Scanner scanl= new Scanner(System.in);
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter Product details: ");
		System.out.print("Name of the product: ");
		name=scl.nextLine();				//accept details of product to be deleted
		System.out.print("Enter type of product: ");
		type=sc.nextInt();
		switch(type)
		{
		case 1: 
			items1.remove(name);
			break;
		 case 2: 
			 items2.remove(name);	 //remove product from particular linked hashmap acc. To its type
			 break;
		 case 3: 
			 items3.remove(name);
			 break;
		 default:
			 System.out.println("Enter a valid input");
		 }
	}
		
	public void display() {			//display list of products
		Set<Map.Entry<String,Product>> s = items1.entrySet();
		Iterator<Map.Entry<String,Product>> itr =s.iterator();
		System.out.printf("*********** Daily Needs ***************\n\n");
		System.out.printf("%10s %10s %10s \n","Product","Price","Quantity");//display item1 list

		while (itr.hasNext()) { 

			Map.Entry<String,Product> entry = (Map.Entry<String,Product>) itr.next();
			String name = entry.getKey();
			Product product= entry.getValue();	
			System.out.printf("%10s %10s %10s\n",name,product.price,product.quantity);
		}
		s=items2.entrySet();
		itr =s.iterator();						//display item2 list
		System.out.printf("\n*********** Packed Food ***************\n\n");
		System.out.printf("%10s %10s %10s \n","Product","Price","Quantity");
		while (itr.hasNext()) { 

			Map.Entry<String,Product> entry = (Map.Entry<String,Product>) itr.next();
			String name = entry.getKey();
			Product product= entry.getValue();
			System.out.printf("%10s %10s %10s\n",name,product.price,product.quantity);
		}
		s=items3.entrySet();
		itr =s.iterator();
		System.out.printf("\n*********** Food Grains **************\n\n");
		System.out.printf("%10s %10s %10s\n","Product","Price", "Quantity");
		while (itr.hasNext()) { 

			Map.Entry<String,Product> entry = (Map.Entry<String,Product>) itr.next();
			String name = entry.getKey();
			Product product= entry.getValue();
			System.out.printf("%10s %10s %10s\n",name,product.price,product.quantity);		//display item3 list
		}
	}
	
	
	public void add_orders(Customer temp) //Accept general information from customer
	{
		//System.out.println("In add orders ");
		this.orders++;
		temp.order_id=this.orders;
		this.root=insert(this.root,temp);
		//System.out.println("After add orders ");
	}
	
	
	public Customer insert(Customer root,Customer temp)	//insert customer node in AVL tree
	{
		if (root==null) //Initial node 1 .1.1
			return temp;

		if (temp.address<root.address) {	//if new customer is less than root address insert in left it in left of root 
			root.left=insert(root.left,temp);
			int bal=balance_addressct(root);
			if(bal==2||bal==-2) {				//if not balanced
				if(temp.address<root.left.address) {
					root=LL(root) ;			//LL rotation
				}else {
					root=LR(root);			//LR rotation
				}
			}
		}
		else
		{
			root.right=insert(root.right,temp);
			int bal = balance_addressct(root);
			if(bal==2||bal==-2)
			{
				if(temp.address>root.right.address)
				{
					root=RR(root); 		//RR rotation
				}
				else
				{
					root=RL(root);			//RL rotation
				}
			}
		}
		root.h = height(root);
		return(root);

	}

	int balance_addressct(Customer root)	//calculate balance factor
	{
		int bf,lh,rh;
		if(root==null)
		{
			return 0;
		}
		if(root.left==null)		//no left child
		{
			lh=0;
		}
		else
		{
			lh = 1+height(root.left);
		}
		if(root.right==null)	     	//no right child
		{
			rh=0;
		}
		else
		{
			rh = 1+height(root.right);
		}
		bf=lh-rh;
		
		return bf;
	}
	int height(Customer root)		//calculate height of AVL tree
	{
		int lh,rh;
		if(root==null)
		{
			return 0;
		}
		if(root.left==null)	//no left child

		{
			lh=0;
		}
		else
		{
			lh = 1+height(root.left);
		}
		if(root.right==null)     	//no right child

		{
			rh=0;
		}
		else
		{
			rh = 1+height(root.right);
		}
		if(lh>rh)
		{
			return lh;
		}
		else
		{
			return rh;
		}
	}
	
	public Customer RR(Customer root)		//RR rotation
	{

		Customer temp=root.right;
		root.right=temp.left;
		temp.left=root;
		temp.h=height(temp);
		root.h=height(root);
		return temp;
	}
	public Customer RL(Customer root)		//RL rotation
	{
		root.right=LL(root.right);
		root=RR(root);
		return root;
	}
	public Customer LL(Customer root)		//LL rotation
	{
		Customer temp=root.left; 
		root.left=temp.right;
		temp.right=root;
		temp.h=height(temp);
		root.h=height(root);

		return temp;
	}

	public Customer LR(Customer root)		//LR rotation
	{
		root.left=RR(root.left);
		root=LL(root);

		return root;
	}
public void inOrder(Customer root) {
		
		if(root.left!=null){					//inOrder recursive method
            inOrder(root.left);
        }
        System.out.print(root.address+ " ");
        
        if(root.right!=null){
            inOrder(root.right);
        }
          return;
    }
void sector_houses(Customer root,int sect,int c)		//get all houses of particular sector
{
	
		if(root.left!=null  )     	//no left child
		{		 
			  sector_houses( root.left,sect,c );
		}
		
		if(root.address/1000 == sect) {
			to_complete.add(root.address);		//add to to_complete
			if(c==1) {
				root.status=true;			//change status of order
			}
		}
		if(root.right!=null   )     	//no right child
		{	
			sector_houses( root.right,sect,c );
		}
			
		
	}


void completeOrdersOfSector(Customer root,int sect)			 //this is tree root
{
	Customer cus;
	
	to_complete.clear();			                                                        //clear previous sector data
	
	cus=searchSec(root,sect);
	sector_houses(cus, sect,1);		                                              //call sector_houses
	
	for(int i=0;i<to_complete.size();i++) {		                            //loop till all houses of sector sect
		
		deleteNode(this.root,to_complete.get(i).intValue());		//delete that customer i.e placed his order
	}
	
}

public Customer searchSec(Customer root,int sec) {              //search particular region
	
    if (this.root == null)
    {
        return root;
    }
   
    	if (root.address/1000 ==sec )		//if sector of Customer node root equals to sec
    	{
    	
        return root;				//return root
        }
        else if (root.address/1000  < sec)	//if sector of Customer node-root less than sec
        {
            return searchSec(root.right,sec);	//go to right subtree
        }
        else  {
        	return searchSec(root.left,sec);	//go to left subtree
        }
      
}
public Customer search_exact(Customer root,int address) {		//search particular customer in AVL tree

	if (this.root == null)
	{
		return root;
	}

	if (root.address==address )
	{
		return root;
	}
	else if (root.address  < address)
	{
		return search_exact(root.right,address);		//go to right subtree
	}
	else  {
		return search_exact(root.left,address);		//go to left subtree
	}

}
void completeSpecificOrder(Customer root,int address) {		//complete a specific order method	
	
	Customer cus=search_exact(root,address);				//search the exact customer on the basics on address provided 
															//in the pending orders avl tree
	cus.status=true;										//update the order status to true
	deleteNode(this.root,address);							//delete the customer from avl tree
	
}
public Customer deleteNode(Customer p,int data){			//complete the order and delete the customer order from pending orders avl tree

    if(p.left == null && p.right == null){					//if the left and right child is null return null
            if(p==this.root) {								//case in which the order matches the root 
            	
                this.root = null;							//set the root to null
            }
        
        return null;										
    }

    Customer t;
    Customer q;
    if(p.address < data){									//if the address to be searched is right side
        p.right = deleteNode(p.right,data);
    }
    else if(p.address > data){								//if the address to be searched is right side
        p.left = deleteNode(p.left,data);
    }
    else{													//found the node to deleted
        if(p.left != null){									//if the node's left child exist
            q = inpre(p.left);								//determine the inorder predecessor of node
                 
            exchange(p,q);									//exchange its data with node
            p.left=deleteNode(p.left,q.address);			//recursive call and store it in p.left
            
        }
        else{												//if the node's left child exist
            q = insuc(p.right);								//determine the inorder successor of node
            
            exchange(p,q);									//exchange its data with node
            p.right = deleteNode(p.right,q.address);		//recursive call and store it in p.right
        }
    }	
    														//balance the tree
    if(balance_addressct(p)==2 &&balance_addressct(p.left)==1){ 	//LL rotation case		
    	if(p==this.root) {										//if root is involved
    		this.root=p.left;									//set root to p left child
    	}
    	p = LL(p); 
    }                  
    else if(balance_addressct(p)==2 && balance_addressct(p.left)==-1){ //LR rotation case	
    	if(p==this.root) {											//if root is involved
    		this.root=inpre(p.left);								//set root to inorder predecessor of p
    	}
    	p = LR(p); 
    }
    else if(balance_addressct(p)==2 && balance_addressct(p.left)==0){   //LR rotation case
    	if(p==this.root) {												//if root is involved
    		this.root=inpre(p.left);									//set root to inorder predecessor of p
    	}
    	p = LR(p); 
    }
    else if(balance_addressct(p)==-2 && balance_addressct(p.right)==-1){//RR rotation case
    	if(p==this.root) {
    		this.root=p.right;
    	}
    	p = RR(p);
    	}
    else if(balance_addressct(p)==-2 &&balance_addressct(p.right)==1){ //RL rotation case
    	if(p==this.root) {
    		this.root=insuc(p.right);
    	}
    	p = RL(p);
    		
    	}
    else if(balance_addressct(p)==-2 && balance_addressct(p.right)==0){    //RL rotation case
    	if(p==this.root) {
    		this.root=insuc(p.right);
    	}
    	p = RL(p);
    }

    return p;
}

public void exchange(Customer p, Customer q) {				//exchange method to exchange the data of p and q customer
	
	cus_data.remove(p.username);
	cus_data.remove(q.username);
	
	Customer temp=new Customer();

	temp.name=p.name;temp.sector=p.sector;temp.house=p.house;temp.password=p.password;temp.cart=p.cart;
	temp.address=p.address;temp.bill=p.bill;temp.status=p.status;temp.username=p.username;temp.order_id=p.order_id;
	
	p.name=q.name;p.sector=q.sector;p.house=q.house;p.password=q.password;p.cart=q.cart;p.bill=q.bill;
	p.address=q.address;p.bill=q.bill;p.status=q.status;p.username=q.username;p.order_id=q.order_id;
	
	cus_data.put(p.username, p);
	cus_data.put(temp.username, temp);
	
}

Customer inpre(Customer p){									//method to find inorder predecessor of passed node
	while(p.right!=null)
		p = p.right;
	return p;    
}

Customer insuc( Customer p){								//method to find inorder successor of passed node
	while(p.left!=null)
		p = p.left;

	return p;    
}

}
class Customer implements java.io.Serializable  {				//customer class storing customer details
	String name;
	int sector,house;
	String password,username;
	int h;
	int address;
	ArrayList<Product_order> cart = new ArrayList<Product_order>();
	int bill;
	boolean status;
	int order_id;
	Customer right,left;
	Customer(String name,String username,int sector,int house,String password){			//customer class parametrized constructor
		right=left=null;
		this.h=0;
		this.name=name;
		this.username=username;
		this.sector=sector;
		this.house=house;
		this.password=password;
		this.address=addressMaker();
		this.status=false;
		this.order_id=0;
	}
	Customer(){															//customer class default constructor
		
	}

	public void display_details() {										//method to display customer details
		String s_status,id_order;
		System.out.println("*****************************************************************************************");
		System.out.printf("\n%10s %10s %10s %10s %10s %10s\n","Username","Name","Sector No","House No","Order status","Order Id");
		System.out.println("---------------------------------------------------------------------------------");
		if(status==false) {													//set status as per its value
			s_status="Pending";
		}else {
			s_status="Completed";
		}
		if(order_id==0) 													//set order id as per its value
			id_order="No orders";					
		else 
			id_order=""+order_id;
		System.out.printf("%10s %10s %10s %10s %10s %10s\n",username,name,sector,house,s_status,id_order);
		System.out.printf("\n%10s\n","Items in cart: ");
		display_cart();													//call to display cart
		
	}
	public void display_cart()											// display cart method
	{   
		if(cart.size()!=0) {											//if cart size is not zero display the cart
		System.out.printf("\n%10s %10s %10s %10s \n","Product","Price","Quantity","Bill");
		
	    System.out.println("---------------------------------------------------------------------------------");
		for(int i=0;i<cart.size();i++)
		{
			System.out.printf("%10s %10s %10s %10s\n",cart.get(i).name,cart.get(i).price,cart.get(i).quantity,cart.get(i).bill);
			
		}
		System.out.println("\nTotal amount: "+ bill);
		}else {
			System.out.println("Cart is empty");						//else display cart is empty
		}
		System.out.println("*****************************************************************************************");
	}
	void cancel_specific(String name,int type,Shop my_shop)			//remove an item from the cart before the order is placed
	{   
		Scanner s1=new Scanner(System.in);
		if(cart.size()==0)									//cart empty case			
		{
			System.out.println("Cart is empty");
		}
		else								
		{
		if(this.order_id!=0) {								//order already placed case
			System.out.println("Cannot remove as order was placed and is out for processsing");
			return;
		    }
		int flag=0;
		for(int i=0;i<cart.size();i++) {					//finding the product in the cart
			if(cart.get(i).name.equals(name)) {				
				this.bill-=cart.get(i).bill;				//updating the bill
				Product p=null;
				switch(type) {								//finding shop product and increasing its quantity
				case 1:
					p=my_shop.items1.get(name);
					p.quantity+=cart.get(i).quantity;
					break;
				case 2:
					p=my_shop.items2.get(name);
					p.quantity+=cart.get(i).quantity;
					break;
				case 3:
					p=my_shop.items3.get(name);
					p.quantity+=cart.get(i).quantity;
					break;
				}
				cart.remove(i);							//removing from cart
				flag=1;
				break;
			}
			
		}
		if(flag==1) {
			System.out.println("the product removed successfully");
		}else {
			System.out.println("No such product exist in the cart");			//no product found case
		}
		
		System.out.println("Now your cart is: ");
	    this.display_cart();									//display udpated cart
		}
	}

	 public void order_placed(Shop my_shop) 				//order placing method
		{   	
			if(this.order_id==0) {							//order yet to be placed case
				if(this.cart.size()==0) {					//empty cart case
					System.out.println("Cart is empty nothing to order");
				}else {
				my_shop.add_orders(this);					//else add the orders to pending orders avl tree
				System.out.println("Ordered placed successfully");
				}
			}else {
				System.out.println("Order already placed");		//already placed order case
			}
			
		}
		
	 public void addToCart(String name,int type,int quantity,Shop my_shop) {			//add to cart method
		 
		 if(this.order_id==0) {												//addition if order not yet dispatched
		    Scanner sc = new Scanner(System.in);
			int ans=1;
			Product_order product=my_shop.search(name,type,quantity);
			
			if(product.quantity < quantity)										//if quantity demanded is extra than available case
			    {System.out.println("Quantity not available , Do u want to go with available quantity? enter 1 if yes ");
			     ans = sc.nextInt();}
			if(ans==1 || product.quantity>=quantity) 					//going with available quantity case
			{
			this.cart.add(product);									//addition to cart
			my_shop.decrement(product.quantity,type,name);			//decrement shop quantity
			System.out.println("Added to cart successfully");			
			this.bill+=product.bill;								//increment bill
			
			}
		 }else {													//order dispatched already case
			 System.out.println("Order already dispatched cannot make changes ! Wait till its completed");
		 }
		 
	 }
	 
	public int addressMaker() {						//method to create a unique address for insertion in avl tree
		int addr;									//generates a number having its last 3 digit as house representation and
		String addr_str;							//rest digits as sector no
		if(house/10==0) {							//single digit house no case			
			addr_str=""+sector+"00"+house;
			addr=Integer.parseInt(addr_str);
		}else if(house/100==0) {					//double digit house no case	
			addr_str=""+sector+"0"+house;
			addr=Integer.parseInt(addr_str);
		}else {
			addr_str=""+sector+house;				//three digit house no case	
			addr=Integer.parseInt(addr_str);
		}
		return addr;
	}



}                   	
class Fileditor {							// class handling files for user database of shop class
	int n;  
	File file;								//file
	String name;
	Scanner input = new Scanner(System.in);
	FileOutputStream fileOut;			//output stream object reference
	ObjectOutputStream objectOut;
	FileInputStream fileIn;			   //input stream object reference
	ObjectInputStream objectIn;


	Scanner scan= new Scanner(System.in);
	Scanner scanl= new Scanner(System.in);
		
   Fileditor(String fileName) throws IOException		//constructor 
		{
			
			file = new File(fileName);						//makes file with the name passed as parameter 
			name=fileName;
			fileOut = new FileOutputStream(name);			// make file outstream obj
            objectOut = new ObjectOutputStream(fileOut);

            fileIn = new FileInputStream(name);         // make file input stream obj
            objectIn = new ObjectInputStream(fileIn);
          
		}

	public void closeFile() throws IOException 
		{
			if(file!=null)
			{
				
				objectOut.close();
				objectIn.close();
			}
		}
	
	
	public void WriteObjectToFile(Shop my_shop) {   // method to write object to file
		 
        try {     //try block to handel exception
 
            objectOut.writeObject(my_shop.cus_data);   // write object to file
            objectOut.flush();
 
        } catch (Exception ex) {
        	System.out.println("Error");   // catch exception
        }
        
	}
	public void ReadObjectFromFile(Shop my_shop) {  // method to read object from file
		 
        try {
            Object data = objectIn.readObject();
            my_shop.cus_data=(LinkedHashMap<String,Customer>)data;
      
        } catch (Exception ex) {    // catch(handel) exception 
            ex.printStackTrace();
           
        }
    }
	public void initialize_file(Shop my_shop) {   // added customers to the file
		
		my_shop.cus_data.put("james_j", new Customer("James","james_j",10,5,"jam"));
		my_shop.cus_data.put("emily_e",new Customer("Emily","emily_e",10,3,"emi"));
		my_shop.cus_data.put("rossel_r",new Customer("Rossel","rossel_r",10,2,"ros"));
		my_shop.cus_data.put("adam_a",new Customer("Adam","adam_a",9,2,"ada"));
		my_shop.cus_data.put("harry_h",new Customer("harry","harry_h",10,4,"har"));	
		WriteObjectToFile(my_shop);
	}	

}
public class Project {    // class project
	
	public static void main(String args[]) throws IOException{   // method can io exception
		
		Scanner scl= new Scanner(System.in);   // scanner class objects
		Scanner sc= new Scanner(System.in);
		String name1;
		int q1,type1;
		
		Fileditor database=new Fileditor("Users") ;   // fileeditor class object
		Shop my_shop=new Shop();       // shop class object
		Customer customer=null;     // initialize customer class object to null
		
		database.initialize_file(my_shop);
		database.ReadObjectFromFile(my_shop);       // call method to read object from file
		
		int choice=1;
		 
		do          //menu
		{
			System.out.println("**********");
			System.out.println("!!!WELCOME TO FRESHMART!!!");
			System.out.println("**********");
			System.out.println("-------------------------------------------------");
			System.out.println("1.Press 1 if you are Admin");
			System.out.println("2.Press 2 if you are user");
			System.out.println("0.Exit from System..");
			System.out.println("-------------------------------------------------");
			System.out.println("Enter your choice of operation");
			choice =sc.nextInt();
			  switch(choice)
			  {
			  case 1:   //admin
				  int ch1=1;
				  while(true) {
				  System.out.println("Enter password");
				  String pass=scl.next();
				  if(pass.equals(my_shop.password)) {  // when correct password is entered login successfully
					  System.out.println("Logged in successfully");
					  break;
				  }else {
					  System.out.println("Sorry, re-check your password");
					  System.out.println("Enter 1 to retry or 0 to exit"); // reenter password if wrong password is entered
					  int retry=sc.nextInt();
					 if(retry==0) {
						  ch1=9;
						  break;
					  }
				  }
				  }
				  do   // options for admin 
				  {   
					  System.out.println("-------------------------------");
					  System.out.println("1.Add a new product");
					  System.out.println("2.Remove a product");
					  System.out.println("3.Display shop products");
					  System.out.println("4.Display all customers");
					  System.out.println("5.Display a particular customer");
					  System.out.println("6.Complete orders of a region");
					  System.out.println("7.Complete specific customer order");
					  System.out.println("8.Display all pending orders");
					  System.out.println("0.Exit");
					  System.out.println("-------------------------------");
					  System.out.println("Enter your choice");
					  ch1=sc.nextInt();
					    switch(ch1) // switch case
					    {
					    case 1:
					    	my_shop.add_pro();
					    	break;
					    case 2:
					    	my_shop.remove_pro();
					    	break;
					    case 3:
					    	my_shop.display();
					    	break;
					    case 4:
					    	my_shop.display_UserDatabase();
					    	break;
					    case 5:
					    	System.out.println("Enter the username of customer to be searched");
					    	String user=scl.next();
					    	my_shop.display_partiUser(user);
					    	break;
					    case 6:
					    	if(my_shop.root==null) {
					    		System.out.println("No pending orders to complete");
					    	}else {
					    		System.out.println("Enter the region whose orders are to be delivered");
					    		int sec=sc.nextInt();
					    		my_shop.completeOrdersOfSector(my_shop.root, sec);
					    		System.out.println("Orders for sector "+ sec+ " set off for delivery");
					    	}
					    	break;
					    case 7:
					    	System.out.println("Enter the username of customer to be searched");
					    	String userr=scl.next();
					    	Customer cus=my_shop.cus_data.get(userr);
					    	if(my_shop.root!=null) {
					    	my_shop.completeSpecificOrder(my_shop.root, cus.address);
					    	}
					    	else {
					    		System.out.println("The customer has not yet ordered anything");
					    	}
					    	break;
					    case 8:
					    	if(my_shop.root!=null) {
					    	my_shop.Display_Customer_header(my_shop.root);
					    	}else {
					    		System.out.println("No orders yet");
					    	}
					    	break;
					    case 0:
					    	System.out.println("3.Exited");
					    	break;
					    default:
					    	System.out.println("Please enter valid choice..");
					    	break;
					    	
					    }
				  }while(ch1!=0);  // end admin switch case
				  break;
			  case 2:   // user 
				  int cho=1;
				  do     // options for user
				  { 
					  System.out.println("----------------------------------------------------------------");
					  System.out.println("1.Choose 1 if you are Already Registered with us.");        //registered customer
					  System.out.println("2.Choose 2 if you want to Create New Account in Freshmart.");  // new customer
					  System.out.println("0.Exit ");
					  System.out.println("-----------------------------------------------------------------");
					  System.out.println("Enter your choice");
					  
					  cho=sc.nextInt();
					 
					    switch(cho)
					    {
					    
					    case 1: // already registered
					    	while(true) {
					    		customer=my_shop.login();
					    		if(customer==null) {
					    			System.out.println("Sorry re-check your credentials");
					    			System.out.println("Enter 1 for retry or 0 to exit");
					    			int retry=sc.nextInt();
					    			if(retry==0)
					    				break;

					    		}else {
					    			System.out.println("Successfully Logged In!");
					    			break;
					    		}
					    	}
					    	break;
					    case 2: // new registration
					    	customer=my_shop.register();
					    	break;
					    case 0:
					    	System.out.println("3.Exit");
					    	break;
					    default:
					    	System.out.println("Please enter a valid choice");
					    	break;
					    }
					    if(customer==null) {
					    	cho=8;
					    }
					    if(cho==1 || cho==2) {
					    	
					    	System.out.println("");
					    	int ch_new=0;
				    		do 
				    		{   // options available for customers once registered
				    			System.out.println("-------------------------------");
				    			System.out.println("1.Add to cart");
				    			System.out.println("2.Delete any specific item");
				    			System.out.println("3.Display your cart");
				    			System.out.println("4.Place order");
				    			System.out.println("5.Display your details");
				    			System.out.println("6.Display shop products");
				    			System.out.println("0.Exit");
				    			System.out.println("-------------------------------");
				    			System.out.println("Enter your choice");
				    			ch_new=sc.nextInt();
				    			switch(ch_new)
				    			{
				    			case 1:  // add to cart
				    			
				    				my_shop.display();
				    				System.out.println("\nSelect Product");
				    				System.out.println("Enter name of product");
				    				name1=sc.next();
				    				System.out.println("Enter type of product");
				    				type1=sc.nextInt();
				    				System.out.println("Enter quantity of product");
				    				q1=sc.nextInt();
				    				customer.addToCart(name1,type1,q1,my_shop);
				    				break;
				    			case 2:  // delete item
				    				System.out.println("Enter name of product");
				    				name1=sc.next();
				    				System.out.println("Enter type of product");
				    				type1=sc.nextInt();
				    				customer.cancel_specific(name1,type1,my_shop);
				    			    break;
				    			case 3:  // display cart
				    				 customer.display_cart();
				    				break;
				    			case 4: //place order
				    				customer.order_placed(my_shop);
				    				break;
				    			case 5:  // display details
				    				customer.display_details();
				    				break;
				    			case 6:
				    				my_shop.display();
				    			case 0:
				    				System.out.println("Exited..");
				    				break;
				    			}
				    				
				    		}while(ch_new!=0); //end of customer switch case
					    	
					    }
				  }while(cho!=0);
			  break;
			  case 0:
				  System.out.println("Exited");   //exit switch case
				  break;
			 default:
				 System.out.println("Please enter a valid choice");
			  }
				
		}while(choice!=0);  //end do while
		database.closeFile();  // close file
		
	}
}//end of program
/*OUTPUT:
 ****
!!!WELCOME TO FRESHMART!!!
****
1.Press 1 if you are Admin
2.Press 2 if you are user
0.Exit from System..
Enter your choice of operation
2
1.Choose 1 if you are Already Registered with us.
2.Choose 2 if you want to Create New Account in Freshmart.
0.Exit 
Enter your choice
1
Enter your user name
neena
Enter your password
kkk
Wrong username
Sorry re-check your credentials
Enter 1 for retry or 0 to exit
1
Enter your user name
james_j
Enter your password
ccc
Invalid password
Sorry re-check your credentials
Enter 1 for retry or 0 to exit
1
Enter your user name
james_j
Enter your password
jam
Logged in successfully
Successfully Logged In!

-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
1
**** Daily Needs ******

   Product      Price   Quantity 
Toothpaste         34         10
      Soap         59          6
 Detergent         54          2

**** Packed Food ******

   Product      Price   Quantity 
      Lays         23          8
Chocolates         43          7
Cold_Drink         45          6

**** Food Grains *****

   Product      Price   Quantity
      Rice         12          7
     Wheat         45         12
       Dal         32         15

Select Product
Enter name of product
Soap
Enter type of product
1
Enter quantity of product
2
Added to cart successfully
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
1
**** Daily Needs ******

   Product      Price   Quantity 
Toothpaste         34         10
      Soap         59          4
 Detergent         54          2

**** Packed Food ******

   Product      Price   Quantity 
      Lays         23          8
Chocolates         43          7
Cold_Drink         45          6

**** Food Grains *****

   Product      Price   Quantity
      Rice         12          7
     Wheat         45         12
       Dal         32         15

Select Product
Enter name of product
Dal
Enter type of product
3
Enter quantity of product
2
Added to cart successfully
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
3

   Product      Price   Quantity       Bill 
---------------------------------------------------------------------------------
      Soap         59          2        118
       Dal         32          2         64

Total amount: 182
*******************************
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
2
Enter name of product
Soap
Enter type of product
1
the product removed successfully
Now your cart is: 

   Product      Price   Quantity       Bill 
---------------------------------------------------------------------------------
       Dal         32          2         64

Total amount: 64
*******************************
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
3

   Product      Price   Quantity       Bill 
---------------------------------------------------------------------------------
       Dal         32          2         64

Total amount: 64
*******************************
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
5
*******************************

  Username       Name  Sector No   House No Order status   Order Id
---------------------------------------------------------------------------------
   james_j      James         10          5    Pending  No orders

Items in cart: 

   Product      Price   Quantity       Bill 
---------------------------------------------------------------------------------
       Dal         32          2         64

Total amount: 64
*******************************
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
4
Ordered placed successfully
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
5
*******************************

  Username       Name  Sector No   House No Order status   Order Id
---------------------------------------------------------------------------------
   james_j      James         10          5    Pending          1

Items in cart: 

   Product      Price   Quantity       Bill 
---------------------------------------------------------------------------------
       Dal         32          2         64

Total amount: 64
*******************************
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
0
Exited..
1.Choose 1 if you are Already Registered with us.
2.Choose 2 if you want to Create New Account in Freshmart.
0.Exit 
Enter your choice
2
Choose any user name
james_j
Username already exist choose a diffrent one please
Choose any user name
abcd
Enter the name:
Ram
Enter the sector number:
7
Enter the house number:
6
Make your password:
rrr
Account created successfully

-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
1
**** Daily Needs ******

   Product      Price   Quantity 
Toothpaste         34         10
      Soap         59          6
 Detergent         54          2

**** Packed Food ******

   Product      Price   Quantity 
      Lays         23          8
Chocolates         43          7
Cold_Drink         45          6

**** Food Grains *****

   Product      Price   Quantity
      Rice         12          7
     Wheat         45         12
       Dal         32         13

Select Product
Enter name of product
Lays
Enter type of product
2
Enter quantity of product
2
Added to cart successfully
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
3

   Product      Price   Quantity       Bill 
---------------------------------------------------------------------------------
      Lays         23          2         46

Total amount: 46
*******************************
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
4
Ordered placed successfully
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
5
*******************************

  Username       Name  Sector No   House No Order status   Order Id
---------------------------------------------------------------------------------
      abcd        Ram          7          6    Pending          2

Items in cart: 

   Product      Price   Quantity       Bill 
---------------------------------------------------------------------------------
      Lays         23          2         46

Total amount: 46
*******************************
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
0
Exited..
1.Choose 1 if you are Already Registered with us.
2.Choose 2 if you want to Create New Account in Freshmart.
0.Exit 
Enter your choice
1
Enter your user name
emily_e
Enter your password
emi
Logged in successfully
Successfully Logged In!

-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
1
**** Daily Needs ******

   Product      Price   Quantity 
Toothpaste         34         10
      Soap         59          6
 Detergent         54          2

**** Packed Food ******

   Product      Price   Quantity 
      Lays         23          6
Chocolates         43          7
Cold_Drink         45          6

**** Food Grains *****

   Product      Price   Quantity
      Rice         12          7
     Wheat         45         12
       Dal         32         13

Select Product
Enter name of product
Rice
Enter type of product
3
Enter quantity of product
3
Added to cart successfully
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
4
Ordered placed successfully
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
0
Exited..
1.Choose 1 if you are Already Registered with us.
2.Choose 2 if you want to Create New Account in Freshmart.
0.Exit 
Enter your choice
1
Enter your user name
rossel_r
Enter your password
ros
Logged in successfully
Successfully Logged In!

-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
1
**** Daily Needs ******

   Product      Price   Quantity 
Toothpaste         34         10
      Soap         59          6
 Detergent         54          2

**** Packed Food ******

   Product      Price   Quantity 
      Lays         23          6
Chocolates         43          7
Cold_Drink         45          6

**** Food Grains *****

   Product      Price   Quantity
      Rice         12          4
     Wheat         45         12
       Dal         32         13

Select Product
Enter name of product
Wheat
Enter type of product
3
Enter quantity of product
5
Added to cart successfully
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
4
Ordered placed successfully
-------------------------------
1.Add to cart
2.Delete any specific item
3.Display your cart
4.Place order
5.Display your details
6.Display shop products
0.Exit
-------------------------------
Enter your choice
0
Exited..
1.Choose 1 if you are Already Registered with us.
2.Choose 2 if you want to Create New Account in Freshmart.
0.Exit 
Enter your choice
0
3.Exit
****
!!!WELCOME TO FRESHMART!!!
****
1.Press 1 if you are Admin
2.Press 2 if you are user
0.Exit from System..
Enter your choice of operation
1
Enter password
sss
Enter 1 for retry or 0 to exit
1
Enter password
admin_abc
Logged in successfully
1.Add a new product
2.Remove a product
3.Display shop products
4.Display all customers
5.Display a particular customer
6.Complete orders of a region
7.Complete specific cutomer order
8.Dispay all pending orders
0.Exit
Enter your choice
1
Enter Product details: 
Name of the product: Noodles
Price of the product: 45
Quantity of the product: 20
Type of the product: 2
1.Add a new product
2.Remove a product
3.Display shop products
4.Display all customers
5.Display a particular customer
6.Complete orders of a region
7.Complete specific cutomer order
8.Dispay all pending orders
0.Exit
Enter your choice
3
**** Daily Needs ******

   Product      Price   Quantity 
Toothpaste         34         10
      Soap         59          6
 Detergent         54          2

**** Packed Food ******

   Product      Price   Quantity 
      Lays         23          6
Chocolates         43          7
Cold_Drink         45          6
   Noodles         45         20

**** Food Grains *****

   Product      Price   Quantity
      Rice         12          4
     Wheat         45          7
       Dal         32         13
1.Add a new product
2.Remove a product
3.Display shop products
4.Display all customers
5.Display a particular customer
6.Complete orders of a region
7.Complete specific cutomer order
8.Dispay all pending orders
0.Exit
Enter your choice
2
Enter Product details: 
Name of the product: Noodles
Enter type of product: 2
1.Add a new product
2.Remove a product
3.Display shop products
4.Display all customers
5.Display a particular customer
6.Complete orders of a region
7.Complete specific cutomer order
8.Dispay all pending orders
0.Exit
Enter your choice
3
**** Daily Needs ******

   Product      Price   Quantity 
Toothpaste         34         10
      Soap         59          6
 Detergent         54          2

**** Packed Food ******

   Product      Price   Quantity 
      Lays         23          6
Chocolates         43          7
Cold_Drink         45          6

**** Food Grains *****

   Product      Price   Quantity
      Rice         12          4
     Wheat         45          7
       Dal         32         13
1.Add a new product
2.Remove a product
3.Display shop products
4.Display all customers
5.Display a particular customer
6.Complete orders of a region
7.Complete specific cutomer order
8.Dispay all pending orders
0.Exit
Enter your choice
8

  Username       Name  Sector No   House No Order status    OrderId
-----------------------------------------------------------------------------------
      abcd        Ram          7          6    Pending          2
-------------------------------------------------------------------------
  rossel_r     Rossel         10          2    Pending          4
-------------------------------------------------------------------------
   emily_e      Emily         10          3    Pending          3
-------------------------------------------------------------------------
   james_j      James         10          5    Pending          1
-------------------------------------------------------------------------
1.Add a new product
2.Remove a product
3.Display shop products
4.Display all customers
5.Display a particular customer
6.Complete orders of a region
7.Complete specific cutomer order
8.Dispay all pending orders
0.Exit
Enter your choice
6
Enter the region whose orders are to be delivered
10
Orders for sector 10 set off for delivery
1.Add a new product
2.Remove a product
3.Display shop products
4.Display all customers
5.Display a particular customer
6.Complete orders of a region
7.Complete specific cutomer order
8.Dispay all pending orders
0.Exit
Enter your choice
8

  Username       Name  Sector No   House No Order status    OrderId
-----------------------------------------------------------------------------------
      abcd        Ram          7          6    Pending          2
-------------------------------------------------------------------------
1.Add a new product
2.Remove a product
3.Display shop products
4.Display all customers
5.Display a particular customer
6.Complete orders of a region
7.Complete specific cutomer order
8.Dispay all pending orders
0.Exit
Enter your choice
4

  Username       Name  Sector No   House No Order status    OrderId
-----------------------------------------------------------------------------------
   james_j      James         10          5  Completed          1
  rossel_r     Rossel         10          2  Completed          4
    adam_a       Adam          9          2    Pending          0
   harry_h      harry         10          4    Pending          0
      abcd        Ram          7          6    Pending          2
   emily_e      Emily         10          3  Completed          3
1.Add a new product
2.Remove a product
3.Display shop products
4.Display all customers
5.Display a particular customer
6.Complete orders of a region
7.Complete specific cutomer order
8.Dispay all pending orders
0.Exit
Enter your choice
7
Enter the username of customer to be searched
abcd
1.Add a new product
2.Remove a product
3.Display shop products
4.Display all customers
5.Display a particular customer
6.Complete orders of a region
7.Complete specific cutomer order
8.Dispay all pending orders
0.Exit
Enter your choice
8
No orders yet*/

