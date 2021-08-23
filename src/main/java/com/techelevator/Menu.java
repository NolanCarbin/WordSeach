package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

    private PrintWriter out;
    private Scanner in;

    public Menu(InputStream input, OutputStream output){
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    public String getChoiceFromOptions(String[] options){
        String choice = null;
        while (choice == null){
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private String getChoiceFromUserInput(String[] options){
        String choice = null;
        String userInput = in.nextLine();
        try{
            int selectedOption = Integer.parseInt(userInput);
            if (selectedOption > 0 && selectedOption <= options.length){
                choice = options[selectedOption - 1];
            }

        } catch (NumberFormatException exception) {
            //eat the exception and print the message bellow since choice will be null
        }
        if (choice == null){
            out.println("\n"+ "*** " + userInput + " is not a valid option ***\n");
        }
        return choice;
    }

    private void displayMenuOptions(String[] options){
        System.out.println("\nOPTIONS:");
        for(int i = 0; i < options.length; i++){
            int optionNumber = i + 1;
            out.println(String.format("(%d) %s",optionNumber, options[i]));
        }
        out.print(System.lineSeparator() + "Please select an options: ");
        out.flush();
    }

}
