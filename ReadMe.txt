/*
 * Rajendra Jadi
 * ID: 801023390
 * Project 2 - Shortest Path
 */

Programming Language: Java

Compiler Version: java version "1.8"

Execution Details:
Compilation: 
javac Graph.java

After compiling run the below command to execute the program
Run:
java Graph file_name_along_with_path < queries_file > output file name
Eg: 
java Graph network.txt < queries.txt > output.txt

Program Design:
The program builds the initial graph using input file & updating the graph to reflect changes, finding the shortest path between any two vertices in the graph based on its current state, printing the graph, and finding reachable sets of vertices.

Implementation Details:
	The program consists of 2 java files, namely Graph.java,  BinaryHeap.java and 4 classes which are described in detail as follows:
1. Vertex class, represents a vertex and it contains implementation details of the vertex such as the name, distance, previous vertex, check to visited nodes the list of edges to it.
2. The Edge class which represents an edge and it contains implementation details of the status of edge, edgeweight, and vertex.
3. The Binary Heap class is the implementation of priority queue using the Min heap. It handles functionalities like adding a vertex, removing the vertex with the minimum distance and reorganizing of min heap.
4. The Graph class contains implementations of addedge, deleteedge, edgeup, edgedown etc . 
	The Graph class is the main class where it performs below functionalities:
	1. reads a file containing edges (supplied as a command-line parameter).
	The data file is a sequence of lines of the format
	<source> <destination> <weight>
	2. Forms the graph;
	3. Repeatedly prompts for the query. The query can be one of the below
		a. print = it prints the current details of the graph
		b. addedge <source> <destination> <weight> = adds the edge from source to destination
			of the given weight
		c. deleteedge <source> <destination> = deletes the edge from source to destination
		d. edgeup <source> <destination> = marks the edge up from source to destination
		e. edgedown <source> <destination> = marks the edge from source to destinationas down
		f. vertexdown <vertexname> = marks the vertex down
		g. vertexup <vertexbname> = marks the vertex up
		i. path <source> <distance> = finds the shortest distance from source to destination using
		Dijkstra algorithm implmented using Min heap priority queue
		j. reachable = finds all the reachable vertexes from all the vertexes
		h. quit = quits the application


Program Flow:
 First, build the initial graph which connects edges and vertices accordingly and later user inputs valid commands depending on which the functions are called accordingly. 
 Later, to find the shortest path between two vertices, Dijkstra's algorithm is used which is implemented using min binary heap to implement priority queue. 
 The path from source to destination is printed along with the cost. 
 To implement reachable functionality, depth first search is called recursively which is passed to the comparator to sort the vertices. 

Scope of program:
 To find the shortest path between two vertices

Data Structure used:
1. Hashmap is used. To sort the Hashmap while printing the reachables or the details of the graph and have used the Comparator  to sort them accordingly from java.Collections. 
2. Arraylist is used for implementing the priority queue using the Min heap to keep the track of vertexes.
 



 


