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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class EmployeeTest extends BaseIntegrationTest
{
    @Test
    public void testThatTokenPageIsWorking() throws Exception
    {
        mockMvc.perform(get("/employee/token"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/token"));
    }

    @Test
    @WithUserDetails("datev@example.com")
    public void testEmployeeAsLodasFileDownload() throws Exception
    {
        final MockHttpServletResponse response = mockMvc.perform(get("/employee/download/text").param("id", "2"))
                                     .andExpect(status().isOk())
                                     .andExpect(content().contentType(MediaType.TEXT_PLAIN)).andReturn().getResponse();

        final String contentDisp = response.getHeader("Content-Disposition");
        Assert.assertNotNull("Content-Disposition is null", contentDisp);
        Assert.assertTrue("Content-Disposition .", contentDisp.contains("attachment;filename"));
    }
}
