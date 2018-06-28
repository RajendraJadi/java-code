/**
 * Rajendra jadi
 * 801023390
 * 
 * Decoder.java
 */

package proj1;

import java.io.*;
import java.util.*;

public class Decoder {

	public static String decode(ArrayList<Integer> encoded, Integer bit_length) {
		int MAX_TABLE_SIZE = (int) Math.pow(2, bit_length);
		int dict_size = 255;
		StringBuffer result = new StringBuffer();
		Map<Integer, String> dictionary = new HashMap<Integer, String>();
		for (int i = 0; i < 256; i++) {
			dictionary.put(i, "" + (char) i);
		}
		int a = encoded.get(0);
		String str = dictionary.get(a);
		String new_string = null;
		for (int i = 0; i < encoded.size(); i++) {
			// new_string;
			a = encoded.get(i);
			if (dictionary.containsKey(a)) {
				new_string = dictionary.get(a);
			} else {
				new_string = str + str.charAt(0);
			}

			result.append(new_string);

			// Add string+new_string[0] to the dictionary.
			if (dict_size < MAX_TABLE_SIZE) {
				dictionary.put(dict_size++, str + new_string.charAt(0));
			}
			str = new_string;
		}
		return result.toString();
	}

	
	// start of main
	public static void main(String[] args) throws IOException {
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
			// StringBuilder str = new StringBuilder();
			ArrayList<Integer> encoded = new ArrayList<Integer>();
			try {
				// BufferReader to read and write files
				fr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-16"));
				double curval = 0;

				while ((curval = fr.read()) != -1) {
					encoded.add((int) curval);
				}
				System.out.println(encoded);

				String decoded = decode(encoded, bit_length);
				System.out.println(decoded);
				String fnm = file.getName();
				fnm = fnm.substring(0, fnm.lastIndexOf('.'));
				fnm += "_decoded.txt";
				fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fnm), "UTF-8"));
				fw.write(decoded);
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
