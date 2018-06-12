package tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapEditorWelcomePage extends JPanel {


    /** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -4454971288195181405L;
	private Toolkit toolkit;
    private String sologen = "欢迎！";
    private String subtitle = "这 是 一 个 地 图 编 辑 器 ";
    private JLabel sologenLabel;
    private JLabel subtitleLabel;
    private int colorR = 255;
    private int colorG = 245;
    private int colorB = 239;
    private Font fontSologen = new Font("苹方 粗体", Font.PLAIN, 100);
    private Font fontSubtitle = new Font("苹方 粗体", Font.PLAIN, 30);
    private Font fontButton = new Font("苹方 粗体", Font.PLAIN, 15);
    protected JButton newMap;
    protected JButton close;
    protected JButton viewMap;

     public MapEditorWelcomePage(){
         super(null);
         setBackground(new Color(colorR,colorG,colorB));
         newMap = new JButton("新建地图");
         newMap.setFont(fontButton);
         close = new JButton("关闭");
         close.setFont(fontButton);
         viewMap = new JButton("查看已有地图");
         viewMap.setFont(fontButton);
         toolkit = Toolkit.getDefaultToolkit();
         sologenLabel = new JLabel(sologen);
         subtitleLabel = new JLabel(subtitle);
         subtitleLabel.setFont(fontSubtitle);
         sologenLabel.setFont(fontSologen);
         sologenLabel.setBounds(100,0,400,400);
         subtitleLabel.setBounds(100,250,400,100);
         close.setBounds(700,460,240,75);
         viewMap.setBounds(400,460,240,75);
         newMap.setBounds(100,460,240,75);
         viewMap.setEnabled(false);
         this.add(close);
         this.add(viewMap);
         this.add(newMap);
         this.add(sologenLabel);
         this.add(subtitleLabel);

     }

     @Override
    protected void paintComponent(Graphics graphics){
         super.paintComponent(graphics);
         graphics.drawImage(toolkit.getImage("img//mapeditor//welcome.png"),450,120,null);
     }



}
