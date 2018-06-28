/*
 * Rajendra Jadi
 * ID: 801023390
 * Project 2 - Shortest Path
 */
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;


/*
 * Represents a vertex in the graph.
 * Vertex Class Implementation
 */
class Vertex implements Comparable<Vertex>
{
	public String name;   
	public List<Edge> adj;
	public Vertex prev;   
	public Double dist;  
	public String verstatus;
	int isvisited;


	public int compareTo(Vertex myvertex) 
	{
		return name.compareTo(myvertex.name);
	}


	public Vertex(String vername) 
	{
		name = vername;
		adj = new LinkedList<Edge>();
		reset();
		verstatus = "UP";
	}


	//resets the vertex details such as previous and the distance
	public void reset() 
	{
		dist = Graph.INFINITY;
		prev = null;
		isvisited = 0;
	}
}

/*
 * Edge class implementation
 */
class Edge implements Comparable<Edge>
{
	public String edgestatus; 
	public float edgewt; 
	public Vertex vertex;  

	public Edge(Vertex vertex, float edgewe) 
	{
		this.vertex = vertex;
		edgewt = edgewe;
		edgestatus = "UP";
	}

	@Override
	public int compareTo(Edge edge) 
	{
		return vertex.name.compareTo(edge.vertex.name);
	}
}


/*
 * Graph Class Contains Implementation of  adding,delete,updating edges etc
 */
public class Graph 
{
	public static final Double INFINITY = Double.MAX_VALUE;
	private Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();

	//Since when add edge is specified in the commands, the edge should be only from source to destination vertex,
	//so this function created an directed edge from source to destination only
	private void addDiredge(Vertex vertex, Edge edge) 
	{
		List<Edge> listofVer = vertex.adj;
		int edgeExist = 0;
		int myEdge = 0;

		for(int i = 0 ;i < listofVer.size(); i++)
		{
			if (edge.equals(listofVer.get(i))) 
			{
				edgeExist = 1;
				myEdge = i;
			}
		}
		if (edgeExist == 1) 
		{
			vertex.adj.get(myEdge).edgewt = edge.edgewt;
		}
		else
		{
			vertex.adj.add(edge);
		}
	}

	//Function to implement Edge Down Functionality
	private void edgeDown(Vertex sourcevertex, Vertex destinationvertex) 
	{
		List<Edge> listofVer = sourcevertex.adj;
		for(int i =0 ; i<listofVer.size();i++)
		{
			if (listofVer.get(i).vertex.equals(destinationvertex) &&  (!sourcevertex.adj.get(i).edgestatus.equals("DOWN"))) 
			{
				sourcevertex.adj.get(i).edgestatus ="DOWN";
			}
		}
	}


	//Function that implements Delete Directed Edge Functionality
	private void deleteDiredge(Vertex sourcevertex, Vertex destinationvertex) 
	{
		List<Edge> listofVer = sourcevertex.adj;
		for(int i =0;i<listofVer.size();i++)
		{
			if (listofVer.get(i).vertex.equals(destinationvertex)) 
			{
				// edge found, delete it
				sourcevertex.adj.remove(i);
			}
		}
	}

	//Function that implements Adding Edge Up Functionality
	private void edgeUp(Vertex sourcevertex, Vertex destinationvertex)
	{
		List<Edge> adjedges = sourcevertex.adj;
		for(int i =0;i<adjedges.size();i++)
		{
			if(adjedges.get(i).vertex.equals(destinationvertex) && (!sourcevertex.adj.get(i).edgestatus.equals("UP")))
			{
				// update edge status
				sourcevertex.adj.get(i).edgestatus="UP";
			}
		}
	}

	//Function that implements Adding UnDirected Edge Functionality
	public void addUDEdge(String sourcevertex, String destinationvertex, float weight) 
	{
		Vertex srcVer = getVertex(sourcevertex);
		Vertex destVer = getVertex(destinationvertex);
		Edge dstedge = new Edge(destVer, weight);
		Edge srcedge = new Edge(srcVer, weight);
		addDiredge(srcVer, dstedge);
		addDiredge(destVer, srcedge);
	}

