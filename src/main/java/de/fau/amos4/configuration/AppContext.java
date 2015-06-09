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
package de.fau.amos4.configuration;

import org.springframework.context.ApplicationContext;
/*
 * This class is needed so that the drop down classes Disabled, Sex, MaritalStatus...have access to the locale.
 * 
 */
public class AppContext { 

    private static ApplicationContext ctx; 

    /**
     * Injected from the class "ApplicationContextProvider" which is automatically
     * loaded during Spring-Initialization.
     */ 

    public static void setApplicationContext(ApplicationContext applicationContext) { 
        ctx = applicationContext; 
    } 


    /**
     * Get access to the Spring ApplicationContext from everywhere in your Application
     *
     * @return
     */ 

    public static ApplicationContext getApplicationContext() { 
        return ctx; 
    } 
}
