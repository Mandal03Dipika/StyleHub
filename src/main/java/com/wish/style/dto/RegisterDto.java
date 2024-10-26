package com.wish.style.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
	private String username;
	private String email;
	private String phone;
	private String address;
//	private int role_id;
	private String name;
	private String password;
	private Date created_at;
}
