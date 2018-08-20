package com.mMind;



import java.util.Random;
import java.util.Scanner;

public class Mastermind {
    final int nbTry;
    final int nbColors;
    final int codeSize;
    final int wellPlaced = 0;
    final int misPlaced = 1;
    final boolean colorMode;
    Random rand;
    final Scanner scanner;

    int turn = 0;
    int[][] submissions;
    int[][] placements;
    int[] codeToBreak;

    boolean won = false;

    public Mastermind(){
        nbTry = 10;
        nbColors = 6;
        codeSize = 4;
        submissions = new int[nbTry][codeSize];
        placements = new int[nbTry][2]; //Only two row, one for well
        colorMode = false;
        rand = new Random();
        scanner = new Scanner(System.in);
        codeToBreak = generateCode(); //Mais que je suis con, j'essayais de générer un aléatoire avant de générer ma graine
    }


    public Mastermind(Scanner sc){
        nbTry = 10;
        nbColors = 6;
        codeSize = 4;
        colorMode = false;
        submissions = new int[nbTry][codeSize];
        placements = new int[nbTry][2]; //Only two row, one for well
        rand = new Random();
        scanner = sc;
        codeToBreak = generateCode();
    }

    public Mastermind(boolean colorMode){
        nbTry = 10;
        nbColors = 6;
        codeSize = 4;
        submissions = new int[nbTry][codeSize];
        placements = new int[nbTry][2]; //Only two row, one for well
        this.colorMode = colorMode;
        rand = new Random();
        scanner = new Scanner(System.in);
        codeToBreak = generateCode(); //Mais que je suis con, j'essayais de générer un aléatoire avant de générer ma graine
    }

    private int[] generateCode() {
        int[] code = new int[codeSize];
        for (int i = 0; i<codeSize; i++){

            int r = rand.nextInt(nbColors);
            code[i] = r;
        }
        return code;
    }

    public void play(){

        System.out.println("BIENVENU !\n Dans mon Mastermind, vous avez "+ nbTry+ " tours pour me vaincre");
        while ((turn < nbTry) && (!won)){
            askSubmission();
            printSubmission();
            compareSubmission();

            if (placements[turn][wellPlaced] == codeSize)
                won = true;
            else
                System.out.println("Il y a " + placements[turn][wellPlaced] + " couleurs bien placées et "+ placements[turn][misPlaced]+ " couleurs mal placées");

            turn ++;
        }
        if (won)
            System.out.println("Victoire");
        else
            System.out.println("Better luck next time");

    }

    private void printSubmission() {
        System.out.println("Votre Submission pour le tour " + turn + " est");
        for (int i=0; i< codeSize; i++){
            if (!colorMode)
                System.out.print(submissions[turn][i] + " ");
            else
                System.out.print(getColorString(submissions[turn][i]) + " ");
        }
        System.out.println();
    }


    //Très inélégant
    private String getColorString(int i) {
        String toReturn= "";
        for (Color color : Color.values()){
            if (i == color.id()) toReturn = color.string();
        }
        return toReturn;
    }

    private void compareSubmission() {
        int[] colorCounter = new int[nbColors]; //l'index correspond à une couleur;

        for (int i = 0; i< colorCounter.length; i++) {
            colorCounter[i] = 0;
        } // On met le compteur de chaque couleur à 0

        for (int i = 0; i < codeSize; i++) {
            colorCounter[codeToBreak[i]]++;
        }//codeToBreak[j] contient le numéro d'une couleur soit un index du tableau de compteur

        placements[turn][wellPlaced] =0;
        placements[turn][misPlaced] =0;

        for (int i = 0; i< codeSize; i++){
            if (codeToBreak[i] == submissions[turn][i]){
                placements[turn][wellPlaced]++;
                colorCounter[codeToBreak[i]] --;
            }//Si une couleur est au même emplacement on incrémente le compteur de couleur bien placés et décrément le nombre de couleur présentes
        }

        for (int i = 0; i< codeSize; i++) {
            //si il repasse sur une couleur déjà bien placé <== ERREUR
            if (colorCounter[submissions[turn][i]]>0 && (codeToBreak[i] != submissions[turn][i])){
                colorCounter[submissions[turn][i]]--;
                placements[turn][misPlaced]++;
            }
        }

    }

    private void askSubmission() {

        int i = 0;
        int submission;
        while (i< codeSize) {
            submission = (colorMode)? askStringtoInt() : askInt();
            if (submission >-1 || submission > nbColors){
                submissions[turn][i] = submission;
                i++;
            } else {
                System.out.println("Cette Proposition n'est pas valable");
            }
        }
    }

    private int askStringtoInt() {
        System.out.println("Entre une couleur parmi :");
        for (Color c : Color.values()){
            System.out.print(c.string() + " ");
        }
        System.out.println();

        String s =  scanner.next();
        s = s.toLowerCase();
        int toReturn = -1;
        for (Color c: Color.values()) {
            if (s.equals(c.string())) toReturn = c.id();
        }
        return toReturn;
    }

    private int askInt() {
        System.out.println("Entre la proposition pour la case (entre 0 et " + nbColors + ")");
        int submission = scanner.nextInt();
        if (submission > -1 && submission < nbColors) {
            return submission;
        }
        return -1;

    }
}
