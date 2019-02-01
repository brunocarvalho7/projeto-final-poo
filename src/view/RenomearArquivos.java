package view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RenomearArquivos {

	public static void main(String[] args) {
		
		try {
			Files.list(Paths.get("D:\\Backup Kamila\\noioiio\\Pictures\\Fotos Kamila")).forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub

	}

}
