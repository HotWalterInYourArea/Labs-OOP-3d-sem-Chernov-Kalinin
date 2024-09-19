class MyFirstClass {
    public static void main(String[] s) {
        MySecondClass bah= new MySecondClass(1,1);
        System.out.println(bah.sum());
        for(int i=1;i<=8;i++){
                    for (int j=1; j <= 8; j++) {
                        bah.seta(i);
                        bah.setb(j);
                     	System.out.print(bah.sum());
                        System.out.print(" ");
                }
                System.out.println();
        }

    }
}
class MySecondClass{
    private int a;
    private int b;
    public MySecondClass(int a1,int b1){this.a=a1;this.b=b1;}
    public void seta(int c){ this.a=c;}
    public void setb(int c){ this.b=c;}
    public int geta(){return a;}
    public int getb(){return b;}
    public int sum(){return a+b;}
}
