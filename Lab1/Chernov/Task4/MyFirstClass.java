import myfirstpackage.*;
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

