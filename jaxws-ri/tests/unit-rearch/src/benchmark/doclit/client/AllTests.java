/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package benchmark.doclit.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author JAX-RPC RI Development Team
 */
public class AllTests extends TestCase {
    public AllTests(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(EchoBenchmark.class);
        suite.addTestSuite(EchoVoidBenchmark.class);
        suite.addTestSuite(EchoBooleanBenchmark.class);
        suite.addTestSuite(EchoStringBenchmark.class);
        suite.addTestSuite(EchoIntegerBenchmark.class);
        suite.addTestSuite(EchoFloatBenchmark.class);
        suite.addTestSuite(EchoComplexTypeBenchmark.class);
        suite.addTestSuite(EchoBase64Benchmark.class);
        suite.addTestSuite(EchoDateBenchmark.class);
        suite.addTestSuite(EchoDecimalBenchmark.class);
        suite.addTestSuite(EchoEnumBenchmark.class);
        suite.addTestSuite(EchoNestedComplexTypeBenchmark.class);
        suite.addTestSuite(EchoStringArrayBenchmark.class);
        suite.addTestSuite(EchoIntegerArrayBenchmark.class);
        suite.addTestSuite(EchoFloatArrayBenchmark.class);
        suite.addTestSuite(EchoComplexTypeArrayBenchmark.class);
        suite.addTestSuite(EchoHandlerBenchmark.class);
        return suite;
    }
}
