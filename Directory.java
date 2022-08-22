public class Directory implements Block {
    
    private int FRWD;
    private int BACK;
    private byte FREE;
    private byte[] FILL;
    
    private String name;
    
    private Block[] sector;
    private int SECTORLIMIT = 10;
    private int[] deleted;
    
    private int counter;
    
    public Directory(String n) {
        sector = new Block[SECTORLIMIT];
        counter = 0;
        name = n;
    }
    
    // Setters and Getters
    public void setFrwd(int i) {
        FRWD = i;
    }
    
    public void setBack(int i) {
        BACK = i;
    }
    
    public void setFree(byte b) {
        FREE = b;
    }
    
    public void setFill(byte[] b) {
        FILL = b;
    }
    
    public void setName(String n) {
        name = n;
    }
    
    public String getName() {
        return name;
    }
    
    // Functionality
    public Data createFile(String name) {
        if (openFile(name) != null) {
            deleteFile(name);
            System.out.println("File recreated.");
        }
        sector[counter++] = new Data(name);
        return (Data)sector[counter-1];
    }
    
    public Data openFile(String name) {
        for (int i = 0; i < counter; i++) {
            if (sector[i] instanceof Data) {
                Data temp = (Data)sector[i];
                if (temp.getName().equals(name))
                    return (temp);
            }
        }
        return null;
    }
    
    public String deleteFile(String name) {
        int index = -1;
        
        for (int i = 0; i < counter; i++) {
            if (sector[i] instanceof Data) {
                Data temp = (Data)sector[i];
                if (temp.getName().equals(name))
                    index = i;
                    break;
            }
        }
        
        if (index == -1) {
            return "File does not exist.";
        }
        
        if (index == SECTORLIMIT - 1)
            sector[index] = null;
        else {
            for (int i = index; i < SECTORLIMIT - 1; i++) 
                sector[i] = sector[i+1];
        }
        counter--;
        return "File deleted.";
    }
    
    public void createDir(String name) {
        sector[counter++] = new Directory(name);
        System.out.println("Directory created.");
    }
    
    public Directory findDir(String name) {
        
        String[] command = name.split("/", 2);
        
        if (command.length != 1) {
            for (int i = 0; i < counter; i++) {
                if (sector[i] instanceof Directory) {
                    Directory temp = (Directory)sector[i];
                    if (temp.getName().equals(command[0]))
                        return temp.findDir(command[1]);
                }
            }
            return null;
        }
        return this;
    }
}