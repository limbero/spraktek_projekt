/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class ClosestWords {

    int maxdist = 10;
    LinkedList<String>[] closestWords = new LinkedList[maxdist];

    int[][] mymatrix;
    int closestDistance = 40;

    String laststring = "";
    int lastdist = 41;

    int Distance(String w1, String w2, int offset) {
        int w1len = w1.length();
        int w2len = w2.length();

        //int[][] tempmat = mymatrix;

        if(w1.equals(w2)){
            return 0;
        }
        else if (Math.abs(w1len-w2len) > closestDistance) {
            return 41;
        }
        /*else if (offset+lastdist-w2len > closestDistance) {
            return 41;
        }*/

        offset++;

        for (int i=offset; i<=w2len; i++) {
            /*boolean breakflag=true;*/
            for (int j=1; j<=w1len; j++) {
                int up = mymatrix[i][j-1];
                int diag = mymatrix[i-1][j-1];
                int left = mymatrix[i-1][j];
                if(w2.charAt(i-1) == w1.charAt(j-1)){
                    mymatrix[i][j] = diag;
                }
                else {
                    mymatrix[i][j] = Math.min(Math.min(up, diag), left)+1;
                }
                /*if(tempmat[i][j]<=closestDistance){
                    breakflag=false;
                }*/
            }
            /*if(breakflag){
                return 41;
            }*/
        }

        //mymatrix = tempmat;
        lastdist = mymatrix[w2len][w1len];
        laststring = w2;
        return mymatrix[w2len][w1len];
    }


    public ClosestWords(String w, List<String> wordList) {

        mymatrix = new int[41][41];
        for (int i=0; i<=40; i++) {
            mymatrix[i][0] = i;
        }
        for (int i=1; i<=40; i++) {
            mymatrix[0][i] = i;
        }


        for (int i = 0; i < maxdist; i++)
            closestWords[i] = new LinkedList<String>();

        for (String s : wordList) {

            int p=0;

            if (!s.equals("") && !laststring.equals("")) {
                while(p < s.length() && p < laststring.length()) {
                    if (s.charAt(p) == laststring.charAt(p)) {
                        p++;
                    }
                    else
                        break;
                }
            }

            int dist = Distance(w, s, p);

            //System.out.println("d(" + w + "," + s + ")=" + dist);
            /*if (dist < closestDistance) {
                closestDistance = dist;
                closestWords = new LinkedList<String>();
                closestWords.add(s);
            }
            else if (dist == closestDistance) {
                closestWords.add(s);
            }*/
            if (dist < maxdist) {
                closestWords[dist].add(s);
            }
        }
    }

    int getMinDistance() {
        return closestDistance;
    }

    List<String> getClosestWords() {
        LinkedList<String> listToReturn = new LinkedList<String>();
        for (int i = 0; i < maxdist; i++)
            for (String w : closestWords[i])
                listToReturn.add(w);

        return listToReturn;
    }
}
