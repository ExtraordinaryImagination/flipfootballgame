package com.chao.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FlipFootboll {
    //创建Frame对象
    private Frame frame=new Frame();
    //定义常量，设置桌面宽高
    private final int TABLE_WIDTH=300;
    private final int TABLE_HIGHT=400;
    //定义变量，设置小球的大小
    private final int BOLL_SIZE=16;
    //定义变量，设置球的坐标
    private int boll_X=120;
    private int boll_Y=10;
    //定义变量，设置小球的运动速度
    private int speed_X=10;
    private int speed_Y=5;
    //定义变量，设置球拍的起始大小
    private final int RACKET_WIDTH=60;
    private final int RACKET_HIGHT=20;
    //定义变量设置球拍的坐标
    private int racket_X=120;
    private final int racket_Y=350;
    //设置变量，记录游戏是否结束
    private boolean isEnd=false;

    //创建画布继承Cancas
    class MyCanvas extends Canvas{
        @Override
        public void paint(Graphics g) {
            if (isEnd){
                //游戏结束
                g.setColor(Color.BLUE);
                g.setFont(new Font("Times",Font.BOLD,30));
                g.drawString("游戏结束",50,180);
            }else {
                //绘制界面
                //绘制小球
                g.setColor(Color.red);
                g.fillOval(boll_X,boll_Y,BOLL_SIZE,BOLL_SIZE);
                //绘制球拍
                g.setColor(Color.PINK);
                g.fillRect(racket_X,racket_Y,RACKET_WIDTH,RACKET_HIGHT);
            }
        }
    }

    //创建画布
    MyCanvas myCanvas=new MyCanvas();

    //创建Timer对象性
    private Timer timer;

    //组装界面
    public void init(){

        //创建创建监听
        KeyListener listener=new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(keyCode==KeyEvent.VK_LEFT){
                    //向左《-
                    if (racket_X>=0){
                        racket_X=racket_X-10;
                    }
                }else if(KeyEvent.VK_RIGHT==keyCode){
                    //向右
                    if (racket_X<=TABLE_WIDTH-BOLL_SIZE){
                        racket_X=racket_X+10;
                    }
                }

            }
        };
        //注册监听
        frame.addKeyListener(listener);
        myCanvas.addKeyListener(listener);

        //创建Timer对象
        //判断小球界面
        ActionListener tackit=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //左右边界条件反弹
                if (boll_X<=0||boll_X>=TABLE_WIDTH-BOLL_SIZE){
                   speed_X=-speed_X;
                }
                //上下反弹
                if (boll_Y<=0||(boll_Y>=racket_Y-RACKET_HIGHT&&boll_X>=racket_X&&boll_X<=racket_X)){
                    speed_Y=-speed_Y;
                }
                //结束
                if (boll_Y>racket_Y||(boll_Y<racket_Y-RACKET_HIGHT&&boll_X<racket_X&&boll_X>racket_X)){
                    timer.stop();
                    isEnd=true;
                    myCanvas.repaint();
                }

                //更新小球坐标
                boll_X+=speed_X;
                boll_Y+=speed_Y;
                myCanvas.repaint();
            }
        };
        timer=new Timer(100,tackit);
        timer.start();
        //将画板添加到对象中
        myCanvas.setPreferredSize(new Dimension(TABLE_WIDTH,TABLE_HIGHT));
        frame.add(myCanvas);
        //调试最后
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new FlipFootboll().init();
    }
}
