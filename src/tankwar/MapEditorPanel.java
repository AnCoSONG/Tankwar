package tankwar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 * @author  AnCo
 * @Description 实现编辑界面的UI布置
 */
public class MapEditorPanel extends JPanel  {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 4262918317723271561L;
	//单次的属性
	protected int xChoice = -1;
	protected int yChoice = -1;
	protected Point pointChoice;
	protected int imageChoice = 0;
	protected boolean canPass = false;
	protected String saveUrl = "";
	protected String imageChoiceFileName = "";
	protected int tankBornPointChoiceX = -1;
	protected int tankBornPointChoiceY = -1;
	protected Point tankBornPointChoice;
	protected int enemyTankBornPointChoiceX = -1;
	protected int enemyTankBornPointChoiceY = -1;
	protected Point enemyTankBornPointChoice;
	protected String tankDir = "UP";
	protected String enemyTankDir = "UP";


	private int colorR = 255;
	private int colorG = 245;
	private int colorB = 239;
	private int[] xs;
	private int[] ys;
	private int width = 60;
	private int height = 60;
	private String fileName = "";
	ArrayList<MapEditorSingleMapBoxPane> boxPanes = new ArrayList<>(100);
	JButton inputImage = new JButton("导入图片");
	JButton inputImageViaFolder = new JButton("从文件夹导入素材");
	JComboBox<JLabel> resourceImage;
	private Font fontButton = new Font("苹方 粗体", Font.PLAIN, 20);
	private Font fontLabel = new Font("苹方 粗体", Font.PLAIN, 15);
	private Font fontTips = new Font("苹方 粗体", Font.PLAIN, 12);
	private Font fontScaleValue = new Font("苹方 常规", Font.PLAIN, 15);
	private ArrayList<JLabel> labelsArrayForJComboBox = new ArrayList<>();
	private JLabel applyTheImageToWhere;
	private JLabel XforInputLabel;
	private JLabel YforInputLabel;
	private Integer[] positionChoice = {-1,0,1,2,3,4,5,6,7,8,9};
	private String[] Direction = {"UP","DOWN","LEFT","RIGHT"};
	JLabel introductionTip;
	JLabel xLabel;
	JLabel yLabel;
	JLabel tankBornPoint;
	JComboBox<Integer> tankBornPointX;
	JComboBox<Integer> tankBornPointY;
	JComboBox<String> tankBornDirection;
	JLabel enemyTankBornPoint;
	JComboBox<Integer> enemyTankBornPointX;
	JComboBox<Integer> enemyTankBornPointY;
	JComboBox<String> enemyTankBornDirection;
	/**图片应用的X坐标*/
	JComboBox<Integer> pointComboBoxX;
	/**图片应用的Y坐标*/
	JComboBox<Integer> pointComboBoxY;
	/**确认并生成预览*/
	JButton ensureAndGeneratePreview = new JButton("确认并生成预览");
	JButton ensureTankAndEnemyInfo = new JButton("确认坦克和敌方信息");
	private JLabel canMoveLabel;
	JComboBox<String> canMoveComboBox;
	//可否移动的选项
	String[] canMoveBoolean = {"false","true"};
	JLabel tipsWhenSelectedImage;


