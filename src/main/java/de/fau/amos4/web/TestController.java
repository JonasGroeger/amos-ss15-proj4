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
package de.fau.amos4.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{

    @Autowired
    public TestController()
    {
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/testA")
    public String messageAdmin()
    {
        return "You are ADMIN.";
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @RequestMapping("/testC")
    public String messageClient()
    {
        return "You are CLIENT.";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @RequestMapping("/testE")
    public String messageEmployee()
    {
        return "You are EMPLOYEE.";
    }
}
