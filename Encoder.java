/**
 * Rajendra jadi
 * 801023390
 * 
 * Encoder.java
 */

package proj1;

import java.io.*;
import java.util.*;

public class Encoder {
	public static List<Integer> encode(String str, Integer bit_length) {
		int MAX_TABLE_SIZE = (int) Math.pow(2, bit_length);
		int dict_size = 256;
		// Build the dictionary wit character and its ASCII value
		Map<String, Integer> dictionary = new HashMap<String, Integer>();
		for (int i = 0; i < 255; i++)
			dictionary.put("" + (char) i, i);
		String string = "";
		// ArrayList to hold the result
		List<Integer> result = new ArrayList<Integer>();
		for (char symbol : str.toCharArray()) {
			String stringSymbol = string + symbol;
			if (dictionary.containsKey(stringSymbol))
				string = stringSymbol;
			else {
				result.add(dictionary.get(string));
				if (dict_size < MAX_TABLE_SIZE)
					dictionary.put(stringSymbol, dict_size++);
				string = "" + symbol;
			}
		}
		if (!string.equals(""))
			result.add(dictionary.get(string));
		return result;
	}

	
	
	// start of main
	public static void main(String[] args) throws IOException {
		// Error handling for boundary conditions
		int bitlen;
		if (args.length < 2)// enters only if 1 argument
		{
			System.out.println("Enter proper args");
			System.exit(1);
		}
		try {
			bitlen = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("bit_length: " + args[1] + " must be an integer.");
			System.exit(1);
		}

		if (args.length > 1 && args.length < 3 && Integer.parseInt(args[1]) <= 16) {
			// Read Command line arguments
			File file = new File(args[0]);
			Integer bit_length = Integer.parseInt(args[1]);
			BufferedReader fr = null;
			BufferedWriter fw = null;
			StringBuilder str = new StringBuilder();
			try {
				// BufferReader to read and write files
				fr = new BufferedReader(new FileReader(file));
				String curLine;
				while ((curLine = (String) fr.readLine()) != null) {
					str.append(curLine);
				}
				System.out.println(str);
				List<Integer> encoded;
				encoded = encode(str.toString(), bit_length);
				System.out.println(encoded);
				String fnm = file.getName();
				fnm = fnm.substring(0, fnm.lastIndexOf('.'));
				fnm += ".lzw";
				fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fnm), "UTF-16BE"));
				for (Integer line : encoded) {
					fw.write(line);
				}
			} catch (IOException e) {
				System.out.println("wrong filename");
				System.exit(1);
				// e.printStackTrace();
			} finally {
				if (fr != null)
					fr.close();
				if (fw != null)
					fw.close();
			}
		} else {

			System.out.println("Enter Correct values");

		}
	}
	// end of main
}
