package com.bzh.common.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by linzuk on 2017/11/3.
 */

public class SerializableMap implements Serializable {

    private Map<String, String> map;

    public SerializableMap(Map<String, String> map) {
        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
