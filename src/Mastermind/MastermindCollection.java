package Mastermind;

import java.util.*;

public class MastermindCollection {

    private final int nbTry = 10;
    private final int nbColors = 6;
    private final int codeSize = 4;


    private Random rand;
    private final Scanner scanner;

    private int turn = 0;
    private ArrayList<String> submissions;
    private ArrayList<String> codeToBreak;


    private  int wellPlaced = 0;
    private  int misPlaced = 0;
    private boolean won = false;

    private ArrayList<String> generateCode() {
        ArrayList<String> code = new ArrayList<>(codeSize);
        for (int i = 0; i<codeSize; i++){

            int r = rand.nextInt(nbColors);
            code.add(i, getColorFromInt(r));
        }
        return code;
    }

    private String getColorFromInt(int r) {
        String toReturn = "";
        switch (r){
            case 0:
                toReturn = "Bleu";
                break;
            case 1:
                toReturn = "Rouge";
                break;
            case 2:
                toReturn = "Jaune";
                break;
            case 3:
                toReturn = "Violet";
                break;
            case 4:
                toReturn = "Turquoise";
                break;
            case 5:
                toReturn = "Grenat";
            default:
                break;
        }
        return toReturn;
    }

    public MastermindCollection(Scanner scanner){
        this.scanner = scanner;
        rand = new Random();
        codeToBreak = generateCode();
    }

    public MastermindCollection(){
        this.scanner = new Scanner(System.in);
        rand = new Random();
        codeToBreak = generateCode();
        submissions = new ArrayList<>(codeSize);
    }

    public void play(){

        System.out.println("BIENVENU !\n Dans mon Mastermind, vous avez "+ nbTry+ " tours pour me vaincre");
        while ((turn < nbTry) && (!won)){

            askSubmission();
            printSubmission();
            compareSubmission();

            if (wellPlaced == codeSize)
                won = true;
            else
                System.out.println("Il y a " +wellPlaced + " couleurs bien placées et "+ misPlaced + " couleurs mal placées");

            turn ++;
        }
        if (won)
            System.out.println("Victoire");
        else
            System.out.println("Better luck next time");

    }

    private void compareSubmission() {
        List<String> toBreak;
        toBreak = (List<String>)codeToBreak.clone();

        wellPlaced = misPlaced = 0;

        for (int i = 0; i< codeSize; i++){
            if (codeToBreak.get(i).equalsIgnoreCase(submissions.get(i))){
                toBreak.remove(codeToBreak.get(i));
                wellPlaced++;
            }
        }

        for (String s : submissions){
            ListIterator<String> li = toBreak.listIterator();
            boolean found = false;

            while (li.hasNext() && !found){
                String tmp = li.next();
                if (tmp.equalsIgnoreCase(s)){
                    li.remove();
                    misPlaced++;
                    found = true;
                }
            }
        }

    }

    private void printSubmission() {
        System.out.println();
        for (String s: submissions) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void askSubmission() {
        System.out.println("entre 4 couleur\nCouleurs disponibles : Bleu, Rouge, Jaune, Violet, Turquoise, Grenat");
        submissions.clear();

        for (int i = 0; i < codeSize; i++)
            submissions.add(i, scanner.next());
    }

}
