package src.com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class GameWin extends JFrame {
//0未开始 .1运行中 2，商店  3，失败 4.胜利
    static int state;
    //储存金块。石块
    List<Object> objectList=new ArrayList<>();
    Bg bg=new Bg();

    //引用线类
    line line=new line(this);
    //引用金块类
    //Gold gold=new Gold();
    {
        //是否可以放置
    boolean isPlace=true;
    for(int i=0;i<11;i++){
        double random=Math.random();
        Gold gold;//存放当前生成的金块
        if(random<0.3){
            gold=new GoldMini();
        }else if(random<0.7){
            gold=new Gold();
        }else{
            gold=new GoldPlus();
        }
        for(Object obj:objectList){
            if(gold.getRec().intersects(obj.getRec())){
                //不放置，需要重新生成
                isPlace=false;
            }
        }
        if(isPlace){
            objectList.add(gold);
        }else{
            isPlace=true;
            i--;
        }
    }
    for(int i=0;i<5;i++){
        Rock rock=new  Rock();
        for(Object obj:objectList){
            if(rock.getRec().intersects(obj.getRec())){
                isPlace=false;
            }
        }
        if (isPlace){
            objectList.add(rock);
        }else{
            isPlace=true;
            i++;
        }
    }

     }
     //引入新图片类
   Image offScreenImage;
    void launch(){
        //设置窗口是否可见
        this.setVisible(true);
        //设置窗口大小
        this.setSize(768,1000);
        //设置窗口位置
        this.setLocationRelativeTo(null);
        //设置窗口标题
        this.setTitle("黄金矿工");
        //设置关闭窗口的方法
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //创建鼠标点击事件
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (state){
                    case 0:
                        if(e.getButton()==3){
                            state=1;
                            bg.startTime=System.currentTimeMillis();
                        }
                        break;
                    case 1:
                        //左键编码为1
                        //左右摇摆点击左键
                        if(e.getButton()==1&&line.state==0){
                            line.state=1;
                        }
                        //抓取返回，点击右键
                        if(e.getButton()==3&&line.state==3&&Bg.waterNum>0){
                            Bg.waterFlag=true;
                            Bg.waterNum--;

                        }
                        break;
                    case 2:
                        if(e.getButton()==1){
                            bg.shop=true;

                        }
                        if(e.getButton()==3){
                            state=1;
                            bg.startTime=System.currentTimeMillis();
                        }
                        break;
                    case 3:
                    case 4:
                        if(e.getButton()==1){
                            state=0;
                            bg.reGame();
                            line.reGame();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        while (true){
            repaint();
            nextlevel();
            //设置延迟
            try {
                Thread.sleep(10);

            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
    }
//下一关
    public void nextlevel(){
        if(bg.gameTime()&&state==1){
            if(Bg.count>=bg.goal){
                if(Bg.level==5){
                    state=4;
                }else{
                    state=2;
                    Bg.level++;
                }
            }else{
                state=3;
            }
            dispose();
            GameWin gameWin=new GameWin();
            gameWin.launch();
        }
    }

    @Override
    public void paint(Graphics g){
        //解决图片闪动问题
        offScreenImage=this.createImage(768,1000);
        Graphics gImage=offScreenImage.getGraphics();
        bg.paintSelf(gImage);
        if(state==1){
            //绘制物体
            for (Object obj:objectList){
                obj.paintSelf(gImage);
            }
            line.paintSelf(gImage);
        }
        g.drawImage(offScreenImage,0,0,null);
    }

    public static void main(String[] args) {
        //调用上面类
        GameWin gameWin=new GameWin();
        //调用类中方法
        gameWin.launch();
    }

}