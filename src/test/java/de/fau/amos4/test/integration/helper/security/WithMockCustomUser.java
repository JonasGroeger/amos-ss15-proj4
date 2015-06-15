/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
 * other purposes.
 * Copyright (C) 2015 Attila Bujaki, Werner Sembach, Jonas Gröger, Oswaldo
 *     Bejarano, Ardhi Sutadi, Nikitha Mohan, Benedikt Rauh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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