/*
 * Rajendra Jadi
 * ID: 801023390
 * Project 2 - Shortest Path
 */

import java.util.ArrayList;

/*
 *  Binary heap implementation
 */
class BinaryHeap 
{
	private int size;
	private int bsize;
	private ArrayList<Vertex> verList;
	
	//Initializing constructor
	public BinaryHeap() 
	{
		size = 0;
		verList = new ArrayList<Vertex>();
		verList.add(null);
	}

	//Function that adds the vertex to priority queue time complexity is O(log n),  n is the size of the priority queue.
	public void add(Vertex ver) 
	{
		verList.add(ver);        
		size++;             
		bsize = size;       
		while (bsize>1 && ver.dist< verList.get(bsize / 2).dist ) 
		{
			verList.set(bsize, verList.get(bsize / 2));
			bsize=bsize/2;
		}
		verList.set(bsize, ver);
	}

	//Function to remove the smallest element  in time complexity of O(log n) where n is the size of the priority queue. 
	public Vertex remove() 
	{
		if (!isEmpty()) 
		{
			Vertex hold = verList.get(1);
			verList.set(1, verList.get(size));
			verList.remove(size);
			size--;
			if (size > 1) 
			{
				reorganize(1);
			}
			return hold;
		}
		return null;
	}
	
	/* Function to implement reorganize the structure with 
	 * Time complexity of
	 * O(log(size()- heapindex)) time
	 * where heapindex is the index at which reorganizing the structure for correctness occurs
	 */
	private void reorganize(int heapindex) 
	{
		int j;
		int i = heapindex;
		Vertex lastVertex = verList.get(heapindex);
		while (2 * i <= size) 
		{
			j = 2 * i;
			if (size > j  && verList.get(j + 1).dist < verList.get(j).dist ) 
			{
				j++;
			}
			if (verList.get(j).dist>=lastVertex.dist) 
			{
				break;
			}
			else
			{
				verList.set(i, verList.get(j));
				i = j;
			}
		}
		verList.set(i, lastVertex);
	}

	public int size() 
	{
		return size;
	}
	public boolean isEmpty() 
	{
		return size == 0;
	}
}
