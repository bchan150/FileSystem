import java.util.Scanner;

public class Menu {
    
    private Directory sector;
    
    // Constructor
    public Menu() {
        sector = new Directory("ROOT");
        start();
    }
    
    // Starts the menu.
    public void start() {
        while (true) {
            System.out.println("Main Menu");
            System.out.println("---------");
            System.out.println("Commands: Create, Open, Delete");
            
            Scanner input = new Scanner(System.in);
            System.out.print("Enter Command: ");
                
            String[] command = input.nextLine().split(" ", 0);
            
            if (command[0].equals("Create")) {
                create(command[1], command[2]);
            }
            
            else if (command[0].equals("Open")) {
                open(command[1], command[2]);
            }
            
            else if (command[0].equals("Delete")) {
                delete(command[1]);
            }
            
            else
                System.out.println("Invalid command.");
        }
    }
    
    // Create command.
    
    private void create(String type, String command) {
        
        String[] search = command.split("/", 0);
            
        String name = search[search.length - 1];
        
        Directory currDir = search(search);
        
        if (currDir == null) {
            System.out.println("Directory does not exist.");
            return;
        }
        
        if (type.equals("U")) {
            Data temp = currDir.createFile(name);
            write(temp);
        }
        
        if (type.equals("D")) {
            currDir.createDir(name);
        }
    }
    
    // Open command.
    
    private void open(String mode, String command) {
        
        String[] search = command.split("/", 0);
            
        String name = search[search.length - 1];
            
        Directory currDir = search(search);
        
        if (currDir == null) {
            System.out.println("Directory does not exist.");
            return;
        }
        
        Data temp = currDir.openFile(name);
        
        if (temp == null) {
            System.out.println("File does not exist.");
            return;
        }
        
        if (mode.equals("I")) {
            read(temp);
        }
        
        if (mode.equals("O")) {
            write(temp);
        }
        else if (mode.equals("U")) {
            update(temp);
        }
    }
    
    // Delete command.

    private void delete(String command) {
        
        String[] search = command.split("/", 0);
            
        String name = search[search.length - 1];
         
        Directory currDir = search(search);
        
        if (currDir == null) {
            System.out.println("Directory does not exist.");
            return;
        }
        
        System.out.println(currDir.deleteFile(name));
    }
    
    private void read(Data file) {
        file.seek(-1, 0);
        
        while (true) {
            System.out.println("The current file is " + file.getName() + ".");
            System.out.println("---------");
            System.out.println("Commands: Read, Seek, Close");
            
            Scanner input = new Scanner(System.in);
            System.out.print("Enter Command: ");
                
            String[] command = input.nextLine().split(" ", 3);
            
            if (command[0].equals("Read")) {
                System.out.println(file.read(Integer.parseInt(command[1])));
            }
            
            else if (command[0].equals("Seek")) {
                file.seek(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
            }
            
            else if (command[0].equals("Close")) {
                break;
            }
            
            else
                System.out.println("Invalid command.");
        }
    }
    
    
    private void write(Data file) {
        file.seek(1, 0);
        
        while (true) {
            
            System.out.println("The current file is " + file.getName() + ".");
            System.out.println("---------");
            System.out.println("Commands: Write, Close");
            
            Scanner input = new Scanner(System.in);
            System.out.print("Enter Command: ");
                
            String[] command = input.nextLine().split(" ", 3);
            
            if (command[0].equals("Write")) {
                file.write(Integer.parseInt(command[1]), command[2]);
            }
            
            else if (command[0].equals("Close")) {
                break;
            }
            
            else
                System.out.println("Invalid command.");
        }
    }
    
    private void update(Data file) {
        file.seek(-1, 0);
        
        while (true) {
            System.out.println("The current file is " + file.getName() + ".");
            System.out.println("---------");
            System.out.println("Commands: Read, Write, Seek, Close");
            
            Scanner input = new Scanner(System.in);
            System.out.print("Enter Command: ");
                
            String[] command = input.nextLine().split(" ", 3);
            
            if (command[0].equals("Read")) {
                System.out.println(file.read(Integer.parseInt(command[1])));
            }
            
            else if (command[0].equals("Write")) {
                file.write(Integer.parseInt(command[1]), command[2]);
            }
            
            else if (command[0].equals("Seek")) {
                file.seek(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
            }
            
            else if (command[0].equals("Close")) {
                break;
            }
            
            else
                System.out.println("Invalid command.");
        }
    }
    
    private Directory search(String[] command) {
            
        String dir = "";
            
        for (int i = 0; i < (command.length - 1); i++) {
            dir += command[i];
            if (i < (command.length - 1))
                dir += "/";
        }
            
        return sector.findDir(dir);
    }
}