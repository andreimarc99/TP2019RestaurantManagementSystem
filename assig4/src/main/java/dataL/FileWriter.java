package dataL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import businessL.Restaurant;

public class FileWriter {

	public static void writeToFile(Restaurant restaurant) {
		ObjectOutputStream objectOutputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(new File(
					"E:\\Facultate - UTCN\\An 2 - Sem 2\\TP\\HWs\\pt2019_30224_marc_andrei_assignment_4\\items.txt"));
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(restaurant);
			fileOutputStream.close();
			objectOutputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
}
