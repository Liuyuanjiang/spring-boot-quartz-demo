package com.liuyuanjiang.springbootquartzdemo.bean;

import lombok.Data;

import java.lang.reflect.Field;

@Data
public class DeclaredTest
{
    private String staffID;
    private String detail;
    private String[] alert = new String[100];
    private String[] priority;

    public String transferToLineStr() throws IllegalAccessException {
        StringBuilder lineStr = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        String val = new String();
        int fieldslength=fields.length;
        Object obj ;
        for(int i = 0 ; i <fieldslength ;i++){
            fields[i].setAccessible(true);
            if(fields[i].getType().getName().equals(java.lang.String.class.getName())){
                obj = fields[i].get(this);
                val = obj==null?"":obj.toString();
                lineStr.append((i==0?"\"":",\"")+val+"\"");
            }else if (fields[i].getType().getName().equals(java.lang.String[].class.getName())){
                Object[] objs = (Object[])fields[i].get(this);
                int objslength = objs.length;
                for (int j = 0; j < objslength ; j++){
                val = objs[j]==null?"":objs[j].toString();
                lineStr.append((i==0?"\"":",\"")+val+"\"");
                }

            }

        }
        return lineStr.toString();
    }
}
