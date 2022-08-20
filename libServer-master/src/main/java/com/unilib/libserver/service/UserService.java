package com.unilib.libserver.service;

import com.unilib.libserver.entity.ApiResponse;
import com.unilib.libserver.entity.Roles;
import com.unilib.libserver.entity.User;
import com.unilib.libserver.repo.RoleRepo;
import com.unilib.libserver.repo.UserData;
import com.unilib.libserver.repo.UsersRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private RoleRepo roleRepo;
    public ApiResponse<JSONObject> addUser(User user){
        ApiResponse<JSONObject> response= new ApiResponse<>();
        User dataExists = usersRepo.findByEmail(user.getEmail());
        if (dataExists==null){
            String pw_hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            Optional<Roles> role = roleRepo.findById(user.getRole().getId());
            user.setRole(role.get());
            user.setPassword(pw_hash);
            response.success =true;
            User resp=usersRepo.save(user);
            JSONObject userData=new JSONObject();
            userData.put("uid",resp.getUid());
            userData.put("name",resp.getName());
            userData.put("email",resp.getEmail());
            userData.put("user_type",resp.getRole().getId());
            response.data=userData;
        }else {

            Optional<Roles> role = roleRepo.findById(user.getRole().getId());
            dataExists.setRole(role.get());
            dataExists.setName(user.getName());
            dataExists.setEmail(user.getEmail());
            response.success =true;
            User resp= usersRepo.save(dataExists);
            JSONObject userData=new JSONObject();
            userData.put("uid",resp.getUid());
            userData.put("name",resp.getName());
            userData.put("email",resp.getEmail());
            userData.put("user_type",resp.getRole().getId());
            response.data=userData;
                    }
        return response;
    }

    public ApiResponse<JSONObject> userLogin(String email,String pass){
        ApiResponse<JSONObject> response= new ApiResponse<>();
        User data = usersRepo.findByEmail(email);
        if (data==null){
            response.success =false;
            response.data=null;
            response.errorMessage="Invalid Email Id!";
        }else {
            if (BCrypt.checkpw(pass,data.getPassword())){
                response.success =true;
                JSONObject userData=new JSONObject();
                userData.put("uid",data.getUid());
                userData.put("name",data.getName());
                userData.put("email",data.getEmail());
                userData.put("role",data.getRole());
                response.data=userData;
            }else {
                response.success =false;
                response.data=null;
                response.errorMessage="Invalid Password!";
            }
        }
        return response;
    }

    public ApiResponse<List<UserData>> getAllUsers(String id){
        ApiResponse<List<UserData>> response = new ApiResponse<>();
        List<UserData> data = usersRepo.findAllUsers(Integer.parseInt(id));
        if (!data.isEmpty()){
            System.out.println(data);
            response.success=true;
            response.data=data;
        }else {
            response.success=false;
            response.errorMessage="No Users Found!";
        }

        return response;
    }

    public ApiResponse<List<UserData>> changeUserStatus(String id){
        ApiResponse<List<UserData>> response = new ApiResponse<>();
        response.success=true;
        response.data=null;
        if (usersRepo.findById(Integer.parseInt(id)).get().getRole().getId()==4){
            usersRepo.deactivateUser(Integer.parseInt(id),3);
        }else {
            usersRepo.deactivateUser(Integer.parseInt(id),4);
        }
        return response;
    }

    public ApiResponse<String> changePassword(String currentPass,String newPass,int id){
        ApiResponse<String> response = new ApiResponse<>();
        Optional<User> data = usersRepo.findById(id);
        if (BCrypt.checkpw(currentPass,data.get().getPassword())){
            response.success=true;
            String pw_hash = BCrypt.hashpw(newPass, BCrypt.gensalt());
            data.get().setPassword(pw_hash);
            usersRepo.save(data.get());
            response.data="Password Successfully changed!";
        }else {
            response.success=false;
            response.data="Invalid Current Password";
        }
        return response;
    }

//    public ApiResponse<String> updateUserService(User user){
//
//    }


}