	//Function to implement Adding Directed Edge Functionality
	public void addDiredge(String sourcevertex, String destinationvertex, float weight) 
	{
		Vertex srcVer = getVertex(sourcevertex);
		Vertex destVer = getVertex(destinationvertex);
		Edge edge = new Edge(destVer, weight);
		addDiredge(srcVer, edge);
	}


	//Function to implement Edge Up Functionality
	public void edgeUp(String sourcevertex,String destinationvertex) 
	{
		Vertex srcVer = getVertex(sourcevertex);
		Vertex destVer = getVertex(destinationvertex);
		edgeUp(srcVer, destVer);
	}


	//Function to implement Edge Down Functionality
	public void edgeDown( String sourcevertex, String destinationvertex)
	{
		Vertex srcVer = getVertex(sourcevertex);
		Vertex destVer = getVertex( destinationvertex);
		edgeDown(srcVer,destVer);        
	}

	//Function to implement DeleteEdge Functionality
	public void deleteEdge(String sourcevertex, String destinationvertex) 
	{
		Vertex srcVer = getVertex(sourcevertex);
		Vertex destVer = getVertex(destinationvertex);
		deleteDiredge(srcVer, destVer);
	}

	//Function to implement Vertex Down Functionality
	public void vertexDown(String vertexName)
	{
		Vertex vertex = getVertex(vertexName);
		if(!vertex.verstatus.equals("DOWN"))
		{
			vertex.verstatus="DOWN";      
		}
	}
	//Function to implement Vertex Up Functionality
	public void vertexUp(String vertexName)
	{
		Vertex vertex=getVertex(vertexName);  
		if(!vertex.verstatus.equals("UP"))
		{
			vertex.verstatus="UP";      
		}
	}

	//Function to print total distance and if the vertex is reachable from a vertex, a recursive routine to print shortest path
	public void printPath(String destinationVertex)
	{
		Vertex ver = vertexMap.get(destinationVertex);

		if (ver == null) 
		{
			throw new NoSuchElementException("Invalid Destination Vertex ");
		}
		else if (ver.dist==INFINITY)
		{
			System.out.println(destinationVertex + " is unreachable or Source vertextex is down");
		}
		else
		{
			printPath(ver);
			double v = Math.round(ver.dist * 100d) / 100d ;
			System.out.print(" " +v);
			System.out.println();
		}
	}

	// Fuction recursively calls routine to print shortest path from source to destination after running shortest path algorithm if path exists between source & destination.
	private void printPath(Vertex dest) 
	{
		if (dest.prev != null) 
		{
			printPath(dest.prev);
			System.out.print(" ");
		}
		System.out.print(dest.name);
	}

	//Print commands function to print graph along with its status and weight.
	public void printGraph() 
	{
		List<Vertex> verMap = new ArrayList<Vertex>(vertexMap.values());
		Collections.sort(verMap);
		for (Vertex v : verMap) 
		{
			System.out.print(v.name);
			if (v.verstatus.equals("DOWN")) 
			{
				System.out.print(" ");
				System.out.println(v.verstatus);
			}
			else
			{
				System.out.println();
			}
			List<Edge> adjedges = v.adj;
			Collections.sort(adjedges);
			for (int i = 0; i < adjedges.size(); i++) 
			{
				System.out.print("  ");
				System.out.print(adjedges.get(i).vertex.name);
				System.out.print(" ");

				double b = Math.round(adjedges.get(i).edgewt * 100d) / 100d ;
				System.out.print(b);
				if (adjedges.get(i).edgestatus.equals("DOWN")) 
				{
					System.out.print(" ");
					System.out.println(adjedges.get(i).edgestatus);
				}
				else 
				{
					System.out.println();
				}
			}
		}
	}


