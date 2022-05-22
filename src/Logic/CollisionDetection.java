package Logic;

import GUI.GamePanel;
import Logic.Entities.Entity;

public class CollisionDetection {
    GamePanel gamePanel;

    public CollisionDetection(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }


    public void checkTile(Entity entity){
        //check for collisions
        double entityMoveToX1 = entity.getX() - 0.5;
        double entityMoveToY1 = entity.getY() - 0.5;
        double entityMoveToX2 = entity.getX() + 0.5;
        double entityMoveToY2 = entity.getY() + 0.5;




        if(entity.getActionMove() == 1){ //walk

            entityMoveToX1 += ( (1 / (double) gamePanel.getTileSize()) * (entity.getBaseSpeed()/entity.getSpeedRatio()) ) * Math.cos(Math.toRadians(entity.getViewAngle()));
            entityMoveToY1 += ( (1 / (double) gamePanel.getTileSize()) * (entity.getBaseSpeed()/entity.getSpeedRatio()) ) * Math.sin(Math.toRadians(entity.getViewAngle()));
            entityMoveToX2 += ( (1 / (double) gamePanel.getTileSize()) * (entity.getBaseSpeed()/entity.getSpeedRatio()) ) * Math.cos(Math.toRadians(entity.getViewAngle()));
            entityMoveToY2 += ( (1 / (double) gamePanel.getTileSize()) * (entity.getBaseSpeed()/entity.getSpeedRatio()) ) * Math.sin(Math.toRadians(entity.getViewAngle()));
        }else{ //sprint
            entityMoveToX1 += ( (1 / (double) gamePanel.getTileSize()) * (entity.getSprintSpeed()/entity.getSpeedRatio()) ) * Math.cos(Math.toRadians(entity.getViewAngle()));
            entityMoveToY1 += ( (1 / (double) gamePanel.getTileSize()) * (entity.getSprintSpeed()/entity.getSpeedRatio()) ) * Math.sin(Math.toRadians(entity.getViewAngle()));
            entityMoveToX2 += ( (1 / (double) gamePanel.getTileSize()) * (entity.getSprintSpeed()/entity.getSpeedRatio()) ) * Math.cos(Math.toRadians(entity.getViewAngle()));
            entityMoveToY2 += ( (1 / (double) gamePanel.getTileSize()) * (entity.getSprintSpeed()/entity.getSpeedRatio()) ) * Math.sin(Math.toRadians(entity.getViewAngle()));
        }

        int nextTile1, nextTile2, nextTile3;

        if(entity.getViewAngle() <= 90 && entity.getViewAngle() >= 0){
            nextTile1 = gamePanel.tileM.mapTile[(int) entityMoveToX1][(int) entityMoveToY1];
            nextTile2 = gamePanel.tileM.mapTile[(int) entityMoveToX2][(int) entityMoveToY1];
            nextTile3 = gamePanel.tileM.mapTile[(int) entityMoveToX2][(int) entityMoveToY2];
            if(gamePanel.tileM.tile[nextTile1].collision == true || gamePanel.tileM.tile[nextTile2].collision == true || gamePanel.tileM.tile[nextTile3].collision == true){
                entity.setCollision(true);
            }
        }else if( entity.getViewAngle() < 180 && entity.getViewAngle() > 90){
            nextTile1 = gamePanel.tileM.mapTile[(int) entityMoveToX1][(int) entityMoveToY1];
            nextTile2 = gamePanel.tileM.mapTile[(int) entityMoveToX1][(int) entityMoveToY2];
            nextTile3 = gamePanel.tileM.mapTile[(int) entityMoveToX2][(int) entityMoveToY1];
            if(gamePanel.tileM.tile[nextTile1].collision == true || gamePanel.tileM.tile[nextTile2].collision == true || gamePanel.tileM.tile[nextTile3].collision == true){
                entity.setCollision(true);
            }
        }else if( entity.getViewAngle() <= 270  && entity.getViewAngle() >= 180){
            nextTile1 = gamePanel.tileM.mapTile[(int) entityMoveToX1][(int) entityMoveToY1];
            nextTile2 = gamePanel.tileM.mapTile[(int) entityMoveToX1][(int) entityMoveToY2];
            nextTile3 = gamePanel.tileM.mapTile[(int) entityMoveToX2][(int) entityMoveToY2];
            if(gamePanel.tileM.tile[nextTile1].collision == true || gamePanel.tileM.tile[nextTile2].collision == true || gamePanel.tileM.tile[nextTile3].collision == true){
                entity.setCollision(true);
            }
        }else if(entity.getViewAngle() < 360 && entity.getViewAngle() > 270 ){
            nextTile1 = gamePanel.tileM.mapTile[(int) entityMoveToX1][(int) entityMoveToY2];
            nextTile2 = gamePanel.tileM.mapTile[(int) entityMoveToX2][(int) entityMoveToY1];
            nextTile3 = gamePanel.tileM.mapTile[(int) entityMoveToX2][(int) entityMoveToY2];
            if(gamePanel.tileM.tile[nextTile1].collision == true || gamePanel.tileM.tile[nextTile2].collision == true || gamePanel.tileM.tile[nextTile3].collision == true){
                entity.setCollision(true);
            }
            for (int i = 0; i < gamePanel.entityM.guards.length; i++) {

            }
        }
    }
}
