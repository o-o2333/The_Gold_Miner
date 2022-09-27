package src.com;

import java.awt.*;

import static java.awt.Color.green;

public class Bg {
    //关卡数
    static int level=1;
    //目标得分
    int goal=level*15;
    //总分
    static  int count=0;
    //药水数量
    static int waterNum=3;
    //药水状态，默认为F,T标识正在使用
    static boolean waterFlag=false;
    //开始时间
    long startTime;
    //结束时间
    long endTime;
    //药水价格
    int price=(int)(Math.random()*10);
    //是否进入商店F不购买

    boolean shop=false;
    //载入药水图片
    Image water=Toolkit.getDefaultToolkit().getImage("imgs/yaoshui.png");
    //设置背景图片
    Image bg=Toolkit.getDefaultToolkit().getImage("imgs/bj.jpg");
    Image bg1=Toolkit.getDefaultToolkit().getImage("imgs/bj2.jpg");
    Image peo=Toolkit.getDefaultToolkit().getImage("imgs/rr.png");
    //传入画笔元素
    void paintSelf(Graphics g){
        g.drawImage(bg1,0,0,null);
        g.drawImage(bg,0,200,null);
        switch(GameWin.state){
            case  0:
            drawWord(g,20,Color.green,"准备开始（右键开始、左键抓取，右键快速回收）",180,400);
            break;
            case 1:
                g.drawImage(peo,310,50,null);
                //调用传参
                drawWord(g,30,Color.black,"积分："+count,30,150);
                //绘制药水
                g.drawImage(water,450,40,null);
                drawWord(g,30,Color.black,"*"+waterNum,510,70);
                //关卡数
                drawWord(g,20,Color.black,"第"+level+"关",30,60);
                //目标积分
                drawWord(g,30,Color.black,"目标积分"+goal,30,110);
                //时间组件
                endTime=System.currentTimeMillis();
                long tim=20-(endTime-startTime)/1000;
                drawWord(g,30,Color.black,"时间"+(tim>0?tim:0),520,150);
                break;
            case 2:
                g.drawImage(water,300,400,null);
                drawWord(g,30,Color.black,"价格："+price,300,500);
                drawWord(g,20,Color.black,"是否购买？(左键购买)"+price,300,550);
                if(shop){
                    count=count-price;
                    waterNum++;
                    shop=false;
                    GameWin.state=1;
                    startTime=System.currentTimeMillis();
                }
                break;
            case 3:
                drawWord(g,40,Color.cyan,"成功（左键准备）",250,350);
                drawWord(g,80,Color.cyan,"积分"+count,200,450);
                break;
            case 4:
                drawWord(g,40,Color.RED,"成功（左键准备）",250,350);
                drawWord(g,80,Color.RED,"积分"+count,200,450);
                break;
            default:
                break;
        }
    }
    //t倒计时完成，f正在倒计时
    boolean gameTime(){
        long tim=(endTime-startTime)/1000;
        if(tim>20){
            return true;
        }
        return false;
    }
    //重置元素
    void reGame(){
        //关卡数
        level=1;
        //目标得分
        goal=level*15;
        //总分
        count=0;
        //药水数量
        waterNum=3;
        //药水状态，默认为F,T标识正在使用
        waterFlag=false;

    }
    //绘制字符串
    public static void drawWord(Graphics g,int size,Color color,String str,int x,int y){
        g.setColor(color);
        g.setFont(new Font("仿宋",Font.BOLD,size));
        g.drawString(str,x,y);
    }
}
