package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;


    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    @PostConstruct
    public void postConstruct(){

        System.out.println("Credential post consrcut created");

    }

    public Integer addOrEditCredential(Credential credential) {
        if (credential.getCredentialId() == null){
            return this.credentialMapper.addCredential(credential);
        }
        else{
            return this.credentialMapper.updateCredential(credential);
        }
    }

    public Integer deleteCredential(Integer credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }

    public List<Credential> getCredentials(Integer userId){
        return credentialMapper.getAllCredential(userId);
    }

    public Credential getCredentialById(Integer credential){ return credentialMapper.getCredential(credential); }

    public String getKeyByCredentialId(Integer credential){ return credentialMapper.credentialKey(credential); }

    public int getCredentialByURLandUsername(String url, String username){
        return credentialMapper.CredntialUrlAndUserName(url, username);
    }



}
