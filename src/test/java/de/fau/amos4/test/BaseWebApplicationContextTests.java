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
package de.fau.amos4.test;

import de.fau.amos4.Application;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.ClientService;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.service.TranslatorService;
import de.fau.amos4.web.EmployeeFormController;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ComponentScan(basePackages = "de.fau.amos4")
@EnableAutoConfiguration
@Profile("test")
public abstract class BaseWebApplicationContextTests {
    // this servlet is going to be instantiated by ourselves
    // so that we can test the servlet behaviour w/o actual web container
    // deployment
    protected DispatcherServlet servlet;

    // we need to get at the context already loaded via the @ContextConfiguration annotation.
    @Resource
    protected ApplicationContext applicationContext;

    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;
    protected EmployeeFormController controller;

    @Autowired
    protected EmployeeRepository employeeRepository;

    @Autowired
    protected ClientRepository clientRepository;
    
    @Autowired
    protected ClientService clientService;

    @Autowired
    protected TranslatorService translatorService;

    @Before
    public void initDispatcherServlet() throws Exception{
        servlet = new DispatcherServlet() {

            @Override
            protected WebApplicationContext createWebApplicationContext(
                    WebApplicationContext parent) throws BeansException {

                GenericWebApplicationContext gwac = new GenericWebApplicationContext();
                gwac.setParent(applicationContext);
                gwac.refresh();
                return gwac;
            }
        };

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        
        MockServletContext servletContext = new MockServletContext("src/main/java/amos4/model", new FileSystemResourceLoader());

        servlet.init(new MockServletConfig(servletContext));
    }
}
