package com.versionone.demo1server.object.vo;

import com.versionone.demo1server.utils.Base64Util;

public class ResultObject {

    private byte[] img ;
    private byte[] depthImg;
    private byte[] biredImg;
    private boolean light ;  //false == green , true == red
    private boolean people;

    public ResultObject(String[] line){
        img = Base64Util.getImgBase64ToImgFile(line[0]);
        depthImg = Base64Util.getImgBase64ToImgFile(line[1]);
        biredImg = Base64Util.getImgBase64ToImgFile(line[2]);
        light = "True".equals(line[3]);
        people = "True".equals(line[4]);
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public boolean isPeople() {
        return people;
    }

    public void setPeople(boolean people) {
        this.people = people;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public byte[] getDepthImg() {
        return depthImg;
    }

    public void setDepthImg(byte[] depthImg) {
        this.depthImg = depthImg;
    }

    public byte[] getBiredImg() {
        return biredImg;
    }

    public void setBiredImg(byte[] biredImg) {
        this.biredImg = biredImg;
    }
}
