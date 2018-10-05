/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.wsdl_hello_lit.client;

import junit.framework.*;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class AllTests extends TestCase {

    public AllTests(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(HelloLiteralTest.class);
        //not working below-kw till fixed
        //suite.addTestSuite(DispatchHelloLiteralTest.class);
        //suite.addTestSuite(ClientAPITest.class);
        return suite;
    }
}