	// Function to find all the reachable vertexes from all the vertexes
	/*
	 * DFS (Depth First Search) is being used to get the details of all the reachable vertexes from every vertex, 
	 * For each of the vertex clearAll function is called to clear the previous, distance and other details
	 * and for each vertex DFS function is called taking it as source vertex and it gets all the reachable vertexes from that vertex,
	 *  As we know that the efficiency of the DFS is O(V+E) where V is the number of vertexes and E is the number of Edges
	 * and we are calling this function for V times so the efficiency of this function is  O V(V+E) 
	 * */
	public void reachable() 
	{
		List<Vertex> verMap = new ArrayList<Vertex>(vertexMap.values());
		Collections.sort(verMap);
		for (Vertex ver : verMap) 
		{
			clearAll();
			List<String> list = new LinkedList<String>();
			if (ver.verstatus.equals("UP"))
			{
				System.out.print(ver.name);
				System.out.println();
				dfs(ver, list);
			}
			Collections.sort(list);
			for (int i = 0; i < list.size(); i++) 
			{
				System.out.println("  " + list.get(i));
			}
		}
	}
	/*
	 * Function to implement DFS
	 */
	private void dfs(Vertex fromVertex, List<String> list) 
	{
		fromVertex.isvisited = 1;
		for (Edge edge : fromVertex.adj) 
		{
			if (edge.edgestatus.equals("UP")) 
			{
				Vertex vertex = edge.vertex;
				if (vertex.verstatus.equals("UP")) 
				{
					if (vertex.isvisited!= 1) 
					{
						list.add(vertex.name);
						dfs(vertex, list);
					}
				}
			}
		}
	}

	private void clearAll() 
	{
		for (Vertex v : vertexMap.values()) 
		{
			v.reset();
		}
	}

	//Function to implement Dijsktras algorithm
	public void dijkstras(String source) 
	{
		clearAll();
		Vertex startVertex = vertexMap.get(source);
		if (startVertex == null) 
		{
			throw new NoSuchElementException("Couldn't find Start vertex ");
		}
		//Create instance of BinaryHeap class
		BinaryHeap heap = new BinaryHeap();
		heap.add(startVertex);
		startVertex.dist = 0.0;
		while(!heap.isEmpty())
		{
			Vertex vertex = heap.remove();
			if(vertex.verstatus.equals("UP"))
			{
				for( Edge edge : vertex.adj )
				{
					if(edge.edgestatus.equals("UP"))
					{
						Vertex v = edge.vertex;
						float input= edge.edgewt;
						if( v.dist > vertex.dist+input )
						{
							v.dist = vertex.dist + input;
							v.prev = vertex;
							heap.add(v);
						}
					}
				}
			}
		}
	}


	/*
	 * add vertexName to vertexMap incase, if its not present.
	 * In either case, return the Vertex.
	 */
	private Vertex getVertex(String name1) 
	{
		Vertex vertex = vertexMap.get(name1);
		if (vertex == null) {
			vertex = new Vertex(name1);
			vertexMap.put(name1, vertex);
		}
		return vertex;
	}

