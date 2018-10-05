/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server.sei;

import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.message.ByteArrayAttachment;
import com.sun.xml.ws.message.DataHandlerAttachment;
import com.sun.xml.ws.message.JAXBAttachment;
import com.sun.xml.ws.model.ParameterImpl;
import com.sun.xml.ws.spi.db.XMLBridge;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;
import javax.activation.DataHandler;
import javax.xml.transform.Source;
import javax.xml.ws.WebServiceException;
import com.sun.xml.ws.api.message.Attachment;

/**
 * Puts a non-payload message parameter to {@link Message}.
 *
 * <p>
 * Instance of this class is used to handle header parameters and attachment parameters.
 * They add things to {@link Message}.
 *
 * @author Kohsuke Kawaguchi
 * @author Jitendra Kotamraju
 */
public abstract class MessageFiller {

    /**
     * The index of the method invocation parameters that this object looks for.
     */
    protected final int methodPos;

    protected MessageFiller( int methodPos) {
        this.methodPos = methodPos;
    }

    /**
     * Moves an argument of a method invocation into a {@link Message}.
     */
    public abstract void fillIn(Object[] methodArgs, Object returnValue, Message msg);
    
    /**
     * Adds a parameter as an MIME attachment to {@link Message}.
     */
    public static abstract class AttachmentFiller extends MessageFiller {
        protected final ParameterImpl param;
        protected final ValueGetter getter;
        protected final String mimeType;
        private final String contentIdPart;
        
        protected AttachmentFiller(ParameterImpl param, ValueGetter getter) {
            super(param.getIndex());
            this.param = param;
            this.getter = getter;
            mimeType = param.getBinding().getMimeType();
            try {
                contentIdPart = URLEncoder.encode(param.getPartName(), "UTF-8")+'=';
            } catch (UnsupportedEncodingException e) {
                throw new WebServiceException(e);
            }
        }
        
        /**
         * Creates an MessageFiller based on the parameter type
         *
         * @param param
         *      runtime Parameter that abstracts the annotated java parameter
         * @param getter
         *      Gets a value from an object that represents a parameter passed
         *      as a method argument.
         */
        public static MessageFiller createAttachmentFiller(ParameterImpl param, ValueGetter getter) {
            Class type = (Class)param.getTypeInfo().type;
            if (DataHandler.class.isAssignableFrom(type) || Source.class.isAssignableFrom(type)) {
                return new DataHandlerFiller(param, getter);
            } else if (byte[].class==type) {
                return new ByteArrayFiller(param, getter);
            } else if(isXMLMimeType(param.getBinding().getMimeType())) {
                return new JAXBFiller(param, getter);
            } else {
                return new DataHandlerFiller(param, getter);
            }
        }
        
        String getContentId() {
            return contentIdPart+UUID.randomUUID()+"@jaxws.sun.com";
        }
    }
    
    private static class ByteArrayFiller extends AttachmentFiller {
        protected ByteArrayFiller(ParameterImpl param, ValueGetter getter) {
            super(param, getter);
        }
        
        public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
            String contentId = getContentId();
            Object obj = (methodPos == -1) ? returnValue : getter.get(methodArgs[methodPos]);
            if (obj != null) {
                Attachment att = new ByteArrayAttachment(contentId,(byte[])obj,mimeType);
                msg.getAttachments().add(att);
            }
        }
    }
    
    private static class DataHandlerFiller extends AttachmentFiller {
        protected DataHandlerFiller(ParameterImpl param, ValueGetter getter) {
            super(param, getter);
        }
        
        public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
            String contentId = getContentId();
            Object obj = (methodPos == -1) ? returnValue : getter.get(methodArgs[methodPos]);
            DataHandler dh = (obj instanceof DataHandler) ? (DataHandler)obj : new DataHandler(obj,mimeType);
            Attachment att = new DataHandlerAttachment(contentId, dh);
            msg.getAttachments().add(att);
        }
    }
    
    private static class JAXBFiller extends AttachmentFiller {
        protected JAXBFiller(ParameterImpl param, ValueGetter getter) {
            super(param, getter);
        }
        
        public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
            String contentId = getContentId();
            Object obj = (methodPos == -1) ? returnValue : getter.get(methodArgs[methodPos]);
            Attachment att = new JAXBAttachment(contentId, obj, param.getXMLBridge(), mimeType);
            msg.getAttachments().add(att);
        }
    }

    /**
     * Adds a parameter as an header.
     */
    public static final class Header extends MessageFiller {
        private final XMLBridge bridge;
        private final ValueGetter getter;

        public Header(int methodPos, XMLBridge bridge, ValueGetter getter) {
            super(methodPos);
            this.bridge = bridge;
            this.getter = getter;
        }

        public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
            Object value = (methodPos == -1) ? returnValue : getter.get(methodArgs[methodPos]);
            msg.getHeaders().add(Headers.create(bridge,value));
        }
    }
    
    private static boolean isXMLMimeType(String mimeType){
        return mimeType.equals("text/xml") || mimeType.equals("application/xml");
    }
}
