package com.minhhai.thuctap.common;

public class AppConfig {

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // chứa đường dẫn đến thư viện jdbc
    public static final String URL_DATABASE = "jdbc:mysql://localhost:3306/dbthuctap?serverTimezone=UTC&useLegacyDatetimeCode=false"; // đường dẫn để kết nối đến schema
    public static final String USERNAME = "root";
    public static final String PASSWORD  = ""; // mật khẩu của mysql

}
