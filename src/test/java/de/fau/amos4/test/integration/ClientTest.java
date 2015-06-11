/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
 * other purposes.
 * Copyright (C) 2015 Attila Bujaki, Werner Sembach, Jonas Gröger, Oswaldo
 * Bejarano, Ardhi Sutadi, Nikitha Mohan, Benedikt Rauh
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
package de.fau.amos4.test.integration;

import de.fau.amos4.test.BaseIntegrationTest;

import org.junit.Assert;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ClientTest extends BaseIntegrationTest
{
    @Test
    public void testThatMainPageIsLogin() throws Exception
    {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/login"));
    }

    @Test
    public void testThatMainPageHasForgotPassowrdLinkDE() throws Exception
    {
        String content = mockMvc.perform(get("/?lang=de"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assert.assertTrue("Forgot password caption not found. (DE)", content.contains("Passwort vergessen?"));
    }
    @Test
    public void testThatMainPageHasForgotPasswordLinkEN() throws Exception
    {
        String content = mockMvc.perform(get("/?lang=en"))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();
        Assert.assertTrue("Forgot password caption not found. (EN)", content.contains("Forgot password?"));
    }

    @Test
    public void testThatForgotPasswordPageHasNeededCaptionsEN() throws Exception
    {
        String content = mockMvc.perform(get("/client/forgotPassword?lang=en"))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

        Assert.assertTrue("New password caption not found. (EN)", content.contains("New password"));
        Assert.assertTrue("Forgot password caption not found. (EN)", content.contains("Forgot password?"));
    }
    @Test
    public void testThatForgotPasswordPageHasNeededCaptionsDE() throws Exception
    {
        String content = mockMvc.perform(get("/client/forgotPassword?lang=de"))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

        Assert.assertTrue("New password caption not found. (DE)", content.contains("Neues Passwort"));
        Assert.assertTrue("Forgot password caption not found. (DE)", content.contains("Passwort vergessen?"));
    }

    @Test
    public void testThatDashboardRedirectsToLoginIfUnauthenticated() throws Exception
    {
        mockMvc.perform(get("/client/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(createUrl("/")));
    }

    @Test
    public void testThatTheRegistrationPageIsAvailable() throws Exception
    {
        mockMvc.perform(get("/client/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/register"));
    }
}
