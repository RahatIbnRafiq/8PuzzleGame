/**
 * @(#)8Puzzle.java
 *
 * 8Puzzle application
 *
 * @author 
 * @version 1.00 2011/4/1
 */
 
import java.util.Scanner;
import java.util.*;
import java.lang.Math;


class node
{
	 int f_limit,g,h,f,parent,id;
	 ArrayList<Integer>children;
	 int []state;
	 node()
	 {
	 	children = new ArrayList<Integer>();
	 	state = new int[9];
	 }
}

class ResultClass
{
	int isSuccess;
	int f_limit;
	ResultClass()
	{
		isSuccess=0;
		f_limit=0;
	}
}
public class Puzzle {
	
	int [] initial;
	static int loop=0;
	int [] goal;
	Scanner scn;
	ArrayList <node> nodelist = new ArrayList <node> ();
	node n;
	static int nodeID=0;
	int result;
	ResultClass r;
	int variant;
	Puzzle(int variant)
	{
		this.variant = variant;
		this.initial = new int[9];
		this.goal = new int [9];
		Input();//take goal state and initial state as inputs
		node n1 = new node ();
		n1 = makeNode(initial);
		nodelist.add(n1);
		print(n1);
		r = RBFS(n1,1000);
	}
	
	public ResultClass RBFS(node n1, int fLimit)
	{
		int sizeOfList;
		sizeOfList = nodelist.size();
		DeleteNodes(n1.id);
	    sizeOfList = nodelist.size();
		int IfGoal = GoalTest(n1.state);
		ResultClass r =new ResultClass();
		if(IfGoal==1)//then the result is found
		{
			r.isSuccess=1;
			r.f_limit = n1.f_limit;
			System.out.println("success  "+n1.g);
			return r;
		}
	
		r = Expand(n1);
		if(r.isSuccess==1)//one of the generated children is the goal state
		{
			System.out.println("success!!!!! the cost is "+r.f_limit);
			return r;
		}
		if(n1.children.isEmpty())//no successors
		{
			r.isSuccess=0;
			r.f_limit=1000;
		}
		node best = new node();
		node alternative = new node();
		int bestfound =0,alterfound=0;
		
		while(true)
		{
			DeleteNodes(n1.id);
			bestfound=0;alterfound=0;
			for(int i=0;i<nodelist.size();i++)//find the best and alternative
			{
				node temp = nodelist.get(i);
				if(temp.parent==n1.id&&bestfound==0)
				{
					best = nodelist.get(i);
					bestfound =1;
				}
				else if(alterfound==0&&temp.parent==n1.id)
				{
					alternative = nodelist.get(i);
					alterfound=1;
				
					break;
				}
			}
			
			if(best.f>fLimit)
			{
				for(int i=0;i<nodelist.size();i++)
				{
					node temp = nodelist.get(i);
					if(temp.parent==n1.id)
						nodelist.remove(i);//remove all the children of this node.
				}
				r.isSuccess=0;
				r.f_limit=best.f;
				return r;
			}
			if(fLimit<alternative.f)
			{
				r = RBFS(best,fLimit);
				best.f = r.f_limit;//set the f value of this node to the f value of its best child
				for(int j=0;j<nodelist.size();j++)
				{
					node temp = nodelist.get(j);
					if(temp.id==best.id)
					{
						nodelist.remove(j);//remove the old instance from the nodelist
						break;
					}
				}
				addToNodelist(best);//add the new best node to the nodelist
			}
			else
			{
				r = RBFS(best,alternative.f);
				best.f = r.f_limit;
				for(int j=0;j<nodelist.size();j++)
				{
					node temp = nodelist.get(j);
					if(temp.id==best.id)
					{
						nodelist.remove(j);
						break;
					}
				}
				addToNodelist(best);
			}
			
			if(r.isSuccess==1)
			{
				r.f_limit=best.g;
				return r;
			}
	 	}
	}
	
