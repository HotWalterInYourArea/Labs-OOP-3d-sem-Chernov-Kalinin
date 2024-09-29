package functions;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction{
    protected static class Node {
        public Node next=null;
        public Node prev=null;
        public double x;
        public double y;
        public Node(double x,double y){this.x=x;this.y=y;}
    }
    private Node head=null;
    protected void addNode(double x,double y){
        Node nu_node=new Node(x,y);
        if (this.head!=null){
            Node last = head.prev;
            nu_node.prev=last;
            nu_node.next=head;
            last.next=nu_node;
            head.prev=nu_node;
        }
        else {
            this.head=nu_node;
            head.prev=head.next=head;
        }
        this.count++;
    }
    public LinkedListTabulatedFunction(double[] xValues,double[] yValues){
        for(int i=0;i<xValues.length;i++){addNode(xValues[i],yValues[i]);}
    }
    public LinkedListTabulatedFunction(MathFunction source,double xFrom,double xTo,int count){
        if(Math.abs(xFrom-xTo)>EPSILON){
            if(xFrom>xTo){
                double tmp= xFrom;
                xFrom=xTo;
                xTo=tmp;
            }
            for(int i=0;i<=count;i++){
                double x0 = xFrom+(xTo-xFrom)/count*i;
                addNode(x0,source.apply(x0));
            }
        }else{
            double y=source.apply(xFrom);
            for(int i=0;i<=count;i++){addNode(xFrom,y);}
        }
    }
    @Override
    public int getCount(){return count;}
    @Override
    public double leftBound(){return this.head.x;}
    @Override
    public double rightBound(){return this.head.prev.x;}
    protected Node getNode(int index){
        if (index<=((this.count-1)/2)){
            Node nu_node=this.head;
            for(int i=0;i<index;i++){nu_node=nu_node.next;}
            return nu_node;
        }
        else{
            Node nu_node=this.head.prev;
            for(int i=count-1;i>index;i--){nu_node=nu_node.prev;}
            return nu_node;
        }
    }
    @Override
    public double getX(int index){return getNode(index).x;}
    @Override
    public double getY(int index){return getNode(index).y;}
    @Override
    public void setY(int index,double value){getNode(index).y=value;}
    @Override
    public int indexOfX(double x){
        int index=0;
        Node nu_node=this.head;
        while((Math.abs(nu_node.x-x)>EPSILON)&&(index<this.count)){nu_node=nu_node.next;index++;}
        if(index<this.count){return index;}
        else{return -1;}
    }
    @Override
    public int indexOfY(double y){
        int index=0;
        Node nu_node=this.head;
        while((Math.abs(nu_node.y-y)>EPSILON)&&(index<this.count)){nu_node=nu_node.next;index++;}
        if(index<this.count){return index;}
        else{return -1;}
    }
    @Override
    protected  int floorIndexOfX(double x){
        if (x < leftBound()) return 0;
        if (x > rightBound()) return count;
        Node nu_node=this.head;
        int index;
        int tmpInd=0;
        do{
            index=tmpInd;
            tmpInd++;
            nu_node=nu_node.next;
        }while(nu_node!=this.head && nu_node.x<=x);
        return index;
    }
    @Override
    protected double extrapolateRight(double x){
        if(this.head.next==this.head){return this.head.y;}
        double leftX=this.head.prev.prev.x;
        double rightX=this.head.prev.x;
        double leftY=this.head.prev.prev.y;
        double rightY=this.head.prev.y;
        return interpolate(x,leftX,rightX,leftY,rightY);
    }
    @Override
    protected double extrapolateLeft(double x){
        if(this.head.next==this.head){return this.head.y;}
        double leftX=this.head.x;
        double rightX=this.head.next.x;
        double leftY=this.head.y;
        double rightY=this.head.next.y;
        return interpolate(x,leftX,rightX,leftY,rightY);
    }
    protected  double interpolate(double x,Node floorNode){
        return interpolate(x,floorNode.x,floorNode.next.x,floorNode.y,floorNode.next.y);
    }
    @Override
    protected  double interpolate(double x,int floorIndex){
        Node nu_node=getNode(floorIndex);
        return interpolate(x,nu_node);
    }
    protected  Node floorNodeOfX(double x){
        if (x < leftBound()) return this.head;
        if (x > rightBound()) return this.head.prev;
        Node nu_node=this.head;
        Node floorNode;
        do{
            floorNode=nu_node;
            nu_node=nu_node.next;
        }while(nu_node!=this.head && nu_node.x<=x);
        return floorNode;
    }
    @Override
    public double apply(double x) {
        if (x < leftBound()) {return extrapolateLeft(x);}
        else if (x > rightBound()) return extrapolateRight(x);
        else if(indexOfX(x)==-1){
            if(count==1)return x;
            return interpolate(x,floorNodeOfX(x));
        }
        return getY(floorIndexOfX((x)));
    }

}
