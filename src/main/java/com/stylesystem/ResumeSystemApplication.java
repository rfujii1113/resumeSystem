package com.stylesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 経歴書システムのメインアプリケーションクラス。
 * Spring Bootアプリケーションのエントリーポイントです。
 */
@SpringBootApplication
public class ResumeSystemApplication {

    /**
     * アプリケーションのエントリーポイント。
     * Spring Bootアプリケーションを起動します。
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(ResumeSystemApplication.class, args);
    }

}
