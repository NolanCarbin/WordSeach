package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordSearchCLI {
    private Menu menu;
    private Scanner userInput;
    private File wordsFile;

    private static final String MAIN_MENU_OPTION_1 = "8 x 8 Board";
    private static final String MAIN_MENU_OPTION_2 = "10 x 10 Board";
    private static final String MAIN_MENU_OPTION_3 = "15 x 15 Board";
    private static final String MAIN_MENU_OPTION_EXIT = "EXIT";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_1, MAIN_MENU_OPTION_2, MAIN_MENU_OPTION_3, MAIN_MENU_OPTION_EXIT};


    public WordSearchCLI(Menu menu) {
        this.menu = menu;
        this.userInput = new Scanner(System.in);
        String currentDir = System.getProperty("user.dir");
        this.wordsFile = new File(currentDir + "/words.txt");
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        WordSearchCLI cli = new WordSearchCLI(menu);
        cli.run();
    }

    public void run(){
        System.out.println("Lets do a Word Search!");
        //menu items
        while(true){
            String choice = menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if(choice.equals(MAIN_MENU_OPTION_1)){
                playWordSearch(8);
            } else if (choice.equals(MAIN_MENU_OPTION_2)){
                playWordSearch(10);
            } else if (choice.equals(MAIN_MENU_OPTION_3)){
                playWordSearch(15);
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)){
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    public void playWordSearch(int boardSize){
        Board board = new Board(boardSize);
        List<String> words = getWordsFromFile(this.wordsFile, boardSize - 2);
        if (words == null){
            //if the file isn't found getEasyWordsFromFile will return null.
            //break from method and return to menu.
            System.out.println("Make sure words.txt file is in the directory");
            return;
        }
        board.setWords(words);
        board.addWordsToBoard();
        guessWord(board);
    }

    public void guessWord(Board board){
        System.out.println("ALL WORDS ARE GREATER THEN 3 CHARACTERS");
        while(!board.isGameOver()){
            board.showBoard();
            System.out.print("\nPlease enter a word: ");
            String guess = userInput.nextLine().toLowerCase();
            if(board.getWords().contains(guess)){
                board.addToGuessedWords(guess);
            } else if (guess.equals("quit")){
                board.showAllWords();
                break;
            } else if (guess.equals("s")){
                board.showGuessedWords();
            } else {
                System.out.println("Sorry \"" + guess + "\" is not a valid word.");
            }
        }
    }

    public List<String> getWordsFromFile(File file, int maxLength){
        List<String> words = new ArrayList<String>();
        int lineNumber = 0;
        int randomNumber = (int)(1 + Math.random() * 50);
        try(Scanner fileInput = new Scanner(file)){
            while(fileInput.hasNext()){
                String line = fileInput.nextLine();
                lineNumber++;
                if(lineNumber % randomNumber == 0 && line.length() < maxLength){
                    words.add(line);
                    randomNumber = (int)(1 + Math.random() * 50);
                    if(words.size() == maxLength){
                        return words;
                    }
                }
            }
        } catch(FileNotFoundException fileNotFoundException){
            System.out.println("File was not found : " + fileNotFoundException);
        }
        return null;
    }
}
