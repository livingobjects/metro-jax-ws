/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.absseiimpl.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


import javax.jws.WebService;

/**
 * @author JAX-RPC Development Team
 */
@WebService(targetNamespace="portypeNamespace")
public interface EchoIF extends Remote {
    public Bar echoBar(Bar bar) throws RemoteException;
    public String echoString(String str) throws RemoteException;
}

