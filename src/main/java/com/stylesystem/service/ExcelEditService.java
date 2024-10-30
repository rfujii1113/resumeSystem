package com.stylesystem.service;

import org.springframework.stereotype.Service;

import com.stylesystem.model.Project;
import com.stylesystem.model.Users;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // finalで宣言したもののインスタンス自動生成
public class ExcelEditService {

	Users users = new Users();
	List<Project> projects = new ArrayList<>();
}
