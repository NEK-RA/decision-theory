public class Hosting{
    String id;
    String name;
    int diskSpace;
    int sites;
    double DBlim;
    double support;
    double downTime;
    double price;

    Hosting(int nid, String nm, int dskQt, int sts, double dblim, double dwntm, double spprt, double prc){
        this.id=String.valueOf(nid);
        this.name=nm;
        this.diskSpace=dskQt;
        this.sites=sts;
        this.DBlim=dblim;
        this.downTime=dwntm;
        this.support=spprt;
        this.price=prc;
    }

    public static void writeHeader(){
        System.out.print("Hosting name | ");
        System.out.print("Disk space(mb) | ");
        System.out.print("Sites | ");
        System.out.print("DB max size(mb) | ");
        System.out.print("Downtime(%) | ");
        System.out.print("Support (wait,h) | ");
        System.out.print("Price (RuR/month)\n");
    }

    public void write(){
        System.out.print(this.name+"\t\t");
        System.out.print(this.diskSpace+"\t\t\t");
        System.out.print(this.sites+"\t\t");
        System.out.print(this.DBlim+"\t\t\t\t");
        System.out.print(this.downTime+"\t\t\t");
        System.out.print(this.support+"\t\t\t\t\t");
        System.out.println(this.price);
    }
}
