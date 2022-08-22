import java.io.FileWriter;
import java.io.IOException;

public class Data implements Block {
    
    // DATA
    private int BACK;
    private int FRWD;
    private byte[] data = new byte[504];
    
    // DATA LIMIT
    private int DATALIMIT = 504;
    
    // Seeker + Used.
    private int position;
    private int used;
    
    // Name of file.
    private String name;
    
    // Constructor
    public Data(String n) {
        for (int i = 0; i < DATALIMIT; i++) {
            data[i] = 3;
        }
        BACK = 0;
        FRWD = 0;
        
        position = 0;
        used = 0;
        
        name = n;
    }
    
    // Setters / Getters
    
    public int getBack() {
        return BACK;
    }
    
    public int getFrwd() {
        return FRWD;
    }
    
    public int getPosition() {
        return position;
    }
    
    public int getUsed() {
        return used;
    }
    
    public String getName() {
        return name;
    }
    
    public void setBack(int i) {
        BACK = i;
    }
    
    public void setFrwd(int i) {
        FRWD = i;
    }
    
    public void setPosition(int i) {
        position = i;
    }
    
    public void setUsed(int i) {
        used = i;
    }
    
    public void setName(String n) {
        name = n;
    }
    
    // Reads data and outputs it.
    public String read(int n) {
        
        String combined = "";
        
        int i = 0;
        
        while (i != n) {
            if (position == DATALIMIT || (byte)data[i] == 3) {
                combined += "\nEnd of file reached.";
                break;
            }
            combined += (char)data[position];
            
            position++;
            i++;
        }
        return combined;
    }
    
    // Writes to data.
    public void write(int n, String input) {
        input = input.substring(1, input.length()-1);
        char[] input_array = input.toCharArray();
        
        int i = 0;
        
        // Number of Max Bytes Reached
        while (i != n) {
            // Max end of file reached.
            if (position == DATALIMIT) {
                System.out.println("End of file reached. No more data can be written.");
                break;
            }
                
            // Writing
            else if (i >= input_array.length)
                data[position] = 32;
            else
                data[position] = (byte)input_array[i];
                
            position++;
            i++;
            
            if (position > used)
                used++;
        }
        System.out.println("Data has been written.");
    }
    
    // Sets position.
    public void seek(int option, int offset) {
        
        // Offset too high error checking.
        if (offset >= DATALIMIT) {
            System.out.println("Error: Offset is too high. Pointer is unchanged.");
            return;
        }
        
        if (option == -1) {
            // Error Checking
            if (outOfRangeCheck(0, offset))
                return;
            
            setPosition(0 + offset);
        }
        else if (option == 0) {
            // Error Checking
            if (outOfRangeCheck(position, offset))
                return;
            
            setPosition(position + offset);
        }
        else if (option == 1) {
            // Error Checking
            if (outOfRangeCheck(getUsed(), offset))
                return;
                
            setPosition(getUsed() + offset);
        }
    }
    
    // Evaulates seek function to see if valid.
    private boolean outOfRangeCheck(int position, int offset) {
        if ((position + offset) < 0) {
            System.out.println("Error: Offset makes pointer out of range. Pointer is unchanged.");
            return true;
        }
        if ((position + offset) >= DATALIMIT){
            System.out.println("Error: Offset makes pointer out of range. Pointer is unchanged.");
            return true;
        }
        return false;
    }
    
    // Block saving for reuse. Not sure if needed.
    public void writeToDisk(int blockNum) {
        try {  
            FileWriter myWriter = new FileWriter(blockNum + ".txt");
            
            String combined = "";
            
            combined += (String.format("%04d", BACK)) + (String.format("%04d", FRWD));
            for (byte character : data) {
                combined += (char) character;
            }
            
            myWriter.write(combined);
            myWriter.close();
            
            System.out.println("Sucessfully wrote to the file.");
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}