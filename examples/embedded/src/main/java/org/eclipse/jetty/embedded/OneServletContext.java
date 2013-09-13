//
//  ========================================================================
//  Copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.embedded;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class OneServletContext
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Serve content from java.io.tmpdir
        ServletHolder holder = context.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class,"/tmp/*");
        String tmpDir = System.getProperty("java.io.tmpdir");
        holder.setInitParameter("resourceBase",tmpDir);
        holder.setInitParameter("pathInfoOnly","true");
        
        // A Dump Servlet
        context.addServlet(new ServletHolder(new DumpServlet()),"/*");

        server.start();
        server.join();
    }
}