	public ResultClass Expand(node n)
	{
		//System.out.println("hii");
		int temp1,temp2;
		int check;
		ResultClass r = new ResultClass();
		if(n.state[0]==0)
		{
			node successor1 = new node();
			node successor2 = new node();
			if((n.state[0]!=n.state[1]))
			{
				successor1 = makenode2(n,0,1);
				check = Test(successor1.state,initial);
				if(check==0)
				{
					n.children.add(successor1.id);
					print(successor1);
					addToNodelist(successor1);	
				}
				check = GoalTest(successor1.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor1.f;
				}
			}
			if(n.state[0]!=n.state[3])
			{
				successor2 = makenode2(n,0,3);
				check = Test(successor2.state,initial);
				if(check==0)
				{
					
					n.children.add(successor2.id);
					print(successor2);
					addToNodelist(successor2);
				}
				check = GoalTest(successor2.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor2.f;
				}
			}			
		}
	    if(n.state[1]==0)
		{
			node successor1 = new node();
			node successor2 = new node();
			node successor3 = new node();
			if(n.state[1]!=n.state[0])
			{
				successor1 = makenode2(n,1,0);
				check = Test(successor1.state,initial);
				if(check==0)
				{
					n.children.add(successor1.id);
					print(successor1);
					addToNodelist(successor1);
				}
				check = GoalTest(successor1.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor1.f;
				}
			}
			if(n.state[1]!=n.state[2])
			{
				successor2 = makenode2(n,1,2);
				check = Test(successor2.state,initial);
				if(check==0)
				{
					n.children.add(successor2.id);
					print(successor2);
					addToNodelist(successor2);
				}
				check = GoalTest(successor2.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor2.f;
				}
			}
			if(n.state[1]!=n.state[4])
			{
			 	successor3 = makenode2(n,1,4);
			 	check = Test(successor3.state,initial);
				if(check==0)
				{
					n.children.add(successor3.id);
					print(successor3);
					addToNodelist(successor3);
				}
				check = GoalTest(successor3.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor3.f;
				}
			}	
		}
		if(n.state[2]==0)
		{
			node successor1 = new node();
			node successor2 = new node();
			if(n.state[2]!=n.state[1])
			{
				successor1 = makenode2(n,2,1);
				check = Test(successor1.state,initial);
				if(check==0)
				{
					n.children.add(successor1.id);
					print(successor1);
					addToNodelist(successor1);
				}
				check = GoalTest(successor1.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor1.f;
				}
			}
			if(n.state[2]!=n.state[5])
			{
				successor2 = makenode2(n,2,5);
				check = Test(successor2.state,initial);
				if(check==0)
				{
					n.children.add(successor2.id);
					print(successor2);
					addToNodelist(successor2);
				}
				check = GoalTest(successor2.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor2.f;
				}
			}			
		}
		
		if(n.state[3]==0)
		{
			node successor1 = new node();
			node successor2 = new node();
			node successor3 = new node();
			if(n.state[3]!=n.state[0])
			{
				successor1 = makenode2(n,3,0);
				check = Test(successor1.state,initial);
				if(check==0)
				{
					n.children.add(successor1.id);
					print(successor1);
					addToNodelist(successor1);
				}
				check = GoalTest(successor1.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor1.f;
				}
			}
			if(n.state[3]!=n.state[6])
			{
				successor2 = makenode2(n,3,6);
				check = Test(successor2.state,initial);
				if(check==0)
				{
					n.children.add(successor2.id);
					print(successor2);
					addToNodelist(successor2);
				}
				check = GoalTest(successor2.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor2.f;
				}
			}
			if(n.state[3]!=n.state[4])
			{
				successor3 = makenode2(n,3,4);
				check = Test(successor3.state,initial);
				if(check==0)
				{
					n.children.add(successor3.id);
					print(successor3);
					addToNodelist(successor3);
				}
				check = GoalTest(successor3.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor3.f;
				}
			}		
		}
		
	    if(n.state[4]==0)
		{
			node successor1 = new node();
			node successor2 = new node();
			node successor3 = new node();
			node successor4 = new node();
			
			if(n.state[4]!=n.state[1])
			{
				successor1 = makenode2(n,4,1);
				check = Test(successor1.state,initial);
				if(check==0)
				{
					n.children.add(successor1.id);
					print(successor1);
					addToNodelist(successor1);
				}
				check = GoalTest(successor1.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor1.f;
				}
			}
			if(n.state[4]!=n.state[7])
			{
				successor2 = makenode2(n,4,7);
				check = Test(successor2.state,initial);
				if(check==0)
				{
					n.children.add(successor2.id);
					print(successor2);
					addToNodelist(successor2);
					
				}
				check = GoalTest(successor2.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor2.f;
				}
			}
			if(n.state[4]!=n.state[3])
			{
				successor3 = makenode2(n,4,3);
				check = Test(successor3.state,initial);
				if(check==0)
				{
					n.children.add(successor3.id);
					print(successor3);
					addToNodelist(successor3);
				}
				check = GoalTest(successor3.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor3.f;
				}
			}
			if(n.state[4]!=n.state[5])
			{
				successor4 = makenode2(n,4,5);
				check = Test(successor4.state,initial);
				if(check==0)
				{
					n.children.add(successor4.id);
					print(successor4);
					addToNodelist(successor4);
				}
				check = GoalTest(successor4.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor4.f;
				}
			} 		
		}
		
		if(n.state[5]==0)
		{
			node successor1 = new node();
			node successor2 = new node();
			node successor3 = new node();
			
			if(n.state[5]!=n.state[4])
			{
				successor1 = makenode2(n,5,4);
				check = Test(successor1.state,initial);
				if(check==0)
				{
					n.children.add(successor1.id);
					print(successor1);
					addToNodelist(successor1);
				}
				check = GoalTest(successor1.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor1.f;
				}
			}
			if(n.state[5]!=n.state[2])
			{
				successor2 = makenode2(n,5,2);
				check = Test(successor2.state,initial);
				if(check==0)
				{
					n.children.add(successor2.id);
					print(successor2);
					addToNodelist(successor2);
				}
				check = GoalTest(successor2.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor2.f;
				}
			}
			if(n.state[5]!=n.state[8])
			{
				successor3 = makenode2(n,5,8);
				check = Test(successor3.state,initial);
				if(check==0)
				{
					n.children.add(successor3.id);
					print(successor3);
					addToNodelist(successor3);
				}
				check = GoalTest(successor3.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor3.f;
				}
			}		
		}
		
	    if(n.state[6]==0)
		{
			node successor1 = new node();
			node successor2 = new node();
			
			if(n.state[6]!=n.state[3])
			{
				successor1 = makenode2(n,6,3);
				check = Test(successor1.state,initial);
				if(check==0)
				{
					n.children.add(successor1.id);
					print(successor1);
					addToNodelist(successor1);
				}
				check = GoalTest(successor1.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor1.f;
				}
			}
			if(n.state[6]!=n.state[7])
			{
				successor2 = makenode2(n,6,7);
				check = Test(successor2.state,initial);
				if(check==0)
				{
					n.children.add(successor2.id);
					print(successor2);
					addToNodelist(successor2);
				}
				check = GoalTest(successor2.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor2.f;
				}
			}			
		}
	    if(n.state[7]==0)
		{
			
			node successor1 = new node();
			node successor2 = new node();
			node successor3 = new node();
			
			if(n.state[7]!=n.state[4])
			{
				successor1 = makenode2(n,7,4);
				check = Test(successor1.state,initial);
				if(check==0)
				{
					n.children.add(successor1.id);
					print(successor1);
					addToNodelist(successor1);
				}
				check = GoalTest(successor1.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor1.f;
				}
			}
			if(n.state[7]!=n.state[6])
			{
				successor2 = makenode2(n,7,6);
				check = Test(successor2.state,initial);
				if(check==0)
				{
					n.children.add(successor2.id);
					print(successor2);
					addToNodelist(successor2);
				}
				check = GoalTest(successor2.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor2.f;
				}
			}
			if(n.state[7]!=n.state[8])
			{
				successor3 = makenode2(n,7,8);
				check = Test(successor3.state,initial);
				if(check==0)
				{
					n.children.add(successor3.id);
					print(successor3);
					addToNodelist(successor3);
				}
				check = GoalTest(successor3.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor3.f;
				}
			}		
		}
		
	    if(n.state[8]==0)
		{
			node successor1 = new node();
			node successor2 = new node();
			if(n.state[8]!=n.state[7])
			{
				successor1 = makenode2(n,8,7);
				check = Test(successor1.state,initial);
				if(check==0)
				{
					n.children.add(successor1.id);
					print(successor1);
					addToNodelist(successor1);
				}
				check = GoalTest(successor1.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor1.f;
				}
			}
			if(n.state[8]!=n.state[5])
			{
				successor2 = makenode2(n,8,5);
				check = Test(successor2.state,initial);
				if(check==0)
				{
					n.children.add(successor2.id);
					print(successor2);
					addToNodelist(successor2);
				}
				check = GoalTest(successor2.state);
				if(check==1)
				{
					r.isSuccess=1;
					r.f_limit = successor2.f;
				}
			}
		}
		
		return r;
	}
	
