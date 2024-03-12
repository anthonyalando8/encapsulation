package com.encapsulation;

public class SystemModel {
    String osName;
    public SystemModel(){
        this.osName = System.getProperty("os.name");
    }

    public String getOsName() {
        return osName;
    }
}