	//function that takes inputs from user and calls respective functions
	public static boolean processRequest(Scanner in, Graph g) 
	{
		try
		{
			String command = in.nextLine();
			StringTokenizer st = new StringTokenizer(command);
			String input = st.nextToken();

			switch(input)
			{
			case "print": 
				if (st.countTokens() != 0) 
				{
					System.err.println("Skipping ill-formatted lines " + command);
				} 
				else
				{
					g.printGraph();
					System.out.println();
				}
				break;

			case "edgeup":
				if (st.countTokens() != 2) 
				{
					System.err.println("Skipping ill-formatted lines " + command);
				}
				else
				{
					String source = st.nextToken();
					String dest = st.nextToken();
					g.edgeUp(source, dest);
				}
				break;
			case "reachable":
				if (st.countTokens() != 0) 
				{
					System.err.println("Skipping ill-formatted lines " + command);
				}
				else
				{
					g.reachable();
					System.out.println();
				}
				break;

			case "addedge":
				if (st.countTokens() != 3) 
				{
					System.err.println("Skipping ill-formatted lines " + command);
				} 
				else
				{
					String source = st.nextToken();
					String destination = st.nextToken();
					float edgewt = Float.parseFloat(st.nextToken());
					g.addDiredge(source, destination, edgewt);
				}
				break;
			case "deleteedge":
				if (st.countTokens() != 2) 
				{
					System.err.println("Skipping ill-formatted lines " + command);
				}
				else
				{
					String sourceEdge = st.nextToken();
					String destionEdge = st.nextToken();
					g.deleteEdge(sourceEdge, destionEdge);
				}
				break;
			case "edgedown":
				if (st.countTokens() != 2) 
				{
					System.err.println("Skipping ill-formatted lines " + command);
				}
				else
				{
					String source = st.nextToken();
					String dest = st.nextToken();
					g.edgeDown(source, dest);
				}
				break;

			case "path":
				if (st.countTokens() != 2) 
				{
					System.err.println("Skipping ill-formatted lines " + command);
				}
				else
				{
					String source = st.nextToken();
					String destination = st.nextToken();
					g.dijkstras(source);
					g.printPath(destination);
					System.out.println();
				}
				break;

			case "vertexup":
				if (st.countTokens() != 1) 
				{
					System.err.println("Skipping ill-formatted lines " + command);
				}
				else
				{
					String vertextoup = st.nextToken();
					g.vertexUp(vertextoup);
				}
				break;
			case "vertexdown":
				if (st.countTokens() != 1) 
				{
					System.err.println("Skipping ill-formatted lines " + command);
				}
				else
				{
					String vertextodown = st.nextToken();
					g.vertexDown(vertextodown);
				}
				break;
			case "quit":
				return false;
			default:
				System.out.println("Please check if you have quit written as last query line. Something wrong with the command. ");
			}
		}
		catch (Exception e) 
		{
			//System.err.println(e);
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
/*
 * A main routine that performs following tasks:
 * 1. Reads a network file containing edges (supplied as a command-line arg);
 * 2. Forms the graph using the input file;
 * 3. Repeatedly prompts for the query. The query can be one of the below
 *    a. print  => prints the  current details of the graph
 *    b. addedge <source> <destination> <weight>  => adds the edge from source to destination of the given weight
 *    c. deleteEdge <source> <destination>  =>marks the edge up from source to destination
 *    e. edgeDown <source> <destination> => marks the edge from source to destination down
 *    f. vertexDown <vertexname> => marks the vertex down
 *    g. vertexUp <vertexbname> => marks the vertex up
 *    i. path <source> <distance> => finds the shortest distance from source to destination using Dijkstra algorithm implemented using Min heap priority queue
 *    j. reachable => finds all the reachable vertexes from all the vertexes
 *    h. quit => quits the application
 */
	public static void main(String[] args) 
	{
		Graph graph = new Graph();
		try
		{
			FileReader fin = new FileReader(args[0]);
			Scanner graphFile = new Scanner(fin);
			String line;
			while (graphFile.hasNextLine()) 
			{
				line = graphFile.nextLine();
				StringTokenizer st = new StringTokenizer(line);
				try
				{
					if (st.countTokens() != 3) 
					{
						System.err.println("Skipping ill-formatted lines " + line);
						continue;
					}
					String source = st.nextToken();
					String dest = st.nextToken();
					float edgewt = Float.parseFloat(st.nextToken());
					graph.addUDEdge(source, dest, edgewt);
				} 
				catch (NumberFormatException e) 
				{
					System.err.println("Skipping ill-formatted lines " + line);
				}
			}   
			graphFile.close();
		} 
		catch (IOException e) 
		{
			System.err.println(e);
		}
		Scanner in = new Scanner(System.in);
		while (processRequest(in, graph));
	}
}
