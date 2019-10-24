import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays; 

class Rated{
    public int num;
    public String name;
    public int rate;
    Rated(int nmb, String nm, int rt){
        this.num=nmb;
        this.name=nm;
        this.rate=rt;
    }
    public String getRate(){
        return String.valueOf(this.rate);
    }
    @Override
    public String toString(){
        return "<"+this.num+">"+this.name+"("+this.rate+")";
    }
}

class Electra{

    public static void main(String[] args) {
        //Читаем альтернативы
        ArrayList<Hosting> hostlist = new ArrayList<Hosting>(10);
        fillList(hostlist);
        if(hostlist.size()!=0){
            //Читаем информацию о критериях        
            ArrayList<Critery> crts = new ArrayList<>();
            getCriteries(crts);
            if(crts.size()!=0){
                //Составляем конечный список альтернатив
                buildTable(hostlist,crts);
                System.out.println();
                show(null,crts);
                System.out.println();
                show(hostlist,null);
                System.out.println();
                double[][] table =new double[hostlist.size()][hostlist.size()];
                computeTable(hostlist,table,1);
                //System.out.println();
                buildLevels(table,hostlist);
                System.out.println("\n");
                letUseCustomThreshold(hostlist,table);
            }else{
                System.out.println("Отсутствует информация о критериях");
            }
        }else{
            System.out.println("Отсутствует информация об альтернативах");
        }
    }

    public static double readNum(){
        Scanner sc = new Scanner(System.in);
        double result = 0;
        boolean isGood = false;
        String raw = sc.nextLine();
        try{
            result=Double.parseDouble(raw);
            isGood=true;
        }catch(Exception e){}
        if(isGood==false || result<1&&result>0 || result<0){
            System.out.print("Некорректный ввод (недопустимо: порог меньше 1, посторонние символы)\nПовторите ввод: ");
            result=readNum();
        }
        return result;
    }

    public static void letUseCustomThreshold(ArrayList<Hosting> hosts,double[][] ts1){
        System.out.println("\n");
        System.out.println("Результат выше действителен для порога C=1\nДалее вы сможете использовать свое значение. Введите 0 для завершения программы.");
        System.out.println("Новое значение порога: ");
        double threshold;
        while(true){
            threshold = readNum();
            System.out.println();
            if(threshold==0){
                break;
            }else{
                double[][] table = ts1.clone();
                computeTable(hosts,table.clone(),threshold);
                buildLevels(table,hosts);
                System.out.println("\n");
                System.out.println("\n");
                System.out.println("Результат выше действителен для порога C="+threshold+"\nДалее вы сможете использовать свое значение. Введите 0 для завершения программы.");
                System.out.println("Новое значение порога: ");
            }
        }
    }
    

    public static void buildLevels(double[][] arr,ArrayList<Hosting> hosts){
        ArrayList<Rated> rates = new ArrayList<>();
        for(int i=0;i<hosts.size();i++){
            rates.add(new Rated(Integer.parseInt(hosts.get(i).id)+1,hosts.get(i).name,rateOf(arr, i)));
        }
        ArrayList<Integer> level = getLevels(rates);
        sortLevels(level);
        
        System.out.println("\nСписок ярусов будет иметь следующий формат:\n Ярус Х: <Номер>Имя альтернативы(рейтинг)\n");
        for(int i=0;i<level.size();i++){
            System.out.print("Ярус "+(i+1)+": ");
            for(int j=0;j<rates.size();j++){
                if(rates.get(j).rate==level.get(i)){
                    System.out.print(rates.get(j).toString()+", ");
                }
            }
            System.out.println();
        }
    }

    public static void sortLevels(ArrayList<Integer> lvl){
        int[] arr = new int[lvl.size()];
        for(int i=0;i<arr.length;i++){
            arr[i]=lvl.get(i);
        }
        Arrays.sort(arr);
        lvl.clear();
        for(int i=arr.length-1;i>=0;i--){
            lvl.add(arr[i]);
        }
    }

    public static ArrayList<Integer> getLevels(ArrayList<Rated> arr){
        ArrayList<Integer> values = new ArrayList<>();
        for(int i=0;i<arr.size();i++){
            if(!values.contains(arr.get(i).rate)){
                values.add(arr.get(i).rate);
            }
        }
        return values;
    }

