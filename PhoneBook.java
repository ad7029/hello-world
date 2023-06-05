package hashmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class PhoneBook {

	private static Map<String, String> phoneBook = new HashMap<>();

	public static void main(String[] args) {
		loadPhoneBook();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("1. 查詢電話號碼");
			System.out.println("2. 添加電話號碼");
			System.out.println("3. 列出所有電話號碼");
			System.out.println("4. 刪除電話號碼");
			System.out.println("5. 退出");
			System.out.print("請輸入您的選擇：");

			int choice = scanner.nextInt();
			scanner.nextLine(); // 消耗掉多餘的換行符

			switch (choice) {
			case 1:
				queryPhoneNumber(scanner);
				break;
			case 2:
				addPhoneNumber(scanner);
				break;
			case 3:
				listAllPhoneNumbers();
				break;
			case 4:
				deletePhoneNumber(scanner);
				break;
			case 5:
				savePhoneBook();
				System.exit(0);
				break;
			default:
				System.out.println("無效的選擇");
				break;
			}
		}
	}

	private static void queryPhoneNumber(Scanner scanner) {
		System.out.print("請輸入姓名：");
		String name = scanner.nextLine();
		if (phoneBook.containsKey(name)) {
			System.out.println("電話號碼為：" + phoneBook.get(name));
		} else {
			System.out.println("找不到此人的電話號碼");
		}
	}

	private static void addPhoneNumber(Scanner scanner) {
		System.out.print("請輸入姓名：");
		String name = scanner.nextLine();
		System.out.print("請輸入電話號碼：");
		String phoneNumber = scanner.nextLine();
		phoneBook.put(name, phoneNumber);
		System.out.println("電話號碼已添加");
	}
	
	private static void deletePhoneNumber(Scanner scanner) {
		System.out.print("請輸入刪除對象:");
		String name = scanner.nextLine();
		if(phoneBook.containsKey(name)) {
			phoneBook.remove(name);
			System.out.println("電話號碼已刪除");
		}else {
			System.out.println("查無此人");
		}
	}


	private static void listAllPhoneNumbers() {
		sortPhoneBook();
		if (phoneBook.isEmpty()) {
			System.out.println("電話簿是空的");
		} else {
			for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
		}
	}
	
	private static void sortPhoneBook() {
	    List<Map.Entry<String, String>> entries = new ArrayList<>(phoneBook.entrySet());
	    Collections.sort(entries, new Comparator<Map.Entry<String, String>>(){
	        @Override
	        public int compare(Map.Entry<String, String> entry1, Map.Entry<String, String> entry2) {
	            return entry1.getValue().compareTo(entry2.getValue());
	        }
	    });
	    phoneBook = new LinkedHashMap<>();
	    int i = 1;
	    for (Map.Entry<String, String> entry : entries) {
	        phoneBook.put(i + ". " + entry.getKey(), entry.getValue());
	        i++;
	    }   
	}

	private static void loadPhoneBook() {
		try {
			File file = new File("Phone.txt");
			if (!file.exists()) {
				return;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader (new FileInputStream(file), "Big5"));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(":");
				if (parts.length == 2) {
					phoneBook.put(parts[0], parts[1]);
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Failed to read from file: " + e.getMessage());
		}
	}

	private static void savePhoneBook() {
		try {
			File file = new File("Phone.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "Big5")); 
			for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
				writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
			}
			writer.close();// 關閉資源
		} catch (IOException e) {
			System.out.println("Failed to read from file: " + e.getMessage());
		}
	}
}

//public class PhoneBook {
//    private static HashMap<String, String> phoneBook = new HashMap<String, String>();
//
//    public static void main(String[] args) {
//        readFromFile("Phone.txt");
//        System.out.println(phoneBook);
//
//        manualInput();
//
//        writeToFile("Phone.txt");
//    }
//
//    private static void readFromFile(String fileName) {
//        try {
//            File file = new File(fileName);
//            Scanner scanner = new Scanner(file);
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine(); 
//                String[] parts = line.split(",", 2);
//                if (parts.length >= 2) {
//                    String name = parts[0];
//                    String number = parts[1];
//                    phoneBook.put(name, number);
//                }
//            }
//            scanner.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found: " + fileName);
//        }
//    }
//
//    private static void writeToFile(String fileName) {
//        try {
//            FileWriter writer = new FileWriter(fileName);
//            for (String name : phoneBook.keySet()) {
//                String number = phoneBook.get(name);
//                writer.write(name + "," + number + "\n");
//            }
//            writer.close();
//        } catch (IOException e) {
//            System.out.println("An error occurred while writing to file.");
//            e.printStackTrace();
//        }
//    }
//
//    private static void manualInput() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter the number of entries you want to add:");
//        int numEntries = Integer.parseInt(scanner.nextLine());
//        for (int i = 0; i < numEntries; i++) {
//            System.out.println("Entry " + (i + 1) + ":");
//            System.out.println("Name:");
//            String name = scanner.nextLine();
//            System.out.println("Phone number:");
//            String phoneNumber = scanner.nextLine();
//            phoneBook.put(name, phoneNumber); 
//        }
//    }
//}