	public void DeleteNodes(int ParentID)
	{
		for(int i=0;i<nodelist.size();i++)
		{
			node temp = nodelist.get(i);
			if(temp.parent>ParentID)
			{
				nodelist.remove(i);
			}
		}
	}
	
	public void addToNodelist(node child)
	{
		for(int  i =0; i<nodelist.size();i++)
		{
			node temp = nodelist.get(i);
			if(child.f<temp.f)
			{
				nodelist.add(i,child);
				return ;
			}
		}
		nodelist.add(child);
	}
	
	public int GoalTest(int []s )
	{
		for(int i =0;i<9;i++)
		{
			if(s[i]!=goal[i])
				return 0;
		}
		
		return 1;
	}
	
	public void print(node n)
	{
		int isgoal = GoalTest(n.state);
		if(isgoal==1)
		{
			System.out.print(" success state is found !!---->");
		}
		for(int i =0;i<9;i++)
		{
			System.out.print(n.state[i]+" ");
		}
		System.out.println();
		System.out.println(" function"+n.f+"  Cost"+n.g+"  f limit"+n.f_limit+"  parent"+n.parent+"  id"+n.id);
		System.out.println("________________________________________________");
	}
	
	public void Input()
	{
		this.goal[0]=1;this.goal[1]=2;this.goal[2]=3;
		this.goal[3]=4;this.goal[4]=5;this.goal[5]=6;
		this.goal[6]=0;this.goal[7]=0;this.goal[8]=0;
		
		this.initial[0]=1;this.initial[1]=2;this.initial[2]=4;
		this.initial[3]=0;this.initial[4]=3;this.initial[5]=5;
		this.initial[6]=0;this.initial[7]=0;this.initial[8]=6;
	}
	
