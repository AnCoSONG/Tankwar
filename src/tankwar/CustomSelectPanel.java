package tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomSelectPanel extends JPanel {

    /** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 5026154162727584610L;
	JButton inputMap;
    JButton startGame;
    JButton help;
    JButton backToStandard;
    JButton createMap;
    JLabel title;
    JLabel tip;
    Font font_button = new Font("苹方 粗体", Font.PLAIN, 16);
    Font font_small_button = new Font("苹方 粗体", Font.PLAIN, 12);
    Font font_title = new Font("苹方 粗体", Font.PLAIN, 40);



    public CustomSelectPanel(){
        super(null);
        setBackground(new Color(245,239,219));
        createMap = new JButton("创建地图");
        createMap.setFont(font_small_button);
        createMap.setBounds(-5,425,100,50);
        inputMap = new JButton("导入地图");
        inputMap.setFont(font_button);
        inputMap.setBounds(200,350,200,50);
        help = new JButton("帮助");
        help.setFont(font_button);
        help.setBounds(200,425,200,50);
        startGame = new JButton("开始自定义游戏");
        startGame.setFont(font_button);
        startGame.setBounds(200,500,200,50);
        startGame.setEnabled(false);
        backToStandard = new JButton("回到标准模式");
        backToStandard.setFont(font_small_button);
        backToStandard.setBounds(505,425,100,50);
        title = new JLabel("自 定 义 模 式");
        title.setFont(font_title);
        title.setBounds(175,240,300,100);
        tip = new JLabel("");
        tip.setFont(font_small_button);
        tip.setBounds(200,560,200,40);

        this.add(tip);
        this.add(createMap);
        this.add(title);
        this.add(backToStandard);
        this.add(help);
        this.add(inputMap);
        this.add(startGame);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("img/mapeditor/customselect.png"),0,0,null);
    }

}
