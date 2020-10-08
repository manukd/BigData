import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class main {

    public static void main(String[] args) throws IOException {
        int compteur = 0;
        ArrayList<String[]> best20 = new ArrayList<>();
        ArrayList<String[]> worse20 = new ArrayList<>();
        HashMap<Integer, String[]> map = new HashMap<Integer, String[]>();
        try
        {
            // Le fichier d'entrée
            File file = new File("wikirank-fr.tsv");
            // Créer l'objet File Reader
            FileReader fr = new FileReader(file);  
            // Créer l'objet BufferedReader        
            BufferedReader br = new BufferedReader(fr);  
            StringBuffer sb = new StringBuffer();    
            String line;
            int allLine = 0;
            while((line = br.readLine()) != null)
            {
                allLine++;
                String[] lineItems = line.split("\t");
                boolean verif = true;
                try {
                    // Verification ID
                    int id = Integer.parseInt(lineItems[0]);
                    if(id > 0 && map.containsKey(id)) {
                        verif = false;
                    }
                    // Verification score
                    float score = Float.parseFloat(lineItems[2]);
                    if(score < 0 || score > 100) {
                        verif = false;
                    }
                    //Verification popularite
                    int popularite = Integer.parseInt(lineItems[3]);
                    if(popularite < 0) {
                        verif = false;
                    }
                    //verification interet autheur
                    int auth_interest = Integer.parseInt(lineItems[4]);
                    if(auth_interest < 0) {
                        verif = false;
                    }
                }
                catch(NumberFormatException e) {
                    verif = false;
                }
                if(verif)
                {
                    map.put(Integer.parseInt(lineItems[0]), lineItems);
                    if (best20.size() < 20) {
                        best20.add(lineItems);
                    }
                    else if (Integer.parseInt(lineItems[3]) > Integer.parseInt(best20.get(19)[3])) {
                        best20.remove(best20.get(19));
                        best20.add(lineItems);
                    }

                    Collections.sort(best20, new Comparator<String[]>() {
                        @Override
                        public int compare(String[] o1, String[] o2) {
                            return Integer.compare(Integer.parseInt(o2[3]), Integer.parseInt(o1[3]));
                        }
                    });

                    if (worse20.size() < 20) {
                        worse20.add(lineItems);
                    }
                    else if (Integer.parseInt(lineItems[3]) < Integer.parseInt(worse20.get(19)[3])) {
                        worse20.remove(worse20.get(19));
                        worse20.add(lineItems);
                    }

                    Collections.sort(worse20, new Comparator<String[]>() {
                        @Override
                        public int compare(String[] o1, String[] o2) {
                            return Integer.compare(Integer.parseInt(o1[3]), Integer.parseInt(o2[3]));
                        }
                    });

                }
                else 
                {
                    compteur++;
                }
            }
            fr.close();
            System.out.println("Lignes : " + (allLine - compteur));

            System.out.println("\nLes 20 éléments les plus populaires : ");
            for (String[] i : best20) {
                System.out.println(i[1]);
            }
            System.out.println("\nLes 20 éléments les moins populaires : ");
            for (String[] i : worse20) {
                System.out.println(i[1]);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}