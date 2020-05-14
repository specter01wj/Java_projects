/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab17_4_copyfiles;

import java.io.*;

/**
 *
 * @author james.wang
 */

// Copy
public class Lab17_4_copyFiles {

    /**
     * @param args[0] for sourcefile 
     * @param args[1] for target file
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        if (args.length != 2) { 
            System.out.println(
              "Usage: java Copy sourceFile targetfile");
            System.exit(1);
        }
        
        File sourceFile = new File(args[0]);
        if (!sourceFile.exists()) {
           System.out.println("Source file " + args[0] 
             + " does not exist");
           System.exit(2);
        }
        
        File targetFile = new File(args[1]);
        if (targetFile.exists()) {
          System.out.println("Target file " + args[1] 
            + " already exists");
          System.exit(3);
        }
        
        try (
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(sourceFile));
            
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(targetFile));
        ) {
            int r, numberOfBytesCopied = 0;
            while ((r = input.read()) != -1) {
              output.write((byte)r);
              numberOfBytesCopied++;
            }
            
            System.out.println(numberOfBytesCopied + " bytes copied");
        }
        
    
    }
    
}
