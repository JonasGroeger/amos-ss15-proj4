package de.fau.amos4.test.integration.helper.security;

import de.fau.amos4.model.ClientRole;
import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * Annotation that understands how to mock a user in a test. Internally it uses a custom WithSecurityContextFactory
 * ({@link WithMockCustomUserSecurityContextFactory}) that is used to populate and return a security context.
 */
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser
{
    /**
     * The E-Mail under which the user is known as.
     *
     * @return The email address of the user
     */
    String email();

    /**
     * The plaintext password of the user. The password will be stored as a hash in the
     * {@link WithMockCustomUserSecurityContextFactory} created.
     *
     * @return The plaintext password
     */
    String password();

    /**
     * @return the role the user has
     */
    ClientRole role();
}