    public static int rateOf(double[][] arr,int alt){
        int rate=0;
        int in=0;
        int out=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i][alt]!=0){
                in++;
            }
        }
        for(int i=0;i<arr.length;i++){
            if(arr[alt][i]!=0){
                out++;
            }
        }
        rate=out-in;
        return rate;
    }

    public static double round(double value, int num) {
        double scale = Math.pow(10, num);
        return Math.round(value * scale) / scale;
    }

    static void computeTable(ArrayList<Hosting> hosts,double[][] table, double min){
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[0].length;j++){
                table[i][j]=0;
            }
        }

        for(int i=0;i<hosts.size();i++){
            for(int j=0;j<hosts.size();j++){
                if(i==j){
                    continue;
                }else{
                    double value = computeIndex(hosts.get(i), hosts.get(j));
                    if(value!=Double.POSITIVE_INFINITY){
                        value=round(value,2);
                    }
                    table[i][j]= value>min? value:0;
                }
            }
        }

        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[0].length;j++){
                String toShow = "";
                if(table[i][j]==Double.POSITIVE_INFINITY){
                    toShow="INF";
                }else if(table[i][j]==0){
                    toShow="———";
                }else{
                    toShow=String.valueOf(table[i][j]);
                }
                System.out.print(toShow+"\t");
            }
            System.out.println();
        }
    }

    static double computeIndex(Hosting h1, Hosting h2){
        double score1=0;
        double score2=0;
        //Сравниваем память
        if(h1.eds>h2.eds){
            score1+=4;
        }else if(h1.eds<h2.eds){
            score2+=4;
        }
        //сравниваем количество сайтов
        if(h1.ests>h2.ests){
            score1+=2;
        }else if(h1.ests<h2.ests){
            score2+=2;
        }
        //сравниваем максимальный размер одной БД
        if(h1.edbl>h2.edbl){
            score1+=4;
        }else if(h1.edbl<h2.edbl){
            score2+=4;
        }
        //сравниваем простой хостинга
        if(h1.edtm>h2.edtm){
            score1+=3;
        }else if(h1.edtm<h2.edtm){
            score2+=3;
        }
        //сравниваем поддержку cron
        if(h1.espt>h2.espt){
            score1+=2;
        }else if(h1.espt<h2.espt){
            score2+=2;
        }
        //сравниваем цену
        if(h1.eprc>h2.eprc){
            score1+=5;
        }else if(h1.eprc<h2.eprc){
            score2+=5;
        }
        double rst=0;
        /* 
        Отбрасываем индексы согласия которые:
        •	Меньше чем 1
        •	Равны 1
        •	Представляют из себя дробь с 0 в числителе
        */
        if(score1!=0 && score2!=0){
            rst = (score1/score2)>1 ? score1/score2:0;
        }else if(score1!=0 && score2==0){
            rst = Double.POSITIVE_INFINITY;
        }
        return rst;
    }

    static void buildTable(ArrayList<Hosting> hosts, ArrayList<Critery> crts){
        for(int i = 0;i<hosts.size();i++){
            hosts.get(i).eds = Critery.getDScode(hosts.get(i).diskSpace,crts.get(0));
            hosts.get(i).ests = Critery.getSTScode(hosts.get(i).sites,crts.get(1));
            hosts.get(i).edbl = Critery.getDBLcode(hosts.get(i).DBlim,crts.get(2));
            hosts.get(i).edtm = Critery.getDTMcode(hosts.get(i).downTime,crts.get(3));
            hosts.get(i).espt = Critery.getSPTcode(hosts.get(i).support,crts.get(4));
            hosts.get(i).eprc = Critery.getPRCcode(hosts.get(i).price,crts.get(5));
        }
    }

    /*Выводим таблицу информации о критериях и альтернативы в виде: show(null,null)
    где на заменяем один из null на список для вывода - первый для списка альтернатив, второй для критериев*/
    static void show(ArrayList<Hosting> hst,ArrayList<Critery> crts){
        if(hst!=null){
            System.out.print("Название");
            System.out.print("\tДиск.прост-во");
            System.out.print("\tКол-во сайтов");
            System.out.print("\tПред.лимит БД");
            System.out.print("\tПростой серв.");
            System.out.print("\tОтвет поддерж.");
            System.out.print("\tСтоимость в мес.\n");
            for(int i = 0;i<hst.size();i++){
                hst.get(i).ewrite();
            }
        }else if(crts!=null){
            System.out.print("Критерий");
            System.out.print("\tВес");
            System.out.print("\t\tШкалы");
            System.out.print("\t\tКоды");
            System.out.println("\tСтремление");
            for(int i = 0;i<crts.size();i++){
               crts.get(i).out();
            }
        }else{System.out.println("Для указанного класса вывод не предусматривается");}
    }

    static void getCriteries(ArrayList<Critery> crts){
            
        try{
            File file = new File ("criteries.txt");
            String str;
            if(file.exists()) {
                System.out.println(file.getName() +  " exists!");
                Scanner fr = new Scanner(file);
                int line = 1;
                while(fr.hasNextLine()){
                    str = fr.nextLine();
                    if(str.indexOf("|")!=-1){
                        try{
                            crts.add(new Critery(str));
                        }catch(Exception e){
                            e.printStackTrace();
                            System.out.println("String number "+line+" contain incorrect data...Skipped...");
                            line++;
                            continue;
                        }
                    }else{
                        System.out.println("String number "+line+" is incorrect...Skipped...");
                        line++;
                        continue;
                    }
                    line++;
                }
            }
            else {
                System.out.println("The file does not exist");
            }
        }catch(Exception e){
            System.out.println("The file does not exist");
        }
    }
    
    static void fillList(ArrayList<Hosting> lst){
        try{
            File file = new File ("altlist.txt");
            String str;
            String[] splited;
            if(file.exists()) {
                System.out.println(file.getName() +  " exists!");
                Scanner fr = new Scanner(file);
                int line = 1;
                while(fr.hasNextLine()){
                    str = fr.nextLine();
                    if(str.indexOf("|")!=-1){
                        splited = str.split("[|]");
                        try{
                            int id = Integer.parseInt(splited[0]);
                            String name = splited[1];
                            int ds = Integer.parseInt(splited[2]);
                            int sts = Integer.parseInt(splited[3]);
                            double db = Double.parseDouble(splited[4]);
                            double dt = Double.parseDouble(splited[5]);
                            double sprt = Double.parseDouble(splited[6]);
                            double prc = Double.parseDouble(splited[7]);
                            lst.add(new Hosting(id,name,ds,sts,db,dt,sprt,prc));
                        }catch(Exception e){
                            e.printStackTrace();
                            System.out.println("String number "+line+" contain incorrect data...Skipped...");
                            line++;
                            continue;
                        }
                    }else{
                        System.out.println("String number "+line+" is incorrect...Skipped...");
                        line++;
                        continue;
                    }
                    line++;
                }
            }
            else {
                System.out.println("The file does not exist");
            }
        }catch(Exception e){
            System.out.println("The file does not exist");
        }
    }
}