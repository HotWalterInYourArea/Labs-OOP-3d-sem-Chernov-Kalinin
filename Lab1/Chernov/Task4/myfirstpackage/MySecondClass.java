package myfirstpackage;
public class MySecondClass{
    private int a;
    private int b;
    public MySecondClass(int a1,int b1){this.a=a1;this.b=b1;}
    public void seta(int c){ this.a=c;}
    public void setb(int c){ this.b=c;}
    public int geta(){return a;}
    public int getb(){return b;}
    public int sum(){return a+b;}
}