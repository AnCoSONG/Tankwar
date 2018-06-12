package tankwar;
/**
 * 
* @ClassName: Difficulty 
* @Description: 枚举类 枚举了所有的难度
* @author AnCo 
* @date 2018年4月4日 下午1:42:24 
*
 */
public enum Difficulty {

	Fool("菜鸡",0),
	Easy("简单",1),
	Normal("一般",2),
	Hard("有点难",3),
	Hell("地狱",4),
	MustLose("不可能赢",5);
	
	private String description;
	@SuppressWarnings("unused")
	private int index;
	
	private Difficulty(String description,int index) {
		this.description = description;
		this.index = index;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	
}
