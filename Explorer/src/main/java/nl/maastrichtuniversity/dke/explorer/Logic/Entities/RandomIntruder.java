package nl.maastrichtuniversity.dke.explorer.Logic.Entities;

import nl.maastrichtuniversity.dke.explorer.GUI.GamePanel;
import nl.maastrichtuniversity.dke.explorer.Logic.Tiles.Cell.Node;

import java.util.ArrayList;


public class RandomIntruder extends Intruder{

    private int desiredX;
    private int desiredY;
    private double desiredAngle;

    //rotate after reaching a cell
    private boolean rotate;

    private int[] decisions;

    public RandomIntruder(int id, double x, double y, double viewAngle, double viewRange, double viewAngleSize, double baseSpeed, double sprintSpeed, GamePanel gamePanel) {
        super(id, x, y, viewAngle, viewRange, viewAngleSize, baseSpeed, sprintSpeed, gamePanel);
        this.desiredX = (int) this.movement.getX();
        this.desiredY = (int) this.movement.getY();
        decisions = new int[2];
        rotate = false;
    }

    public void update(boolean isGuard){
        randomDecision();
        setAction(decisions[0], decisions[1]);
        super.update(isGuard);
    }

    public boolean randomDecision(){
        if(this.movement.isThroughTeleport()){
            desiredX = (int) this.movement.getX();
            desiredY = (int) this.movement.getY();
            desiredAngle = this.vision.getViewAngle();
        }else if(gamePanel.tileM.mapTile[(int) this.movement.getX()][(int) this.movement.getY()] == 4){
            decisions = new int[]{0, 0};
        }else if((int) this.movement.getX() == desiredX && (int) this.movement.getY() == desiredY && desiredAngle == this.vision.getViewAngle()){
            if(rotate == true){
                desiredAngle = Math.round(Math.random()*180);
                rotate = false;
            }else{
                ArrayList<Node> inView = this.vision.tilesInView();

                for (int i = 0; i < inView.size(); i++) {
                    if(gamePanel.tileM.mapTile[inView.get(i).getX()][inView.get(i).getY()] == 4){
                        desiredX = inView.get(i).getX();
                        desiredY = inView.get(i).getY();
                        desiredAngle=angleBetween(desiredX, desiredY);
                        return true;
                    }
                }
                do{
                    int choice = (int) (Math.random()*inView.size());
                    desiredX = inView.get(choice).getX();
                    desiredY = inView.get(choice).getY();
                    desiredAngle= angleBetween(desiredX, desiredY);
                }while(gamePanel.tileM.mapTile[desiredX][desiredY] == 1);
                rotate = true;
            }

        }
        if(desiredAngle != this.vision.getViewAngle()){

            decisions = new int[]{0, this.vision.turnWhichWay(desiredAngle)};
            return false;
        }else if(((int) this.movement.getX() != desiredX || (int) this.movement.getY() != desiredY) && desiredAngle == this.vision.getViewAngle()){
            if(((int) this.movement.getX() == desiredX && (int) this.movement.getY() != desiredY) || ((int) this.movement.getX() != desiredX && (int) this.movement.getY() == desiredY)){
                if(desiredAngle != angleBetween(desiredX, desiredY)){
                    desiredAngle = angleBetween(desiredX, desiredY);
                    decisions = new int[]{0, this.vision.turnWhichWay(desiredAngle)};
                }else{
                    decisions = new int[]{1, 0};
                }
            }else{
                decisions = new int[]{1, 0};
            }
            return false;
        }else{
            decisions = new int[]{0, 0};
            return false;
        }
    }

    public double angleBetween(int x2, int y2){
        int deltaX = x2 - (int)this.movement.getX();
        int deltaY = y2 - (int)this.movement.getY();

        double angle = Math.round(Math.toDegrees(Math.atan2(deltaY, deltaX)));
        if(angle < 0){
            angle = 360 + angle;
        }
        if(angle == 360){
            angle = 0;
        }
        return angle;
    }


}
