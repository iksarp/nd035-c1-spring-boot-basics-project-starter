package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialsService {

    @Autowired
    private CredentialMapper mapper;

    @Autowired
    private EncryptionService encryptionService;

    public void addCredential(String url, String username, String password) throws Exception {
        if (url.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw new Exception("Url, username and password must be not empty");
        }
        String key = encryptionService.createNewKey();
        String encryptedPassword = encryptionService.encryptValue(password, key);
        if (mapper.insert(new Credential(-1, url, username, key, encryptedPassword)) < 0) {
            throw new Exception("Internal error: Could not add the credential");
        }
    }

    public void updateCredential(int credentialId, String url, String username, String password) throws Exception {
        if (url.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw new Exception("Url, username and password must be not empty");
        }

        Credential credential = mapper.getCredential(credentialId);
        if (credential == null) {
            throw new Exception("No such credential to update");
        }

        String encryptedPassword = encryptionService.encryptValue(password, credential.getKey());
        if (mapper.update(credentialId, url, username, encryptedPassword) < 0) {
            throw new Exception("Internal error: Could not update the credential");
        }
    }

    public void deleteCredential(int credentialId) throws Exception {
        if (mapper.getCredential(credentialId) == null) {
            throw new Exception("No such credential to delete");
        }
        if (mapper.delete(credentialId) < 0) {
            throw new Exception("Internal error: Could not delete the credential");
        }
    }

    public Credential getCredential(int credentialId) {
        return mapper.getCredential(credentialId);
    }

    public List<Credential> getCredentials() {
        return mapper.getCredentials();
    }
}
