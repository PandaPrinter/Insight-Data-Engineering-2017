// example of program that detects suspicious transactions
// fraud detection algorithm

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;

import com.opencsv.*;

public class Antifraud {
    
    public static void main(String[] args) {
        
        String batch_payment = args[0];
        String stream_payment = args[1];
        String output_feature_one = args[2];
        String output_feature_two = args[3];
        String output_feature_three = args[4];
        
        CSVReader reader = null;
        try {
            String[] line;
            Graph g = new Graph();
            
            PrintWriter w1 = new PrintWriter(output_feature_one, "UTF-8");
            PrintWriter w2 = new PrintWriter(output_feature_two, "UTF-8");
            PrintWriter w3 = new PrintWriter(output_feature_three, "UTF-8");
            
            // Parse batch payment file
            reader = new CSVReader(new FileReader(batch_payment));
            line = reader.readNext();
            while ((line = reader.readNext()) != null) {
                if (line.length < 3)
                    continue;
                String userId1 = line[1];
                String userId2 = line[2];
                g.addEdge(userId1, userId2);
            }
            
            // Parse stream payment file
            reader = new CSVReader(new FileReader(stream_payment));
            line = reader.readNext();
            boolean oneDegree = false, twoDegree = false;
            while ((line = reader.readNext()) != null) {
                if (line.length < 3)
                    continue;
                String userId1 = line[1];
                String userId2 = line[2];
                if (g.isInGraph(userId1, userId2)) {
                    // Check feature 1
                    if (g.checkFirstDegree(userId1, userId2)) {
                        oneDegree = true;
                        w1.println("trusted");
                    } else {
                        w1.println("unverified");
                    }
                    
                    // Check feature 2
                    if (oneDegree || g.checkSecondDegree(userId1, userId2)) {
                        twoDegree = true;
                        w2.println("trusted");
                    } else {
                        w2.println("unverified");
                    }
                    
                    // Check feature 3
                    if (twoDegree || g.checkFourthDegree(userId1, userId2)) {
                        w3.println("trusted");
                    } else {
                        w3.println("unverified");
                    }
                } else {
                    w1.println("unverified");
                    w2.println("unverified");
                    w3.println("unverified");
                }
                
                g.addEdge(userId1, userId2);
                oneDegree = false;
                twoDegree = true;
            }
            
            w1.close();
            w2.close();
            w3.close();
            reader.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
