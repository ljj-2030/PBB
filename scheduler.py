
'''
理解误区：不是直接将进程按服务时间的长短排序后顺序执行！！，而是先按到达时间排序，
若有多个进程的到达时间小于上一进程的结束时间，则将这多个进程按服务时间长短调度
'''
class PBB:
    def __init__(self,name,arrival_time,service_time):
        self.name=name
        self.arrival_time=arrival_time
        self.service_time=service_time
        self.finish_time=0
        self.turnover_time=0
        self.power_time=0
        self.resp_percent_time=0

    def set_turnover_time(self,time):
        self.turnover_time=time
    def set_power_time(self,time):
        self.power_time=time
    def set_resp_percent(self,time):
        self.resp_percent_time=time
    def set_finish_time(self,time):
        self.finish_time=time
    def __str__(self):
        return "{},{},{},{}".format(self.name,self.arrival_time,self.service_time,self.finish_time)
#循环给每个对象的周转时间，和带权周转时间赋值,并返回总周转时间，与总带权周转时间
#周转时间=finish时间-当前的到达时间
#带权周转时间=当前的周转时间/当前的服务时间
def compute_turnover_time(list):
    tur=0
    power=0
    for item in list:
        item.set_turnover_time(item.finish_time-item.arrival_time)
        item.set_power_time(item.turnover_time/item.service_time)
        tur+=item.turnover_time
        power+=item.power_time
    return tur,power
#格式化输出
def prt_list(list):
    tur_sum, pow_sum = compute_turnover_time(list)  #获取总的轮转时间，和带权周转时间
    print("name         arrival         service         finish      turnover        power")
    for i in list:
        print('''{}             {}                {}             {}            {}            {:.2f}'''.format(i.name,i.arrival_time,i.service_time,i.finish_time,i.turnover_time,i.power_time))
    size=len(list)
    print("注意！，这里做了小数点四舍五入，所以与可本书答案略有出入")
    print("{}个作业的平均周转时间是:{:.2f}\n{}个作业的平均带权周转时间是:{:.3f}".format(size,tur_sum/size,size,pow_sum/size))

#函数过滤，返回没有计算过的对象的集合
def filter_not_finished(list):
    data=[]
    for item in list:
        if(item.finish_time==0):
            data.append(item)
    return data

#函数过滤，返回小于finish的对象的集合
def filter_lt_finish(list,no):
    data=[]
    for i in list:
        if(i.arrival_time<no):
          data.append(i)
    return data

#响应比用的函数
#遍历将列表中的数据赋值响应比，并通过降序，取出最大的响应比返回
def set_not_finish_resp_percent(list,pre):
    for item in list:
        resp_time=1+(pre.finish_time-item.arrival_time)/item.service_time
        item.set_resp_percent(resp_time)
    list.sort(key=lambda x: x.resp_percent_time, reverse=True)
    return list[0]

#先到先出
def FCFS(list):
    list.sort(key=lambda x:x.arrival_time) #先按照时间进行排序
    pre=list[0]
    for item in list:
        item.set_finish_time(pre.finish_time+item.service_time)
        pre=item
    prt_list(list)

