package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class User extends  BaseModel {

    private String password;
    private Auth authorization;

    public User(String id, String pass, Auth  auth) {
        setId(id);
        this.password = pass;
        this.authorization = auth;
    }
}

