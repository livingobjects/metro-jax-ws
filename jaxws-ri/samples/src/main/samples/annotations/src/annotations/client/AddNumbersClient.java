/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package annotations.client;

public class AddNumbersClient {
    public static void main(String[] args) {
        try {
            AddNumbers port = new AddNumbersImplService().getAddNumbersImplPort();
            int number1 = 10;
            int number2 = 20;

            System.out.printf("Invoking addNumbers(%d, %d)\n", number1, number2);
            int result = port.add(number1, number2);
            System.out.printf("The result of adding %d and %d is %d.\n\n", number1, number2, result);

            number1 = -10;
            System.out.printf("Invoking addNumbers(%d, %d)\n", number1, number2);
            result = port.add(number1, number2);
            System.out.printf("The result of adding %d and %d is %d.\n", number1, number2, result);

        } catch (AddNumbersException_Exception ex) {
                System.out.printf("Caught AddNumbersException_Exception: %s\n", ex.getFaultInfo().getFaultInfo());
        }
    }
}
