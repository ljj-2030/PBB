package comp;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //剩余资源对象
        consumer avaliable=new consumer();
        //设置一个最大资源对象，用来做最大贷款额度
        System.out.println("请输入A类的最大资源: ");
        avaliable.max_a=scanner.nextInt();
        System.out.println("请输入B类的最大资源: ");
        avaliable.max_b=scanner.nextInt();
        System.out.println("请输入C类的最大资源: ");
        avaliable.max_c=scanner.nextInt();

        //给每条进程初始化
        System.out.println("请输入进程数:");
        consumer[] plist=initPlist(scanner.nextInt());
        setNeed(plist);
        prtList(plist);


        //得到最初可用贷款额度avaliable,
        int sum_a=0,sum_b=0,sum_c=0;
        for(consumer consumer:plist){
            sum_a+=consumer.allocation_a;
            sum_b+=consumer.allocation_b;
            sum_c+=consumer.allocation_c;
        }
        avaliable.max_a=avaliable.max_a-sum_a;
        avaliable.max_b=avaliable.max_b-sum_b;
        avaliable.max_c=avaliable.max_c-sum_c;
        //当前运行的进程为该集合中的第一个
        consumer current=null;

        consumer[] consumers = avaliableLtNeed(avaliable, plist);
            List<String> xl = new ArrayList<>();
            //这个循环判断逻辑还是有点问题，若是数组里面没有
            while (consumers.length > 0) {
                current = consumers[0];
                avaliable.max_a += current.allocation_a;
                avaliable.max_b += current.allocation_b;
                avaliable.max_c += current.allocation_c;
                current.flag = true;
                xl.add(current.name);
                consumers = avaliableLtNeed(avaliable, plist);
            }
            if(xl.size()<plist.length){
                System.out.println("该序列不安全");
            }else{
                System.out.println("有序序列是: "+xl);
            }


    }
    public static consumer[] initPlist(int n){
        Scanner scanner=null;
        consumer[] plist=new consumer[n];
        for(int i=0;i<n;i++){
            scanner=new Scanner(System.in);
            consumer temp=new consumer();
            String name="p"+i;
            System.out.println("请输入"+name+"进程的A类最大资源: ");
            temp.max_a=scanner.nextInt();
            System.out.println("请输入"+name+"进程的B类最大资源: ");
            temp.max_b=scanner.nextInt();
            System.out.println("请输入"+name+"进程的C类最大资源: ");
            temp.max_c=scanner.nextInt();
            System.out.println("请输入"+name+"进程的A类已分配资源: ");
            temp.allocation_a=scanner.nextInt();
            System.out.println("请输入"+name+"进程的B类已分配资源: ");
            temp.allocation_b=scanner.nextInt();
            System.out.println("请输入"+name+"进程的C类已分配资源: ");
            temp.allocation_c=scanner.nextInt();
            temp.name=name;
            plist[i]=temp;
        }

        return plist;
    }
    public static void  setNeed(consumer[] plist){
        //设置每个对象的need
        for(int i=0;i<plist.length;i++) {
            consumer temp = plist[i];
            temp.setNeed_a(temp.max_a - temp.allocation_a);
            temp.setNeed_b(temp.max_b - temp.allocation_b);
            temp.setNeed_c(temp.max_c - temp.allocation_c);
        }
    }
    //所有小于avaliable的nee的数组
    public static consumer[] avaliableLtNeed(consumer avaliable,consumer[]plist){
        List<consumer> list=new ArrayList<consumer>();
        for(int i=0;i<plist.length;i++) {
            if (AvaliableIsLtNeed(avaliable, plist[i])) {
                if(!plist[i].flag) {
                    list.add(plist[i]);
                }
            }
        }
        consumer consumer[]=new consumer[list.size()];
        list.toArray(consumer);
        return consumer;

    }
    public static boolean AvaliableIsLtNeed(consumer ava,consumer need){
        //如果avaliable中的a,b,c资源中有一个小于need中的对应资源时，该线程死锁，
        if(ava.max_a-need.need_a<0)return false;
        if(ava.max_b-need.need_b<0)return false;
        if(ava.max_c-need.need_c<0)return false;

        return true;
    }
    public static void prtList(consumer[] plist){
        System.out.println("---------打印如下-----------");
        System.out.println("n\tA\tB\tC\t\tA\tB\tC\t\tA\tB\tC\t\tflag");
        for(int i=0;i<plist.length;i++){
            consumer temp=plist[i];
            String prt=temp.name+"\t"+temp.max_a+"\t"+temp.max_b+"\t"+temp.max_c+"\t\t"
                    +temp.allocation_a+"\t"+temp.allocation_b+"\t"+temp.allocation_c+"\t\t"
                    +temp.need_a+"\t"+temp.need_b+"\t"+ temp.need_c+"\t\t"+temp.flag;
            System.out.println(prt);
        }
    }
    public static void prtList(consumer cur,consumer avaliable){
        System.out.println(cur.name+"\t\t"+avaliable.max_a+"\t"+avaliable.max_b+"\t"+avaliable.max_c);
    }
}
