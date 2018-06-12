package tankwar;

import java.util.ArrayList;

public class EditorPanelState {
    private ArrayList<MapEditorPanel> states = new ArrayList<>();
    private int position = 0;

    /**在position位置添加state并将position++*/
    public void saveStates(MapEditorPanel state) {
        states.add(position,state);
        position++;
    }
    /**获取指定状态*/
    public MapEditorPanel getState(int index){
        return states.get(index);
    }

    /**获取状态个数*/
    public int getStatesSize(){
        return states.size();
    }
    /**获取上一个状态*/
    public MapEditorPanel getLastState(){
        position--;
        return states.get(position-1);
    }

    public MapEditorPanel getNextState(){
        position++;
        return states.get(position-1);
    }

    public boolean canUndo(){
        boolean canUndo = false;
        if(position>=2){
            canUndo = true;
        }else{
            canUndo = false;
        }
        return canUndo;
    }

    public boolean canRedo(){
        if(position<=states.size()-1){
            return true;
        }else{
            return false;
        }
    }

    public int getPosition() {
        return position-1;
    }

    public void trimState(){

    }
}