	@SuppressWarnings("unchecked")
	public MapEditorPanel() {
		super(null);
		System.out.println("运行中");
		setBackground(new Color(colorR, colorG, colorB));
		//初始化画图的相关变量
		xs = new int[10];
		ys = new int[10];
		for (int i = 0; i < xs.length; i++) {
			xs[i] = 100 + i * width / 2 + 5 * i;
		}
		for (int i = 0; i < ys.length; i++) {
			ys[i] = 100 + i * height / 2 + 5 * i;
		}
		for (int i = 0; i < xs.length; i++) {
			for (int j = 0; j < ys.length; j++) {
				MapEditorSingleMapBoxPane boxPane = new MapEditorSingleMapBoxPane(new Point(i,j), xs[i], ys[j], width / 2, height / 2);
				System.out.println(boxPane.getId());
				boxPanes.add(boxPane);
			}
		}
		xLabel = new JLabel("X");
		xLabel.setFont(fontLabel);
		xLabel.setBounds(110,20,50,50);
		yLabel = new JLabel("Y");
		yLabel.setFont(fontLabel);
		yLabel.setBounds(50,90,50,50);
		introductionTip = new JLabel("暂无提示信息");
		introductionTip.setFont(fontLabel);
		introductionTip.setBounds(100,450,300,50);
		ensureTankAndEnemyInfo.setFont(fontLabel);
		ensureTankAndEnemyInfo.setBounds(780,560,200,50);
		enemyTankBornPoint = new JLabel("敌方初始信息:");
		enemyTankBornPoint.setFont(fontLabel);
		enemyTankBornPoint.setBounds(500,510,100,30);
		enemyTankBornPointX = new JComboBox<>(positionChoice);
		enemyTankBornPointX.setFont(fontLabel);
		enemyTankBornPointX.setBounds(600,510,70,30);
		enemyTankBornPointY = new JComboBox<>(positionChoice);
		enemyTankBornPointY.setFont(fontLabel);
		enemyTankBornPointY.setBounds(700,510,70,30);
		enemyTankBornDirection = new JComboBox<>(Direction);
		enemyTankBornDirection.setFont(fontLabel);
		enemyTankBornDirection.setBounds(800,510,120,30);
		tankBornDirection = new JComboBox<>(Direction);
		tankBornDirection.setFont(fontLabel);
		tankBornDirection.setBounds(800,450,120,30);
		tankBornPointX = new JComboBox<>(positionChoice);
		tankBornPointX.setFont(fontLabel);
		tankBornPointX.setBounds(600,450,70,30);
		tankBornPointY = new JComboBox<>(positionChoice);
		tankBornPointY.setFont(fontLabel);
		tankBornPoint = new JLabel("坦克初始信息:");
		tankBornPoint.setFont(fontLabel);
		tankBornPoint.setBounds(500,450,100,30);
		tankBornPointY.setBounds(700,450,70,30);
		tipsWhenSelectedImage = new JLabel("使用前请点击关于—帮助!!");
		tipsWhenSelectedImage.setFont(fontTips);
		tipsWhenSelectedImage.setBounds(500,260,200,30);
		ensureAndGeneratePreview.setFont(fontButton);
		ensureAndGeneratePreview.setBounds(780,370,200,50);
		canMoveLabel = new JLabel("该节点可否被穿过: ");
		canMoveLabel.setFont(fontLabel);
		canMoveLabel.setBounds(500,370,150,30);
		canMoveComboBox = new JComboBox<String>(canMoveBoolean);
		canMoveComboBox.setFont(fontLabel);
		canMoveComboBox.setBounds(630,370,100,30);
		pointComboBoxX = new JComboBox<Integer>(positionChoice);
		pointComboBoxX.setFont(fontLabel);
		pointComboBoxX.setBounds(660,310,70,30);
		pointComboBoxY = new JComboBox<>(positionChoice);
		pointComboBoxY.setFont(fontLabel);
		pointComboBoxY.setBounds(780,310,70,30);
		XforInputLabel = new JLabel("X:");
		XforInputLabel.setFont(fontLabel);
		XforInputLabel.setBounds(630,310,30,30);
		YforInputLabel = new JLabel("Y:");
		YforInputLabel.setFont(fontLabel);
		YforInputLabel.setBounds(750,310,30,30);
		applyTheImageToWhere = new JLabel("将该图片应用在:");
		applyTheImageToWhere.setFont(fontLabel);
		applyTheImageToWhere.setBounds(500,300,150,50);
		resourceImage = new JComboBox<JLabel>();
		resourceImage.setFont(fontButton);
		resourceImage.setBounds(500, 200, 450, 50);
		resourceImage.setRenderer(new TextAndImageRender());
		resourceImage.setMaximumRowCount(4);
		resourceImage.addItem(new JLabel("选择一张图片(选中本选项则为空)"));
		inputImage.setFont(fontButton);
		inputImage.setBounds(500, 100, 200, 50);
		inputImageViaFolder.setFont(fontButton);
		inputImageViaFolder.setBounds(750, 100, 200, 50);

		this.add(xLabel);
		this.add(yLabel);
		this.add(introductionTip);
		this.add(ensureTankAndEnemyInfo);
		this.add(enemyTankBornPoint);
		this.add(enemyTankBornPointX);
		this.add(enemyTankBornPointY);
		this.add(enemyTankBornDirection);
		this.add(tankBornDirection);
		this.add(tankBornPoint);
		this.add(tankBornPointX);
		this.add(tankBornPointY);
		this.add(tipsWhenSelectedImage);
		this.add(canMoveComboBox);
		this.add(canMoveLabel);
		this.add(ensureAndGeneratePreview);
		this.add(pointComboBoxX);
		this.add(pointComboBoxY);
		this.add(XforInputLabel);
		this.add(YforInputLabel);
		this.add(applyTheImageToWhere);
		this.add(resourceImage);
		this.add(inputImage);
		this.add(inputImageViaFolder);


		//初始化相关选项情况
		pointChoice = new Point(xChoice,yChoice);
		tankBornPointChoice = new Point(tankBornPointChoiceX,tankBornPointChoiceY);
		enemyTankBornPointChoice = new Point(enemyTankBornPointChoiceX,enemyTankBornPointChoiceY);

	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		for (MapEditorSingleMapBoxPane i : boxPanes) {//画出小窗格
			i.draw(graphics);
		}
		graphics.setFont(fontScaleValue);
		for(int i = 0; i < xs.length; i ++){//画出刻度X
			graphics.drawString(""+i,110+i*width/2+i*5,80);
		}
		for(int j = 0; j < ys.length ; j ++){//画出刻度Y
			graphics.drawString(""+j,80,120+j*height/2+j*5);
		}

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setImageJComboBox(File[] files) { //显示文字有问题！！！已解决！setOpaque（true）使之正常显示
		ArrayList<File> filesArrayForJComboBox = new ArrayList<>();
		for(int i = 0; i < files.length; i ++){
			filesArrayForJComboBox.add(files[i]);
		}
		for(File i: filesArrayForJComboBox){
			labelsArrayForJComboBox.add(new JLabel(i.getName(), new ImageIcon(i.getPath()), SwingConstants.CENTER));
		}
		for(JLabel i : labelsArrayForJComboBox){
			resourceImage.addItem(i);
		}
		System.out.println("已导入的图片数量："+labelsArrayForJComboBox.size());

	}

	public void generatePreviewImage(File[] files){
		ArrayList<File> image = new ArrayList<>();
		for(int i = 0; i < files.length; i ++){
			image.add(files[i]);
		}
		for(File i: image){
			try{
				BufferedImage in = ImageIO.read(i);
				BufferedImage out = new BufferedImage(width/2,height/2,BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = out.createGraphics();
				out = g2d.getDeviceConfiguration().createCompatibleImage(width/2, height/2, Transparency.TRANSLUCENT);
				g2d.dispose();
				g2d = out.createGraphics();
				g2d.drawImage(in.getScaledInstance(width/2,height/2,Image.SCALE_AREA_AVERAGING),0,0,null);
				ImageIO.write(out,i.getName().substring(i.getName().lastIndexOf(".")+1),
						new File("cache"+File.separator+"cache"+i.getName()));
            } catch (IOException e) {
				e.printStackTrace();
			}
        }
	}

	public void doBeforeLeave(){
		//重置界面为空 将状态返回state0
		//清空图片链表 清空JComboBox；
		//在学会备忘录模式后再写
	}

}


@SuppressWarnings({ "serial", "rawtypes" })
class TextAndImageRender extends JLabel implements ListCellRenderer{


	public TextAndImageRender(){
		setOpaque(true); //!!!正常显示设置为true
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		JLabel a = (JLabel) value;
		setText(a.getText());
		setIcon(a.getIcon());

		return this;
	}

}



