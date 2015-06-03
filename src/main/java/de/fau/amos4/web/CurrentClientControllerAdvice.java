package de.fau.amos4.web;

import de.fau.amos4.model.CurrentClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentClientControllerAdvice
{
    /**
     * Makes the current user available in all views with the name "currentClient".
     *
     * @param authentication
     * @return
     */
    @ModelAttribute("currentClient")
    public CurrentClient getCurrentUser(Authentication authentication)
    {
        return (authentication == null) ? null : (CurrentClient) authentication.getPrincipal();
    }
}