#最短作业优先算法SJF
def SJF(list):
    #默认先处理第一个对象
    pre = current = list[0]
    #设置当前对象的finish时间=上一个对象的finish+当前对象的服务时间
    current.set_finish_time(pre.finish_time + current.service_time)
    #将当前对象赋值给上一个对象
    pre = current
    #找出原先集合中没有被处理过的对象组成的集合
    not_finished = filter_not_finished(list)
    #找出没有被处理的对象的集合中的小于finish的对象组成的集合
    lt_finish = filter_lt_finish(not_finished, pre.finish_time)
    #当所有的对象都被处理过了，就退出循环，因为在PDD中每个对象都有一个finish属性默认值，被处理过后，就不等于默认值了
    while not_finished != []:
        if lt_finish == []:     #当当前集合中没有小于finish的时候
            current = not_finished[0]   #就将当前集合的第一个对象赋值给当前对象
            current.set_finish_time(pre.finish_time + current.service_time)
            pre = current       #将当前对象赋值给上一个对象
        else:                   #如果当前集合中有小于finish的对象时，将集合按照服务时间的大小进行升序，第一个就是最小的
            lt_finish.sort(key=lambda x: x.service_time)
            current = lt_finish[0]          #将最小服务时间的对象赋值给当前对象
            current.set_finish_time(pre.finish_time + current.service_time)
            pre = current                #将当前对象赋值给上一个对象
        not_finished = filter_not_finished(list)   #重新找到没有被操作的对象组成的集合，直到全部被操作为止
        lt_finish = filter_lt_finish(not_finished, pre.finish_time) #同上
    prt_list(list) ###打印

#响应比HRRF
def HRRF(list):
    pre = current = list[0]
    current.set_finish_time(pre.finish_time + current.service_time)  #默认先将第一个计算出来作为上一个对象
    current.set_resp_percent(current.service_time)
    pre = current
    not_finish = filter_not_finished(list)  #以上借鉴我之前写的sjf算法
    while not_finish != []:  #从没被计算过后的列表中循环计算
        current = set_not_finish_resp_percent(not_finish, pre)   #这个方法是从响应比中取出最大的对象并返回作为当前对象，每一次处理后，取出一个响应比最大的对象
        current.set_finish_time(pre.finish_time + current.service_time)
        current.set_resp_percent(current.service_time)
        pre = current     #将当前对象赋值给上一个作为上一个对象
        not_finish = filter_not_finished(list)  #重新取出没有处理过的集合
    prt_list(list)

#选择算法
def option(list):

    while True:
        print("----输入数字，选择要使用的调度方法----")
        print('''
                           1 :         使用 FCFS
                           2:          使用 SJF
                           3：          使用HRRF
                           0:          退出
                   ''')
        num=input("请输入: ")
        if(num=='1'):
            print("\n------------这是FCFS算法-----------")
            FCFS(list)
            break
        if(num=='2'):
            print("\n------------这是SJF算法-----------")
            SJF(list)
            break
        if(num=='3'):
            print("\n------------这是HRRF算法-----------")
            HRRF(list)
            break
        if(num=='0'):
            break


#输入的主体
def init():
    list=[]
    print("----开始输入,输入exit退出输入作业-----")
    count=1
    while True:
        print("请输入第{}条".format(count))
        arr=int(input("arrival: "))
        ser=int(input("service: "))
        name="thread{}".format(count)
        list.append(PBB(name,arr,ser))
        count+=1
        code=input("请输入任意键或输入exit退出>>>>")
        if(code=="exit"):
            break
    return list
def prt(list):
    print("\n=========您输入的内容是=============")
    print("\nname             arrival         service          finish")
    for item in list:
        print("{}            {}                {}                   {}".format(item.name,item.arrival_time,item.service_time,item.finish_time))
    print("\n")

#主方法
def main():
    # 用了三个while有点差劲!
    while True:
        print('''
    *********作业调度算法操作如下**********
    0:          退出
    1:          输入内容
            ''')
        str = input()
        if (str == "0"):
            print("======退出程序，谢谢惠顾=====")
            break
        if (str == "1"):
            data = init()  # 接收到得到的集合
            prt(data)  # 先打印出来
            option(data)  # 把集合传进去，选择作业算法进行输出。


''''
说明：
本程序完成的算法有
1.先进先出
2.最优程序SJF
3.响应比 HRRF

********************注意************************
在每次输入service_time回车之后，如果想要继续输入下一条，
那么需要再次回车或按任意键
如果不想要输入下一条，则请输入exit就会退出程序然后可以选择算法了


'''

if __name__ == '__main__':

    main()
