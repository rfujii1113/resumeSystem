package com.stylesystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class MailTestController {

    //application.propatiesから送信元のメールアドレスを取得
    @Value("${spring.mail.username}") // プロパティから送信元アドレスを取得
    private String fromEmail;

    //JavaMailSender springで提供されているメールを送信するためのインターフェース
   @Autowired
    private JavaMailSender mailSender;

    @GetMapping("goMail") //クライアントがlocalhost/loginにアクセスしたときに呼ばれる
	public String showSendMailPage() {
		return "goMail"; // manageExcel.htmlを返す
	}

    @PostMapping("/sendMail")
    public String postMethodName(@RequestParam(name = "mailAD") String mailAD) {
        /*
          *メールを送るコード
          *SimpleMailMessage Springで提供されているメールを送るためのクラス
          *@Param mailAD viewから受け取った宛先のメールアドレス
        */
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAD); // 宛先メールアドレス
        message.setSubject("経歴書システムへようこそ"); // メールの件名
        message.setText("以下のページからログイン"+"http://localhost:8080/login"); // メールの本文
        message.setFrom(fromEmail); // 送信元のメールアドレス
        
        mailSender.send(message); // メール送信
        return  "redirect:goMail";
    }
    
}
