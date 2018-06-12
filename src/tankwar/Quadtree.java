package tankwar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>四叉树筛选可能发生碰撞的单个地图块</p>
 * @author  Internet
 */
public class Quadtree {

    private int MAX_OBJECTS = 10;
    private int MAX_LEVELS = 5;

    private int level;        // 子节点深度
    private List<Rectangle> objects;     // 物体数组
    private Rectangle bounds; // 区域边界
    private Quadtree[] nodes; // 四个子节点

    /*
     * 构造函数
     */
    public Quadtree(int pLevel, Rectangle pBounds) {
        level = pLevel;
        objects = new ArrayList<Rectangle>();
        bounds = pBounds;
        nodes = new Quadtree[4];
    }

    /*
     * 清空四叉树
     */
    public void clear() {
        objects.clear();

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    /*
     * 将一个节点分成四个子节点（实际是添加四个子节点）
     */
    private void split() {
        int subWidth = (int)(bounds.getWidth() / 2);
        int subHeight = (int)(bounds.getHeight() / 2);
        int x = (int)bounds.getX();
        int y = (int)bounds.getY();

        nodes[0] = new Quadtree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new Quadtree(level+1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new Quadtree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new Quadtree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    /*
     * 用于判断物体属于哪个子节点
     * -1指的是当前节点可能在子节点之间的边界上不属于四个子节点而还是属于父节点
     */

    private int getIndex(Rectangle pRect) {
        int index = -1;
        // 中线
        double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

        // 物体完全位于上面两个节点所在区域
        boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
        // 物体完全位于下面两个节点所在区域
        boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

        // 物体完全位于左面两个节点所在区域
        if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint) {
            if (topQuadrant) {
                index = 1; // 处于左上节点
            }
            else if (bottomQuadrant) {
                index = 2; // 处于左下节点
            }
        }
        // 物体完全位于右面两个节点所在区域
        else if (pRect.getX() > verticalMidpoint) {
            if (topQuadrant) {
                index = 0; // 处于右上节点
            }
            else if (bottomQuadrant) {
                index = 3; // 处于右下节点
            }
        }

        return index;
    }

    /*
     * 将物体插入四叉树
     * 如果当前节点的物体个数超出容量了就将该节点分裂成四个从而让多数节点分给子节点
     */
    public void insert(Rectangle pRect) {

        // 插入到子节点
        if (nodes[0] != null) {
            int index = getIndex(pRect);

            if (index != -1) {
                nodes[index].insert(pRect);

                return;
            }
        }

        // 还没分裂或者插入到子节点失败，只好留给父节点了
        objects.add(pRect);

        // 超容量后如果没有分裂则分裂
        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }
            // 分裂后要将父节点的物体分给子节点们
            int i = 0;
            while (i < objects.size()) {
                int index = getIndex((Rectangle) objects.get(i));
                if (index != -1) {
                    nodes[index].insert((Rectangle) objects.remove(i));
                }
                else {
                    i++;
                }
            }
        }
    }

    /*
     * 返回所有可能和指定物体碰撞的物体
     */
    public List<Rectangle> retrieve(List<Rectangle> returnObjects, Rectangle pRect) {
        int index = getIndex(pRect);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, pRect);
        }

        returnObjects.addAll(objects);

        return returnObjects;
    }

}
