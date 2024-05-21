package com.theeduconnect.exeeduconnectbe.features.loginGG.dtos;

import java.util.ArrayList;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Root {
    public String at_hash;
    public String sub;
    public boolean email_verified;
    public String iss;
    public String given_name;
    public String nonce;
    public String picture;
    public ArrayList<String> aud;
    public String azp;
    public String name;
    public Date exp;
    public Date iat;
    public String email;
}
