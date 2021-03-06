/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.messagehandler.server;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;

//@javax.jws.HandlerChain(name="",file="handlers.xml")
@WebService(name="Hello", serviceName="HelloService", targetNamespace="urn:test")
public class HelloService_Impl {

    @WebMethod
    public int hello(@WebParam(name="x")int x) {
        System.out.println("HelloService_Impl received: " + x);
        return x;
    }

}
