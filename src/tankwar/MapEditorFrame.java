package tankwar;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

public class MapEditorFrame extends JFrame implements ActionListener ,ItemListener {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -8471785831203482502L;
	
	Logger logger = Logger.getLogger("INFO");
	private int width = 1080;
	private int height = 720;
	JMenuItem foldSet = new JMenuItem("设置");
	JMenuItem foldGetResource = new JMenuItem("导入图片");
	JMenuItem foldGetResourceViaFolder = new JMenuItem("从文件夹导入素材");
	JMenuItem foldNew = new JMenuItem("新建");
	JMenuItem foldSave = new JMenuItem("保存");
	JMenuItem foldClose = new JMenuItem("关闭");
	JMenuItem foldSaveAs = new JMenuItem("另存为");
	JMenuItem editReset = new JMenuItem("重置地图");
	JMenuItem editUndo = new JMenuItem("撤销");
	JMenuItem editRedo = new JMenuItem("重做");
	JMenuItem aboutHelp = new JMenuItem("帮助");
	JMenuItem aboutAbout = new JMenuItem("关于");
	JMenu fold = new JMenu("文件");
	JMenu edit = new JMenu("编辑");
	JMenu about = new JMenu("关于");
	JMenuBar menuBar = new JMenuBar();
	CardLayout cards = new CardLayout();

	JPanel all;
	Font fontFrameTitle = new Font("苹方 细体", Font.PLAIN, 10);
	protected String mapPath = "map";
	protected String mapSourcePath = "mapsource";
	protected boolean instantSave = true;


	//文件过滤器
	ImageFileFilter imageFileFilter; //导入图片使用
	XMLFileFilter xmlFileFilter; //xml文件过滤器

	//图片类型
	static final int steel = 1;
	static final int steels = 2;//4格steel
	static final int wall = 3;
	static final int walls = 4;//4格wall
	static final int water = 5;
	static final int grass = 6;
	String steel_url = "cache//cachesteel.gif";
	String steels_url = "cache//cachesteels.gif";
	String wall_url = "cache//cachewall.gif";
	String walls_url = "cache//cachewalls.gif";
	String water_url = "cache//cachewater.gif";
	String grass_url = "cache//cachegrass.png";

	private int currentCard = 0;

	MapEditorPanel mapEditorPanel = new MapEditorPanel();
	MapEditorNewPanel mapEditorNewPanel = new MapEditorNewPanel();
	MapEditorWelcomePage mapEditorWelcomePage = new MapEditorWelcomePage();
	EditorPanelState editorPanelState = new EditorPanelState();

