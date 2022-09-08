import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the requested
	 * file does not exist or if an error occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		hash = new HashMap<>();
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = rd.readLine();
				if (line == null)
					break;
				StringTokenizer tk = new StringTokenizer(line, " ");
				String name = tk.nextToken();
				NameSurferEntry entry = new NameSurferEntry(line);
				hash.put(name, entry);
			}
			rd.close();
		} catch (Exception e) {
			System.out.println("There was an unexpected eroor, try again :(" + e);
		}
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If the
	 * name does not appear in the database, this method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		String updatedName = updateName(name);
		if (hash.containsKey(updatedName)) {
			return hash.get(updatedName);
		}
		return null;
	}

	// * Takes in the name given in the field and returns it with starting character
	// in upper case and all of the others in the lower case.
	private String updateName(String name) {
		String updated = new String();

		updated = name.toLowerCase();
		String str = updated.substring(0, 1);
		str = str.toUpperCase();
		updated = updated.substring(1);
		updated = str + updated;
		return updated;
	}

	private HashMap<String, NameSurferEntry> hash;
}
