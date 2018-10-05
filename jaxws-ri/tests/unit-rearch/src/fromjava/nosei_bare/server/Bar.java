/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei_bare.server;

public class Bar {
   private int age; 
   
   public Bar() {}

   public Bar(int age) {
       this.age = age;
   }

   public int getAge() {
       return age;
   }

   public void setAge(int age) {
       this.age = age;
   }
}
