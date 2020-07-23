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

    public void addCredential(String userId, String url, String username, String password) throws Exception {
        if (url.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw new Exception("Url, username and password must be not empty");
        }
        String key = encryptionService.createNewKey();
        String encryptedPassword = encryptionService.encryptValue(password, key);
        if (mapper.insert(new Credential(-1, url, username, key, encryptedPassword, userId)) < 0) {
            throw new Exception("Internal error: Could not add the credential");
        }
    }

    public void updateCredential(String userId, int credentialId, String url, String username, String password) throws Exception {
        if (url.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw new Exception("Url, username and password must be not empty");
        }

        Credential credential = getCredential(userId, credentialId);
        if (credential == null) {
            throw new Exception("No such credential to update");
        }

        String encryptedPassword = encryptionService.encryptValue(password, credential.getKey());
        if (mapper.update(userId, credentialId, url, username, encryptedPassword) < 0) {
            throw new Exception("Internal error: Could not update the credential");
        }
    }

    public void deleteCredential(String userId, int credentialId) throws Exception {
        if (getCredential(userId, credentialId) == null) {
            throw new Exception("No such credential to delete");
        }
        if (mapper.delete(userId, credentialId) < 0) {
            throw new Exception("Internal error: Could not delete the credential");
        }
    }

    public Credential getCredential(String userId, int credentialId) {
        return mapper.getCredential(userId, credentialId);
    }

    public List<Credential> getCredentials(String userId) {
        return mapper.getCredentials(userId);
    }
}
