/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.rpclit_134.server;

import javax.xml.ws.Holder;

/**
 * @author Vivek Pandey
 */
@javax.jws.HandlerChain(
    name="",
    file="handlers.xml"
)
@javax.jws.WebService(endpointInterface="fromwsdl.rpclit_134.server.HelloPortType")
public class TestEndpointImpl
        implements HelloPortType {

    public int echo3(String echo3Req) {
        return Integer.valueOf(echo3Req);
    }

}
