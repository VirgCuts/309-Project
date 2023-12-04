package com.example.sumon.androidvolley;public class Lobby {
    private String name;
    private int currentUserCount;
    private int maxUserCount;
    private boolean isGameInProgress;

    public Lobby(String name, int currentUserCount, int maxUserCount, boolean isGameInProgress) {
        this.name = name;
        this.currentUserCount = currentUserCount;
        this.maxUserCount = maxUserCount;
        this.isGameInProgress = isGameInProgress;
    }

    public String getName() {
        return name;
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
