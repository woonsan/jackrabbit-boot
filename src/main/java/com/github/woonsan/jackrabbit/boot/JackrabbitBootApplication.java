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

import java.io.File;

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

    private static final String PROP_REPOSITORY_HOME = "repository.home";
    private static final String PROP_REPOSITORY_CONFIG = "repository.config";

    public static void main(String[] args) {
        SpringApplication.run(JackrabbitBootApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<JackrabbitRepositoryServlet> repositoryServlet() {
        final JackrabbitRepositoryServlet servlet = new JackrabbitRepositoryServlet();
        final ServletRegistrationBean<JackrabbitRepositoryServlet> regBean = new ServletRegistrationBean<>(servlet);
        regBean.setLoadOnStartup(1);

        // If system property set (e.g, -Drepository.home=jackrabbit-repository),
        // then ensure the directory to be created and set the absolute path to the init param.
        final String repositoryHome = System.getProperty(PROP_REPOSITORY_HOME, "jackrabbit-repository");
        if (repositoryHome != null && repositoryHome.length() != 0) {
            final File dir = new File(repositoryHome);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
            regBean.addInitParameter(PROP_REPOSITORY_HOME, dir.getAbsolutePath());
        }

        // If system property set (e.g, -Drepository.config=repository.xml), add it to init params.
        final String repositoryConfig = System.getProperty(PROP_REPOSITORY_CONFIG);
        if (repositoryConfig != null && repositoryConfig.length() != 0) {
            regBean.addInitParameter(PROP_REPOSITORY_CONFIG, repositoryConfig);
        }

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
