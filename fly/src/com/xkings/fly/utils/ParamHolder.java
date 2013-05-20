package com.xkings.fly.utils;

import java.util.Map;

public class ParamHolder {

    private final Map<String, Param> params;

    public ParamHolder(String[] args) {
        params = args != null ? Param.getParams(args) : Param.getParams();
    }

    public Param getParam(String... args) {
        for (String arg : args) {
            Param param = params.get(arg);
            if (param != null) {
                return param;
            }
        }
        return null;
    }

}
