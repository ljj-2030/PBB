package system;

import java.util.*;

public class Scheduler {
    public static void main(String[] args) {
      
    }

            //输出。格式化将需要的数据设置上
            public static void prt(PBB[] plist){
                PBB pbb=null;
                double sumt=0;
                double sump=0;
            for(int i=0;i<plist.length;i++){
                pbb=plist[i];
                pbb.setTurnoverTime(pbb.getFinishTime()-pbb.getStartTime());
                pbb.setPowerTime(pbb.getTurnoverTime()*1.0/pbb.getEndTime());
                sumt+=pbb.getTurnoverTime();
                sump+=pbb.getPowerTime();
            }

            System.out.println("name\t start\t end\t finish\t turnover\t power");
            for(int i=0;i<plist.length;i++) {
                System.out.println(plist[i].getName() + "\t\t\t" + plist[i].getStartTime() + "\t\t"
                        + plist[i].getEndTime() + "\t\t" + plist[i].getFinishTime()
                +"\t\t"+plist[i].getTurnoverTime()+"\t\t"+String.format("%.2f",plist[i].getPowerTime()));
            }
                System.out.println(plist.length+"个作业的平均周转时间是:"+String.format("%.2f",sumt/plist.length));
                System.out.println(plist.length+"个作业的平均带权周转时间是:"+String.format("%.3f",sump/plist.length));

            }

             /**
     * sjf算法
     * @param pbb
     */
             public static void SJF(PBB[] pbb){
                PBB current=pbb[0];
                PBB pre=current;
                current.setFinishTime(pre.getFinishTime()+current.getEndTime());
                pre=current;
                PBB[] notFinish = filterNotFinish(pbb);
                PBB[] gtFinish = filterGtFinish(notFinish, pre);
                while (notFinish.length!=0){
                    if(gtFinish.length==0){
                        //如果没有小于上一个完成时间的
                        current=notFinish[0];
                        current.setFinishTime(pre.getFinishTime()+current.getEndTime());
                        pre=current;
                    }else{
                        current=gtFinish[0];
                        current.setFinishTime(pre.getFinishTime()+current.getEndTime());
                        pre=current;
                    }
                    notFinish=filterNotFinish(pbb);
                    gtFinish=filterGtFinish(notFinish,pre);
                }
                prt(pbb);
            }
            public static PBB[] filterNotFinish(PBB[] pbb){
                List<PBB>list=new ArrayList<PBB>();
                for(int i=0;i<pbb.length;i++){
                    if(pbb[i].getFinishTime()==0){
                        list.add(pbb[i]);
                    }
                }
                return listToArray(list);
            }
            public static PBB[] filterGtFinish(PBB[] pbb,PBB pre){
                List<PBB> pbbs = new ArrayList<>();
                for(int i=0;i<pbb.length;i++){
                    if(pbb[i].getStartTime()<pre.getFinishTime()){
                        pbbs.add(pbb[i]);
                    }
                }
                pbbs.sort(new Comparator<PBB>() {
                    @Override
                    public int compare(PBB o1, PBB o2) {
                        return o1.getEndTime()-o2.getEndTime();
                    }
                });
                return listToArray(pbbs);
            }
            public static PBB[] listToArray(List<PBB> list){
                PBB[] pbb=new PBB[list.size()];
                for(int i=0;i<list.size();i++){
                    pbb[i]=list.get(i);
                }
                return pbb;
            }

    /**
     * 先进先出算法
     * @param pbb
     */
            public static void FCFS(PBB[] pbb){
                 //先按照到达时间进行升序排序
                Arrays.sort(pbb,new Comparator<PBB>(){
                    @Override
                    public int compare(PBB o1, PBB o2) {
                        return o1.getStartTime()-o2.getStartTime();
                    }
                });

                PBB current=pbb[0];
                PBB pre=current;
                current.setFinishTime(pre.getFinishTime()+current.getEndTime());
                for(int i=1;i<pbb.length;i++){
                    current=pbb[i];
                    current.setFinishTime(pre.getFinishTime()+current.getEndTime());
                    pre=current;
                }
                 prt(pbb);
            }


    /**
     * 响应优先级
     */
            public static void HRRF(PBB[] pbb){
                PBB current=pbb[0];
                PBB pre=current;
                current.setFinishTime(pre.getFinishTime()+pre.getEndTime());
                pre=current;
                PBB[] notFinish = filterNotFinish(pbb);
                while (notFinish.length!=0){
                    current=theNexMaxResponse(notFinish,pre);
                    current.setFinishTime(pre.getFinishTime()+current.getEndTime());
                    pre=current;
                    notFinish=filterNotFinish(pbb);
                }
                prt(pbb);

            }
            //返回当前集合中最大的响应的对象作为下一个执行的资源
            public static PBB theNexMaxResponse(PBB[] notFinish,PBB pre){
                int finishTime=pre.getFinishTime();
                PBB pbb=null;
                for(int i=0;i<notFinish.length;i++){
                    pbb=notFinish[i];
                    double response=(1+(finishTime-pbb.getStartTime())*1.0/pbb.getEndTime());
                    notFinish[i].setResponseTime(response);
                }
                Arrays.sort(notFinish, new Comparator<PBB>() {
                    @Override
                    public int compare(PBB o1, PBB o2) {
                        return (int)( o2.getResponseTime()-o1.getResponseTime());
                    }
                });
                return notFinish[0];//返回最大的当作current

            }


}
