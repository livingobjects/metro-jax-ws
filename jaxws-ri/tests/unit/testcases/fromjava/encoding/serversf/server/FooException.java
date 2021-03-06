/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.encoding.serversf.server;

import java.lang.Float;
import java.lang.Integer;
import java.lang.String;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Jitendra Kotamraju
 */
@XmlType(name = "FooException", namespace = "urn:test:types")
public class FooException {

    @XmlElement(name = "varString", namespace = "", type = String.class)
    protected String varString;
    @XmlElement(name = "varInt", namespace = "", type = Integer.class)
    protected int varInt;
    @XmlElement(name = "varFloat", namespace = "", type = Float.class)
    protected float varFloat;

    @XmlTransient
    public String getVarString() {
        return varString;
    }

    public void setVarString(String value) {
        this.varString = value;
    }

    @XmlTransient
    public int getVarInt() {
        return varInt;
    }

    public void setVarInt(int value) {
        this.varInt = value;
    }

    @XmlTransient
    public float getVarFloat() {
        return varFloat;
    }

    public void setVarFloat(float value) {
        this.varFloat = value;
    }

}
