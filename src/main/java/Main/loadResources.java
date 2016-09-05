package Main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by loren on 18/07/2016.
 */
public class loadResources {
	String inputReaded = "";
	loadResources(String path) {
		//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(path).getFile());
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				inputReaded += line + "\n";
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getResource(){return inputReaded;}
}
