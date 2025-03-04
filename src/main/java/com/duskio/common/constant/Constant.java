package com.duskio.common.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;

public class Constant {
    
    public static final String PUBLIC_PATH = "/static/public/";
    public static final String ADMIN_PATH = "/admin/";
    public static final String API_PATH = "/api/v1/";
    public static final String ADMIN_API_PATH = API_PATH + "admin/";
    public static final String PUBLIC_API_PATH = API_PATH + "static/public/";
    
    public static final String ROLE_ADMIN = "ADMIN";

    public static final Charset ENCODING = StandardCharsets.UTF_8;
    
    public static final String SEPARATOR = FileSystems.getDefault().getSeparator();
    public static final String RESOURCES_PATH = "src/main/resources/" + SEPARATOR;
    public static final String MISC_RESOURCES_PATH = RESOURCES_PATH + "misc" + SEPARATOR;
}
