/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.woonsan.jackrabbit.boot;

import javax.jcr.Repository;

import org.apache.jackrabbit.core.RepositoryContext;
import org.apache.jackrabbit.server.remoting.davex.JcrRemotingServlet;
import org.apache.jackrabbit.servlet.jackrabbit.JackrabbitRepositoryServlet;
import org.apache.jackrabbit.servlet.jackrabbit.StatisticsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class JackrabbitBootApplication {

    private static Logger log = LoggerFactory.getLogger(JackrabbitBootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JackrabbitBootApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<JackrabbitRepositoryServlet> repositoryServlet() {
        final JackrabbitRepositoryServlet servlet = new JackrabbitRepositoryServlet();
        final ServletRegistrationBean<JackrabbitRepositoryServlet> regBean = new ServletRegistrationBean<>(servlet);

        regBean.setLoadOnStartup(1);
        regBean.addInitParameter("repository.home", "target/jackrabbit-boot-repository");
        regBean.addInitParameter("repository.config", "target/jackrabbit-boot-repository/repository.xml");

        return regBean;
    }

    @SuppressWarnings("serial")
    @Bean
    public ServletRegistrationBean<JcrRemotingServlet> jcrWebdavServerServlet() {
        final JcrRemotingServlet servlet = new JcrRemotingServlet() {
            @Override
            protected Repository getRepository() {
                Repository repository = null;
                final RepositoryContext repositoryContext = (RepositoryContext) getServletContext()
                        .getAttribute(RepositoryContext.class.getName());

                if (repositoryContext != null) {
                    repository = repositoryContext.getRepository();
                } else {
                    log.error("RepositoryContext not found.");
                }

                return repository;
            }
        };

        final ServletRegistrationBean<JcrRemotingServlet> regBean = new ServletRegistrationBean<>(servlet, "/server/*");
        return regBean;
    }

    @Bean
    public ServletRegistrationBean<StatisticsServlet> statisticsServlet() {
        final StatisticsServlet servlet = new StatisticsServlet();
        final ServletRegistrationBean<StatisticsServlet> regBean = new ServletRegistrationBean<>(servlet,
                "/statistics");
        return regBean;
    }
}
