import java.io.*;

public class MyMap {
    public final Integer cities;

    public Integer[][] Cost=new Integer[50][50];
    public Integer[][] Distance=new Integer[50][50];

    private void loadData(){
        Integer nlines=this.cities;

        BufferedReader reader=null;
        try {
            reader=new BufferedReader(new FileReader("../data/m1.txt"));
            for(Integer i=0;i<nlines;i++){
                String s=reader.readLine();
                String []nums=s.split("\t");
                for(Integer j=0;j<nlines;j++)
                    this.Distance[i][j]=Integer.parseInt(nums[j]);

            }
            reader.close();
            reader=new BufferedReader(new FileReader("../data/m2.txt"));
            for(Integer i=0;i<nlines;i++){
                String s=reader.readLine();
                String []nums=s.split("\t");
                for(Integer j=0;j<nlines;j++)
                    this.Cost[i][j]=Integer.parseInt(nums[j]);

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        System.out.println("distance");
        for(Integer i=0;i<nlines;i++){
            for(Integer j=0;j<nlines;j++)
                System.out.print(this.Distance[i][j] +" ");
            System.out.println();
        }
        System.out.println("cost");
        for(Integer i=0;i<nlines;i++){
            for(Integer j=0;j<nlines;j++)
                System.out.print(this.Cost[i][j] +" ");
            System.out.println();
        }
        */
    }
    public MyMap(Integer cities){
        this.cities=cities;
        Cost=new Integer[this.cities][cities];
        Distance=new Integer[cities][cities];
        loadData();
    }
    public static void main(String[] args){
        new MyMap(50);
    }
}
