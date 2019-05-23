package dataL;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import businessL.Restaurant;

public class RestaurantSerializator {

	public static Restaurant readFromFile() {
		Restaurant restaurant = null;
		FileInputStream fi;
		try {
			fi = new FileInputStream(
					"E:\\Facultate - UTCN\\An 2 - Sem 2\\TP\\HWs\\pt2019_30224_marc_andrei_assignment_4\\items.txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			restaurant = (Restaurant) oi.readObject();
			oi.close();
			fi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return restaurant;
	}
}
