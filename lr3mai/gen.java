//Finished by NEK-RA (Ryoidenshi Aokigahara) at 8th of november 2019 in 17:25 GMT+3 (2:25 PM UTC))
/*
Инструкция:
•Запустить код
•Ввести количество необходимых матриц
•Дождаться результата

P.S.1:
Матрицы выводятся без пробелов и с разделителем |
Таким образом, результат сразу можно распарсить 

P.S.2:
Раскомментируйте все в функции solve, будет вывод для каждой сгенерированной матрицы касающихся ее по алгоритму вычислений (среднее геометрическое строки, вектор приоритетов, согласованность и т.д.)

## ВНИМАНИЕ ##
Если ты(Вы?) решил(и) запускать код сходу здесь, в Sololearn, то имейте ввиду что данный сервис ограничивает время выполнения кода 10 секундами, далее следует прерывание. Этого времени может не хватить на большое количество матриц. 50 штук успевает точно
*/
import java.util.Random;
import java.util.Scanner;
public class generator
{
    public static void main(String[] args){
        generate();
    }
    
    static void generate(){
        int countr=0;
        Scanner sc = new Scanner(System.in);
        boolean doing = true;
        int numof=sc.nextInt();
        System.out.println("GENERATING "+numof+" MATRIXS 5x5 FOR MAI");
        System.out.println();
        System.out.println();
        int done=0;
        while(doing){
            countr++;
            double[][] arr = new double[5][5];
            prepare(arr);
        
            double OS = solve(arr);
            if(OS>0.1){
            continue;
        }else{
            done++;
            for(int i=0;i<arr.length;i++){
                for(int j=0;j<arr.length-1;j++){
                    System.out.print(round(arr[i][j],2)+"|");
                if(j==arr.length-2){
                    System.out.print(round(arr[i][j+1],2));
                }
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
            if(done==numof){
                break;
            }else{
                continue;
            }
        }
        }
        System.out.println("TRIED "+countr+" TIMES");
    }

static double rand(int min, int max){
        Random rnd = new Random();
        int intgr=rnd.nextInt();
        if(intgr<0){
            intgr*=-1;
        }
         double result=(intgr%(max-min+1))+min;
         if(rnd.nextBoolean()){
             result=1.0/result;
         }
         return result;
    }

    static void prepare(double[][] a){
        a[0][0]=1;
        a[1][1]=1;
        a[2][2]=1;
        a[3][3]=1;
        a[4][4]=1;
        
        a[0][1]=rand(2,9);
        a[0][2]=rand(2,9);
        a[0][3]=rand(2,9);
        a[0][4]=rand(2,9);
        
        a[1][2]=rand(2,9);
        a[1][3]=rand(2,9);
        a[1][4]=rand(2,9);
        
        a[2][3]=rand(2,9);
        a[2][4]=rand(2,9);
        
        a[3][4]=rand(2,9);
        
        a[1][0]=1.0/a[0][1];
        a[2][0]=1.0/a[0][2];
        a[3][0]=1.0/a[0][3];
        a[4][0]=1.0/a[0][4];
        a[2][1]=1.0/a[1][2];
        a[3][1]=1.0/a[1][3];
        a[4][1]=1.0/a[1][4];
        a[3][2]=1.0/a[2][3];
        a[4][2]=1.0/a[2][4];
        a[4][3]=1.0/a[3][4];
        
    }

    static double solve(double[][] arr){
        double[] V = new double[arr.length];
        for(int i=0;i<V.length;i++){
            V[i]=geomav(i+1,arr);
        }
        /*for(int i=0;i<V.length;i++){
            System.out.println("V"+(i+1)+"="+V[i]);
        }*/
        
        double NC=0;
        for(int i=0;i<V.length;i++){
            NC+=V[i];
        }
        /*System.out.println("NC="+NC);*/
        
        double[] W = new double[V.length];
        for(int i=0;i<W.length;i++){
            W[i]=(V[i]/NC);
        }
        /*for(int i=0;i<W.length;i++){
            System.out.println("W"+(i+1)+"="+W[i]);
        }*/
        
        double[] S = new double[arr[0].length];
        for(int i=0;i<S.length;i++){
            S[i]=colsum(i+1,arr);
        }
        /*for(int i=0;i<S.length;i++){
            System.out.println("S"+(i+1)+"="+S[i]);
        }*/
        
        double[] P = new double[S.length];
        for(int i=0;i<P.length;i++){
            P[i]=S[i]*W[i];
        }
        /*for(int i=0;i<P.length;i++){
            System.out.println("P"+(i+1)+"="+P[i]);
        }*/
        double AV=0;
        for(int i=0;i<P.length;i++){
            AV+=P[i];
        }
        /*System.out.println("AV="+AV);*/
        
        double IS=(AV-arr.length)/(arr.length-1);
        
        double[] SI = new double[15];
        SI[0]=0;  SI[1]=0;  SI[2]=0.58;  SI[3]=0.90;  SI[4]=1.12;
        SI[5]=1.24;  SI[6]=1.32;  SI[7]=1.41;  SI[8]=1.45;  SI[9]=1.49;
        SI[10]=1.51;  SI[11]=1.48;  SI[12]=1.56;  SI[13]=1.57;  SI[14]=1.59;
        
        double OS=IS/SI[arr.length-1];
        return OS;
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