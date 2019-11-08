import java.util.Random;
import java.io.File;
import java.util.Scanner;

public class tprlr3
{

    static void prepare(int n,double[][] a){
        String path=String.valueOf(n)+".txt";
        File matr=new File(path);
        try{
            if(matr.exists()){
                Scanner read = new Scanner(matr);
                int line = 0;
                while(read.hasNextLine()){
                    String str = read.nextLine();
                    String[] nums = str.split("[|]");
                    for(int i=0;i<a.length;i++){
                        a[line][i]=Double.parseDouble(nums[i]);
                    }
                    line++;
                }
            }else{
                System.out.println("FILE NOT FOUND");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        double[][] krt = new double[6][6];
        prepare(0,krt);
        for(int i=0;i<krt.length;i++){
            for(int j=0;j<krt.length;j++){
                System.out.print("  ");
                System.out.print(round(krt[i][j],2)+"\t");
            }
        }
        String[] wpg = solve(krt).split(";");
        
        System.out.println();

        String[][] wpk = new String[6][5];

        System.out.println();
        wpk[0] = getWP(1).split(";");
        
        System.out.println();
        wpk[1] = getWP(2).split(";");

        System.out.println();
        wpk[2] = getWP(3).split(";");

        System.out.println();
        wpk[3] = getWP(4).split(";");

        System.out.println();
        wpk[4] = getWP(5).split(";");

        System.out.println();
        wpk[5] = getWP(6).split(";");

        System.out.println();
        System.out.println();
        double[] altrate = new double[wpk[0].length];
        for(int i=0;i<altrate.length;i++){
            altrate[i]=0;
        }

        for(int j=0;j<altrate.length;j++){
            for(int i=0;i<wpg.length;i++){
                altrate[j]+=Double.parseDouble(wpg[i])*Double.parseDouble(wpk[i][j]);
            }
        }

        for(int i=0;i<altrate.length;i++){
            System.out.println("Приоритет альтернативы №"+(i+1)+": "+altrate[i]);
        }
        System.out.println();
        
        int bst=armax(altrate);
        System.out.println("Лучшая альтернатива: №"+(bst+1)+" с приоритетом "+altrate[bst]);

        System.out.println();
    }

    static int armax(double[] arr){
        double max=0;
        int ndx=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>max){
                max=arr[i];
                ndx=i;
            }
        }
        return ndx;
    }

    static String getWP(int num){
        double[][] arr = new double[5][5];
        prepare(num,arr);
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr.length;j++){
                System.out.print("  ");
                System.out.print(round(arr[i][j],2)+"\t");
            }
        }
        return solve(arr);
    }

    static String solve(double[][] arr){
        double[] V = new double[arr.length];
        for(int i=0;i<V.length;i++){
            V[i]=geomav(i+1,arr);
        }
        System.out.println("Среднее геометрическое строк: ");
        for(int i=0;i<V.length;i++){
            System.out.println("V"+(i+1)+"="+V[i]);
        }
        System.out.println();
        double NC=0;
        for(int i=0;i<V.length;i++){
            NC+=V[i];
        }
        System.out.println("Коэффициент нормализации: "+NC+"\n");
        
        double[] W = new double[V.length];
        for(int i=0;i<W.length;i++){
            W[i]=(V[i]/NC);
        }
        System.out.println("Важность приоритетов: ");
        for(int i=0;i<W.length;i++){
            System.out.println("W"+(i+1)+"="+W[i]);
        }
        
        String wp = "";
        for(int i=0;i<arr.length-1;i++){
            wp+=W[i]+";";
        }
        wp+=W[arr.length-1];
        System.out.println("\nВектор приоритетов: ("+wp+")");

        System.out.println("\nСуммы столбцов: ");
        double[] S = new double[arr[0].length];
        for(int i=0;i<S.length;i++){
            S[i]=colsum(i+1,arr);
        }
        for(int i=0;i<S.length;i++){
            System.out.println("S"+(i+1)+"="+S[i]);
        }
        
        double[] P = new double[S.length];
        for(int i=0;i<P.length;i++){
            P[i]=S[i]*W[i];
        }
        for(int i=0;i<P.length;i++){
            System.out.println("P"+(i+1)+"="+P[i]);
        }
        double AV=0;
        for(int i=0;i<P.length;i++){
            AV+=P[i];
        }
        System.out.println("Среднее максимальное значение: "+AV);
        
        double IS=(AV-arr.length)/(arr.length-1);
        System.out.println("Индекс согласованности: "+IS);
        
        double[] SI = new double[15];
        SI[0]=0;  SI[1]=0;  SI[2]=0.58;  SI[3]=0.90;  SI[4]=1.12;
        SI[5]=1.24;  SI[6]=1.32;  SI[7]=1.41;  SI[8]=1.45;  SI[9]=1.49;
        SI[10]=1.51;  SI[11]=1.48;  SI[12]=1.56;  SI[13]=1.57;  SI[14]=1.59;
        
        double OS=IS/SI[arr.length-1];
        System.out.println("Отношение согласованности: "+OS);
        return wp;
    }
    
    static double colsum(int n,double[][] arr){
        n--;
        double sum=0;
        for(int i=0;i<arr.length;i++){
            sum+=arr[i][n];
        }
        return sum;
    }
    
    static double geomav(int n,double[][] arr){
        n--;
        double sum=1;
        for(int i=0;i<arr.length;i++){
            sum*=arr[n][i];
        }
        return croot(sum,arr.length);
    }
    //A - number, i.e. 81; N - n'th root, i.e. 4
    static double croot(double A, int N) { 
        // This code is contributed by Anant Agarwal.
        //Used link is: https://www.geeksforgeeks.org/n-th-root-number/
        // intially guessing a random number between 
        // 0 and 9 
        double xPre = Math.random() % 10; 
      
        // smaller eps, denotes more accuracy 
        double eps = 0.001; 
      
        // initializing difference between two 
        // roots by INT_MAX 
        double delX = 2147483647; 
      
        // xK denotes current value of x 
        double xK = 0.0; 
      
        // loop untill we reach desired accuracy 
        while (delX > eps) 
        { 
            // calculating current value from previous 
            // value by newton's method 
            xK = ((N - 1.0) * xPre + 
            (double)A / Math.pow(xPre, N - 1)) / (double)N; 
            delX = Math.abs(xK - xPre); 
            xPre = xK; 
        } 
      
        return xK; 
    }
    
    public static double round(double value, int num) {
        double scale = Math.pow(10, num);
        return Math.round(value * scale) / scale;
    }
}