	public node makeNode(int [] s)
	{
		node n1 = new node ();
		for(int i=0;i<9;i++)
		{
			n1.state[i] = s[i];//copy the state array
		}
		n1.id = nodeID;//give the node an ID
		nodeID++;
		n1.g = findG(n1);
		n1.h = findH(n1);
		n1.f = n1.h;
		if(n1.id==0)
		{
			n1.parent = -1;//root node
			n1.f_limit = 1000;
		}
		
		return n1;
	}
	
	public node makenode2 (node n1, int blank, int swap)
	{
		//int [] s =new int [9];
		node child = new node();
		for(int i=0;i<9;i++)
		{
			if(i==blank)
			{
				child.state[blank]=n1.state[swap];
			}
			else if(i==swap)
			{
				child.state[swap] = n1.state[blank];
			}
			else
				child.state[i]=n1.state[i];
		}
		child.parent = n1.id;
		child.id = nodeID;//node er id
		nodeID++;
		child.h = findH(child);
		child.g = findG(child);
		if((child.g+child.h)>n1.f)
		{
			child.f = child.g+child.h;
		}
		else
		{
			child.f = n1.f;
		}
		
		n1.children.add(child.id);//parent er childrean er id er list
		//nodelist.add(child);
		return child;
	}
	
	public int findG(node n1)
	{
		if(n1.id==0)
			return 0;//initial node so g=0
		else
		{
			for(int i=0;i<this.nodelist.size();i++)
			{
				node temp = nodelist.get(i);
				if(temp.id==n1.parent)
					return temp.g+1;
			}
		}
		
		return 1;
	}
	
	public int findH(node n1)
	{
		/*int misplacedTiles = 0;
		for(int i=0;i<9;i++)
		{
			if(n1.state[i]!=this.goal[i])
				misplacedTiles++;
		}
		return misplacedTiles;*/
		int manhatten=0;
		int [][] goal1 = new int [3][3];
		int [][]test = new int [3][3];
		goal1[0][0]= goal[0];
		goal1[0][1]= goal[1];
		goal1[0][2]= goal[2];
		goal1[1][0]= goal[3];
		goal1[1][1]= goal[4];
		goal1[1][2]= goal[5];
		goal1[2][0]= goal[6];
		goal1[2][1]= goal[7];
		goal1[2][2]= goal[8];
		
		test[0][0]= n1.state[0];
		test[0][1]= n1.state[1];
		test[0][2]= n1.state[2];
		test[1][0]= n1.state[3];
		test[1][1]= n1.state[4];
		test[1][2]= n1.state[5];
		test[2][0]= n1.state[6];
		test[2][1]= n1.state[7];
		test[2][2]= n1.state[8];
		int found;
		//Math  m = new Math();
		int rowdiff;
		int columndiff;
		for(int u=0;u<2;u++)
		{
			for(int v=0;v<2;v++)
			{
				found =0;
				int temp1 = test[u][v];
				for(int w=0;w<2&&found==0;w++)
				{
					for(int x=0;x<2;x++)
					{
						if(goal1[w][x]==temp1)
						{
							found=1;
							rowdiff=u-w;
							columndiff=v-x;
							manhatten= manhatten+Math.abs(rowdiff)+Math.abs(columndiff);
							break;
						}
					}
					
				}
			}
		}
		
		return manhatten;
			
	}
	
	public int  Test (int []s, int [] r)
	{
		for(int i=0;i<9;i++)
		{
			if(s[i]!=r[i])
				return 0;
		}
		
		return 1;
	}

    public static void main(String[] args) {
    	Puzzle p = new Puzzle(1);
    	
    }
}
