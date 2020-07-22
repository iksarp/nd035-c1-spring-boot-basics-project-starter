package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialsController {

    public static final String CREDENTIALS_ACTIVE_KEY = "credentialsActive";

    private static final String CREDENTIAL_SUCCESS_KEY = "credentialSuccess";
    private static final String CREDENTIAL_ERROR_KEY = "credentialError";

    @Autowired
    private CredentialsService credentialsService;

    @PostMapping("/credentials/add")
    String addCredentials(String credentialId, String url, String username, String password, RedirectAttributes redirectAttributes) {
        try {
            // TODO I really don't like how to detect if it should be added or updated.
            // TODO How should I manage the modal so that action name can be parametrized?
            if (credentialId.isEmpty()) {
                credentialsService.addCredential(url, username, password);
                redirectAttributes.addFlashAttribute(CREDENTIAL_SUCCESS_KEY, "Credential added.");
            } else {
                credentialsService.updateCredential(Integer.parseInt(credentialId), url, username, password);
                redirectAttributes.addFlashAttribute(CREDENTIAL_SUCCESS_KEY, "Credential updated.");
            }
        } catch (Exception e) {
            // TODO return more meaningful error not exposing potential code structure
            redirectAttributes.addFlashAttribute(CREDENTIAL_ERROR_KEY, e.getMessage());
        }
        redirectAttributes.addFlashAttribute(CREDENTIALS_ACTIVE_KEY, true);
        return "redirect:/home";
    }

    @PostMapping("/credentials/delete")
    String deleteCredential(String credentialId, RedirectAttributes redirectAttributes) {
        try {
            credentialsService.deleteCredential(Integer.parseInt(credentialId));
            redirectAttributes.addFlashAttribute(CREDENTIAL_SUCCESS_KEY, "Credential deleted.");
        } catch (Exception e) {
            // TODO return more meaningful error not exposing potential code structure
            redirectAttributes.addFlashAttribute(CREDENTIAL_ERROR_KEY, e.getMessage());
        }
        redirectAttributes.addFlashAttribute(CREDENTIALS_ACTIVE_KEY, true);
        return "redirect:/home";
    }
}
