import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class parettoOptimizaciya
{
    static inputChecker chk = new inputChecker();

    static String delRepeats(String arg){
        String result="";
        for(int i=0;i<arg.length();i++){
            if(arg.indexOf(String.valueOf(i))!=-1){
                result+=String.valueOf(i);
            }
        }
        return result;
    }

    static void parettoStage1(ArrayList<Hosting> hl,ArrayList<Hosting> optS1){
        String incIds="";
        for(int i=0;i<hl.size();i++){
            for(int j=0;j<hl.size();j++){
                if(i==j){
                    continue;
                }else{
                    String ccr = pCompare(hl.get(i),hl.get(j));
                    if(!ccr.equals("-1")){
                        if(incIds.indexOf(ccr)==-1){
                            incIds+=ccr;
                        }
                    }
                }
            }
        }

        String[] ids = incIds.split("");

        for(int j=0;j<hl.size();j++){
            for(int i=0;i<ids.length;i++){
                if(hl.get(j).id.equals(ids[i])){
                    optS1.add(hl.get(j));
                }
            }
        }
    }

    static void limit(ArrayList<Hosting> list){
        System.out.print("верхние и нижние границы указываются диапазоном - двумя числами через\":\"");
        System.out.print("\nГраницы дискового пространства(мб) - ");
        String input = chk.readln("range");
        String[] nums = input.split(":");
        double minds=Double.parseDouble(nums[0]);
        double maxds=Double.parseDouble(nums[1]);
        System.out.print("\nКоличество сайтов(шт) - ");
        input = chk.readln("range");
        nums = input.split(":");
        int minsite=Integer.parseInt(nums[0]);
        int maxsite=Integer.parseInt(nums[1]);
        System.out.print("\nРазмер БД(мб) - ");
        input = chk.readln("range");
        nums = input.split(":");
        double mindb=Double.parseDouble(nums[0]);
        double maxdb=Double.parseDouble(nums[1]);
        System.out.print("\nВремя ответа тех.поддержки(ч) - ");
        input = chk.readln("range");
        nums = input.split(":");
        double minst=Double.parseDouble(nums[0]);
        double maxst=Double.parseDouble(nums[1]);
        System.out.print("\nПростой сервера(%) - ");
        input = chk.readln("range");
        nums = input.split(":");
        double mindt=Double.parseDouble(nums[0]);
        double maxdt=Double.parseDouble(nums[1]);
        System.out.print("\nГраницы цены(руб/мес) - ");
        input = chk.readln("range");
        nums = input.split(":");
        double minprc=Double.parseDouble(nums[0]);
        double maxprc=Double.parseDouble(nums[1]);

        for(int i=0;i<list.size();i++){
            if(list.get(i).diskSpace>=minds && list.get(i).diskSpace<=maxds){
                if(list.get(i).sites>=minsite && list.get(i).sites<=maxsite){
                    if(list.get(i).DBlim>=mindb && list.get(i).DBlim<=maxdb){
                        if(list.get(i).support>=minst && list.get(i).support<=maxst){
                            if(list.get(i).downTime>=mindt && list.get(i).downTime<=maxdt){
                                if(list.get(i).price>=minprc && list.get(i).price<=maxprc){
                                    list.get(i).write();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    static void prioritet(ArrayList<Hosting> list){
        int choice = 0;
        int minspace=0,minsite = 0;
        double mindb = 0,maxwait = 0,maxdown = 0,maxprice = 0;
        String cs;
        System.out.println("Список критериев, выберите главный");
        System.out.println("1. Дисковое пространство");
        System.out.println("2. Количество сайтов");
        System.out.println("3. Лимит БД");
        System.out.println("4. Время ожидания ответа поддержки");
        System.out.println("5. Простой сервера");
        System.out.println("6. Стоимость");
        System.out.println("Введите номер главного критерия");
        while(choice>6||choice<1){
            cs = chk.readln("int");
            choice = Integer.parseInt(cs);
        }
        if(choice!=1){
            System.out.print("Мин. дисковое пространство: ");
            cs = chk.readln("int");
            minspace = Integer.parseInt(cs);
        }
        if(choice!=2){
            System.out.print("Мин. кол-во сайтов: ");
            cs = chk.readln("int");
            minsite=Integer.parseInt(cs);
        }
        if(choice!=3){
            System.out.print("Мин. размер БД: ");
            cs = chk.readln("double");
            mindb=Double.parseDouble(cs);
        }
        if(choice!=4){
            System.out.print("Макс. время ожидания ответа поддержки: ");
            cs = chk.readln("double");
            maxwait=Double.parseDouble(cs);
        }
        if(choice!=5){
            System.out.print("Макс. простой сервера: ");
            cs = chk.readln("double");
            maxdown=Double.parseDouble(cs);
        }
        if(choice!=6){
            System.out.print("Макс. стоимость: ");
            cs = chk.readln("double");
            maxprice=Double.parseDouble(cs);
        }
        String delStr="";

        for(int i=0;i<list.size();i++){
            boolean nd=false;
            if(choice!=1){
                if(list.get(i).diskSpace<minspace){
                    nd=true;
                }
            }
            if(choice!=2){
                if(list.get(i).sites<minsite){
                    nd=true;
                }
            }
            if(choice!=3){
                if(list.get(i).DBlim<mindb){
                    nd=true;
                }
            }
            if(choice!=4){
                if(list.get(i).support>maxwait){
                    nd=true;
                }
            }
            if(choice!=5){
                if(list.get(i).downTime>maxdown){
                    nd=true;
                }
            }
            if(choice!=6){
                if(list.get(i).price>maxprice){
                    nd=true;
                }
            }
            if(nd==true){
                delStr+=i;
            }
        }

        if(!delStr.equals("")) {
            if(delStr.length()!=1){
                String[] delete = delRepeats(delStr).split("");
                for (int i = delete.length - 1; i >= 0; i--) {
                    list.remove(Integer.parseInt(delete[i]));
                }
            }else{
                list.remove(Integer.parseInt(delStr));
            }
        }

        System.out.println("Последняя оставшаяся альтернатива:");
        if(list.size()==1){
            list.get(0).write();
        }else{
            int better=0;
            for(int i=0;i<list.size()-1;i++){
                switch(choice){
                    case(1):
                        if(list.get(i).diskSpace<list.get(i+1).diskSpace){
                            better=i+1;
                        }
                        break;
                    case(2):
                        if(list.get(i).sites<list.get(i+1).sites){
                            better=i+1;
                        }
                        break;
                    case(3):
                        if(list.get(i).DBlim<list.get(i+1).DBlim){
                            better=i+1;
                        }
                        break;
                    case(4):
                        if(list.get(i).support>list.get(i+1).support){
                            better=i+1;
                        }
                        break;
                    case(5):
                        if(list.get(i).downTime>list.get(i+1).downTime){
                            better=i+1;
                        }
                        break;
                    case(6):
                        if(list.get(i).price>list.get(i+1).price){
                            better=i+1;
                        }
                        break;
                }
            }
            list.get(better).write();
        }
    }

    static void parettoStage2(ArrayList<Hosting> list){
        System.out.println("\n\nМетод указания границ.");
        limit(list);
        System.out.println("\n\nМетод выбора приоритета.");
        prioritet(list);
    }


    static String pCompare(Hosting h1, Hosting h2){
        int score1=0;
        int score2=0;
        //Сравниваем память
        if(h1.diskSpace>h2.diskSpace){
            score1++;
        }else if(h1.diskSpace<h2.diskSpace){
            score2++;
        }
        //сравниваем количество сайтов
        if(h1.sites>h2.sites){
            score1++;
        }else if(h1.sites<h2.sites){
            score2++;
        }
        //сравниваем максимальный размер одной БД
        if(h1.DBlim>h2.DBlim){
            score1++;
        }else if(h1.DBlim<h2.DBlim){
            score2++;
        }
        //сравниваем простой хостинга
        if(h1.downTime<h2.downTime){
            score1++;
        }else if(h1.downTime>h2.downTime){
            score2++;
        }
        //сравниваем поддержку cron
        if(h1.support<h2.support){
            score1++;
        }else if(h1.support>h2.support){
            score2++;
        }
        //сравниваем цену
        if(h1.price<h2.price){
            score1++;
        }else if(h1.price>h2.price){
            score2++;
        }
        //анализ результата сравнения и ответ
        String winId="";
        if(score1!=0&&score2!=0||score1==0&&score2==0||score1==score2&&score1!=0){
            winId="-1";
        }else if(score1>score2&&score2==0){
            winId=h1.id;
        }else if(score1<score2&&score1==0){
            winId=h2.id;
        }
        return winId;
    }

    static void fillList(ArrayList<Hosting> lst){
        try{
            File file = new File ("alts.txt");
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

    public static void main(String[] args){
        /*Хостинги
        Критерии:
            1) Дисковая квота (мб) +
            2) Количество сайтов для размещения (шт) +
            3) Макс. размер БД (мб) +
            4) Предоставление SSL сертификата (Входит в стоимость = 1/За отдельную плату = 0.5/Нет = 0) +
            5) Простой (%) -
            6) Оперативность поддержки (ч) -
            7) Стоимость (руб.) -
        */
        ArrayList<Hosting> hostlist = new ArrayList<Hosting>(10);
        ArrayList<Hosting> paretoOptimized = new ArrayList<Hosting>(5);
        fillList(hostlist);
        System.out.println(hostlist.size());

        Hosting.writeHeader();
        for(int i=0;i<hostlist.size();i++){
            hostlist.get(i).write();
        }

        parettoStage1(hostlist,paretoOptimized);
        System.out.println("\nArray was optimized by Paretto method...\n");

        Hosting.writeHeader();
        for(int i=0;i<paretoOptimized.size();i++){
            paretoOptimized.get(i).write();
        }

        hostlist.clear();

        parettoStage2(paretoOptimized);
    }
}