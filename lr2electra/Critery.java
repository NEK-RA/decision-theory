public class Critery{
    public String name;
    public int weight;
    public String[] scales;
    public String[] codes;
    public String type;

    public Critery(String str){
        String[] splited = str.split("[|]");
        this.name = splited[0];
        this.weight = Integer.parseInt(splited[1]);
        this.scales = splited[2].split("/");
        this.codes = splited[3].split("/");
        this.type = splited[4];
    }

    public void out(){
        System.out.print(this.name);
        System.out.print("\t"+this.weight);
        System.out.print("\t"+toString(this.scales));
        System.out.print("\t"+toString(this.codes));
        System.out.print("\t\t"+this.type+"\n");
    }

    private String toString(String[] arr){
        String result = "";
        for(int i=0;i<arr.length-1;i++){
            result+=arr[i]+"/";
        }
        result+=arr[arr.length-1];
        return result;
    }
<<<<<<< Updated upstream

    public static int getDScode(int num,Critery crt){
       int code = 0;
        if(num<2048) code = Integer.parseInt(crt.codes[0]);
        if(num>=2048&&num<4096) code = Integer.parseInt(crt.codes[1]);
        if(num>=4096) code = Integer.parseInt(crt.codes[2]);
        return code;
    }

    public static int getSTScode(int num,Critery crt){
        int code = 0;
        if(num<=2) code = Integer.parseInt(crt.codes[0]);
        if(num>2 && num<=4) code = Integer.parseInt(crt.codes[1]);
        if(num>4) code = Integer.parseInt(crt.codes[2]);
        return code;
    }

    public static int getDBLcode(double num,Critery crt){
        int code = 0;
        if(num<2048) code = Integer.parseInt(crt.codes[0]);
        if(num>=2048&&num<=4096) code = Integer.parseInt(crt.codes[1]);
        if(num>4096) code = Integer.parseInt(crt.codes[2]);
        return code;
    }

    public static int getSPTcode(double num,Critery crt){
        int code = 0;
        if(num<=3) code = Integer.parseInt(crt.codes[0]);
        if(num>3&&num<6) code = Integer.parseInt(crt.codes[1]);
        if(num>=6) code = Integer.parseInt(crt.codes[2]);
        return -1*code;
    }

    public static int getDTMcode(double num,Critery crt){
        int code = 0;
        if(num<1) code = Integer.parseInt(crt.codes[0]);
        if(num>=1&&num<=2) code = Integer.parseInt(crt.codes[1]);
        if(num>2) code = Integer.parseInt(crt.codes[2]);
        return -1*code;
    }

    public static int getPRCcode(double num,Critery crt){
        int code = 0;
        if(num<=100) code = Integer.parseInt(crt.codes[0]);
        if(num>100&&num<=200) code = Integer.parseInt(crt.codes[1]);
        if(num>200) code = Integer.parseInt(crt.codes[2]);
        return -1*code;
    }
=======
>>>>>>> Stashed changes
}