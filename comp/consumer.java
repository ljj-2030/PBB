package comp;

public class consumer {
    public String name;

    public consumer(int max_a, int max_b, int max_c) {
        this.max_a = max_a;
        this.max_b = max_b;
        this.max_c = max_c;
    }

    //最大资源
    public int max_a;
    public int max_b;
    public int max_c;
    public boolean flag=false;//每个对象都默认没有操作过
    //已分配资源
    public int allocation_a;
    public int allocation_b;
    public int allocation_c;

    //还需要多少资源
    public int need_a;
    public int need_b;
    public int need_c;

    public consumer(String name,int max_a, int max_b, int max_c, int allocation_a, int allocation_b, int allocation_c) {
        this.max_a = max_a;
        this.name=name;
        this.max_b = max_b;
        this.max_c = max_c;
        this.allocation_a = allocation_a;
        this.allocation_b = allocation_b;
        this.allocation_c = allocation_c;
    }

    public int getNeed_a() {
        return need_a;
    }

    public void setNeed_a(int need_a) {
        this.need_a = need_a;
    }

    public int getNeed_b() {
        return need_b;
    }

    public void setNeed_b(int need_b) {
        this.need_b = need_b;
    }

    public int getNeed_c() {
        return need_c;
    }

    public void setNeed_c(int need_c) {
        this.need_c = need_c;
    }

    public consumer() {
    }


    @Override
    public String toString() {
        return "consumer{" +"name="+name+","+
                "max_a=" + max_a +
                ", max_b=" + max_b +
                ", max_c=" + max_c +
                ", allocation_a=" + allocation_a +
                ", allocation_b=" + allocation_b +
                ", allocation_c=" + allocation_c +
                ", need_a=" + need_a +
                ", need_b=" + need_b +
                ", need_c=" + need_c +","+
                "flag="+flag+
                '}';
    }
}
