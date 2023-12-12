package com.example.usersmicroservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;

import com.example.usersmicroservice.entities.Role;
import com.example.usersmicroservice.entities.User;
import com.example.usersmicroservice.repos.RoleRepository;
import com.example.usersmicroservice.repos.UserRepository;


@Transactional
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRep;
    @Autowired
    RoleRepository roleRep;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailAddress;
    @Override
    public User saveUser(User user) {
        String verificationCode = UUID.randomUUID().toString();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Activation Code");
        simpleMailMessage.setText("Le code d activation pour activer le compte pour le plateforme de haythem djebbi: " + verificationCode);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setFrom(mailAddress);
        javaMailSender.send(simpleMailMessage);

        user.setVerificationCode(verificationCode);
        List<Role>roles=new ArrayList<>();
        Role r=roleRep.findByRole("USER");
        roles.add(r);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        return userRep.save(user);
    }

    @Override
    public User activateUser(String username , String code)
    {
        User user=userRep.findByUsername(username);
        if(user!=null)
        {
            if(user.getEnabled()==null || user.getEnabled()==false)
            {
                if(user.getVerificationCode().equals(code)==true)
                {
                    user.setEnabled(true);
                    user.setVerificationCode(null);
                    userRep.save(user);
                    return user;
                }
                else
                {
                    System.out.println(user.getVerificationCode());
                    return user;
                }
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }


    @Override
    public List<User> findAllUsers() {
        return userRep.findAll();
    }

    @Override
    public void deleteUser(long id) {
        userRep.deleteById(id);
    }

    @Override
    public User removeRoleFromUser(long id, Role r) {
        User user=userRep.findUserById(id);
        List<Role> listOfrole=user.getRoles();

        listOfrole.remove(r);
        userRep.save(user);
        return user;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRep.findAll();
    }

    @Override
    public Role findRoleById(Long id) {
        return roleRep.findRoleById(id);
    }

    @Override
    public User findUserById(Long id) {
        return userRep.findById(id).get();
    }

    @Override
    public Role addRole(Role role) {
        return roleRep.save(role);
    }

    @Override
    public User addRoleToUser(long id, Role r) {
        User usr = userRep.findUserById(id);

        List<Role> roles = usr.getRoles();
        roles.add(r);

        usr.setRoles(roles);

        return userRep.save(usr);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRep.findByUsername(username);
    }
}