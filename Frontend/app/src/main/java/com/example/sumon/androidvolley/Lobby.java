package com.example.sumon.androidvolley;public class Lobby {
    private int num;
    private int currentUserCount;
    private int maxUserCount;
    private boolean isGameInProgress;

    public Lobby(int num, int currentUserCount, int maxUserCount, boolean isGameInProgress) {
        this.num = num;
        this.currentUserCount = currentUserCount;
        this.maxUserCount = maxUserCount;
        this.isGameInProgress = isGameInProgress;
    }

    public int getNum() {
        return num;
    }

    public int getCurrentUserCount() {
        return currentUserCount;
    }
    public void setCurrentUserCount(int n) {
        currentUserCount = n;
    }

    public int getMaxUserCount() {
        return maxUserCount;
    }

    public boolean isGameInProgress() {
        return isGameInProgress;
    }

    public String getUserCountText() {
        return currentUserCount + "/" + maxUserCount;
    }
    public boolean checkForGameReady(){
        if(currentUserCount >= maxUserCount){
            isGameInProgress = true;
            return true;
        }else{
            return false;
        }
    }
}
