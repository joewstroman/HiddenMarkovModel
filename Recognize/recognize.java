import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.text.*;
public class recognize {
 public static void main(String args[]) throws IOException {
   File hmm = new File("../sentence.hmm");
   File obs = new File("../example1.obs");
   Scanner scan = new Scanner(hmm);
   int N = scan.nextInt();
   int M = scan.nextInt();
   int T = scan.nextInt();
   scan.nextLine();
   String syntax = scan.nextLine();
   String[] vocab = scan.nextLine().split("\\s+");
   scan.nextLine();
   int rows = 0;
   ArrayList<ArrayList<Double>> amat = new ArrayList<ArrayList<Double>>();
   ArrayList<ArrayList<Double>> bmat = new ArrayList<ArrayList<Double>>();
   double[] pi = new double[N];
   
   while(scan.hasNextDouble()) {
     amat.add(new ArrayList<Double>());
     String line = scan.nextLine();
     int count = line.split("\\s+").length;
     Scanner newScan = new Scanner(line);
     for (int i = 0; i < count; i++) {
       amat.get(rows).add(newScan.nextDouble());
     }
     rows++;
   }
 
   scan.nextLine();
   rows = 0;
   while(scan.hasNextDouble()) {
     bmat.add(new ArrayList<Double>());
     String line = scan.nextLine();
     int count = line.split("\\s+").length;
     Scanner newScan = new Scanner(line);
     for (int i = 0; i < count; i++) {
       bmat.get(rows).add(newScan.nextDouble());
     }
     rows++;
   }
   
   scan.nextLine();
   for (int i = 0; i < N; i++) {
     pi[i] = scan.nextFloat();
   }
   
   scan = new Scanner(obs);
   int numsets = scan.nextInt();
   scan.nextLine();
   int[] numobs = new int[numsets];
   ArrayList<String[]> observ = new ArrayList<String[]>();
   for (int i = 0; i < numsets; i++) {
     numobs[i] = scan.nextInt();
     scan.nextLine();
     observ.add(scan.nextLine().split("\\s+"));
   }
   
   
   
   
   for (int h = 0; h < numsets; h++) {
     double[][] alphas = new double[N][numobs[h]];
     for (int k = 0; k < alphas.length; k++) {
       alphas[k][0] = pi[k]*vocabmatch(observ.get(h)[0], k, vocab, bmat);
     }
     for (int t = 1; t < numobs[h]; t++) {
       for (int j = 0; j < N; j++) {
         double alphabuf = 0;
         for (int i = 0; i < N; i++) {
           alphabuf += alphas[i][t-1]*amat.get(i).get(j);
         }
         alphas[j][t] = alphabuf*vocabmatch(observ.get(h)[t], j, vocab, bmat);
       }
     }
     double woo = alphas[0][numobs[h]-1] + alphas[1][numobs[h]-1] + alphas[2][numobs[h]-1] + alphas[3][numobs[h]-1];
     System.out.println(woo);
   }
 }   
 
 
 public static double vocabmatch(String obs, int state, 
                                 String[] vocab, ArrayList<ArrayList<Double>> bmat) {
   for (int i = 0; i < vocab.length; i++) {
     if (obs.equals(vocab[i])) {   
       return bmat.get(state).get(i);
     }
   }
   return 0.0;
 }
 
}