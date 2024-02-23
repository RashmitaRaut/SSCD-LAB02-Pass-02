import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

class SymbolTableEntry {
    private String symbol;
    private String location;

    public SymbolTableEntry(String symbol, String location) {
        this.symbol = symbol;
        this.location = location;
    }

    public String getSno() {
        return symbol;
    }

    public String getLocation() {
        return location;
    }
    @Override
    public String toString() {
        return "SymbolTableEntry{" +
                "sno='" + symbol + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

public class SecondPass {
    private static Map<String, SymbolTableEntry> symbolTable;
    private static Map<String, SymbolTableEntry> readSymbolTable(String filePath) throws IOException {
        Map<String, SymbolTableEntry> symbolTableMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;


            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                String sno =tokenizer.nextToken();
                String symbol = tokenizer.nextToken();
                String location = tokenizer.nextToken();

                symbolTableMap.put(sno, new SymbolTableEntry(symbol, location));
            }
        }

        System.out.println("Symbol Table:");
        System.out.println(String.format("%-5s %-10s %-10s", "Sno", "Symbol", "Address"));
        System.out.println(String.format("%-5s %-10s %-10s", "------", "------", "--------"));
        for (Map.Entry<String, SymbolTableEntry> entry : symbolTableMap.entrySet()) {
            System.out.println(String.format("%-5s %-10s %-10s%n", entry.getValue().getSno(), entry.getKey(), entry.getValue().getLocation()));
        }

        return symbolTableMap;
    }

    public static void main(String[] args) throws IOException {
        symbolTable = new HashMap<>();
        String pass1FilePath = "C:\\Users\\Rashmita Raut\\Desktop\\Lab02 Pass2\\pass1.txt";
        String symtabFilePath = "C:\\Users\\Rashmita Raut\\Desktop\\Lab 02 Pass2\\symbtab.txt";

        Map<String, SymbolTableEntry> symbolTableMap = readSymbolTable(symtabFilePath);
        BufferedReader reader = new BufferedReader(new FileReader(pass1FilePath));

        int locationCounter = 0;
        String line;
        System.out.println("Second Pass");

        while ((line = reader.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(line, " ,()");
            StringBuilder outputLine = new StringBuilder("");
            int columnNo = 0;
            boolean printLC = true;

            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
              
                if (token.equals("AD")) {

                    break;
                }
               
                else if (token.equals("IS")) {
                    outputLine.append(tokenizer.nextToken()).append(" ");

                    continue;
                }
                else if(token.equals("DL")){
                    String token1 = tokenizer.nextToken();
                    if (token1.equals("01")){
                        String token2 = tokenizer.nextToken();
                        outputLine.append(" ").append(token2);

                    }
                    else {
                        String token2 = tokenizer.nextToken();
                        int Lines = Integer.parseInt(token2), w = 1;
                        while (w < Lines){
                            outputLine.append( "\n----------");
                            w++;
                        }


                    }


                    break;
                }

                else if (token.equals("S")) {
                    
                    if (tokenizer.hasMoreTokens()) {
                        String symbol = tokenizer.nextToken();

                        if (symbolTableMap.containsKey(symbol)) {
                            outputLine.append(symbolTableMap.get(symbol).getLocation()).append(" ");
                        }
                    }
                   
                }




                else {
                    outputLine.append(" ").append(token).append(" ");
                }
                columnNo++;
            }

            System.out.println(outputLine.toString().trim());
        }

        reader.close();
    }



}