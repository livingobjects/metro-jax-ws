/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler_singlepipe.server;

import static fromwsdl.handler_singlepipe.common.TestConstants.*;
import fromwsdl.handler_singlepipe.common.HandlerTracker;

import java.util.List;


/**
 * Impl class for interface generated by wscompile -import.
 * This class will overwrite the impl class generated by wscompile.
 */
@javax.jws.WebService(endpointInterface="fromwsdl.handler_singlepipe.server.ReportService")
public class ReportService_Impl implements ReportService{

    public void setInstruction(String name, int x) {
        if (HandlerTracker.VERBOSE_HANDLERS) {
            System.out.println("report service setting action " + x +
                    " on handler " + name);
        }
        if (x < 200) {
            HandlerTracker.getServerInstance().setHandlerAction(name, x);
        } else {
            HandlerTracker.getServerInstance().setHandleFaultAction(name, x);
        }
    }

    public List<String> getReport(String reportName) {
        if (reportName.equals(REPORT_CALLED_HANDLERS)) {
            return HandlerTracker.getServerInstance().getCalledHandlers();
        }
        if (reportName.equals(REPORT_CLOSED_HANDLERS)) {
            return HandlerTracker.getServerInstance().getClosedHandlers();
        }
        if (reportName.equals(REPORT_DESTROYED_HANDLERS)) {
            return HandlerTracker.getServerInstance().getDestroyedHandlers();
        }
        System.err.println("ERROR: server didn't understand report type: " +
                reportName);
        throw new RuntimeException("invalid understand report type: " +
                reportName);
    }

    public void clearHandlerTracker() {
        if (HandlerTracker.VERBOSE_HANDLERS) {
            System.out.println("report service clearing tracker");
        }
        HandlerTracker.getServerInstance().clearAll();
    }

    public void clearCalledHandlers() {
        if (HandlerTracker.VERBOSE_HANDLERS) {
            System.out.println("report service clearing called handlers");
        }
        HandlerTracker.getServerInstance().clearCalledHandlers();
    }

    // util

    // todo: does List<String>.toArray make String[] or Object[]?
    private String [] toStringArray(List<String> list) {
        String [] s = new String[list.size()];
        for (int i=0; i< s.length; i++) {
            s[i] = list.get(i);
        }
        return s;
    }

}
