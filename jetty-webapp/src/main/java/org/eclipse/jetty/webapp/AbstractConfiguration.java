//
//  ========================================================================
//  Copyright (c) 1995-2016 Mort Bay Consulting Pty. Ltd.
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

package org.eclipse.jetty.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractConfiguration implements Configuration
{
    private final boolean _enabledByDefault;
    private final List<String> _after;
    private final List<String> _before;
    private final List<String> _system;
    private final List<String> _server;
    
    protected AbstractConfiguration()
    {
        this(false,(String[])null,(String[])null,null,null);
    }

    protected AbstractConfiguration(String[] before,String[] after)
    {
        this(false,before,after,null,null);
    }
    
    protected AbstractConfiguration(boolean enableByDefault,String[] before,String[] after)
    {
        this(enableByDefault,before,after,null,null);
    }
    
    /**
     * @param before Configurations that come before this configuration
     * @param after Configuration that come after this configuration
     * @param systemClasses
     * @param serverClasses
     */
    protected AbstractConfiguration(String[] before,String[] after,String[] systemClasses,String[] serverClasses)
    {
        this (false,before,after,systemClasses,serverClasses);
    }

    /**
     * @param enabledByDefault
     * @param before Configurations that come before this configuration
     * @param after Configuration that come after this configuration
     * @param systemClasses
     * @param serverClasses
     */
    protected AbstractConfiguration(
            boolean enableByDefault,
            Class<? extends Configuration>[] before,
            Class<? extends Configuration>[] after,
            String[] systemClasses,
            String[] serverClasses)
    {
        _enabledByDefault=enableByDefault;
        _after=Collections.unmodifiableList(after==null?Collections.emptyList():Arrays.asList(after).stream().map(Class::getName).collect(Collectors.toList()));
        _before=Collections.unmodifiableList(before==null?Collections.emptyList():Arrays.asList(before).stream().map(Class::getName).collect(Collectors.toList()));
        _system=new ArrayList<>(systemClasses==null?Collections.emptyList():Arrays.asList(systemClasses));
        _server=new ArrayList<>(serverClasses==null?Collections.emptyList():Arrays.asList(serverClasses));
    }
    
    /**
     * @param enabledByDefault
     * @param before Configurations that come before this configuration
     * @param after Configuration that come after this configuration
     * @param systemClasses
     * @param serverClasses
     */
    protected AbstractConfiguration(boolean enableByDefault,String[] before,String[] after,String[] systemClasses,String[] serverClasses)
    {
        _enabledByDefault=enableByDefault;
        _after=Collections.unmodifiableList(after==null?Collections.emptyList():Arrays.asList(after));
        _before=Collections.unmodifiableList(before==null?Collections.emptyList():Arrays.asList(before));
        _system=new ArrayList<>(systemClasses==null?Collections.emptyList():Arrays.asList(systemClasses));
        _server=new ArrayList<>(serverClasses==null?Collections.emptyList():Arrays.asList(serverClasses));
    }
    
    protected void addSystemClass(String... systemClass)
    {
        for (String s:systemClass)
            _system.add(s);
    }
    
    protected void addServerClass(String... serverClass)
    {
        for (String s:serverClass)
            _system.add(s);
    }
    
    @Override
    public List<String> getConfigurationsAfterThis()
    {
        return _after;
    }

    @Override
    public List<String> getConfigurationsBeforeThis()
    {
        return _before;
    }

    @Override
    public List<String> getSystemClasses()
    {
        return _system;
    }
    
    @Override
    public List<String> getServerClasses()
    {
        return _server;
    }
    
    public void preConfigure(WebAppContext context) throws Exception
    {
    }

    public boolean configure(WebAppContext context) throws Exception
    {
        return true;
    }

    public void postConfigure(WebAppContext context) throws Exception
    {
    }

    public void deconfigure(WebAppContext context) throws Exception
    {
    }

    public void destroy(WebAppContext context) throws Exception
    {
    }

    public void cloneConfigure(WebAppContext template, WebAppContext context) throws Exception
    {
    }

    @Override
    public boolean isAddedByDefault() 
    { 
        return _enabledByDefault; 
    }
}
