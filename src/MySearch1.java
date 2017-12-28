import java.util.ArrayList;

class MyNode {
    Integer ID=0;
    Integer pathlen=0;
    Integer curcost=0;
    MyNode prevNode=null;

    Integer step=0;
    public boolean equals(Object obj){
        return this.ID==((MyNode)obj).ID;
    }

    public MyNode(Integer id){
        this.ID=id;
    }
    public MyNode(MyNode node){
        this.ID=node.ID;
        this.pathlen=node.pathlen;
        this.curcost=node.curcost;
        this.prevNode=node.prevNode;
        this.step=node.step;
    }
    public void connect(MyNode node,MyMap map){
        //update link and the pathlen and cost for node
        node.step=this.step+1;
        node.prevNode=this;
        node.pathlen=this.pathlen+map.Distance[this.ID][node.ID];
        node.curcost=this.curcost+map.Cost[this.ID][node.ID];
    }

    public ArrayList<MyNode> getNeighbors(MyMap map){
        ArrayList<MyNode> nbs=new ArrayList<>();
        for(Integer i=0;i<map.cities;i++){
            if(i==this.ID||map.Distance[this.ID][i]>9999) //assume e_w>=9999 means no connections
                continue;
            MyNode node=new MyNode(i);
            this.connect(node,map);
            nbs.add(node);
        }
        return nbs;
    }

}
public class MySearch1 {
    MyMap map=new MyMap(50);
    ArrayList<MyNode> branches=new ArrayList<>();
    ArrayList<MyNode> visitedNodes=new ArrayList<>();

    private void addNode(MyNode node){
        if(this.visitedNodes.contains(node))
            return;

        if(this.branches.contains(node)){
            //System.out.println("contain "+node.ID);
            int index=this.branches.indexOf(node);
            if(node.pathlen>=this.branches.get(index).pathlen)
                return;
            //System.out.println("new node "+node.ID+ " better than old one "+"==> "+node.pathlen+","+this.branches.get(index).pathlen);
            this.branches.remove(index);
            this.addNode(node);
        }

        for(Integer i=0;i<this.branches.size();i++) {
            if (node.pathlen < this.branches.get(i).pathlen) {
                this.branches.add(i, node);
                return;
            }
        }
        this.branches.add(this.branches.size(),node);
    }
    public MyNode SearchForPath(MyNode src1, MyNode dst1, Integer cost_sum){
        MyNode src=new MyNode(src1), dst=new MyNode(dst1);
        System.out.println("Seaching Path from "+src.ID+" to "+dst.ID);
        this.branches.add(src);
        Integer count=0;
        while (!this.branches.isEmpty()){
            MyNode cur=this.branches.get(0);
            this.branches.remove(0);

            if(cur.step>map.cities-1)
                System.exit(11);

            if(cur.ID==dst.ID) {
                System.out.println("found path from " + src.ID + " to " + dst.ID + " with (cost,distance)=" + cur.curcost+","+cur.pathlen);
                return cur;
            }

            ArrayList<MyNode> nbs=cur.getNeighbors(this.map);
            for(MyNode nb : nbs){
                Integer P=nb.curcost;
                Integer Q=this.map.Cost[nb.ID][dst.ID];

                if(P+Q<=cost_sum)
                    this.addNode(nb);

            }

            this.visitedNodes.add(cur);
            count+=1;

            System.out.println("iteration #" + count+": "+cur.ID+"  and branches num="+this.branches.size());

            if(count%1000==0) {

                System.out.println("iteration #" + count+"  and branches num="+this.branches.size());
            }
        }

        return null;
    }

    public static  void main(String[]args){
        MySearch1 solver=new MySearch1();
        MyNode dst=new MyNode(49),src=new MyNode(0);
        dst=solver.SearchForPath(src,dst,1500);//shortest path with constraint
        //dst=solver.SearchForPath(src,dst,999999999);// shortest without constraint


        if(dst==null) {
            System.out.println("Non --avalable solution!!!");
            System.exit(10);
        }
        ArrayList<MyNode> path=new ArrayList<>();
        while (dst!=null) {
            path.add(0, dst);
            dst=dst.prevNode;
        }
        Integer preN=path.get(0).ID;
        path.remove(0);
        for(MyNode node:path) {

            System.out.println("from " + preN + " to " + node.ID +
                    "  cost:distance " + solver.map.Cost[preN][node.ID]+":"+solver.map.Distance[preN][node.ID]);
            preN = node.ID;
        }
    }
}
