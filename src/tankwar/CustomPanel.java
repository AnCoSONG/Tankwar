package tankwar;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 自定义游戏类该类为卡片布局包含两个子页面 可以由此启动 地图编辑器 以及 直接从 默认路径读取文件并生成地图和游戏界面
 */
public class CustomPanel extends JPanel implements ActionListener ,TankLiveListener{

    /** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -4244053554439669960L;
	CustomSelectPanel selectPanel = new CustomSelectPanel();
    CustomGamingPanel gamingPanel = new CustomGamingPanel();
    File map_saved = null;

    CardLayout cards = new CardLayout();
    public CustomPanel(){
        super();
        setLayout(cards);
        this.add(selectPanel, "Card"+0);
        this.add(gamingPanel,"Card"+1);

        selectPanel.inputMap.addActionListener(this);
        selectPanel.startGame.addActionListener(this);
        selectPanel.createMap.addActionListener(this);
        selectPanel.help.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == selectPanel.inputMap){
            selectPanel.tip.setText("读取地图中...");
            JFileChooser fileChooser = new JFileChooser("map");
            int value = fileChooser.showDialog(selectPanel,"导入");
            XMLFileFilter xmlFileFilter = new XMLFileFilter();
            fileChooser.setFileFilter(xmlFileFilter);
            if(value == JFileChooser.APPROVE_OPTION){
                File map = fileChooser.getSelectedFile();
                map_saved = map;
                boolean reval1  = gamingPanel.loadFile(map);
                if(reval1){
                    selectPanel.tip.setText("读取地图完成");
                }

                boolean reval2 = gamingPanel.loadTank(map);
                if(reval2) {
                    selectPanel.tip.setText("己方坦克信息读取完成");
                }
                boolean reval3 = gamingPanel.loadEnemy(map);
                if(reval3){
                    selectPanel.tip.setText("敌方坦克信息读取完成");
                }

                if(reval1&&reval2&&reval3){
                    selectPanel.tip.setText("全部读取完成");
                    selectPanel.startGame.setEnabled(true);
                }
            }else{
                selectPanel.tip.setText("未导入文件");
            }
        }

        if(source == selectPanel.startGame){
            cards.show(this,"Card"+1);
            gamingPanel.requestFocusInWindow();
            gamingPanel.customTank.addTankLiveListener(this);
            gamingPanel.customEnemy.addTankLiveListener(this);
        }

        if(source == selectPanel.createMap){
            String[] args = {};
            MapEditorFrame.main(args);
        }

        if(source == selectPanel.help){
            JOptionPane.showMessageDialog(selectPanel,"使用步骤: 通过创建地图进入地图编辑器,编辑好后保存,关闭地图编辑器,\n再在此页面导入刚刚创建的xml地图文件,读取完成后,\n下方的提示文字会显示读取完成" +
                    "开始游戏按钮将激活,点击即可进行游戏\n","帮助",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void dead(Even even) {

            cards.show(this,"Card"+0);
            selectPanel.requestFocusInWindow();
            int reval = JOptionPane.showConfirmDialog(selectPanel,"游戏结束，是否重新开始?");
            if(reval == JOptionPane.YES_OPTION){
                //重置游戏面板
                cards.show(this,"Card"+1);
                gamingPanel.init(map_saved);
                gamingPanel.requestFocusInWindow();
                gamingPanel.customTank.addTankLiveListener(this);
                gamingPanel.customEnemy.addTankLiveListener(this);
            }else{
                //do nothing
                gamingPanel.init(map_saved);

            }
    }
}