	public MapEditorFrame() {

		//对frame的初始配置
		super("地图编辑器");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(width, height);
		setMediate(this);
		setResizable(false);
		setFont(fontFrameTitle);

		//对菜单栏的配置
		//设置快捷键
		foldSave.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_MASK));
		foldNew.setAccelerator(KeyStroke.getKeyStroke('N', KeyEvent.CTRL_MASK));
		editUndo.setAccelerator(KeyStroke.getKeyStroke('Z', KeyEvent.CTRL_MASK));
		//设置一些初始不启用的
		foldGetResource.setEnabled(false);
		foldGetResourceViaFolder.setEnabled(false);
		foldSave.setEnabled(false);
		foldSaveAs.setEnabled(false);
		editRedo.setEnabled(false);
		editUndo.setEnabled(false);
		editReset.setEnabled(false);
		foldSet.setEnabled(false);
		mapEditorPanel.inputImageViaFolder.setEnabled(false);

		//添加到menubar里

		fold.add(foldNew);
		fold.addSeparator();
		fold.add(foldSave);
		fold.add(foldSaveAs);
		fold.add(foldGetResource);
		fold.add(foldGetResourceViaFolder);
		fold.addSeparator();
		fold.add(foldSet);
		fold.add(foldClose);
		edit.add(editUndo);
		edit.add(editRedo);
		edit.addSeparator();
		edit.add(editReset);
		about.add(aboutHelp);
		about.addSeparator();
		about.add(aboutAbout);
		menuBar.add(fold);
		menuBar.add(edit);
		menuBar.add(about);
		this.setJMenuBar(menuBar);

		//添加菜单选项事件监听器
		foldSet.addActionListener(this);
		foldClose.addActionListener(this);
		foldNew.addActionListener(this);
		foldSave.addActionListener(this);
		foldSaveAs.addActionListener(this);
		foldGetResource.addActionListener(this);
		foldGetResourceViaFolder.addActionListener(this);
		editUndo.addActionListener(this);
		editRedo.addActionListener(this);
		editReset.addActionListener(this);
		aboutAbout.addActionListener(this);
		aboutHelp.addActionListener(this);

		//添加按键事件监听器
		mapEditorWelcomePage.newMap.addActionListener(this);
		mapEditorWelcomePage.viewMap.addActionListener(this);
		mapEditorWelcomePage.close.addActionListener(this);
		mapEditorNewPanel.next.addActionListener(this);
		mapEditorPanel.inputImage.addActionListener(this);
		mapEditorPanel.inputImageViaFolder.addActionListener(this);
		mapEditorPanel.ensureAndGeneratePreview.addActionListener(this);
		mapEditorNewPanel.last.addActionListener(this);
		mapEditorPanel.ensureTankAndEnemyInfo.addActionListener(this);

		//添加选项监听器
		mapEditorPanel.canMoveComboBox.addItemListener(this);
		mapEditorPanel.resourceImage.addItemListener(this);
		mapEditorPanel.pointComboBoxX.addItemListener(this);
		mapEditorPanel.pointComboBoxY.addItemListener(this);
		mapEditorPanel.tankBornPointX.addItemListener(this);
		mapEditorPanel.tankBornPointY.addItemListener(this);
		mapEditorPanel.tankBornDirection.addItemListener(this);
		mapEditorPanel.enemyTankBornPointX.addItemListener(this);
		mapEditorPanel.enemyTankBornPointY.addItemListener(this);
		mapEditorPanel.enemyTankBornDirection.addItemListener(this);


		//添加卡片布局

		all = new JPanel(cards);
		all.add(mapEditorWelcomePage, "Card" + 0);
		all.add(mapEditorNewPanel, "Card" + 1);
		all.add(mapEditorPanel, "Card" + 2);

		this.add(all);
		setVisible(true);
	}

	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		this.update();
	}


	private void setMediate(JFrame f) {

		int windowsWidth = f.getWidth();
		int windowsHeight = f.getHeight();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();
		int screenWidth = dimension.width;
		int screenHeight = dimension.height;
		f.setLocation(screenWidth / 2 - windowsWidth / 2, screenHeight / 2 - windowsHeight / 2);
	}

	public static void main(String[] args) {
		BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.translucencyAppleLike;

		try {
			UIManager.put("RootPane.setupButtonVisible", false);
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MapEditorFrame mapEditorFrame = new MapEditorFrame();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					mapEditorFrame.repaint();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == mapEditorNewPanel.last) {
			currentCard = 0;
			cards.show(all, "Card" + currentCard);
			mapEditorNewPanel.doBeforeLeave();
		}
		if(source == mapEditorPanel.ensureTankAndEnemyInfo){
			for(MapEditorSingleMapBoxPane i : mapEditorPanel.boxPanes) {
				if (i.getId().getX() == mapEditorPanel.tankBornPointChoice.getX() && i.getId().getY() == mapEditorPanel.tankBornPointChoice.getY()) {
					logger.info("坦克初始点：已找到");
					if(i.isSelected()){
						if(i.isMapPoint()){
							logger.info("不可将地图位置设置为坦克初始位置");
							mapEditorPanel.introductionTip.setText("不可将地图位置设置为坦克初始位置");
						}else if(i.isEnemyPoint()){
							logger.info("不可以在敌方坦克初始位置设置己方坦克");
							mapEditorPanel.introductionTip.setText("不可以在敌方坦克初始位置设置己方坦克");
						}
					}else{
						i.setTankDir(mapEditorPanel.tankDir);
						i.setCanPass(true);
						i.setSelected(true);
						i.setTankPoint(true);
						mapEditorPanel.introductionTip.setText("坦克信息设置完成");
					}
				}
				if (i.getId().getX() == mapEditorPanel.enemyTankBornPointChoice.getX() && i.getId().getY() == mapEditorPanel.enemyTankBornPointChoice.getY()) {
					logger.info("敌方初始点：已找到");
					if(i.isSelected()){
						if(i.isMapPoint()){
							logger.info("不可将地图位置设置为敌方初始位置");
						}else if(i.isTankPoint()){
							logger.info("不可在己方坦克位置设置敌方坦克");
						}
					}else{
						i.setEnemyDir(mapEditorPanel.enemyTankDir);
						i.setCanPass(true);
						i.setSelected(true);
						i.setEnemyPoint(true);
						mapEditorPanel.introductionTip.setText("敌方信息设置完成");
					}
				}
			}
		}
		if (source == mapEditorPanel.ensureAndGeneratePreview) {
			System.out.println("坐标：" + mapEditorPanel.pointChoice + "\n" + "图片类型：" + mapEditorPanel.imageChoice + "\n" + "可否通过：" + mapEditorPanel.canPass);
			for (MapEditorSingleMapBoxPane i : mapEditorPanel.boxPanes) {
				if (i.getId().getX() == mapEditorPanel.pointChoice.getX() && i.getId().getY() == mapEditorPanel.pointChoice.getY()) {
					logger.info("地图元素：已找到对应的位置");
					i.setCanPass(mapEditorPanel.canPass);
					i.setSelected(true);
					i.setMapPoint(true);
					switch (mapEditorPanel.imageChoice) { //希望可以改进成这里设置输出节点 图片类型 可否通过 的xml文件 editorPanel直接打开生成的xml文件进行边解析边绘图
						case steels: //i.setImage(steels_url);
							i.setImage(steels);
							break;
						case grass: //i.setImage(grass_url);
							i.setImage(grass);
							break;
						case water: //i.setImage(water_url);
							i.setImage(water);
							break;
						case walls: //i.setImage(walls_url);
							i.setImage(walls);
							break;
						case -1:
							i.setSelected(false);
							i.setImage("");
							break;
						default:
							i.setImage("cache" + File.separator + "cache" + mapEditorPanel.imageChoiceFileName);
							JOptionPane.showMessageDialog(getContentPane(), "所选图片无法实际起效！", "警告", JOptionPane.WARNING_MESSAGE);
							break;
					}
				}
//				editorPanelState.saveStates(mapEditorPanel);
//				logger.info("确认后" + editorPanelState.getPosition() + "," + editorPanelState.getStatesSize());
			}
			mapEditorPanel.introductionTip.setText("地图预览完成");
			}

			if (source == editUndo) {//撤销功能未完成

				logger.info("执行撤销");
				try {
					logger.info(editorPanelState.getPosition() + "," + editorPanelState.getStatesSize());
					BeanUtils.copyProperties(mapEditorPanel, editorPanelState.getLastState()); //可能需要实现序列化接口?
					logger.info("Now :" + mapEditorPanel.pointChoice + " " + mapEditorPanel.imageChoice + " " + mapEditorPanel.canPass);
					logger.info("Last");
					logger.info(editorPanelState.getPosition() + "," + editorPanelState.getStatesSize());
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				}
				System.out.println(editorPanelState.getPosition());
			}
			if (source == editRedo) {//重做功能未完成
				//功能区域
				if (editorPanelState.canRedo()) {
					logger.info("执行重做");
					logger.info(editorPanelState.getPosition() + "," + editorPanelState.getStatesSize());
				}
			}
			if (source == foldClose || source == mapEditorWelcomePage.close) {

				int reval = JOptionPane.showConfirmDialog(getContentPane(),"这个关闭操作将会关闭JVM\u2122(Java Virtual Machine\u2122)，这意味着程序会完全停止\n当你在自定义模式中开启该程序，你应当选择程序右上角的关闭，那样将只会关闭该地图编辑器\n是否确定完全关闭JVM\u2122?");
				if(reval == JOptionPane.YES_OPTION){
					System.exit(0);
				}else{
					// do nothing
				}

			}
			if (source == foldNew || source == mapEditorWelcomePage.newMap) {
				if (currentCard == 0) {
					currentCard++;
					cards.show(all, "Card" + currentCard);
				} else if (currentCard == 1) {
					JOptionPane.showMessageDialog(null, "你已经处在新建页面！");
				} else if (currentCard == 2) {
					int value = JOptionPane.showConfirmDialog(null, "是否放弃当前地图重新新建地图？");
					if (value == JOptionPane.YES_OPTION) {
						//清空地图配置并回到newPanel
						currentCard = 1;
						setTitle("地图编辑器");
						cards.show(all, "Card" + currentCard);
					} else {
						//do nothing
					}

				}

			}

			if (source == aboutHelp) {
				JOptionPane.showMessageDialog(getContentPane(), "1.地图编辑器仅支持坦克大战\n" +
						"2.地图编辑器理论支持将任意图片设置为地图的部件，但现只支持mapsource文件夹下的四张图片作为素材\n" +
						"3.使用流程：导入文件或从文件夹导入文件-->在选框选中图片-->选择地图位置-\n->确定并预览将生成预览-->设置坦克和敌方初始位置-->确认-->保存");
			}

			if (source == foldSave) {//保存 先检查内存中存储的路径 是否
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement("root");
				ArrayList<Element> point = new ArrayList<>();
				if (instantSave) {
					logger.info("执行保存");
					for (MapEditorSingleMapBoxPane j : mapEditorPanel.boxPanes) {
						if (j.isSelected()) {
							if(j.isMapPoint()) {
								Element temp = root.addElement("Point");
								temp.addText(j.getId().x + "," + j.getId().y);
								temp.addAttribute("MapType", "" + j.getImageChoice());
								temp.addAttribute("CanPass", "" + j.isCanPass());
								point.add(temp);
							}else if(j.isTankPoint()){
								Element temp = root.addElement("Tank");
								temp.addText(j.getId().x+","+j.getId().y);
								temp.addAttribute("Direction",""+j.getTankDir());
							}else if(j.isEnemyPoint()){
								Element temp = root.addElement("Enemy");
								temp.addText(j.getId().x+","+j.getId().y);
								temp.addAttribute("Direction",""+j.getEnemyDir());
							}
						}
					}
					logger.info(mapEditorPanel.getFileName());
					try (FileWriter fileWriter = new FileWriter(mapPath + File.separator + mapEditorPanel.getFileName() + ".xml")) {
						XMLWriter writer = new XMLWriter(fileWriter);
						writer.write(document);
						writer.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (new File(mapPath + File.separator + mapEditorPanel.getFileName() + ".xml").exists()) {
						JOptionPane.showMessageDialog(getContentPane(), "保存成功");
					} else {
						JOptionPane.showMessageDialog(getContentPane(), "保存失败");
					}

				} else {
					JFileChooser jFileChooser = new JFileChooser(mapPath);
					int value = jFileChooser.showSaveDialog(getContentPane());
					xmlFileFilter = new XMLFileFilter();
					jFileChooser.setFileFilter(xmlFileFilter);
					if (value == JFileChooser.APPROVE_OPTION) {
						File map = jFileChooser.getSelectedFile();
						if (map.isDirectory()) {
							mapPath = map.getPath();
							String filename = map.getName();
							mapEditorPanel.setFileName(filename);
							this.setTitle(filename);
							for (MapEditorSingleMapBoxPane j : mapEditorPanel.boxPanes) {
								if (j.isSelected()) {
									if(j.isMapPoint()) {
										Element temp = root.addElement("Point");
										temp.addText(j.getId().x + "," + j.getId().y);
										temp.addAttribute("MapType", "" + j.getImageChoice());
										temp.addAttribute("CanPass", "" + j.isCanPass());
										point.add(temp);
									}else if(j.isTankPoint()){
										Element temp = root.addElement("Tank");
										temp.addText(j.getId().x+","+j.getId().y);
										temp.addAttribute("Direction",""+j.getTankDir());
									}else if(j.isEnemyPoint()){
										Element temp = root.addElement("Enemy");
										temp.addText(j.getId().x+","+j.getId().y);
										temp.addAttribute("Direction",""+j.getEnemyDir());
									}
								}
							}
							try (FileWriter fileWriter = new FileWriter(mapEditorPanel.getFileName() + ".xml")) {
								XMLWriter writer = new XMLWriter(fileWriter);
								writer.write(document);
								writer.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}

				}
			}

			if (source == foldSaveAs) {//另存为

			}
			if (source == aboutAbout) {
				JOptionPane.showMessageDialog(null, "作者：AnCo\n制作日期：5/31/2018", "关于", JOptionPane.INFORMATION_MESSAGE);
			}
			if (source == mapEditorNewPanel.next) {
				String name = mapEditorNewPanel.filenameInput.getText();
				System.out.println(name);
				if (!(name.equals("在此输入名称") || name.equals(""))) {
					currentCard = 2;
					cards.show(all, "Card" + currentCard);
					//创建存储编辑情况state类 并保存第一种情况
					editorPanelState.saveStates(this.mapEditorPanel);
					//设置菜单选项启用情况
					foldGetResource.setEnabled(true);
					foldSave.setEnabled(true);
					foldSaveAs.setEnabled(false);
					mapEditorPanel.setFileName(name);
					this.setTitle("地图编辑器：" + name);
					mapEditorNewPanel.doBeforeLeave();
					foldNew.setEnabled(false);
				} else {
					mapEditorNewPanel.tips.setText("你还没有起名字！");
				}
			}
			if (source == foldSet) {
				//打开设置页面 设置默认导入素材和导出地图路径
			}
			if (source == mapEditorPanel.inputImage || source == foldGetResource) {
				JFileChooser jFileChooser1 = new JFileChooser(mapSourcePath);
				imageFileFilter = new ImageFileFilter();
				jFileChooser1.setFileFilter(imageFileFilter);
				jFileChooser1.setMultiSelectionEnabled(true);
				int value = jFileChooser1.showDialog(this.getContentPane(), "导入");
				if (value == JFileChooser.APPROVE_OPTION) {
					File[] files = jFileChooser1.getSelectedFiles();
					mapEditorPanel.setImageJComboBox(files);
					mapEditorPanel.generatePreviewImage(files);

				}
			}

			if (source == mapEditorWelcomePage.viewMap) {
				System.out.println("已执行ViewMap");
				JFileChooser jFileChooser = new JFileChooser(mapPath);
				jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				xmlFileFilter = new XMLFileFilter();
				jFileChooser.setFileFilter(xmlFileFilter);
				jFileChooser.setMultiSelectionEnabled(false);
				int value = jFileChooser.showDialog(this.getContentPane(), "导入");
				if (value == JFileChooser.APPROVE_OPTION) {
					File map = jFileChooser.getSelectedFile();
					if (map.isFile()) {
						System.out.println(map.getName());
//					if (map.getName().endsWith(".twm")) {
//						try {
//							//导入文件并读取 显示读取进度条 完成后跳转到 编辑地图界面
//							BufferedReader in = new BufferedReader(new FileReader(map));
//							String str = "";
//
//							int line = 1;
//							//下面的代码是可以将twm文本数据翻译出来
//							while ((str = in.readLine()) != null) {
//
//								if (!(str.startsWith("height") || str.startsWith("width"))) {
//									String[] result = str.split(",");
//									for (int i = 0; i < result.length; i++) {
//										System.out.println("第" + line + "行，第" + (i + 1) + "列的地图类型：" + result[i]);
//									}
//
//								} else if (str.startsWith("height")) {
//									String[] result = str.split("=");
//									System.out.println("height" + result[1]);
//								} else if (str.startsWith("width")) {
//									String[] result = str.split("=");
//									System.out.println("width:" + result[1]);
//
//								}
//								line++;
//							}
//							//翻译部分结束
//						} catch (FileNotFoundException e1) {
//							e1.printStackTrace();
//						} catch (IOException e2) {
//							e2.printStackTrace();
//						}}else

						//以上代码弃用
						if (map.getName().endsWith(".xml")) {
							//读XML文件 已可以读取 下一步是将这个结果存在Map类里 Map类加入一个基类 SingleMap类
							//singlemap存储当前地图的坐标，地图类型，以及可否通过...
							SAXReader reader = new SAXReader();
							try {
								Document document = reader.read(map);
								Element root = document.getRootElement();
								List<Element> elementList = root.elements();
								for (Element i : elementList) {
									if(i.getName()=="Point") {
										System.out.println(i.getText());
										System.out.println(i.attributeValue("MapType"));
										System.out.println(i.attributeValue("CanPass"));
									}else if(i.getName()=="Tank"){
										System.out.println(i.getName()+" "+i.getText());
										System.out.println(i.attributeValue("Direction"));
									}else if(i.getName()=="Enemy"){
										System.out.println(i.getName()+" "+i.getText());
										System.out.println(i.attributeValue("Direction"));
									}
								}


							} catch (DocumentException e1) {
								e1.printStackTrace();
							}
						}
					}

				}
			}

		}

		@Override
		public void itemStateChanged (ItemEvent e){
			Object source = e.getSource();
			if (source == mapEditorPanel.resourceImage) {
				logger.info("Selecting image...");
				String temp = ((JLabel) mapEditorPanel.resourceImage.getSelectedItem()).getText();
				mapEditorPanel.imageChoiceFileName = temp;
				switch (temp) {
					case "grass.png":
						mapEditorPanel.imageChoice = 6;
						break;
					case "water.gif":
						mapEditorPanel.imageChoice = 5;
						break;
					case "walls.gif":
						mapEditorPanel.imageChoice = 4;
						break;
					case "wall.gif":
						mapEditorPanel.imageChoice = 3;
						break;
					case "steels.gif":
						mapEditorPanel.imageChoice = 2;
						break;
					case "steel.gif":
						mapEditorPanel.imageChoice = 1;
						break;
					case "选择一张图片(选中本选项则为空)":
						mapEditorPanel.imageChoice = -1;
						break;
					default:
						mapEditorPanel.imageChoice = 0;
						break;
				}
				System.out.println("You choose:" + mapEditorPanel.imageChoice);
			}

			if (source == mapEditorPanel.pointComboBoxX) {
				int temp = ((Integer) mapEditorPanel.pointComboBoxX.getSelectedItem()).intValue();
				mapEditorPanel.xChoice = temp;
				mapEditorPanel.pointChoice.setLocation(mapEditorPanel.xChoice, mapEditorPanel.yChoice);
				System.out.println(mapEditorPanel.pointChoice);
			}

			if (source == mapEditorPanel.canMoveComboBox) {
				String temp = mapEditorPanel.canMoveComboBox.getSelectedItem().toString();
				switch (temp) {
					case "true":
						mapEditorPanel.canPass = true;
						break;
					case "fales":
						mapEditorPanel.canPass = false;
						break;
					default:
						mapEditorPanel.canPass = false;
						break;
				}
				System.out.println("canPass:" + mapEditorPanel.canPass);
			}

			if (source == mapEditorPanel.pointComboBoxY) {
				int temp = ((Integer) mapEditorPanel.pointComboBoxY.getSelectedItem()).intValue();
				mapEditorPanel.yChoice = temp;
				mapEditorPanel.pointChoice.setLocation(mapEditorPanel.xChoice, mapEditorPanel.yChoice);
				System.out.println(mapEditorPanel.pointChoice);
			}

			if (source == mapEditorPanel.tankBornPointX) {
				int temp = ((Integer) mapEditorPanel.tankBornPointX.getSelectedItem()).intValue();
				mapEditorPanel.tankBornPointChoiceX = temp;
				mapEditorPanel.tankBornPointChoice.setLocation(mapEditorPanel.tankBornPointChoiceX, mapEditorPanel.tankBornPointChoiceY);
				logger.info(mapEditorPanel.tankBornPointChoice + "");
			}

			if (source == mapEditorPanel.tankBornPointY) {
				int temp = ((Integer) mapEditorPanel.tankBornPointY.getSelectedItem()).intValue();
				mapEditorPanel.tankBornPointChoiceY = temp;
				mapEditorPanel.tankBornPointChoice.setLocation(mapEditorPanel.tankBornPointChoiceX, mapEditorPanel.tankBornPointChoiceY);
				logger.info(mapEditorPanel.tankBornPointChoice + "");
			}

			if (source == mapEditorPanel.enemyTankBornPointX) {
				int temp = ((Integer) mapEditorPanel.enemyTankBornPointX.getSelectedItem()).intValue();
				mapEditorPanel.enemyTankBornPointChoiceX = temp;
				mapEditorPanel.enemyTankBornPointChoice.setLocation(mapEditorPanel.enemyTankBornPointChoiceX, mapEditorPanel.enemyTankBornPointChoiceY);
				logger.info(mapEditorPanel.enemyTankBornPointChoice + "");
			}

			if (source == mapEditorPanel.enemyTankBornPointY) {
				int temp = ((Integer) mapEditorPanel.enemyTankBornPointY.getSelectedItem()).intValue();
				mapEditorPanel.enemyTankBornPointChoiceY = temp;
				mapEditorPanel.enemyTankBornPointChoice.setLocation(mapEditorPanel.enemyTankBornPointChoiceX, mapEditorPanel.enemyTankBornPointChoiceY);
				logger.info(mapEditorPanel.enemyTankBornPointChoice + "");
			}

			if(source == mapEditorPanel.tankBornDirection){
				String temp = ((String) mapEditorPanel.tankBornDirection.getSelectedItem()).toString();
				logger.info(temp);
				mapEditorPanel.tankDir = temp;
				logger.info(mapEditorPanel.tankDir);
			}

			if(source == mapEditorPanel.enemyTankBornDirection){
				String temp = ((String)mapEditorPanel.enemyTankBornDirection.getSelectedItem()).toString();
				logger.info(temp+"");
				mapEditorPanel.enemyTankDir = temp;
				logger.info(mapEditorPanel.enemyTankDir);
			}

		}

		private void update () {
			if (editorPanelState.canRedo()) {
				this.editRedo.setEnabled(true);
			}

			if (editorPanelState.canUndo()) {
				this.editUndo.setEnabled(true);
			}

			//判断是否可以保存
			if (mapEditorPanel.saveUrl == null) {
				this.instantSave = false;
			} else {
				this.instantSave = true;
			}


		}

}


	
	
	
	

