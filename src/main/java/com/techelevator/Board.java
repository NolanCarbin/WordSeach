package com.techelevator;

import java.util.*;

public class Board {
    private int rows;
    private int cols;
    private List<String> words;
    private Set<String> guessedWords;
    private int correctWordsCounter;
    private List<List<String>> board;

    private static final String[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".split("");
    private static final String LETTER_HOLDER = "#";


    public Board(int size){
        this.rows = size;
        this.cols = size;
        this.words = new ArrayList<String>();
        this.guessedWords = new HashSet<String>();
        this.correctWordsCounter = 0;
        this.board = create2DBoard();
    }

    private List<List<String>> create2DBoard(){
        List<List<String>> board = new ArrayList<List<String>>();
        for(int i = 0; i < this.rows; i++){
            List<String> temp = new ArrayList<String>();
            for(int j = 0; j < this.cols; j++){
                temp.add(LETTER_HOLDER);
            }
            board.add(temp);
        }
        return board;
    }


    public void addWordsToBoard(){
        for(String word : this.words){
            word = word.toLowerCase();
            boolean wordAdded = false;
            while(!wordAdded){
                //Get a random startRow, startCol
                int randomStartRow = (int)(Math.random() * this.rows);
                int randomStartCol = (int)(Math.random() * this.cols);

                //Get a random drow, dcol (-1, 0, +1)
                int[] direction = {-1, 0, 1};
                int randomDirRow = direction[(int)(Math.random() * 3)];
                int randomDirCol = direction[(int)(Math.random() * 3)];
                wordAdded = insertWordIntoBoard(word, randomStartRow, randomStartCol, randomDirRow, randomDirCol);
                System.out.println(wordAdded);
            }
        }
        //fill the remaining "#" with random letters
        fillEmptySlots();
    }
    //Backtracking Method to add a word onto the board
    private boolean insertWordIntoBoard(String word, int row, int col, int drow, int dcol){
        if(word.length() == 0){
            //return true or indicate that the whole word was added
            return true;
        }
        //check if valid
        if(row >= 0 && row < this.rows && col >= 0 && col < this.cols){
            //get current letter
            String currentLetter = Character.toString(word.charAt(0));
            String previousLetter = this.board.get(row).get(col);
            if(currentLetter.equals(previousLetter) || previousLetter.equals(LETTER_HOLDER)){
                //if valid add the letter to the board
                this.board.get(row).set(col, currentLetter);
                //call function again with substring(1), new row, new col and set equal to solution
                boolean solution = insertWordIntoBoard(word.substring(1), row + drow, col + dcol, drow, dcol);
                if (solution){
                    return true;
                }
                //undo move by setting it back to "#" or previous letter
                this.board.get(row).set(col, previousLetter);
            }
        }
        return false;
    }

    public void fillEmptySlots(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                String letter = this.board.get(i).get(j);
                if(letter.equals(LETTER_HOLDER)){
                    int randomIndex = (int)(Math.random() * ALPHABET.length);
                    String randomLetter = ALPHABET[randomIndex];
                    this.board.get(i).set(j, randomLetter);
                }
            }
        }
    }

    public void showBoard(){
        System.out.println();
        for(int i = 1; i <= this.cols * 2 - 1; i++){
            System.out.print("* ");
        }
        System.out.println();
        for (int i = 0; i < board.size(); i++){
            for (int j = 0; j < board.get(0).size(); j++){
                System.out.print(board.get(i).get(j) + "   ");
            }
            System.out.println();
        }
        for(int i = 1; i <= this.cols * 2 - 1; i++){
            System.out.print("* ");
        }
        System.out.println();
    }

    public boolean isGameOver(){
        for(String correctWord : this.words){
            if(!this.guessedWords.contains(correctWord)){
                return false;
            }
        }
        System.out.println("\nCONGRATS YOU FOUND ALL OF THE WORDS!\n");
        return true;
    }

    public void addToGuessedWords(String guessedWord){
        if(guessedWords.contains(guessedWord)){
            System.out.println("\n"+ guessedWord + " was already guessed please try a new word.");
        } else if (this.words.contains(guessedWord)){
            System.out.println("\n" + guessedWord + " is correct!");
            this.guessedWords.add(guessedWord);
            this.correctWordsCounter++;
            System.out.println(this.correctWordsCounter + " / " + this.words.size() + " guessed!");
        } else {
            System.out.println("Sorry \"" + guessedWord + "\" is not a valid option.");
        }
    }

    public void showAllWords(){
        System.out.print("Words: " + this.words + "\n");
    }

    public void showGuessedWords(){
        System.out.println("Guessed words: " + this.guessedWords + "\n");
    }

    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public List<String> getWords() {
        return words;
    }
    public List<List<String>> getBoard(){
        return this.board;
    }

    public void setWords(List<String> words){
        this.words = words;
    }


    //    //Used to check if a word is inside of the board
//    public boolean searchForWord(String word){
//        for(int row = 0; row < rows; row++){
//            for (int col = 0; col < cols; col++){
//                String result = searchFromCell(word, row, col);
//                if (result != null){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private String searchFromCell(String word, int startRow, int startCol){
//            int[] directions = {-1, 0, +1};
//            for (int drow : directions) {
//                for (int dcol : directions) {
//                    if (!(drow == 0 && dcol == 0)) {
//                        String result = searchInDirection(word, startRow, startCol, drow, dcol);
//                        if (result != null){
//                            return result;
//                        }
//                    }
//                }
//            }
//            return null;
//    }
//
//    private String searchInDirection(String word, int startRow, int startCol, int drow, int dcol){
//        for (int i = 0; i < word.length(); i++){
//            int currentRow = startRow + (i * drow);
//            int currentCol = startCol + (i * dcol);
//            String currentLetterOfWord = Character.toString(word.charAt(i));
//            if(currentRow < 0 || currentRow >= this.rows ||
//                currentCol < 0 || currentCol >= this.cols ||
//                !(this.board.get(currentRow).get(currentCol)).equals(currentLetterOfWord)){
//                return null;
//            }
//        }
//        return word;
//    }
}
