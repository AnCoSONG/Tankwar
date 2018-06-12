package tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MapEditorNewPanel extends JPanel implements FocusListener {
    /** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -4857745104868943101L;


    private Toolkit toolkit;

    private Font fontTitle = new Font("苹方 粗体", Font.PLAIN, 50);

    private Font fontFileLabel = new Font("苹方 粗体", Font.PLAIN, 25);
    private Font fontInput = new Font("苹方 粗体", Font.PLAIN, 15);
    private Font fonttips = new Font("苹方 粗体", Font.PLAIN, 12);

    JLabel title;


    JLabel filename;
    JTextField filenameInput;
    JButton next;
    JButton last;
    JLabel tips;

    private String fileName = "";

    public MapEditorNewPanel() {
        super(null);
        tips = new JLabel("输入一个你喜欢的名字!");
        tips.setFont(fonttips);
        tips.setBounds(100,350,200,50);
        title = new JLabel("创建一个新的地图");
        title.setFont(fontTitle);
        title.setBounds(50,0,400,200);
        toolkit = Toolkit.getDefaultToolkit();
        next = new JButton("下一步");
        next.setBounds(600,500,200,50);
        next.setFont(fontInput);
        last = new JButton("上一步");
        last.setFont(fontInput);
        last.setBounds(200,500,200,50);
        filenameInput = new JTextField("在此输入名称",50);
        filenameInput.setFont(fontInput);
        filename = new JLabel("地图名：");
        filename.setLabelFor(filenameInput);
        filenameInput.setBounds(100,300,300,50);
        filename.setFont(fontFileLabel);
        filename.setBounds(100,250,200,50);
        this.add(last);
        this.add(title);
        this.add(next);
        this.add(tips);
        this.add(filename);
        this.add(filenameInput);
        filenameInput.addFocusListener(this);


    }

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(toolkit.getImage("img//mapeditor//newPage.png"),450,0,null);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public void focusGained(FocusEvent e) {
        filenameInput.setText(fileName);
    }

    @Override
    public void focusLost(FocusEvent e) {
        fileName = filenameInput.getText();
        System.out.println(fileName);
    }

    /**
     * @Desciption: 在cards离开这一页之前做的事
     */
    public void doBeforeLeave(){
        fileName = "";
        filenameInput.setText("在此输入名称");
        tips.setText("输入一个你喜欢的名字!");
    }


}
